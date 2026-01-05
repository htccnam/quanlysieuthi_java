package VIEW;

import MODEL.KhachHang;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class KhachHangView extends JPanel {
    // Các components
    private JTextField txtMaKH, txtHoTen, txtSDT, txtEmail, txtNgaySinh, txtTimKiem;
    private JTextField txtDiemTichLuy; 
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;


    private final Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font fontButton = new Font("Segoe UI", Font.BOLD, 13);
    

    private final Color colorPrimary = new Color(0, 102, 204); // Xanh dương đậm
    private final Color colorSuccess = new Color(40, 167, 69); // Xanh lá
    private final Color colorDanger = new Color(220, 53, 69);  // Đỏ
    private final Color colorWarning = new Color(255, 193, 7); // Vàng cam

    public KhachHangView() {
        initComponents(); 
    }
    
    private void initComponents() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(245, 248, 250)); // Màu nền xám nhạt dịu mắt
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlTop = new JPanel(new BorderLayout(15, 15));
        pnlTop.setOpaque(false);


        TitledBorder borderTitle = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                " Thông Tin Khách Hàng ", 
                TitledBorder.LEADING, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 16), colorPrimary
        );
        
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(Color.WHITE);
        pnlInput.setBorder(BorderFactory.createCompoundBorder(borderTitle, new EmptyBorder(15, 20, 15, 20)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Khoảng cách giữa các ô
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // -- Cột 1 --
        addInputRow(pnlInput, gbc, 0, "Mã Khách Hàng:", txtMaKH = createTextField());
        addInputRow(pnlInput, gbc, 1, "Họ và Tên:", txtHoTen = createTextField());
        addInputRow(pnlInput, gbc, 2, "Số Điện Thoại:", txtSDT = createTextField());
        addInputRow(pnlInput, gbc, 3, "Giới Tính:", cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"}));
        styleComboBox(cbGioiTinh);

        // -- Cột 2 -- (Dùng gbc để đẩy sang bên phải)
        addInputRow2(pnlInput, gbc, 0, "Email:", txtEmail = createTextField());
        addInputRow2(pnlInput, gbc, 1, "Ngày Sinh (yyyy-MM-dd):", txtNgaySinh = createTextField());
        addInputRow2(pnlInput, gbc, 2, "Điểm Tích Lũy:", txtDiemTichLuy = createTextField());
        txtDiemTichLuy.setText("0");

        // --- 2. PANEL CHỨC NĂNG (CENTER của Top) ---
        JPanel pnlFunc = new JPanel(new BorderLayout(10, 10));
        pnlFunc.setOpaque(false);
        

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        pnlButtons.setOpaque(false);
        
        btnThem = createButton("Thêm", "plus.png", colorPrimary);
        btnSua = createButton("Sửa", "edit.png", colorWarning);
        btnXoa = createButton("Xóa", "delete.png", colorDanger);
        btnLamMoi = createButton("Làm mới", "refresh.png", new Color(108, 117, 125));
       

        pnlButtons.add(btnThem); 
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa); 
        pnlButtons.add(btnLamMoi);
        
        

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSearch.setOpaque(false);
        JLabel lblTim = new JLabel("Tìm kiếm:");
        lblTim.setFont(fontLabel);
        txtTimKiem = createTextField();
        txtTimKiem.setPreferredSize(new Dimension(200, 35));
        btnTimKiem = createButton("Tìm", "search.png", colorPrimary);
        
        pnlSearch.add(lblTim);
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlFunc.add(pnlButtons, BorderLayout.CENTER);
        pnlFunc.add(pnlSearch, BorderLayout.SOUTH);


        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlFunc, BorderLayout.SOUTH);
        
        this.add(pnlTop, BorderLayout.NORTH);

        // --- 3. BẢNG DỮ LIỆU (CENTER) ---
        String[] columnNames = {"Mã KH", "Họ Tên", "SĐT", "Giới Tính", "Email", "Ngày Sinh", "Điểm"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblKhachHang = new JTable(tableModel);
        
        // Style cho bảng
        tblKhachHang.setRowHeight(30); // Chiều cao hàng
        tblKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblKhachHang.setSelectionBackground(new Color(232, 242, 255));
        tblKhachHang.setSelectionForeground(Color.BLACK);
        tblKhachHang.setGridColor(new Color(230, 230, 230));
        
        // Style cho Header (Tiêu đề bảng)
        JTableHeader header = tblKhachHang.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(colorPrimary);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Căn giữa nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            tblKhachHang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        this.add(scrollPane, BorderLayout.CENTER);
    }


    

    private JTextField createTextField() {
        JTextField txt = new JTextField(18); // Độ rộng
        txt.setFont(fontInput);
        txt.setPreferredSize(new Dimension(200, 30)); // Chiều cao cố định
        return txt;
    }

    private JButton createButton(String text, String iconName, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(fontButton);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 35)); // Kích thước nút
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    // Hàm style ComboBox
    private void styleComboBox(JComboBox box) {
        box.setFont(fontInput);
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(200, 30));
    }

    // Helper thêm dòng nhập liệu (Cột trái)
    private void addInputRow(JPanel p, GridBagConstraints gbc, int row, String labelText, JComponent input) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.weightx = 0; // Không giãn
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(fontLabel);
        p.add(lbl, gbc);
        
        gbc.gridx = 1; 
        gbc.weightx = 0.5; // Giãn nhẹ
        p.add(input, gbc);
    }

    // Helper thêm dòng nhập liệu (Cột phải)
    private void addInputRow2(JPanel p, GridBagConstraints gbc, int row, String labelText, JComponent input) {
        gbc.gridx = 2; gbc.gridy = row; // Cột bắt đầu từ 2
        gbc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(fontLabel);
        p.add(lbl, gbc);
        
        gbc.gridx = 3; 
        gbc.weightx = 0.5;
        p.add(input, gbc);
    }

    // --- CÁC HÀM GET DỮ LIỆU TỪ FORM (LOGIC GIỮ NGUYÊN) ---> Hàm đóng gói dữ liệu đầu vào
    public KhachHang getKhachHangFromForm() throws Exception {
        String ma = txtMaKH.getText().trim();
        String ten = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String gioitinh = cbGioiTinh.getSelectedItem().toString();
        String email = txtEmail.getText().trim();
        String strNgaySinh = txtNgaySinh.getText().trim();
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
            
            txtDiemTichLuy.setText(tblKhachHang.getValueAt(row, 6).toString());
            
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
        txtDiemTichLuy.setText("0");
        
        txtMaKH.setEnabled(true);
        tblKhachHang.clearSelection();
    }
    
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public int showConfirm(String msg) { return JOptionPane.showConfirmDialog(this, msg); }
}