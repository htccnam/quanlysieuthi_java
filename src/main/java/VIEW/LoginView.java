/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

/**
 *
 * @author VŨ HÙNG HẢI
 */
import javax.swing.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    // Để public để Controller có thể truy cập trực tiếp (hoặc dùng getter)
    public JTextField txtUser = new JTextField();
    public JPasswordField txtPass = new JPasswordField();
    public JButton btnLogin = new JButton("Đăng nhập");
    public JButton btnRegister = new JButton("Thoát"); // Đổi nút đăng ký thành Thoát hoặc để ẩn đi tùy bạn

    public LoginView() {
        setTitle("Đăng nhập Hệ thống");
        setSize(350, 200);
        setLayout(null);
        setLocationRelativeTo(null); // Căn giữa màn hình

        addLabel("Tài khoản:", 30, 30); 
        addText(txtUser, 100, 30);
        
        addLabel("Mật khẩu:", 30, 70); 
        addText(txtPass, 100, 70);

        btnLogin.setBounds(50, 120, 100, 30);
        btnRegister.setBounds(180, 120, 100, 30);
        
        add(btnLogin); 
        add(btnRegister);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addLabel(String t, int x, int y){
        JLabel lb = new JLabel(t); 
        lb.setBounds(x, y, 70, 25); 
        add(lb);
    }
    
    private void addText(JComponent t, int x, int y){
        t.setBounds(x, y, 180, 25); 
        add(t);
    }

    // Các hàm để Controller gọi
    public void addLoginListener(ActionListener l){ 
        btnLogin.addActionListener(l); 
    }
    
    public void addRegisterListener(ActionListener l){ 
        btnRegister.addActionListener(l); 
    }

    public void showMsg(String m){ 
        JOptionPane.showMessageDialog(this, m); 
    }
}