/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import com.toedter.calendar.JDateChooser;
import java.awt.Button;
import java.awt.Dimension;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class nhanvien extends JFrame {

    public nhanvien() {
        setSize(600, 700);
        setLayout(null);

        //manhanvien
        JLabel manhanvienJLabel = new JLabel();
        manhanvienJLabel.setBounds(50, 20, 100, 25);
        add(manhanvienJLabel);
        JTextField manhanvienField = new JTextField("nhập mã nhân viên");
        manhanvienField.setBounds(200, 20, 200, 25);
        add(manhanvienField);

        //hoten
        JLabel hotenJLabel = new JLabel();
        hotenJLabel.setBounds(50, 50, 100, 25);
        add(hotenJLabel);
        JTextField hotenField = new JTextField("nhập họ tên");
        hotenField.setBounds(200, 50, 200, 25);
        add(hotenField);

        //ngaysinh
        JLabel ngayJLabel = new JLabel();
        ngayJLabel.setBounds(50, 80, 100, 25);
        add(ngayJLabel);
        JDateChooser ngaysinhChooser = new JDateChooser();
        ngaysinhChooser.setBounds(200, 80, 200, 25);
        add(ngaysinhChooser);
        
        //hoang

    }

    public static void main(String[] args) {
        nhanvien j = new nhanvien();
        j.setVisible(true);
    }

}
