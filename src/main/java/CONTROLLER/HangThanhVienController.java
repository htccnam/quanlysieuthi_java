package CONTROLLER;

import DAO.HangThanhVienDAO;
import VIEW.DialogThemThanhVien;
import VIEW.HangThanhVienView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class HangThanhVienController {
    private HangThanhVienView view;
    private HangThanhVienDAO dao;
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    // Danh sách chờ (Lưu những khách đủ tiền nhưng đang bị ẩn - Status = 0)
    // Dùng để đổ dữ liệu vào Dialog khi bấm nút Thêm
    private List<Vector<Object>> listChoBac = new ArrayList<>();
    private List<Vector<Object>> listChoVang = new ArrayList<>();
    private List<Vector<Object>> listChoKC = new ArrayList<>();

    public HangThanhVienController(HangThanhVienView view) {
        this.view = view;
        this.dao = new HangThanhVienDAO();
        
        // 1. Tải dữ liệu ngay khi mở
        loadData();
        
        // 2. Sự kiện Kiểm tra nhanh (ComboBox)
        view.btnCheck.addActionListener(e -> checkKhachHang());
        
        // 3. Sự kiện XÓA (Ẩn khỏi bảng -> Chuyển về danh sách chờ)
        view.btnXoaBac.addActionListener(e -> xuLyXoa(view.tblBac, "Bạc"));
        view.btnXoaVang.addActionListener(e -> xuLyXoa(view.tblVang, "Vàng"));
        view.btnXoaKC.addActionListener(e -> xuLyXoa(view.tblKC, "VIP"));
        
        // 4. Sự kiện THÊM (Mở Dialog chọn từ danh sách chờ -> Hiện lên bảng)
        view.btnThemBac.addActionListener(e -> xuLyThem("Bạc", listChoBac));
        view.btnThemVang.addActionListener(e -> xuLyThem("Vàng", listChoVang));
        view.btnThemKC.addActionListener(e -> xuLyThem("VIP", listChoKC));
    }

    // --- HÀM TẢI DỮ LIỆU & PHÂN LOẠI ---
    private void loadData() {
        System.out.println("DEBUG: Đang tải dữ liệu...");
        Vector<Vector<Object>> listData = dao.getKhachHangVaTongTien();
        
        // Reset toàn bộ bảng và danh sách chờ
        view.modelBac.setRowCount(0);
        view.modelVang.setRowCount(0);
        view.modelKimCuong.setRowCount(0);
        view.cboKhachHang.removeAllItems();
        listChoBac.clear();
        listChoVang.clear();
        listChoKC.clear();

        for (Vector<Object> row : listData) {
            String ma = row.get(0).toString();
            String ten = row.get(1).toString();
            int status = Integer.parseInt(row.get(2).toString()); // 0 hoặc 1
            double tien = Double.parseDouble(row.get(3).toString());

            // Add vào ComboBox để tra cứu
            view.cboKhachHang.addItem(ma + " - " + ten + " (" + df.format(tien) + ")");

            // Chuẩn bị dòng dữ liệu hiển thị (Mã, Tên)
            Vector<Object> rowDisplay = new Vector<>();
            rowDisplay.add(ma);
            rowDisplay.add(ten);
            
            // Chuẩn bị dòng dữ liệu chờ (Mã, Tên, Tiền - để hiện trong Dialog)
            Vector<Object> rowWaiting = new Vector<>();
            rowWaiting.add(ma);
            rowWaiting.add(ten);
            rowWaiting.add(df.format(tien));

            // --- LOGIC PHÂN LOẠI QUAN TRỌNG ---
            // 1. Kiểm tra Tiền trước để xác định Hạng
            // 2. Kiểm tra Status: Nếu 1 -> Hiện lên bảng, Nếu 0 -> Vào list chờ
            
            if (tien >= 3000000) { // Hạng VIP
                if (status == 1) view.modelKimCuong.addRow(rowDisplay);
                else listChoKC.add(rowWaiting);
            } 
            else if (tien >= 1000000) { // Hạng Vàng
                if (status == 1) view.modelVang.addRow(rowDisplay);
                else listChoVang.add(rowWaiting);
            } 
            else if (tien >= 500000) { // Hạng Bạc
                if (status == 1) view.modelBac.addRow(rowDisplay);
                else listChoBac.add(rowWaiting);
            }
        }
    }

    // --- HÀM XỬ LÝ XÓA (Cập nhật Status = 0) ---
    private void xuLyXoa(JTable table, String tenHang) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng cần xóa khỏi hạng " + tenHang + "!");
            return;
        }
        
        String maKH = table.getValueAt(selectedRow, 0).toString();
        String tenKH = table.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(view, 
            "Bạn có chắc muốn xóa khách " + tenKH + " (" + maKH + ") khỏi danh sách " + tenHang + "?\n" +
            "Lưu ý: Dữ liệu chi tiêu vẫn được giữ nguyên, khách chỉ bị ẩn khỏi bảng.",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.updateTrangThaiHang(maKH, 0)) { // Set về 0 (Ẩn)
                JOptionPane.showMessageDialog(view, "Đã xóa thành công! Khách hàng đã được chuyển vào danh sách chờ.");
                loadData(); // Load lại để cập nhật giao diện
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi cập nhật Database!");
            }
        }
    }

    // --- HÀM XỬ LÝ THÊM (Cập nhật Status = 1) ---
    private void xuLyThem(String tenHang, List<Vector<Object>> listCho) {
        if (listCho.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Hiện không có khách hàng nào đủ điều kiện chờ thêm vào hạng " + tenHang + ".\n(Hoặc tất cả đã có trong danh sách rồi)");
            return;
        }

        // Tìm Frame cha để hiển thị Dialog
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        
        // Khởi tạo Dialog chọn khách
        DialogThemThanhVien dialog = new DialogThemThanhVien(parentFrame, "Thêm khách vào hạng " + tenHang);
        
        // Đổ dữ liệu từ danh sách chờ vào Dialog
        for (Vector<Object> v : listCho) {
            dialog.addCandidate(v.get(0).toString(), v.get(1).toString(), v.get(2).toString());
        }
        
        // Hiển thị Dialog và chờ người dùng thao tác
        dialog.showDialog(); 

        // Sau khi Dialog đóng lại, kiểm tra xem có chọn ai không
        String selectedMa = dialog.getSelectedMaKH();
        if (selectedMa != null) {
            if (dao.updateTrangThaiHang(selectedMa, 1)) { // Set lên 1 (Hiện)
                JOptionPane.showMessageDialog(view, "Đã thêm khách hàng vào hạng " + tenHang + " thành công!");
                loadData(); // Load lại bảng
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi cập nhật Database!");
            }
        }
    }

    // --- HÀM TRA CỨU NHANH ---
    private void checkKhachHang() {
        try {
            String selected = (String) view.cboKhachHang.getSelectedItem();
            if (selected == null || selected.isEmpty()) {
                view.lblTongChiTieuValue.setText("Chưa chọn khách!");
                return;
            }
            
            // Cắt chuỗi lấy tiền trong ngoặc (...)
            int startIndex = selected.lastIndexOf("(") + 1;
            int endIndex = selected.lastIndexOf(")");
            
            if (startIndex > 0 && endIndex > startIndex) {
                String tien = selected.substring(startIndex, endIndex);
                view.lblTongChiTieuValue.setText(tien);
            } else {
                view.lblTongChiTieuValue.setText("Lỗi định dạng");
            }
        } catch (Exception e) {
            view.lblTongChiTieuValue.setText("Lỗi");
        }
    }
}