/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.khuyenmaiDAO;
import MODEL.khuyenmai;
import VIEW.khuyenmaiView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class khuyenmaiController {

    private khuyenmaiDAO kmDAO = new khuyenmaiDAO();
    private khuyenmaiView kmView = new khuyenmaiView();
    private int selectedRow = -1;

    public khuyenmaiController(khuyenmaiView view) {
        this.kmView = view;
        view.addThemActionListener(new them());
        view.addSuaActionListener(new sua());
        view.addXoaActionListener(new xoa());
        view.addResetActionListener(new reset());
        view.addTimKiemActionLister(new timkiem());
        view.addcellClickListener(new cellClickListener());
        load_table();
    }

    public void load_table() {
        try {
            List<khuyenmai> list = kmDAO.getAllKhuyenMai();
            kmView.khuyenmaiDefaultTableModel.setRowCount(0);
            for (khuyenmai km : list) {
                kmView.khuyenmaiDefaultTableModel.addRow(new Object[]{
                    km.getMakhuyenmaiString(),
                    km.getTenkhuyenmaiString(),
                    km.getMotaString(),
                    km.getNgaytaoDate()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kmView, "lỗi load table khuyến mại" + e.getMessage());
        }
    }

    public class them implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String makhuyenmaiString = kmView.makhuyenmaiField.getText().toString().trim();
            String tenkhuyenmaiString = kmView.tenkhuyenmaiField.getText().toString().trim();
            String motaString = kmView.motaField.getText().toString().trim();
            LocalDate ngaytaoDate = kmView.ngaytaoChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(makhuyenmaiString.isEmpty()){
                JOptionPane.showMessageDialog(kmView, "mã khuyến mại không được để trống");
                return;
            }
            if(tenkhuyenmaiString.isEmpty()){
                JOptionPane.showMessageDialog(kmView, "tên khuyến mại không được để trống");
                return;
            }
            try {
                if (kmDAO.checkTrungMaKhuyenMai(makhuyenmaiString)) {
                    JOptionPane.showMessageDialog(kmView, "mã khuyến mại đã tồn tại");
                    return;
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(kmView, "lỗi check trùng mã khuyến mại" + exception.getMessage());
            }

            khuyenmai km = new khuyenmai(makhuyenmaiString, tenkhuyenmaiString, motaString, ngaytaoDate);
            try {
                kmDAO.themKhuyenMai(km);
                JOptionPane.showMessageDialog(kmView, "thêm thành công");
                load_table();

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(kmView, "lỗi thêm khuyến mại" + exception.getMessage());

            }

        }

    }

    public class sua implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String makhuyenmaiString = kmView.makhuyenmaiField.getText().toString().trim();
            String tenkhuyenmaiString = kmView.tenkhuyenmaiField.getText().toString().trim();
            String motaString = kmView.motaField.getText().toString().trim();
            LocalDate ngaytaoDate = kmView.ngaytaoChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            khuyenmai km = new khuyenmai(makhuyenmaiString, tenkhuyenmaiString, motaString, ngaytaoDate);
            try {
                kmDAO.suaKhuyenMai(km);
                JOptionPane.showMessageDialog(kmView, "sửa thành công");
                load_table();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(kmView, exception.getMessage());
            }
        }

    }

    public class xoa implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String makhuyenmaiString = kmView.makhuyenmaiField.getText().toString().trim();
            int check = JOptionPane.showConfirmDialog(kmView, "bạn có chắc chắn muốn xóa");
            if (check == JOptionPane.YES_OPTION) {
                try {
                    kmDAO.xoaKhuyenMai(makhuyenmaiString);
                    JOptionPane.showMessageDialog(kmView, "xóa thành công");
                    load_table();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(kmView, "lỗi xóa khuyến mại" + exception.getMessage());
                }
            }
        }

    }

    public class reset implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            kmView.makhuyenmaiField.setEnabled(true);
            kmView.makhuyenmaiField.setText("");
            kmView.tenkhuyenmaiField.setText("");
            kmView.motaField.setText("");
            kmView.ngaytaoChooser.setDate(Date.valueOf(LocalDate.now()));

            kmView.timkiemField.setText("");

            load_table();
        }

    }

    public class timkiem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String texttimkiemString = kmView.timkiemField.getText().toString().trim();
            kmView.khuyenmaiDefaultTableModel.setRowCount(0);
            try {
                List<khuyenmai> list = kmDAO.timKiemKhuyenMai(texttimkiemString);
                for (khuyenmai km : list) {
                    kmView.khuyenmaiDefaultTableModel.addRow(new Object[]{
                        km.getMakhuyenmaiString(),
                        km.getTenkhuyenmaiString(),
                        km.getMotaString(),
                        km.getNgaytaoDate()
                    });
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(kmView, "lỗi tìm kiếm khuyến mại" + exception.getMessage());
            }
        }

    }

    public class cellClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            kmView.makhuyenmaiField.setEnabled(false);
            selectedRow = kmView.khuyenmaiJTable.getSelectedRow();

            kmView.makhuyenmaiField.setText(kmView.khuyenmaiJTable.getValueAt(selectedRow, 0).toString());
            kmView.tenkhuyenmaiField.setText(kmView.khuyenmaiJTable.getValueAt(selectedRow, 1).toString());
            kmView.motaField.setText(kmView.khuyenmaiJTable.getValueAt(selectedRow, 2).toString());
            kmView.ngaytaoChooser.setDate(Date.valueOf(kmView.khuyenmaiJTable.getValueAt(selectedRow, 3).toString()));
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
