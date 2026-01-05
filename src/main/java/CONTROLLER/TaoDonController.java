package CONTROLLER;

import DAO.DonHangDAO;
import MODEL.ChiTietDon;
import MODEL.DonHang;
import MODEL.SanPham;
import VIEW.TaoDonView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TaoDonController {
    private DonHangDAO dao;
    private TaoDonView view;

    public TaoDonController(TaoDonView view) {      
        this.view = view;
        this.dao = new DonHangDAO();
        
        // Khởi tạo dữ liệu
        view.setNVToComboBox(dao.getMaNV());
        view.setKHToComboBox(dao.getMaKH());
        view.setKMToComboBox(dao.getMaKM());
        view.loadDataTable(dao.getSP());
        
        // Gán sự kiện
        initEvents();
    }

    private void initEvents() {
        // 1. Sự kiện nút THÊM (Yêu cầu 1, 4, 5)
        view.getBtnThem().addActionListener(e -> themSanPham());

        // 2. Sự kiện nút XÓA (Yêu cầu 3)
        view.getBtnXoa().addActionListener(e -> xoaSanPham());

        // 3. Sự kiện TÌM KIẾM (Yêu cầu 2)
        view.getTxtTimKiem().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemSanPham();
            }
        });

        // 4. Sự kiện LƯU ĐƠN (Yêu cầu 6)
        view.getBtnLuu().addActionListener(e -> luuDonHang());
        
        view.getBtnLuu().addActionListener(e -> {
        if (view.getBtnLuu().getText().equals("Cập nhật")) {
            xuLyCapNhat();
        } else {
            luuDonHang(); // Hàm lưu mới cũ của bạn
        }
});
    }

    // --- LOGIC XỬ LÝ ---

    // Xử lý Yêu cầu 1 & 4: Thêm sang phải, trừ kho bên trái, tính thành tiền
    private void themSanPham() {
        int rowTrai = view.getTableSanPham().getSelectedRow();
        if (rowTrai == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm ở bảng bên trái!");
            return;
        }

        int slMua = (int) view.getSpinner().getValue();
        if (slMua <= 0) {
            JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0");
            return;
        }

        // Lấy dữ liệu từ bảng trái (Model cột: 0-Mã, 1-Tên, 2-Giá, 3-Tồn kho)
        // Lưu ý: Cần check lại thứ tự cột trong view của bạn. 
        // Theo code View bạn gửi: colSP = {"Mã", "Tên", "Giá", "Số lượng"} -> index 3 là Tồn kho
        DefaultTableModel modelTrai = view.getModelTableTrai();
        int slTon = Integer.parseInt(modelTrai.getValueAt(rowTrai, 3).toString());

        if (slMua > slTon) {
            JOptionPane.showMessageDialog(view, "Không đủ hàng trong kho (Còn: " + slTon + ")");
            return;
        }

        String maSP = modelTrai.getValueAt(rowTrai, 0).toString();
        String tenSP = modelTrai.getValueAt(rowTrai, 1).toString();
        double donGia = Double.parseDouble(modelTrai.getValueAt(rowTrai, 2).toString());
        double thanhTien = donGia * slMua; // Yêu cầu 4

        // Đổ sang bảng phải
        DefaultTableModel modelPhai = view.getModelTablePhai();
        // Kiểm tra xem sản phẩm đã có bên phải chưa để cộng dồn (Tuỳ chọn, ở đây mình thêm dòng mới cho đơn giản)
        modelPhai.addRow(new Object[]{maSP, tenSP, donGia, slMua, thanhTien});

        // Trừ tồn kho hiển thị bên bảng trái (Yêu cầu 1)
        modelTrai.setValueAt(slTon - slMua, rowTrai, 3);

        // Tính lại tổng tiền
        tinhTongTien();
    }

    // Xử lý Yêu cầu 3: Xóa dòng bên phải, tính lại tiền
    private void xoaSanPham() {
        int rowPhai = view.getTable().getSelectedRow();
        if (rowPhai == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm bên bảng phải để xóa!");
            return;
        }

        // (Nâng cao: Nếu muốn trả lại số lượng về bảng trái thì cần logic phức tạp hơn chút)
        // Ở đây xóa dòng khỏi bảng phải
        view.getModelTablePhai().removeRow(rowPhai);
        
        tinhTongTien();
    }

    // Xử lý Yêu cầu 5: Tính tổng tiền, tạm tính, giảm giá
    private void tinhTongTien() {
        DefaultTableModel modelPhai = view.getModelTablePhai();
        double tamTinh = 0;

        // Duyệt bảng phải cộng dồn cột Thành tiền (index 4)
        for (int i = 0; i < modelPhai.getRowCount(); i++) {
            tamTinh += Double.parseDouble(modelPhai.getValueAt(i, 4).toString());
        }

        double giamGia = 0; // Yêu cầu 5: Set giảm giá = 0
        // Nếu muốn lấy từ Label KM (ví dụ: "100,000 đ"), phải parse chuỗi. Ở đây mình set cứng = 0 theo yêu cầu.
        view.getLblKM().setText("Giảm giá: 0 đ");

        double tongTien = tamTinh - giamGia;

        // Hiển thị lên View (Format số đẹp)
        view.getLblTamTinh().setText(String.format("Tạm tính: %,.0f đ", tamTinh));
        view.getLblTongTien().setText(String.format("Tổng tiền: %,.0f đ", tongTien));
        
        // Lưu giá trị số thực vào biến tag (hoặc client property) nếu cần dùng lại để lưu DB mà không cần parse lại chữ "đ"
        view.getLblTongTien().putClientProperty("value", tongTien);
    }

    // Xử lý Yêu cầu 2: Tìm kiếm
    private void timKiemSanPham() {
        String tuKhoa = view.getTxtTimKiem().getText();
        ArrayList<SanPham> list = dao.timKiemSP(tuKhoa);
        view.loadDataTable(list);
    }

    // Xử lý Yêu cầu 6: Lưu đơn hàng hoàn thiện
    private void luuDonHang() {
    try {
        // 1. Kiểm tra dữ liệu đầu vào
        if (view.getModelTablePhai().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng thêm sản phẩm vào đơn hàng!");
            return;
        }

        // 2. Tạo đối tượng DonHang từ View (Khớp với Model của bạn)
        java.util.Date uDate = view.getNgayGD().getDate();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        
        // Lấy tổng tiền (đã tính ở hàm tinhTongTien)
        Object val = view.getLblTongTien().getClientProperty("value");
        double tongTien = (val != null) ? (double) val : 0;

        DonHang dh = new DonHang(
            view.getTxtMaDon().getText(),
            view.getCboKH().getSelectedItem().toString(),
            view.getCboNV().getSelectedItem().toString(),
            view.getCboMaKM().getSelectedItem().toString(),
            sDate,
            view.getCboBanHang().getSelectedItem().toString(),
            view.getCboThanhToan().getSelectedItem().toString(),
            tongTien
        );

        // 3. Thực hiện lưu vào Database
        if (dao.insert(dh)) {
            DefaultTableModel modelPhai = view.getModelTablePhai();
            boolean checkCT = true;

            for (int i = 0; i < modelPhai.getRowCount(); i++) {
                // Tạo đối tượng ChiTietDon từ mỗi dòng của bảng bên phải
                ChiTietDon ctd = new ChiTietDon(
                    dh.getMaDonHang(),                          // maDonHang
                    modelPhai.getValueAt(i, 0).toString(),      // maSanPham
                    modelPhai.getValueAt(i, 1).toString(),      // tenSanPham
                    Integer.parseInt(modelPhai.getValueAt(i, 3).toString()), // soluong
                    Double.parseDouble(modelPhai.getValueAt(i, 2).toString()), // gia
                    Double.parseDouble(modelPhai.getValueAt(i, 4).toString())  // thanhtien
                );

                if (!dao.insertChiTiet(ctd)) {
                    checkCT = false;
                    break;
                }
            }

            if (checkCT) {
                JOptionPane.showMessageDialog(view, "Lưu đơn hàng thành công!");
                // Xóa trắng bảng phải để làm đơn mới
                modelPhai.setRowCount(0);
                view.getTxtMaDon().setText(""); // Tự sinh mã mới hoặc để trống
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
    }
}
    
    public void setEditData(DonHang dh, ArrayList<ChiTietDon> dsChiTiet) {
    // 1. Set thông tin text/combo
    view.getTxtMaDon().setText(dh.getMaDonHang());
    view.getTxtMaDon().setEditable(false); // Không cho sửa Mã Đơn
    view.getCboKH().setSelectedItem(dh.getMaKH());
    view.getCboNV().setSelectedItem(dh.getMaNV());
    view.getNgayGD().setDate(dh.getNgayGD());
    view.getCboBanHang().setSelectedItem(dh.getPTban());
    view.getCboThanhToan().setSelectedItem(dh.getPTgiaodich());
    
    // 2. Đổ bảng bên phải
    DefaultTableModel modelPhai = view.getModelTablePhai();
    modelPhai.setRowCount(0);
    for (ChiTietDon ct : dsChiTiet) {
        modelPhai.addRow(new Object[]{
            ct.getMaSanPham(), ct.getTenSanPham(), ct.getGia(), ct.getSoluong(), ct.getThanhtien()
        });
    }
    
    // 3. Tính toán lại các nhãn tổng tiền
    tinhTongTien(); 
    
    // 4. Đổi tên nút Lưu thành Cập Nhật
    view.getBtnLuu().setText("Cập nhật");
    }
    
    private void xuLyCapNhat() {
    try {
        // 1. Lấy thông tin từ View (Tương tự hàm lưu mới)
        java.util.Date uDate = view.getNgayGD().getDate();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        
        Object val = view.getLblTongTien().getClientProperty("value");
        double tongTien = (val != null) ? (double) val : 0;

        DonHang dh = new DonHang(
            view.getTxtMaDon().getText(),
            view.getCboKH().getSelectedItem().toString(),
            view.getCboNV().getSelectedItem().toString(),
            view.getCboMaKM().getSelectedItem().toString(),
            sDate,
            view.getCboBanHang().getSelectedItem().toString(),
            view.getCboThanhToan().getSelectedItem().toString(),
            tongTien
        );

        // 2. Lấy danh sách sản phẩm mới từ bảng bên phải
        ArrayList<ChiTietDon> dsMoi = new ArrayList<>();
        DefaultTableModel modelPhai = view.getModelTablePhai();
        for (int i = 0; i < modelPhai.getRowCount(); i++) {
            dsMoi.add(new ChiTietDon(
                dh.getMaDonHang(),
                modelPhai.getValueAt(i, 0).toString(),
                modelPhai.getValueAt(i, 1).toString(),
                Integer.parseInt(modelPhai.getValueAt(i, 3).toString()),
                Double.parseDouble(modelPhai.getValueAt(i, 2).toString()),
                Double.parseDouble(modelPhai.getValueAt(i, 4).toString())
            ));
        }

        // 3. Gọi DAO để cập nhật
        if (dao.update(dh, dsMoi)) {
            JOptionPane.showMessageDialog(view, "Cập nhật đơn hàng thành công!");
            
            // Đóng cửa sổ sửa
            SwingUtilities.getWindowAncestor(view).dispose(); 
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Lỗi cập nhật: " + ex.getMessage());
    }
    }
}