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

public class NhaCungCapView extends JPanel {
    public JTextField txtMaNCC = new JTextField();
    public JTextField txtTenNCC = new JTextField();
    public JTextField txtLoaiHinh = new JTextField();
    public JTextField txtEmail = new JTextField();
    public JTextField txtSDT = new JTextField();
    public JTextField txtDiaChi = new JTextField();
    public JTextField txtTimKiem = new JTextField();
    
    public JButton btnTimKiem = new JButton("Tìm kiếm");
    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");
    
    public JTable table;
    public DefaultTableModel tableModel;
    
    public NhaCungCapView(){
        setLayout(null);
        setBounds(0, 0, 850, 700);
        
        JLabel title = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(280, 20, 400, 30);
        add(title);
        
        addLabel("Mã NCC:", 50, 80);      addInput(txtMaNCC, 150, 80);
        addLabel("Tên NCC:", 50, 130);     addInput(txtTenNCC, 150, 130);
        addLabel("Loại Hình:", 50, 180);   addInput(txtLoaiHinh, 150, 180);
        
        addLabel("Email:", 450, 80);       addInput(txtEmail, 530, 80);
        addLabel("SĐT:", 450, 130);        addInput(txtSDT, 530, 130);
        addLabel("Địa Chỉ:", 450, 180);    addInput(txtDiaChi, 530, 180);
        
        int yBtn = 240; // Vị trí dòng nút
        btnAdd.setBounds(200, yBtn, 90, 30);
        btnEdit.setBounds(310, yBtn, 90, 30);
        btnDelete.setBounds(420, yBtn, 90, 30);
        btnReset.setBounds(530, yBtn, 90, 30);

        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);
        
        JLabel lbTim = new JLabel("Từ khóa:");
        lbTim.setBounds(100, 290, 80, 30);
        add(lbTim);

        txtTimKiem.setBounds(180, 290, 350, 30);
        add(txtTimKiem);

        btnTimKiem.setBounds(540, 290, 100, 30);
        add(btnTimKiem);
        
        String[] cols = {"Mã NCC", "Tên NCC", "Loại Hình", "Email", "SĐT", "Địa Chỉ"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);

        sp.setBounds(30, 340, 800, 300);
        add(sp);
        
    }
    
    // --- HÀM HỖ TRỢ VẼ GIAO DIỆN NHANH ---
    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 100, 25);
        add(lb);
    }

    private void addInput(JTextField field, int x, int y) {
        field.setBounds(x, y, 250, 25); // Chiều rộng 250px
        add(field);
    }

    // --- HÀM HIỆN THÔNG BÁO ---
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // --- CÁC HÀM GẮN SỰ KIỆN (LISTENER) ---
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnTimKiem.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
    
}