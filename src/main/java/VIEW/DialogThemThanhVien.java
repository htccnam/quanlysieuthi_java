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
        
        // Load danh sách cho hạng mặc định là bạc
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


        cboHang.addActionListener(e -> {
            String hang = cboHang.getSelectedItem().toString();
            loadKhachHangByHang(hang);
        });
        

        btnCapNhat.addActionListener(e -> {
            if (cboKhachHang.getItemCount() == 0 || cboKhachHang.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng nào được chọn!");
                return;
            }
            

            String selectedText = cboKhachHang.getSelectedItem().toString();
            String[] parts = selectedText.split(" - ");
            if (parts.length >= 2) {
                selectedMaKH = parts[0];

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
        
        if (hang.equals("Bạc")) {

            listKH = dao.getKhachHangTheoDieuKien(0, 500000);
        } else if (hang.equals("Vàng")) {

            listKH = dao.getKhachHangTheoDieuKien(1000000, 3000000);
        } else if (hang.equals("Kim Cương")) {

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