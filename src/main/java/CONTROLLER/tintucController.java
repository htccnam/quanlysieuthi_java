/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.nhanvienDAO;
import DAO.tintucDAO;
import MODEL.nhanvien;
import MODEL.tintuc;
import VIEW.tintucView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class tintucController {

    tintucView view;
    tintucDAO ttDAO;
    nhanvienDAO nvDAO;
    private int selectedrow = -1;

    public tintucController(tintucView view) {
        nvDAO = new nhanvienDAO();
        this.view = view;
        this.ttDAO = new tintucDAO();

        load_nhanvien();
        load_table();

        view.addThemActionListener(new themTinTuc());
        view.addSuaActionListener(new suaTinTuc());
        view.addXoaActionListener(new xoaTinTuc());
        view.addResetActionListener(new reSet());
        view.addTimKiemActionLister(new timKiemTinTuc());
        view.cellClickListener(new cellClickListener());

    }

    private void load_nhanvien() {
        try {
            List<nhanvien> list = nvDAO.getAllNhanVien();
            for (nhanvien nv : list) {
//                view.manhanvienBox.addItem(nv.getMaNhanVienString().trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi load nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void load_table() {
        view.tintucDefaultTableModel.setRowCount(0);
        List<tintuc> list = ttDAO.getAll();
        for (tintuc tt : list) {
            view.tintucDefaultTableModel.addRow(new Object[]{
                tt.getMatintucString(),
                tt.getTieudeString(),
                tt.getManhanvienString(),
                tt.getNoidungString(),
                tt.getLoaitinString(),
                tt.getNgaydangString()
            });
            view.tintucDefaultTableModel.fireTableDataChanged();
        }
    }

    private class themTinTuc implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày đăng");
                return;
            }

            String matintuc = view.matintucField.getText().trim();
            String tieude = view.tieudeField.getText().trim();
            String manhanvien = view.manhanvienBox.getSelectedItem().toString();
            String noidung = view.noidungField.getText().trim();
            String loaitin = view.loaitinBox.getSelectedItem().toString();
            LocalDate ngaydang = view.dateChooser.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            tintuc tt = new tintuc(matintuc, tieude, manhanvien, noidung, loaitin, ngaydang);

            try {
                if (ttDAO.themTinTuc(tt)) {
                    JOptionPane.showMessageDialog(view, "Thêm thành công");
                    load_table();
                } else {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }

        }

    }

    private class suaTinTuc implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày đăng");
                return;
            }

            String matintuc = view.matintucField.getText().trim();
            String tieude = view.tieudeField.getText().trim();
            String manhanvien = view.manhanvienBox.getSelectedItem().toString();
            String noidung = view.noidungField.getText().trim();
            String loaitin = view.loaitinBox.getSelectedItem().toString();
            LocalDate ngaydang = view.dateChooser.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            tintuc tt = new tintuc(matintuc, tieude, manhanvien, noidung, loaitin, ngaydang);

            try {
                if (ttDAO.suaTinTuc(tt)) {
                    JOptionPane.showMessageDialog(view, "Sửa thành công");
                    load_table();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }
        }

    }

    private class xoaTinTuc implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (ttDAO.xoaTinTuc(view.matintucField.getText().trim())) {
                        JOptionPane.showMessageDialog(view, "Xóa thành công");
                        load_table();
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        }

    }

    private class reSet implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.matintucField.setText("");
            view.tieudeField.setText("");
            view.noidungField.setText("");
            view.manhanvienBox.setSelectedIndex(0);
            view.loaitinBox.setSelectedIndex(0);
            view.dateChooser.setDate(new java.util.Date());

            view.matintucField.setEnabled(true);
            selectedrow = -1;
        }

    }

    private class timKiemTinTuc implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = view.timkiemField.getText().trim();
            view.tintucDefaultTableModel.setRowCount(0);

            List<tintuc> list = ttDAO.timKiem(keyword);
            for (tintuc tt : list) {
                view.tintucDefaultTableModel.addRow(new Object[]{
                    tt.getMatintucString(),
                    tt.getTieudeString(),
                    tt.getManhanvienString(),
                    tt.getNoidungString(),
                    tt.getLoaitinString(),
                    tt.getNgaydangString()
                });
            }
            view.tintucDefaultTableModel.fireTableDataChanged();
        }

    }

    private class cellClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedrow = view.tintucJTable.getSelectedRow();
            view.matintucField.setText(view.tintucDefaultTableModel.getValueAt(selectedrow, 0).toString().trim());
            view.tieudeField.setText(view.tintucDefaultTableModel.getValueAt(selectedrow, 1).toString().trim());
            view.manhanvienBox.setSelectedItem(view.tintucDefaultTableModel.getValueAt(selectedrow, 2));
            view.noidungField.setText(view.tintucDefaultTableModel.getValueAt(selectedrow, 3).toString().trim());
            view.loaitinBox.setSelectedItem(view.tintucDefaultTableModel.getValueAt(selectedrow, 4));
            view.dateChooser.setDate(Date.valueOf(view.tintucDefaultTableModel.getValueAt(selectedrow, 5).toString()));

            view.matintucField.setEnabled(false);
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
