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
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class NhaCungCapView extends JPanel {
    public JTextField txtMa = new JTextField();
    public JTextField txtTen = new JTextField();
    public JTextField txtLoaiHinh = new JTextField();
    public JTextField txtEmail = new JTextField();
    public JTextField txtSDT = new JTextField();
    public JTextField txtDiaChi = new JTextField();

    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");

    public JTable table;
    public DefaultTableModel tableModel;

    public NhaCungCapView() {
        setLayout(null);
        
        JLabel title = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(400, 20, 400, 30);
        add(title);

        // Cột 1
        addLabel("Mã NCC:", 50, 80);   addInput(txtMa, 150, 80);
        addLabel("Tên NCC:", 50, 120);  addInput(txtTen, 150, 120);
        addLabel("Loại hình:", 50, 160);addInput(txtLoaiHinh, 150, 160);

        // Cột 2
        addLabel("Email:", 450, 80);    addInput(txtEmail, 550, 80);
        addLabel("SĐT:", 450, 120);     addInput(txtSDT, 550, 120);
        addLabel("Địa chỉ:", 450, 160); addInput(txtDiaChi, 550, 160);

        // Nút bấm
        int yBtn = 220;
        btnAdd.setBounds(250, yBtn, 90, 30);
        btnEdit.setBounds(360, yBtn, 90, 30);
        btnDelete.setBounds(470, yBtn, 90, 30);
        btnReset.setBounds(580, yBtn, 90, 30);
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);

        // Bảng
        String[] cols = {"Mã NCC", "Tên NCC", "Loại hình", "Email", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 280, 1000, 350);
        add(sp);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 80, 25);
        add(lb);
    }
    private void addInput(JTextField field, int x, int y) {
        field.setBounds(x, y, 250, 25);
        add(field);
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
}