/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.NhaCungCap;
import MODEL.NhaCungCapModel;
import VIEW.NhaCungCapView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class NhaCungCapController {
    private NhaCungCapModel model;
    private NhaCungCapView view;

    public NhaCungCapController(NhaCungCapModel m, NhaCungCapView v) {
        this.model = m;
        this.view = v;
        loadTable();

        view.addAddListener(e -> {
            try {
                if(view.txtMa.getText().isEmpty()) {
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
            String ma = view.txtMa.getText();
            if(ma.isEmpty()) { view.showMessage("Chọn dòng cần xóa!"); return; }
            if(JOptionPane.showConfirmDialog(view, "Xóa nhà cung cấp này?") == 0) {
                model.delete(ma);
                loadTable(); clearForm();
            }
        });

        view.addResetListener(e -> clearForm());

        view.addTableMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if(row >= 0) {
                    view.txtMa.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTen.setText(view.table.getValueAt(row, 1).toString());
                    view.txtLoaiHinh.setText(view.table.getValueAt(row, 2).toString());
                    view.txtEmail.setText(view.table.getValueAt(row, 3).toString());
                    view.txtSDT.setText(view.table.getValueAt(row, 4).toString());
                    view.txtDiaChi.setText(view.table.getValueAt(row, 5).toString());
                    view.txtMa.setEditable(false);
                }
            }
        });
    }

    private NhaCungCap getForm() {
        return new NhaCungCap(
            view.txtMa.getText(), view.txtTen.getText(), 
            view.txtLoaiHinh.getText(), view.txtEmail.getText(), 
            view.txtSDT.getText(), view.txtDiaChi.getText()
        );
    }

    private void loadTable() {
        view.tableModel.setRowCount(0);
        for(NhaCungCap ncc : model.getList()) {
            view.tableModel.addRow(new Object[]{
                ncc.getMaNCC(), ncc.getTenNCC(), ncc.getLoaiHinh(), 
                ncc.getEmail(), ncc.getSdt(), ncc.getDiaChi()
            });
        }
    }

    private void clearForm() {
        view.txtMa.setText(""); view.txtTen.setText("");
        view.txtLoaiHinh.setText(""); view.txtEmail.setText("");
        view.txtSDT.setText(""); view.txtDiaChi.setText("");
        view.txtMa.setEditable(true);
    }
}