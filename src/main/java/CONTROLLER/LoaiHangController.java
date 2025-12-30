/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.LoaiHang;
import MODEL.LoaiHangModel;
import VIEW.LoaiHangView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class LoaiHangController {
    private LoaiHangModel model;
    private LoaiHangView view;

    public LoaiHangController(LoaiHangModel m, LoaiHangView v) {
        this.model = m;
        this.view = v;

        loadTable();

        view.addAddListener(e -> {
            try {
                if(view.txtMaLoai.getText().isEmpty()) {
                    view.showMessage("Mã không được trống!"); return;
                }
                model.add(getForm());
                loadTable(); clearForm();
                view.showMessage("Thêm thành công!");
            } catch (Exception ex) { view.showMessage("Lỗi: " + ex.getMessage()); }
        });

        view.addEditListener(e -> {
            try {
                model.update(getForm());
                loadTable(); clearForm();
                view.showMessage("Sửa thành công!");
            } catch (Exception ex) { view.showMessage("Lỗi: " + ex.getMessage()); }
        });

        view.addDeleteListener(e -> {
            String ma = view.txtMaLoai.getText();
            if(ma.isEmpty()) { view.showMessage("Chọn dòng cần xóa!"); return; }
            if(JOptionPane.showConfirmDialog(view, "Xóa loại hàng này?") == 0) {
                model.delete(ma);
                loadTable(); clearForm();
            }
        });

        view.addResetListener(e -> clearForm());

        view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if(row >= 0) {
                    view.txtMaLoai.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTenLoai.setText(view.table.getValueAt(row, 1).toString());
                    view.txtMaLoai.setEditable(false);
                }
            }
        });
    }

    private LoaiHang getForm() {
        return new LoaiHang(view.txtMaLoai.getText(), view.txtTenLoai.getText());
    }

    private void loadTable() {
        view.tableModel.setRowCount(0);
        for(LoaiHang lh : model.getList()) {
            view.tableModel.addRow(new Object[]{lh.getMaLoai(), lh.getTenLoai()});
        }
    }

    private void clearForm() {
        view.txtMaLoai.setText("");
        view.txtTenLoai.setText("");
        view.txtMaLoai.setEditable(true);
    }
}
