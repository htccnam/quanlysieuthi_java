package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class SanPhamView extends JFrame {
    // 8 ô nhập liệu
    public JTextField txtMaSP = new JTextField();
    public JTextField txtTenSP = new JTextField();
    public JTextField txtMaLoai = new JTextField();
    public JTextField txtMaTH = new JTextField();
    public JTextField txtSoLuong = new JTextField();
    public JTextField txtGiaNhap = new JTextField();
    public JTextField txtGiaBan = new JTextField();
    public JTextField txtDVT = new JTextField();

    // 4 Nút chức năng
    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");

    public JTable table;
    public DefaultTableModel tableModel;

    public SanPhamView() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(900, 650);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("QUẢN LÝ SẢN PHẨM");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(300, 10, 300, 30);
        add(title);

        // --- CỘT TRÁI ---
        int y = 60;
        addLabel("Mã SP:", 50, y);      addInput(txtMaSP, 150, y);
        addLabel("Tên SP:", 50, y+=40); addInput(txtTenSP, 150, y);
        addLabel("Mã Loại:", 50, y+=40);addInput(txtMaLoai, 150, y);
        addLabel("Mã TH:", 50, y+=40);  addInput(txtMaTH, 150, y);

        // --- CỘT PHẢI ---
        y = 60; // Reset y để vẽ cột bên phải
        addLabel("Số lượng:", 450, y);   addInput(txtSoLuong, 550, y);
        addLabel("Giá nhập:", 450, y+=40);addInput(txtGiaNhap, 550, y);
        addLabel("Giá bán:", 450, y+=40); addInput(txtGiaBan, 550, y);
        addLabel("Đơn vị:", 450, y+=40);  addInput(txtDVT, 550, y);

        // --- CÁC NÚT BẤM ---
        int btnY = 240;
        btnAdd.setBounds(150, btnY, 100, 35);
        btnEdit.setBounds(270, btnY, 100, 35);
        btnDelete.setBounds(390, btnY, 100, 35);
        btnReset.setBounds(510, btnY, 100, 35);
        
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);

        // --- BẢNG DỮ LIỆU ---
        String[] cols = {"Mã SP", "Tên SP", "Loại", "Thương hiệu", "Số lượng", "Giá nhập", "Giá bán", "ĐVT"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 840, 280);
        add(sp);

        setVisible(true);
    }

    // Hàm phụ trợ để code ngắn gọn hơn
    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setBounds(x, y, 100, 25);
        add(lb);
    }
    private void addInput(JTextField field, int x, int y) {
        field.setBounds(x, y, 200, 25);
        add(field);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Các hàm lắng nghe sự kiện (Controller sẽ gọi)
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
}