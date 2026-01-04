package CONTROLLER;

import DAO.HangThanhVienDAO;
import VIEW.HangThanhVienView;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class HangThanhVienController {
    private HangThanhVienView view;
    private HangThanhVienDAO dao;
    
    // Định dạng tiền tệ (Ví dụ: 10.000.000 VNĐ)
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public HangThanhVienController(HangThanhVienView view) {
        this.view = view;
        this.dao = new HangThanhVienDAO();
        
        // 1. Tải dữ liệu lên bảng ngay khi mở
        loadData();
        
        // 2. Gắn sự kiện cho nút "Kiểm tra" ở phần Tra Cứu
        view.btnCheck.addActionListener(e -> checkKhachHang());
        
        // 3. Gắn sự kiện cho các nút "Hủy Hạng" (Xóa)
        // Lưu ý: Các bảng tblBac, tblVang, tblKC đã được khai báo public bên View
        view.btnXoaBac.addActionListener(e -> xuLyXoaThanhVien(view.tblBac, "Bạc"));
        view.btnXoaVang.addActionListener(e -> xuLyXoaThanhVien(view.tblVang, "Vàng"));
        view.btnXoaKC.addActionListener(e -> xuLyXoaThanhVien(view.tblKC, "VIP"));
    }
    
    // --- HÀM LOGIC XÓA THÀNH VIÊN (RESET HẠNG) ---
    private void xuLyXoaThanhVien(JTable table, String tenHang) {
        // B1: Kiểm tra xem người dùng có chọn dòng nào trên bảng không
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn Khách hàng trong bảng " + tenHang + " để hủy hạng!");
            return;
        }
        
        // B2: Lấy dữ liệu từ dòng đã chọn
        // Cột 0 là Mã KH, Cột 1 là Tên KH (Do ta đã setup bên View)
        String maKH = table.getValueAt(selectedRow, 0).toString();
        String tenKH = table.getValueAt(selectedRow, 1).toString();
        
        // B3: Hiện hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc chắn muốn hủy hạng của khách: " + tenKH + " (" + maKH + ")?\n" +
                "Lưu ý: Hành động này sẽ XÓA LỊCH SỬ CHI TIÊU của khách để đưa tổng tiền về 0.",
                "Xác nhận hủy hạng", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
        if (confirm == JOptionPane.YES_OPTION) {
            // B4: Gọi DAO để xóa dữ liệu trong DB
            if (dao.resetHangThanhVien(maKH)) {
                JOptionPane.showMessageDialog(view, "Đã hủy hạng thành công! Khách hàng đã trở về hạng Thường.");
                
                // B5: Quan trọng - Load lại dữ liệu để cập nhật giao diện
                loadData(); 
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi: Không thể hủy hạng (Vui lòng kiểm tra kết nối CSDL).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- HÀM TẢI DỮ LIỆU TỪ DB LÊN VIEW ---
    private void loadData() {
        System.out.println("DEBUG: Đang tải dữ liệu hạng thành viên...");
        Vector<Vector<Object>> listData = dao.getKhachHangVaTongTien();
        
        // Reset toàn bộ bảng và ComboBox về trạng thái rỗng
        view.modelBac.setRowCount(0);
        view.modelVang.setRowCount(0);
        view.modelKimCuong.setRowCount(0);
        view.cboKhachHang.removeAllItems();

        for (Vector<Object> rowData : listData) {
            String ma = rowData.get(0).toString();
            String ten = rowData.get(1).toString();
            double tongTien = 0;
            
            // Xử lý an toàn khi chuyển đổi số tiền (tránh lỗi Null hoặc chuỗi rỗng)
            try {
                if (rowData.get(2) != null) {
                    tongTien = Double.parseDouble(rowData.get(2).toString());
                }
            } catch (Exception e) {
                System.out.println("Lỗi parse tiền của KH " + ma + ": " + e.getMessage());
                tongTien = 0;
            }
            
            // 1. Thêm vào ComboBox Tra Cứu (Format: Mã - Tên (Tiền))
            String itemCombo = ma + " - " + ten + " (" + df.format(tongTien) + ")";
            view.cboKhachHang.addItem(itemCombo);

            // 2. Tạo dòng dữ liệu để thêm vào bảng (Chỉ gồm Mã và Tên)
            Vector<Object> rowDisplay = new Vector<>();
            rowDisplay.add(ma);
            rowDisplay.add(ten);

            // 3. Logic phân loại hạng dựa trên tiền
            if (tongTien >= 30000000) {
                view.modelKimCuong.addRow(rowDisplay); // > 30 triệu -> VIP
            } else if (tongTien >= 10000000) {
                view.modelVang.addRow(rowDisplay);     // > 10 triệu -> Vàng
            } else if (tongTien >= 3000000) {
                view.modelBac.addRow(rowDisplay);      // > 3 triệu -> Bạc
            }
            // Dưới 3 triệu không thêm vào bảng nào (Hạng Thường)
        }
    }

    // --- HÀM KIỂM TRA NHANH TRÊN COMBOBOX ---
    private void checkKhachHang() {
        try {
            String selected = (String) view.cboKhachHang.getSelectedItem();
            
            if (selected == null || selected.isEmpty()) {
                view.lblTongChiTieuValue.setText("Chưa chọn khách!");
                return;
            }
            
            // Logic: Cắt lấy chuỗi tiền nằm trong dấu ngoặc đơn cuối cùng (...)
            int startIndex = selected.lastIndexOf("(") + 1;
            int endIndex = selected.lastIndexOf(")");
            
            if (startIndex > 0 && endIndex > startIndex) {
                String tien = selected.substring(startIndex, endIndex);
                view.lblTongChiTieuValue.setText(tien);
            } else {
                view.lblTongChiTieuValue.setText("Lỗi định dạng");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            view.lblTongChiTieuValue.setText("Lỗi");
        }
    }
}