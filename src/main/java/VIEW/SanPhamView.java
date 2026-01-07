package VIEW;

import com.toedter.calendar.JDateChooser; // Import thư viện lịch
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SanPhamView extends JPanel {

    public JTextField txtMaSP = new JTextField();
    public JTextField txtTenSP = new JTextField();
    public JComboBox<Object> cboLoai = new JComboBox<>();
    public JComboBox<Object> cboNCC = new JComboBox<>();
    public JTextField txtXuatXu = new JTextField();
    public JTextField txtSoLuong = new JTextField();
    public JDateChooser txtNgaySX = new JDateChooser();
    public JDateChooser txtHanSD = new JDateChooser(); 
    
    public JComboBox<String> cboTinhTrang = new JComboBox<>(new String[] { "Tốt", "Đã hết hạn" });
    public JTextField txtGiaNhap = new JTextField();
    public JTextField txtGiaBan = new JTextField();
    public JTextField txtDonViTinh = new JTextField();
    
    public JTextField txtTimKiem = new JTextField();

    public JButton btnTimKiem = new JButton("Tìm Kiếm");
    public JButton btnAdd = new JButton("Thêm");
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");
    public JButton btnReset = new JButton("Làm mới");

    public JTable table;
    public DefaultTableModel tableModel;

    public SanPhamView() {
        setLayout(null);
        setBounds(0, 0, 1000, 750); 

        JLabel title = new JLabel("QUẢN LÝ SẢN PHẨM");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLUE);
        title.setBounds(350, 10, 400, 30);
        add(title);


         txtNgaySX.setDateFormatString("dd-MM-yyyy"); 
         txtHanSD.setDateFormatString("dd-MM-yyyy");

        int x1 = 30, yStart = 60, wLabel = 100, wText = 250, gap = 40;
        
        addLabel("Mã SP:", x1, yStart);            add(txtMaSP); txtMaSP.setBounds(x1 + wLabel, yStart, wText, 25);
        addLabel("Tên SP:", x1, yStart + gap);     add(txtTenSP); txtTenSP.setBounds(x1 + wLabel, yStart + gap, wText, 25);
        addLabel("Loại Hàng:", x1, yStart + gap*2); add(cboLoai); cboLoai.setBounds(x1 + wLabel, yStart + gap*2, wText, 25);
        addLabel("Nhà CC:", x1, yStart + gap*3);   add(cboNCC); cboNCC.setBounds(x1 + wLabel, yStart + gap*3, wText, 25);
        addLabel("Xuất Xứ:", x1, yStart + gap*4);  add(txtXuatXu); txtXuatXu.setBounds(x1 + wLabel, yStart + gap*4, wText, 25);
        addLabel("Số Lượng:", x1, yStart + gap*5); add(txtSoLuong); txtSoLuong.setBounds(x1 + wLabel, yStart + gap*5, wText, 25);

        int x2 = 450; 
        
        addLabel("Ngày SX:", x2, yStart);          add(txtNgaySX); txtNgaySX.setBounds(x2 + wLabel, yStart, wText, 25);
        addLabel("Hạn SD:", x2, yStart + gap);     add(txtHanSD); txtHanSD.setBounds(x2 + wLabel, yStart + gap, wText, 25);

        addLabel("Tình Trạng:", x2, yStart + gap*2); add(cboTinhTrang); cboTinhTrang.setBounds(x2 + wLabel, yStart + gap*2, wText, 25);
        addLabel("Giá Nhập:", x2, yStart + gap*3);   add(txtGiaNhap); txtGiaNhap.setBounds(x2 + wLabel, yStart + gap*3, wText, 25);
        addLabel("Giá Bán:", x2, yStart + gap*4);    add(txtGiaBan); txtGiaBan.setBounds(x2 + wLabel, yStart + gap*4, wText, 25);
        addLabel("Đơn Vị:", x2, yStart + gap*5);     add(txtDonViTinh); txtDonViTinh.setBounds(x2 + wLabel, yStart + gap*5, wText, 25);

        int yBtn = 320;
        btnAdd.setBounds(250, yBtn, 100, 35);
        btnEdit.setBounds(370, yBtn, 100, 35);
        btnDelete.setBounds(490, yBtn, 100, 35);
        btnReset.setBounds(610, yBtn, 100, 35);
        
        add(btnAdd); add(btnEdit); add(btnDelete); add(btnReset);

        JLabel lbTim = new JLabel("Tìm kiếm:");
        lbTim.setBounds(150, 370, 80, 30);
        add(lbTim);
        
        txtTimKiem.setBounds(230, 370, 400, 30);
        add(txtTimKiem);
        
        btnTimKiem.setBounds(650, 370, 100, 30);
        add(btnTimKiem);

        String[] cols = {
            "Mã SP", "Tên SP", "Mã Loại", "Mã NCC", "Xuất Xứ", "Số Lượng",
            "Ngày SX", "Hạn SD", "Tình Trạng", "Giá Nhập", "Giá Bán", "Đơn Vị"
        };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 420, 940, 250); 
        add(sp);
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Arial", Font.PLAIN, 14));
        lb.setBounds(x, y, 100, 25);
        add(lb);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
    public void addResetListener(ActionListener l) { btnReset.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnTimKiem.addActionListener(l); }
    public void addTableMouseListener(MouseListener l) { table.addMouseListener(l); }
}