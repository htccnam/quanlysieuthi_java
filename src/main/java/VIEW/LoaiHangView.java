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

public class LoaiHangView extends JPanel { 
    public JTextField txtMaLoai = new JTextField();
    public JTextField txtTenLoai = new JTextField();

    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");

    public JTable table;
    public DefaultTableModel tableModel;

    public LoaiHangView() {
        setLayout(null);
        
        JLabel title = new JLabel("QUẢN LÝ PHÂN LOẠI HÀNG");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(400, 20, 400, 30);
        add(title);

        addLabel("Mã Loại:", 300, 80);  addInput(txtMaLoai, 400, 80);
        addLabel("Tên Loại:", 300, 130); addInput(txtTenLoai, 400, 130);

        int yBtn = 180;
        btnAdd.setBounds(300, yBtn, 90, 30);
        btnEdit.setBounds(410, yBtn, 90, 30);
        btnDelete.setBounds(520, yBtn, 90, 30);
        btnReset.setBounds(630, yBtn, 90, 30);
        
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);

        String[] cols = {"Mã Loại", "Tên Loại"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(100, 240, 900, 400); 
        add(sp);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 80, 25);
        add(lb);
    }
    private void addInput(JTextField field, int x, int y) {
        field.setBounds(x, y, 300, 25);
        add(field);
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
}