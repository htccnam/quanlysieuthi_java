package VIEW;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import MODEL.KhachHang;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class KhachHangView extends JPanel {
    // Thêm các TextField mới
    private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtNgaySinh, txtTimKiem;
    private JTextField txtDiemTichLuy; 
    
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;

    public KhachHangView() {
        initComponents(); 
    }
    
    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- PANEL NHẬP LIỆU (Tăng số dòng lên 9) ---
        JPanel pnlInput = new JPanel(new GridLayout(9, 2, 10, 10)); 
        
        txtMaKH = new JTextField();
        txtHoTen = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtNgaySinh = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        
        // Khởi tạo các ô mới
        txtDiemTichLuy = new JTextField("0"); // Mặc định 0
        

        pnlInput.add(new JLabel("Mã Khách Hàng:")); pnlInput.add(txtMaKH);
        pnlInput.add(new JLabel("Họ và Tên:")); pnlInput.add(txtHoTen);
        pnlInput.add(new JLabel("Số Điện Thoại:")); pnlInput.add(txtSDT);
        pnlInput.add(new JLabel("Giới Tính:")); pnlInput.add(cbGioiTinh);
        pnlInput.add(new JLabel("Email:")); pnlInput.add(txtEmail);
        pnlInput.add(new JLabel("Ngày Sinh (yyyy-MM-dd):")); pnlInput.add(txtNgaySinh);
        
        // Thêm vào giao diện
        pnlInput.add(new JLabel("Điểm Tích Lũy:")); pnlInput.add(txtDiemTichLuy);
       

        // --- PANEL CHỨC NĂNG ---
        JPanel pnlFunc = new JPanel(new BorderLayout(10, 10));
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        
        pnlButtons.add(btnThem); pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa); pnlButtons.add(btnLamMoi);
        
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtTimKiem = new JTextField(15);
        btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Tìm theo tên/mã:"));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlFunc.add(pnlButtons, BorderLayout.CENTER);
        pnlFunc.add(pnlSearch, BorderLayout.SOUTH);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlFunc, BorderLayout.SOUTH);
        
        this.add(pnlTop, BorderLayout.NORTH);

        // --- BẢNG DỮ LIỆU (Thêm cột mới) ---
        String[] columnNames = {"Mã KH", "Họ Tên", "SĐT", "Giới Tính", "Email", "Ngày Sinh", "Điểm"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblKhachHang = new JTable(tableModel);
        this.add(new JScrollPane(tblKhachHang), BorderLayout.CENTER);
    }

    // --- GET DỮ LIỆU TỪ FORM ---
    public KhachHang getKhachHangFromForm() throws Exception {
        String ma = txtMaKH.getText().trim();
        String ten = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String gioitinh = cbGioiTinh.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        String strNgaySinh = txtNgaySinh.getText().trim();
        
        // Lấy dữ liệu mới
        String strDiem = txtDiemTichLuy.getText().trim();
        
        
        if (ma.isEmpty() || ten.isEmpty()) throw new Exception("Vui lòng nhập Mã và Tên!");

        LocalDate ngaySinh = null;
        if (!strNgaySinh.isEmpty()) {
            try {
                ngaySinh = LocalDate.parse(strNgaySinh);
            } catch (DateTimeParseException e) {
                throw new Exception("Ngày sinh sai định dạng (yyyy-MM-dd)!");
            }
        }
        
        int diem = 0;
        try {
            if(!strDiem.isEmpty()) diem = Integer.parseInt(strDiem);
        } catch (NumberFormatException e) {
            throw new Exception("Điểm tích lũy phải là số nguyên!");
        }

        return new KhachHang(ma, ten, sdt, gioitinh, email, ngaySinh, diem);
    }
    
    public String getTuKhoaTimKiem() { return txtTimKiem.getText().trim(); }
    public String getMaKHDangChon() { return txtMaKH.getText().trim(); }

    // --- LISTENER ---
    public void addThemListener(ActionListener al) { btnThem.addActionListener(al); }
    public void addSuaListener(ActionListener al) { btnSua.addActionListener(al); }
    public void addXoaListener(ActionListener al) { btnXoa.addActionListener(al); }
    public void addLamMoiListener(ActionListener al) { btnLamMoi.addActionListener(al); }
    public void addTimKiemListener(ActionListener al) { btnTimKiem.addActionListener(al); }
    public void addTableMouseListener(MouseListener ml) { tblKhachHang.addMouseListener(ml); }

    // --- HIỂN THỊ ---
    public void hienThiBang(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang k : list) {
            tableModel.addRow(new Object[]{
                k.getMaKH(), k.getHoTen(), k.getSdt(), 
                k.getGioiTinh(), k.getEmail(), k.getNgaySinh(),
                k.getDiemtichluy()
            });
        }
    }
    
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
            
            // Đổ dữ liệu mới
            txtDiemTichLuy.setText(tblKhachHang.getValueAt(row, 6).toString());
            
           
        }
    }

    public void resetForm() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
        cbGioiTinh.setSelectedIndex(0);
        
        txtDiemTichLuy.setText("0");
        
        
        txtMaKH.setEnabled(true);
        tblKhachHang.clearSelection();
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public int showConfirm(String msg) { return JOptionPane.showConfirmDialog(this, msg); }
}