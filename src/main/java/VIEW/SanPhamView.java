package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import com.toedter.calendar.JDateChooser; // Import thư viện Lịch

// Import Model để dùng cho ComboBox
import MODEL.LoaiHang;
import MODEL.NhaCungCap;

public class SanPhamView extends JPanel {
    public JTextField txtMaSP = new JTextField();
    public JTextField txtTenSP = new JTextField();
    
    // Thay TextField bằng ComboBox
    public JComboBox<LoaiHang> cbbLoaiHang = new JComboBox<>();
    public JComboBox<NhaCungCap> cbbNhaCungCap = new JComboBox<>();
    
    public JTextField txtXuatXu = new JTextField();
    public JTextField txtSoLuong = new JTextField();
    
    // Thay TextField bằng JDateChooser (Lịch)
    public JDateChooser dateNgaySX = new JDateChooser();
    public JDateChooser dateHanSD = new JDateChooser();
    
    // ComboBox Tình trạng (Cố định)
    public JComboBox<String> cbbTinhTrang = new JComboBox<>(new String[]{"Tốt", "Đã hết hạn"});
    
    public JTextField txtGiaNhap = new JTextField();
    public JTextField txtGiaBan = new JTextField();
    public JTextField txtDVT = new JTextField();

    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");

    public JTable table;
    public DefaultTableModel tableModel;

    public SanPhamView() {
        setLayout(null);
        
        JLabel title = new JLabel("QUẢN LÝ DANH SÁCH SẢN PHẨM");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(350, 10, 500, 30);
        add(title);

        // --- CỘT 1 ---
        int col1_x = 20, col1_val = 100;
        addLabel("Mã SP:", col1_x, 60);     addInput(txtMaSP, col1_val, 60);
        addLabel("Tên SP:", col1_x, 100);    addInput(txtTenSP, col1_val, 100);
        addLabel("Loại:", col1_x, 140);      addComponent(cbbLoaiHang, col1_val, 140); // Dùng hàm addComponent
        addLabel("NCC:", col1_x, 180);       addComponent(cbbNhaCungCap, col1_val, 180);

        // --- CỘT 2 ---
        int col2_x = 320, col2_val = 400;
        addLabel("Xuất xứ:", col2_x, 60);    addInput(txtXuatXu, col2_val, 60);
        addLabel("Số lượng:", col2_x, 100);  addInput(txtSoLuong, col2_val, 100);
        
        // Setup định dạng ngày tháng hiển thị
        dateNgaySX.setDateFormatString("yyyy-MM-dd");
        dateHanSD.setDateFormatString("yyyy-MM-dd");
        addLabel("Ngày SX:", col2_x, 140);   addComponent(dateNgaySX, col2_val, 140);
        addLabel("Hạn SD:", col2_x, 180);    addComponent(dateHanSD, col2_val, 180);

        // --- CỘT 3 ---
        int col3_x = 620, col3_val = 700;
        addLabel("Tình trạng:", col3_x, 60); addComponent(cbbTinhTrang, col3_val, 60);
        addLabel("Giá nhập:", col3_x, 100);  addInput(txtGiaNhap, col3_val, 100);
        addLabel("Giá bán:", col3_x, 140);   addInput(txtGiaBan, col3_val, 140);
        addLabel("Đơn vị:", col3_x, 180);    addInput(txtDVT, col3_val, 180);

        // Nút bấm
        int yBtn = 240;
        btnAdd.setBounds(300, yBtn, 100, 30);
        btnEdit.setBounds(420, yBtn, 100, 30);
        btnDelete.setBounds(540, yBtn, 100, 30);
        btnReset.setBounds(660, yBtn, 100, 30);
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);

        // Bảng
        String[] cols = {"Mã", "Tên", "Loại", "NCC", "Xuất xứ", "SL", "NSX", "HSD", "Tình trạng", "Giá nhập", "Giá bán", "ĐVT"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 1140, 400);
        add(sp);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text); lb.setBounds(x, y, 80, 25); add(lb);
    }
    private void addInput(JTextField field, int x, int y) {
        field.setBounds(x, y, 200, 25); add(field);
    }
    // Hàm mới để add ComboBox và DateChooser
    private void addComponent(JComponent comp, int x, int y) {
        comp.setBounds(x, y, 200, 25); add(comp);
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
}