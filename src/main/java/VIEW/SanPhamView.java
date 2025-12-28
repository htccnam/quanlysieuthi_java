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
import java.awt.event.ActionListener;

public class SanPhamView extends JFrame {
    // Các trường nhập liệu khớp với bảng SanPham
    public JTextField txtMaSP = new JTextField();
    public JTextField txtTenSP = new JTextField();
    public JTextField txtMaLoai = new JTextField();
    public JTextField txtMaTH = new JTextField();
    public JTextField txtSoLuong = new JTextField();
    public JTextField txtGiaNhap = new JTextField();
    public JTextField txtGiaBan = new JTextField();
    public JTextField txtDVT = new JTextField();

    public JButton btnAdd = new JButton("Thêm");
    // Khai báo nút sửa, xóa, lưu...

    public JTable table;
    public DefaultTableModel tableModel;

    public SanPhamView() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(900, 600);
        setLayout(null);
        
        // Bạn tự căn chỉnh tọa độ (setBounds) cho các TextField trên nhé
        // Ví dụ:
        addLabel("Mã SP:", 50, 20); add(txtMaSP); txtMaSP.setBounds(150, 20, 200, 25);
        addLabel("Tên SP:", 50, 60); add(txtTenSP); txtTenSP.setBounds(150, 60, 200, 25);
        // ... Làm tiếp cho giá, số lượng ...

        btnAdd.setBounds(50, 300, 100, 30); add(btnAdd);

        // Table
        String[] cols = {"Mã SP", "Tên SP", "Loại", "Thương hiệu", "Số lượng", "Giá nhập", "Giá bán", "ĐVT"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 350, 840, 200);
        add(sp);

        setVisible(true);
    }
    
    void addLabel(String s, int x, int y) {
        JLabel l = new JLabel(s); l.setBounds(x, y, 100, 25); add(l);
    }
    
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    // Thêm các listener khác...
}