/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.nhanvien_dao;
import MODEL.nhanvienmodel;
import VIEW.nhanvienViews;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class nhanvienController {

    private final nhanvienViews views;
    private final nhanvien_dao nhanviendao;
    private int selectedRow = -1;

    public nhanvienController(nhanvienViews v) {
        this.views = v;
        nhanviendao = new nhanvien_dao();
        views.addThemListener(new themNhanVienlistener());
        load_table();
    }

    private void load_table() {
        views.nhanvienDefaultTableModel.setRowCount(0);
        List<nhanvienmodel> list = nhanviendao.getAllNhanVien();
        for (nhanvienmodel nv : list) {
            views.nhanvienDefaultTableModel.addRow(new Object[]{
                nv.getMaNhanVienString(),
                nv.getTenNhanVienString(),
                nv.getNgaySinhDate(),
                nv.getGioiTinhString(),
                nv.getDiaChiString(),
                nv.getSoDienThoaiString()
            });
        }
        views.nhanvienDefaultTableModel.fireTableDataChanged();
    }

    private class themNhanVienlistener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String textMaNhanVienString = views.manhanvienField.getText().toString().trim();
            String textTenNhanVienString = views.hotenField.getText().toString().trim();
            LocalDate ngaySinhDate = views.ngaysinhChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gioiTinhString = views.gioitinhComboBox.getSelectedItem().toString().trim();
            String diachiString = views.diachiField.getText().toString().trim();
            String soDienThoaiString = views.sodienthoaiField.getText().toString().trim();

            nhanvienmodel nv = new nhanvienmodel(textMaNhanVienString, textTenNhanVienString, ngaySinhDate, gioiTinhString, diachiString, soDienThoaiString);

            try {
                if (nhanviendao.themNhanVien(nv)) {
                    JOptionPane.showMessageDialog(views, "thêm thành công");
                    load_table();
                }
            } catch (RuntimeException exception) {
                JOptionPane.showMessageDialog(views, "thêm thất bại :" + exception.getMessage());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(views, "lỗi không xác định");
            }

        }

    }
}
