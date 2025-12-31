package VIEW;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener; // Import thêm MouseListener
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import MODEL.KhachHang;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class KhachHangView extends JPanel {
    private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtNgaySinh, txtTimKiem; // Thêm txtTimKiem
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem; // Thêm nút
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;

    public KhachHangView() {
        initComponents(); 
    }
    
    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- PANEL NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridLayout(6, 2, 10, 10)); 
        
        txtMaKH = new JTextField();
        txtHoTen = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtNgaySinh = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        
        pnlInput.add(new JLabel("Mã Khách Hàng:")); pnlInput.add(txtMaKH);
        pnlInput.add(new JLabel("Họ và Tên:")); pnlInput.add(txtHoTen);
        pnlInput.add(new JLabel("Số Điện Thoại:")); pnlInput.add(txtSDT);
        pnlInput.add(new JLabel("Giới Tính:")); pnlInput.add(cbGioiTinh);
        pnlInput.add(new JLabel("Email:")); pnlInput.add(txtEmail);
        pnlInput.add(new JLabel("Ngày Sinh (yyyy-MM-dd):")); pnlInput.add(txtNgaySinh);

        // --- PANEL CHỨC NĂNG (Nút bấm + Tìm kiếm) ---
        JPanel pnlFunc = new JPanel(new BorderLayout(10, 10));
        
        // Panel chứa nút
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");     // Mới
        btnXoa = new JButton("Xóa");     // Mới
        btnLamMoi = new JButton("Làm mới");
        
        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        
        // Panel Tìm kiếm
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Tìm theo tên/mã:"));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlFunc.add(pnlButtons, BorderLayout.CENTER);
        pnlFunc.add(pnlSearch, BorderLayout.SOUTH);

        // Gom Input và Chức năng lên đầu
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlFunc, BorderLayout.SOUTH);
        
        this.add(pnlTop, BorderLayout.NORTH);

        // --- BẢNG DỮ LIỆU ---
        String[] columnNames = {"Mã KH", "Họ Tên", "SĐT", "Giới Tính", "Email", "Ngày Sinh"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblKhachHang = new JTable(tableModel);
        this.add(new JScrollPane(tblKhachHang), BorderLayout.CENTER);
    }

    // --- CÁC HÀM GET DỮ LIỆU ---
    public KhachHang getKhachHangFromForm() throws Exception {
        String ma = txtMaKH.getText().trim();
        String ten = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String gioitinh = cbGioiTinh.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        String strNgaySinh = txtNgaySinh.getText().trim(); 
        
        if (ma.isEmpty() || ten.isEmpty()) throw new Exception("Vui lòng nhập Mã và Tên!");

        LocalDate ngaySinh = null;
        if (!strNgaySinh.isEmpty()) {
            try {
                ngaySinh = LocalDate.parse(strNgaySinh);
            } catch (DateTimeParseException e) {
                throw new Exception("Ngày sinh sai định dạng (yyyy-MM-dd)!");
            }
        }
        return new KhachHang(ma, ten, sdt, gioitinh, email, ngaySinh);
    }
    
    public String getTuKhoaTimKiem() {
        return txtTimKiem.getText().trim();
    }
    
    public String getMaKHDangChon() {
        return txtMaKH.getText().trim();
    }

    // --- CÁC HÀM GẮN SỰ KIỆN (LISTENER) ---
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addLamMoiListener(ActionListener al) { btnLamMoi.addActionListener(al); }
    public void addTimKiemListener(ActionListener al) { btnTimKiem.addActionListener(al); }
    
    // Sự kiện Click bảng
    public void addTableMouseListener(MouseListener ml) { tblKhachHang.addMouseListener(ml); }

    // --- HÀM HIỂN THỊ ---
    public void hienThiBang(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang k : list) {
            tableModel.addRow(new Object[]{
                k.getMaKH(), k.getHoTen(), k.getSdt(), 
                k.getGioiTinh(), k.getEmail(), k.getNgaySinh()
            });
        }
    }
    
    // Hàm điền ngược dữ liệu từ bảng lên Form (Khi click row)
    public void fillFormFromSelectedRow() {
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
            txtHoTen.setText(tblKhachHang.getValueAt(row, 1).toString());
            txtSDT.setText(tblKhachHang.getValueAt(row, 2).toString());
            cbGioiTinh.setSelectedItem(tblKhachHang.getValueAt(row, 3).toString());
            txtEmail.setText(tblKhachHang.getValueAt(row, 4).toString());
            
            Object dob = tblKhachHang.getValueAt(row, 5);
            txtNgaySinh.setText(dob != null ? dob.toString() : "");
            
            // Khóa mã khách hàng khi sửa (để tránh sửa Primary Key)
            txtMaKH.setEnabled(false);
        }
    }

    public void resetForm() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtMaKH.setEnabled(true); // Mở lại khóa mã
        tblKhachHang.clearSelection();
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public int showConfirm(String msg) { return JOptionPane.showConfirmDialog(this, msg); }
}