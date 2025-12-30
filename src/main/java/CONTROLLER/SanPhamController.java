/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.SanPham;
import MODEL.SanPhamModel;
import MODEL.LoaiHang;      // Import thêm
import MODEL.LoaiHangModel; // Import thêm
import MODEL.NhaCungCap;    // Import thêm
import MODEL.NhaCungCapModel;// Import thêm
import VIEW.SanPhamView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;       // Lưu ý import sql.Date
import javax.swing.JOptionPane;

public class SanPhamController {
    private SanPhamModel model;
    private SanPhamView view;

    public SanPhamController(SanPhamModel m, SanPhamView v) {
        this.model = m;
        this.view = v;
        
        // 1. Load dữ liệu cho các ComboBox trước khi hiển thị
        loadComboBoxData();
        loadTable();

        // Nút THÊM
        view.addAddListener(e -> {
            try {
                if(!validateData()) return; // Kiểm tra dữ liệu
                model.add(getForm());
                loadTable(); clearForm();
                view.showMessage("Thêm thành công!");
            } catch (Exception ex) { ex.printStackTrace(); view.showMessage("Lỗi: " + ex.getMessage()); }
        });

        // Nút SỬA
        view.addEditListener(e -> {
            try {
                if(!validateData()) return; // Kiểm tra dữ liệu
                model.update(getForm());
                loadTable(); clearForm();
                view.showMessage("Sửa thành công!");
            } catch (Exception ex) { view.showMessage("Lỗi: " + ex.getMessage()); }
        });

        // Nút XÓA (Giữ nguyên)
        view.addDeleteListener(e -> {
            String ma = view.txtMaSP.getText();
            if(ma.isEmpty()) { view.showMessage("Chọn dòng xóa!"); return; }
            if(JOptionPane.showConfirmDialog(view, "Xóa sản phẩm này?") == 0) {
                model.delete(ma); loadTable(); clearForm();
            }
        });

        view.addResetListener(e -> clearForm());

        // CLICK BẢNG
        view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if(row >= 0) {
                    view.txtMaSP.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTenSP.setText(view.table.getValueAt(row, 1).toString());
                    
                    // Chọn lại đúng item trong ComboBox (So sánh bằng Mã)
                    String maLoaiTable = view.table.getValueAt(row, 2).toString();
                    setSelectedLoaiHang(maLoaiTable);
                    
                    String maNCCTable = view.table.getValueAt(row, 3).toString();
                    setSelectedNCC(maNCCTable);
                    
                    view.txtXuatXu.setText(view.table.getValueAt(row, 4).toString());
                    view.txtSoLuong.setText(view.table.getValueAt(row, 5).toString());
                    
                    // Set ngày cho Lịch
                    view.dateNgaySX.setDate((java.sql.Date) view.table.getValueAt(row, 6));
                    view.dateHanSD.setDate((java.sql.Date) view.table.getValueAt(row, 7));
                    
                    view.cbbTinhTrang.setSelectedItem(view.table.getValueAt(row, 8).toString());
                    view.txtGiaNhap.setText(view.table.getValueAt(row, 9).toString());
                    view.txtGiaBan.setText(view.table.getValueAt(row, 10).toString());
                    view.txtDVT.setText(view.table.getValueAt(row, 11).toString());
                    
                    view.txtMaSP.setEditable(false);
                }
            }
        });
    }

    // --- HÀM 1: Load dữ liệu vào ComboBox ---
    private void loadComboBoxData() {
        // Load Loại Hàng
        LoaiHangModel lhModel = new LoaiHangModel();
        view.cbbLoaiHang.removeAllItems();
        for (LoaiHang lh : lhModel.getList()) {
            view.cbbLoaiHang.addItem(lh); // Add cả đối tượng, toString() sẽ lo phần hiển thị
        }

        // Load Nhà Cung Cấp
        NhaCungCapModel nccModel = new NhaCungCapModel();
        view.cbbNhaCungCap.removeAllItems();
        for (NhaCungCap ncc : nccModel.getList()) {
            view.cbbNhaCungCap.addItem(ncc);
        }
    }

    // --- HÀM 2: Kiểm tra ràng buộc dữ liệu ---
    private boolean validateData() {
        if(view.txtMaSP.getText().isEmpty()) {
            view.showMessage("Mã SP không được để trống!"); return false;
        }
        if(view.dateNgaySX.getDate() == null || view.dateHanSD.getDate() == null) {
            view.showMessage("Vui lòng chọn ngày sản xuất và hạn sử dụng!"); return false;
        }
        
        try {
            double giaNhap = Double.parseDouble(view.txtGiaNhap.getText());
            double giaBan = Double.parseDouble(view.txtGiaBan.getText());
            
            if (giaBan <= giaNhap) {
                view.showMessage("LỖI: Giá bán phải cao hơn Giá nhập!");
                return false;
            }
        } catch (NumberFormatException e) {
            view.showMessage("Giá tiền phải là số!"); return false;
        }
        return true;
    }

    // --- HÀM 3: Lấy dữ liệu từ form ---
    private SanPham getForm() {
        // Lấy đối tượng Loại Hàng đang chọn -> lấy mã
        LoaiHang loai = (LoaiHang) view.cbbLoaiHang.getSelectedItem();
        String maLoai = loai.getMaLoai();
        
        // Lấy đối tượng NCC đang chọn -> lấy mã
        NhaCungCap ncc = (NhaCungCap) view.cbbNhaCungCap.getSelectedItem();
        String maNCC = ncc.getMaNCC();

        // Chuyển đổi ngày từ JDateChooser (util.Date) sang sql.Date
        Date ngaySX = new Date(view.dateNgaySX.getDate().getTime());
        Date hanSD = new Date(view.dateHanSD.getDate().getTime());

        return new SanPham(
            view.txtMaSP.getText(), view.txtTenSP.getText(),
            maLoai, maNCC, // Dùng mã lấy từ combobox
            view.txtXuatXu.getText(), 
            Integer.parseInt(view.txtSoLuong.getText()),
            ngaySX, hanSD,
            view.cbbTinhTrang.getSelectedItem().toString(),
            Double.parseDouble(view.txtGiaNhap.getText()),
            Double.parseDouble(view.txtGiaBan.getText()),
            view.txtDVT.getText()
        );
    }

    private void loadTable() {
        view.tableModel.setRowCount(0);
        for(SanPham sp : model.getList()) {
            view.tableModel.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getMaLoai(), sp.getMaNCC(),
                sp.getXuatXu(), sp.getSoLuong(), sp.getNgaySX(), sp.getHanSD(),
                sp.getTinhTrang(), sp.getGiaNhap(), sp.getGiaBan(), sp.getDonViTinh()
            });
        }
    }

    private void clearForm() {
        view.txtMaSP.setText(""); view.txtTenSP.setText(""); 
        view.txtXuatXu.setText(""); view.txtSoLuong.setText(""); 
        view.dateNgaySX.setDate(null); view.dateHanSD.setDate(null); // Reset lịch
        view.txtGiaNhap.setText(""); view.txtGiaBan.setText(""); view.txtDVT.setText("");
        view.txtMaSP.setEditable(true);
    }
    
    // Hàm phụ để set chọn ComboBox khi click bảng
    private void setSelectedLoaiHang(String maLoai) {
        for (int i = 0; i < view.cbbLoaiHang.getItemCount(); i++) {
            LoaiHang item = view.cbbLoaiHang.getItemAt(i);
            if (item.getMaLoai().equals(maLoai)) {
                view.cbbLoaiHang.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void setSelectedNCC(String maNCC) {
        for (int i = 0; i < view.cbbNhaCungCap.getItemCount(); i++) {
            NhaCungCap item = view.cbbNhaCungCap.getItemAt(i);
            if (item.getMaNCC().equals(maNCC)) {
                view.cbbNhaCungCap.setSelectedIndex(i);
                break;
            }
        }
    }
}