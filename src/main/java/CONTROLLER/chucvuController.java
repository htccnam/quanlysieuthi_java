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
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class chucvuController {

    chucvuView view;
    chucvuDAO cvDAO = new chucvuDAO();

    public chucvuController(chucvuView v) {
        this.view = v;
        v.addThemClickListener(new them());
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
                JOptionPane.showMessageDialog(view, "thêm chức vụ thành công");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(view, "Lỗi thêm chức vụ:" + exception.getMessage());
            }
        }

    }
}
