/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.nhanvienDAO;
import MODEL.nhanvien;
import VIEW.nhanvienViews;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class nhanvienController {

    private final nhanvienViews views;
    private final nhanvienDAO nhanviendao;
    private int selectedRow = -1;

    public nhanvienController(nhanvienViews v) {
        this.views = v;
        nhanviendao = new nhanvienDAO();
        views.addThemListener(new themNhanVienlistener());
        views.addSuaListener(new capNhapNhanVienListener());
        views.addXoaListener(new xoaNhanVienListetenr());
        views.addTimKiemListener(new timKiemListener());
        views.addResetListener(new reSetListener());

        views.addCellClicktable(new cellClickListener());
        load_table();
    }

    private void load_table() {
        views.nhanvienDefaultTableModel.setRowCount(0);
        List<nhanvien> list = nhanviendao.getAllNhanVien();
        for (nhanvien nv : list) {
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
            String textTenNhanVienString = views.tennhanvienField.getText().toString().trim();
            LocalDate ngaySinhDate = views.ngaysinhChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String gioiTinhString = views.gioitinhComboBox.getSelectedItem().toString().trim();
            String diachiString = views.diachiField.getText().toString().trim();
            String soDienThoaiString = views.sodienthoaiField.getText().toString().trim();

            nhanvien nv = new nhanvien(textMaNhanVienString, textTenNhanVienString, ngaySinhDate, gioiTinhString, diachiString, soDienThoaiString);

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

    private class capNhapNhanVienListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String textTenNhanVienString = views.tennhanvienField.getText().toString().trim();
            LocalDate ngaysinhDate = views.ngaysinhChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String textGioiTinhString = views.gioitinhComboBox.getSelectedItem().toString().trim();
            String textDiaChiString = views.diachiField.getText().toString().trim();
            String textSoDienThoaiString = views.sodienthoaiField.getText().toString().trim();
            String textMaNhanVienString = views.manhanvienField.getText();

            nhanvien nv = new nhanvien(textMaNhanVienString, textTenNhanVienString, ngaysinhDate, textGioiTinhString, textDiaChiString, textSoDienThoaiString);

            try {
                if (nhanviendao.suaNhanVien(nv)) {
                    JOptionPane.showMessageDialog(views, "sửa thành công");
                    load_table();
                }
            } catch (RuntimeException exception) {
                JOptionPane.showMessageDialog(views, "sửa thất bại:" + exception.getMessage());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(views, "sửa thất bại:" + exception.getMessage());
            }
        }

    }

    private class xoaNhanVienListetenr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(views, "bạn có chắc chắn ");
            if (result == JOptionPane.YES_OPTION) {
                String maNhanVienString = views.manhanvienField.getText().toString();

                try {
                    if (nhanviendao.xoaNhanVien(maNhanVienString)) {
                        JOptionPane.showMessageDialog(views, "xóa thành công");
                        load_table();
                    }
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(views, "xóa thất bại:" + exception.getMessage());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(views, "xóa thất bại:" + exception.getMessage());
                }
            }
        }

    }

    private class reSetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            views.manhanvienField.setText("");
            views.tennhanvienField.setText("");
            views.ngaysinhChooser.setDate(new java.util.Date());
            views.gioitinhComboBox.setSelectedIndex(0);
            views.diachiField.setText("");
            views.sodienthoaiField.setText("");
            load_table();
        }

    }

    private class timKiemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String timkiemString = views.timkiemField.getText().toString().trim();

            List<nhanvien> list = nhanviendao.timKiemNhanVien(timkiemString);
            views.nhanvienDefaultTableModel.setRowCount(0);
            for (nhanvien nv : list) {
                views.nhanvienDefaultTableModel.addRow(new Object[]{
                    nv.getMaNhanVienString(),
                    nv.getTenNhanVienString(),
                    nv.getNgaySinhDate(),
                    nv.getGioiTinhString(),
                    nv.getDiaChiString(),
                    nv.getSoDienThoaiString()

                }
                );
            }
            views.nhanvienDefaultTableModel.fireTableDataChanged();
        }

    }

    private class cellClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = views.nhanvienJTable.getSelectedRow();
            views.manhanvienField.setText(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 0).toString());
            views.tennhanvienField.setText(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 1).toString());
            views.ngaysinhChooser.setDate(Date.valueOf(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 2).toString()));
            views.gioitinhComboBox.setSelectedItem(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 3));
            views.diachiField.setText(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 4).toString());
            views.sodienthoaiField.setText(views.nhanvienDefaultTableModel.getValueAt(selectedRow, 5).toString());

            views.manhanvienField.setEnabled(false);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}
