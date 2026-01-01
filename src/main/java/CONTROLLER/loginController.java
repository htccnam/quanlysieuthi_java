/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import VIEW.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class loginController {
    LoginView view;

    public loginController(LoginView view) {
        this.view=view;
        
        view.addLoginListener(new login());
    }
    public class login implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if( view.txtUser.getText().equalsIgnoreCase("admin")){
                new manhinhchinhController(new manhinhchinh());
                view.dispose();
            }else{
                JOptionPane.showMessageDialog(view, "tài khoản hoặc mật khảu không chính xác");
            }
        }
        
    }
    public static void main(String[] args) {
        new loginController(new LoginView());
    }
}
