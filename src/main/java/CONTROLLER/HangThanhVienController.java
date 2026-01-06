package CONTROLLER;

import DAO.HangThanhVienDAO;
import MODEL.HangThanhVien;
import VIEW.DialogThemThanhVien;
import VIEW.HangThanhVienView;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class HangThanhVienController {
    private HangThanhVienView view;
    private HangThanhVienDAO dao;
    private final DecimalFormat df = new DecimalFormat("#,### VNĐ");

    public HangThanhVienController(HangThanhVienView view) {
        this.view = view;
        this.dao = new HangThanhVienDAO();
        
        System.out.println("DEBUG: HangThanhVienController đã khởi động!"); // <--- KIỂM TRA DÒNG NÀY TRONG OUTPUT
        
        // Load dữ liệu cho cả 2 phần (Tra cứu & Bảng chính)
        loadDataToMainTable();
        loadDataToLookupCombo();
        
        // --- SỰ KIỆN PHẦN TRA CỨU ---
        view.btnCheck.addActionListener(e -> checkTongTien());
        
        // --- SỰ KIỆN PHẦN CRUD BẢNG XẾP HẠNG ---
        view.btnThem.addActionListener(e -> xuLyThem());
        view.btnXoa.addActionListener(e -> xuLyXoa());
    }

    // 1. Load dữ liệu vào Bảng chính (Từ bảng HangThanhVien)
    private void loadDataToMainTable() {
        System.out.println("DEBUG: Đang lấy danh sách xếp hạng từ DB...");
        List<HangThanhVien> list = dao.getDanhSachXepHang();
        
        System.out.println("DEBUG: Tìm thấy " + list.size() + " dòng xếp hạng.");
        
        view.modelXepHang.setRowCount(0);
        for (HangThanhVien h : list) {
            view.modelXepHang.addRow(new Object[]{
                h.getMaKH(), 
                h.getTenKH(), 
                h.getTenHang()
            });
        }
    }

    // 2. Load dữ liệu vào ComboBox Tra Cứu (Từ bảng DonHang - để check tiền)
    private void loadDataToLookupCombo() {
        System.out.println("DEBUG: Đang lấy danh sách chi tiêu để nạp Combo...");
        Vector<Vector<Object>> listData = dao.getKhachHangVaTongTien();
        view.cboKhachHang.removeAllItems();
        
        for (Vector<Object> row : listData) {
            String ma = row.get(0).toString();
            String ten = row.get(1).toString();
            double tien = 0;
            try {
                if (row.get(2) != null) tien = Double.parseDouble(row.get(2).toString());
            } catch (Exception e) {}
            
            // Format: "MA - TEN (TIEN)"
            view.cboKhachHang.addItem(ma + " - " + ten + " (" + df.format(tien) + ")");
        }
    }

    // --- XỬ LÝ SỰ KIỆN ---

    // Nút Kiểm Tra (Check tiền nhanh)
    private void checkTongTien() {
        try {
            String selected = (String) view.cboKhachHang.getSelectedItem();
            if (selected == null) return;
            
            int start = selected.lastIndexOf("(") + 1;
            int end = selected.lastIndexOf(")");
            if (start > 0 && end > start) {
                view.lblTongChiTieuValue.setText(selected.substring(start, end));
            }
        } catch (Exception e) {
            view.lblTongChiTieuValue.setText("Error");
        }
    }

    // Nút Thêm (Mở Dialog)
    private void xuLyThem() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(view);
        DialogThemThanhVien dialog = new DialogThemThanhVien(parent);
        dialog.showDialog();
        
        if (dialog.isConfirmed()) {
            HangThanhVien htv = new HangThanhVien(
                dialog.getMa(), 
                dialog.getTen(), 
                dialog.getHang()
            );
            
            if (dao.themThanhVien(htv)) {
                JOptionPane.showMessageDialog(view, "Thêm xếp hạng thành công!");
                loadDataToMainTable(); // Refresh bảng chính
                // Có thể cần refresh cả Dialog nếu mở lại, nhưng Dialog tạo mới mỗi lần nên ok
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi thêm! (Khách này có thể đã được xếp hạng rồi)");
            }
        }
    }

    // Nút Xóa
    private void xuLyXoa() {
        int row = view.tblXepHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        
        String maKH = view.tblXepHang.getValueAt(row, 0).toString();
        String tenKH = view.tblXepHang.getValueAt(row, 1).toString();
        
        int cf = JOptionPane.showConfirmDialog(view, 
                "Xóa khách hàng " + tenKH + " khỏi bảng xếp hạng?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (cf == JOptionPane.YES_OPTION) {
            if (dao.xoaThanhVien(maKH)) {
                JOptionPane.showMessageDialog(view, "Đã xóa thành công!");
                loadDataToMainTable();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi xóa dữ liệu!");
            }
        }
    }
}