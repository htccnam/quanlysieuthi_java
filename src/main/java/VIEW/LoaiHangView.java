/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VŨ HÙNG HẢI
 */


public class LoaiHangView extends JPanel { 
    public JTextField txtMaLoai = new JTextField();
    public JTextField txtTenLoai = new JTextField();
    public JTextField txtTimKiem = new  JTextField();
    public JButton btnTimKiem = new JButton("Tìm Kiếm");
    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");
    public JTable table;
    public DefaultTableModel tableModel;
    
    public LoaiHangView(){
        setLayout(null);
        
        JLabel title  = new JLabel("QUẢN LÝ PHÂN LOẠI HÀNG");
        title.setFont(new Font("Arial",Font.BOLD,24));
        title.setBounds(300, 20, 400, 30);
        add(title);
        
        addLabel("Mã Loại",200,80); addInput(txtMaLoai,300,80);
        addLabel("Tên Loại",200,130); addInput(txtTenLoai,300,130);
        
        int yBtn = 180;
        btnAdd.setBounds(200, yBtn, 90, 30);
        btnEdit.setBounds(310, yBtn, 90, 30);
        btnDelete.setBounds(420, yBtn, 90, 30);
        btnReset.setBounds(530, yBtn, 90, 30);
        
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);
        
        JLabel lbTim = new JLabel("Từ khóa:");
        lbTim.setBounds(100, 230, 80, 30);
        add(lbTim);
        
        txtTimKiem.setBounds(180, 230, 350, 30); 
        add(txtTimKiem);
        
        btnTimKiem.setBounds(540, 230, 100, 30); 
        add(btnTimKiem);
        
        
        String[] cols = {"Mã Loại", "Tên Loại"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        
        sp.setBounds(50, 280, 800, 350); 
        add(sp);
        
        
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x,y,80,25);
        add(lb);
    }

    private void addInput(JTextField field, int x, int y) {
           field.setBounds(x, y, 300, 25); 
           add(field);
       }
    
    public void showMessage(String msg) { 
        JOptionPane.showMessageDialog(this, msg); 
    }
    public void addAddListener(ActionListener l){
        btnAdd.addActionListener(l);
    }
    public void addEditListener(ActionListener l){
        btnEdit.addActionListener(l);
    }
    public void addDeleteListener(ActionListener l){
        btnDelete.addActionListener(l);
    }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
    public void addSearchListener(ActionListener l) { btnTimKiem.addActionListener(l); }
}