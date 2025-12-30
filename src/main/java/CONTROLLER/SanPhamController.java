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
import VIEW.SanPhamView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class SanPhamController {
    private SanPhamModel model;
    private SanPhamView view;

    public SanPhamController(SanPhamModel m, SanPhamView v) {
        this.model = m;
        this.view = v;

        loadTable();

        // Sự kiện nút THÊM
        view.addAddListener(e -> {
            try {
                model.add(getForm());
                view.showMessage("Thêm thành công!");
                loadTable();
                clearForm();
            } catch (Exception ex) {
                view.showMessage("Lỗi nhập liệu: " + ex.getMessage());
            }
        });

        // Sự kiện nút SỬA
        view.addEditListener(e -> {
            try {
                model.update(getForm());
                view.showMessage("Cập nhật thành công!");
                loadTable();
                clearForm();
            } catch (Exception ex) {
                view.showMessage("Lỗi: " + ex.getMessage());
            }
        });

        // Sự kiện nút XÓA
        view.addDeleteListener(e -> {
            String maSP = view.txtMaSP.getText();
            if (maSP.isEmpty()) {
                view.showMessage("Vui lòng chọn sản phẩm để xóa!");
                return;
            }
            int confirm = javax.swing.JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
            if (confirm == 0) {
                model.delete(maSP);
                loadTable();
                clearForm();
            }
        });

        // Sự kiện nút LÀM MỚI
        view.addResetListener(e -> clearForm());

        // Sự kiện CLICK VÀO BẢNG -> Đổ dữ liệu lên ô nhập
        view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row >= 0) {
                    view.txtMaSP.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTenSP.setText(view.table.getValueAt(row, 1).toString());
                    view.txtMaLoai.setText(view.table.getValueAt(row, 2).toString());
                    view.txtMaTH.setText(view.table.getValueAt(row, 3).toString());
                    view.txtSoLuong.setText(view.table.getValueAt(row, 4).toString());
                    view.txtGiaNhap.setText(view.table.getValueAt(row, 5).toString());
                    view.txtGiaBan.setText(view.table.getValueAt(row, 6).toString());
                    view.txtDVT.setText(view.table.getValueAt(row, 7).toString());
                    
                    view.txtMaSP.setEditable(false); // Không cho sửa Mã SP khi đang update
                }
            }
        });
    }

    // Hàm lấy dữ liệu từ form
    private SanPham getForm() {
        String ma = view.txtMaSP.getText();
        String ten = view.txtTenSP.getText();
        String loai = view.txtMaLoai.getText();
        String th = view.txtMaTH.getText();
        int sl = Integer.parseInt(view.txtSoLuong.getText());
        BigDecimal gn = new BigDecimal(view.txtGiaNhap.getText());
        BigDecimal gb = new BigDecimal(view.txtGiaBan.getText());
        String dvt = view.txtDVT.getText();
        return new SanPham(ma, ten, loai, th, sl, gn, gb, dvt);
    }

    // Hàm load lại bảng
    private void loadTable() {
        view.tableModel.setRowCount(0);
        for (SanPham sp : model.getList()) {
            view.tableModel.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getMaLoai(), sp.getMaTH(),
                sp.getSoLuong(), sp.getGiaNhap(), sp.getGiaBan(), sp.getDonViTinh()
            });
        }
    }

    // Hàm xóa trắng form
    private void clearForm() {
        view.txtMaSP.setText("");
        view.txtMaSP.setEditable(true); // Cho phép nhập lại Mã SP
        view.txtTenSP.setText("");
        view.txtMaLoai.setText("");
        view.txtMaTH.setText("");
        view.txtSoLuong.setText("");
        view.txtGiaNhap.setText("");
        view.txtGiaBan.setText("");
        view.txtDVT.setText("");
    }
}