/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import DAO.NhanVienDAO;
import VIEW.LoginView;
import VIEW.MainFrame;
import java.awt.event.ActionEvent;

public class LoginController {
    private LoginView view;
    private NhanVienDAO dao = new NhanVienDAO();

    public LoginController(LoginView v) {
        this.view = v;
        view.addLoginListener((ActionEvent e) -> {
            String u = view.txtUser.getText();
            String p = new String(view.txtPass.getPassword());
            if (dao.checkLogin(u, p)) {
                view.dispose();
                new MainFrame(); // Mở màn hình chính
            } else {
                view.showMsg("Sai tài khoản hoặc mật khẩu!");
            }
        });
    }
    public static void main(String[] args) {
        new LoginController(new LoginView());
    }
}