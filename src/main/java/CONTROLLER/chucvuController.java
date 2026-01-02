/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.chucvuDAO;
import MODEL.chucvu;
import VIEW.chucvuView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class chucvuController {

    chucvuView view;
    chucvuDAO cvDAO = new chucvuDAO();
    private int selectRow = -1;

    public chucvuController(chucvuView v) {
        this.view = v;
        v.addThemClickListener(new them());
        v.addSuaClickListener(new sua());
        v.addXoaClickListener(new xoa());
        v.addResetClickListener(new reSet());
        v.addClickTableListener(new clickTable());
        v.addTimKiemClickListener(new timKiem());

        loadTable();
    }

    public class them implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String machucvuString = view.machucvuField.getText().toString().trim();
            String tenchucvuString = view.tenchucvuField.getText().toString().trim();

            if (machucvuString.isEmpty()) {
                JOptionPane.showMessageDialog(view, "mã chức vụ không được để trống");
                return;
            }
            if (tenchucvuString.isEmpty()) {
                JOptionPane.showMessageDialog(view, "tên chức vụ không được để trống");
                return;
            }

            if (cvDAO.checkTrungMaChucVu(machucvuString)) {
                JOptionPane.showMessageDialog(view, "mã chức vụ đã tồn tại");
                view.machucvuField.setText("");
                return;
            }

            try {
                chucvu cv = new chucvu(machucvuString, tenchucvuString);
                cvDAO.themChucVu(cv);
                loadTable();
                JOptionPane.showMessageDialog(view, "thêm chức vụ thành công");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(view, "Lỗi thêm chức vụ:" + exception.getMessage());
            }
        }

    }

    public class sua implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String machucvuString = view.machucvuField.getText().toString().trim();
            String tenchucvuString = view.tenchucvuField.getText().toString().trim();

            if (tenchucvuString.isEmpty()) {
                JOptionPane.showMessageDialog(view, "tên chức vụ không được để trống");
                return;
            }
            try {
                chucvu cv = new chucvu(machucvuString, tenchucvuString);
                cvDAO.suaChucVu(cv);
                view.machucvuField.setEnabled(true);
                JOptionPane.showMessageDialog(view, "sửa thành công");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(view, "lỗi sửa chức vụ:" + exception.getMessage());
            }

        }

    }

    public class xoa implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String machucvuString = view.machucvuField.getText().toString().trim();

            int check = JOptionPane.showConfirmDialog(view, "bạn có chắc chắn muốn xóa không");
            if (check == JOptionPane.YES_OPTION) {
                try {
                    if (cvDAO.checkXoaChucVu(machucvuString)) {
                        JOptionPane.showMessageDialog(view, "chức vụ đã được chọn cho nhân viên nên không thể xóa");
                        return;
                    }
                    if (cvDAO.xoaChucVu(machucvuString)) {
                        JOptionPane.showMessageDialog(view, "xóa thành công");
                        view.machucvuField.setText("");
                        view.tenchucvuField.setText("");
                        loadTable();
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(view, "lỗi xóa chức vụ:" + exception.getMessage());
                }
            }
        }

    }

    public class reSet implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.machucvuField.setText("");
            view.tenchucvuField.setText("");
            view.timkiemField.setText("");
        }

    }

    public class timKiem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String dieukientimkiemString = view.timkiemField.getText().toString().trim();

            try {
                List<chucvu> list = cvDAO.timKiemChucVu(dieukientimkiemString);
                view.chucvuDefaultTableModel.setRowCount(0);
                for(chucvu cv : list){
                    view.chucvuDefaultTableModel.addRow(new Object[]{
                        cv.getMachucvuString(),
                        cv.getTenchucvuString()
                    });
                }

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(view, "lỗi tìm kiếm:" + exception.getMessage());
            }
        }

    }

    public class clickTable implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectRow = view.chucvuJTable.getSelectedRow();
            view.machucvuField.setEnabled(false);
            view.machucvuField.setText(view.chucvuJTable.getValueAt(selectRow, 0).toString());
            view.tenchucvuField.setText(view.chucvuJTable.getValueAt(selectRow, 1).toString());
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

    public void loadTable() {
        try {
            List<chucvu> list = cvDAO.getAllChucVu();
            view.chucvuDefaultTableModel.setRowCount(0);
            for (chucvu cv : list) {
                view.chucvuDefaultTableModel.addRow(new Object[]{
                    cv.getMachucvuString(),
                    cv.getTenchucvuString()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "lỗi load bảng chức vụ" + e.getMessage());
        }
    }
}
