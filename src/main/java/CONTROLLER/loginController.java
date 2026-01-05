/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import DAO.loginDAO;
import VIEW.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.View;

/**
 *
 * @author Admin
 */
public class loginController {

    LoginView view;
    loginDAO lgDAO = new loginDAO();

    public loginController(LoginView view) {
        this.view = view;

        view.addDangNhapListener(new dangnhap());
    }

    public class dangnhap implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String taikhoanString = view.taikhoanField.getText().toString().trim();
            String matkhauString = String.valueOf(view.matkhauField.getPassword());
            try {
                if (lgDAO.checkMatKhauTaiKhoan(taikhoanString, matkhauString)) {
                    manhinhchinh menu = new manhinhchinh();
                    new manhinhchinhController(menu);
                    menu.setVisible(true);
                    view.dispose();
                    JOptionPane.showMessageDialog(view, "Đăng nhập thành công");

                } else {
                    JOptionPane.showMessageDialog(view, "tài khoản hoặc mật khảu không chính xác");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "lỗi đăng nhâp:" + ex.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginController controller = new loginController(loginView);
        loginView.setVisible(true);

    }
}
