package VIEW;

import DAO.HangThanhVienDAO;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DialogThemThanhVien extends JDialog {
    
    private JComboBox<String> cboHang;
    private JComboBox<String> cboKhachHang;
    private JButton btnCapNhat;
    private HangThanhVienDAO dao;
    
    // Biến lưu kết quả để Controller lấy
    private boolean isConfirmed = false;
    private String selectedMaKH = null;
    private String selectedTenKH = null;
    private String selectedHang = null;

    public DialogThemThanhVien(JFrame parent) {
        super(parent, "Thêm Thành Viên Vào Hạng", true);
        this.dao = new HangThanhVienDAO();
        initComponents();
        
        // Load danh sách cho hạng mặc định (Bạc)
        loadKhachHangByHang("Bạc");
    }

    private void initComponents() {
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        
        JPanel pnlCenter = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlCenter.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // 1. Mục Hạng Thành Viên
        pnlCenter.add(new JLabel("1. Chọn Hạng Thành Viên:"));
        cboHang = new JComboBox<>(new String[]{"Bạc", "Vàng", "Kim Cương"});
        pnlCenter.add(cboHang);
        
        // 2. Mục Khách Hàng
        pnlCenter.add(new JLabel("2. Lựa Chọn Khách Hàng (Đủ điều kiện):"));
        cboKhachHang = new JComboBox<>();
        pnlCenter.add(cboKhachHang);
        
        add(pnlCenter, BorderLayout.CENTER);
        
        // Nút Cập Nhật
        btnCapNhat = new JButton("Cập Nhật");
        btnCapNhat.setBackground(new Color(0, 102, 204));
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setPreferredSize(new Dimension(100, 40));
        
        JPanel pnlBot = new JPanel();
        pnlBot.add(btnCapNhat);
        add(pnlBot, BorderLayout.SOUTH);

        // --- SỰ KIỆN ---
        
        // Khi đổi hạng -> Load lại danh sách khách
        cboHang.addActionListener(e -> {
            String hang = cboHang.getSelectedItem().toString();
            loadKhachHangByHang(hang);
        });
        
        // Khi ấn nút Cập nhật
        btnCapNhat.addActionListener(e -> {
            if (cboKhachHang.getItemCount() == 0 || cboKhachHang.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng nào được chọn!");
                return;
            }
            
            // Xử lý chuỗi lấy ra Mã và Tên
            // Format: "MA - TEN (TIEN)"
            String selectedText = cboKhachHang.getSelectedItem().toString();
            String[] parts = selectedText.split(" - ");
            if (parts.length >= 2) {
                selectedMaKH = parts[0];
                // Lấy tên (cắt bỏ phần tiền phía sau)
                selectedTenKH = parts[1].substring(0, parts[1].lastIndexOf("(")).trim();
                selectedHang = cboHang.getSelectedItem().toString();
                isConfirmed = true;
                dispose();
            }
        });
    }

    private void loadKhachHangByHang(String hang) {
        cboKhachHang.removeAllItems();
        Vector<String> listKH = new Vector<>();
        
        // Logic điều kiện tiền (Giống quy định cũ)
        if (hang.equals("Bạc")) {
            // Từ 0đ -> 500.000đ
            listKH = dao.getKhachHangTheoDieuKien(500000, 1000000);
        } else if (hang.equals("Vàng")) {
            // Từ 500k đến dưới 1tr
            listKH = dao.getKhachHangTheoDieuKien(1000000, 3000000);
        } else if (hang.equals("Kim Cương")) {
            // Trên 3tr (đến số rất lớn)
            listKH = dao.getKhachHangTheoDieuKien(3000000, 99999999.9);
        }
        
        if (listKH.isEmpty()) {
            cboKhachHang.addItem("--- Không có khách đủ điều kiện ---");
        } else {
            for (String s : listKH) cboKhachHang.addItem(s);
        }
    }
    
    // Getters
    public boolean isConfirmed() { return isConfirmed; }
    public String getMa() { return selectedMaKH; }
    public String getTen() { return selectedTenKH; }
    public String getHang() { return selectedHang; }
    
    public void showDialog() {
        this.setVisible(true);
    }
}