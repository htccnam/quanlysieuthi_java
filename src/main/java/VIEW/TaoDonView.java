package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaoDonView extends JPanel {

    // ===== Components cần controller dùng =====
    private JTextField txtMaDon, txtNgayGD, txtTimKiem, txtTenSP;
    private JComboBox<String> cboBanHang, cboThanhToan, cboMaNV, cboMaKM;
    private JTable table;
    private JLabel lblTongTien, lblTamTinh, lblKM;
    private JSpinner spinner;

    public TaoDonView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        add(panelThongTinChung());
        add(Box.createVerticalStrut(15));
        add(panelChiTiet());
        add(Box.createVerticalStrut(15));
        add(panelTongTien());
    }

    // ================= THÔNG TIN CHUNG =================
    private JPanel panelThongTinChung() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Thông tin chung (Đơn hàng)"));
        p.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtMaDon = new JTextField("DH-2023-001");
        txtNgayGD = new JTextField();
        cboMaNV = new JComboBox<>(new String[]{"--Chọn nhân viên--"});
        cboMaKM = new JComboBox<>(new String[]{"Không áp dụng", "Áp dụng"});
        cboBanHang = new JComboBox<>(new String[]{"Online", "Offline"});
        cboThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});

        g.gridx = 0; g.gridy = 0;
        p.add(new JLabel("Mã đơn hàng"), g);
        
        g.gridx = 1;
        p.add(txtMaDon, g);

        g.gridx = 2;
        p.add(new JLabel("Ngày giao dịch"), g);
        
        g.gridx = 3;
        p.add(txtNgayGD, g);

        g.gridx = 4;
        p.add(new JLabel("Nhân viên"), g);

        g.gridx = 5;
        p.add(cboMaNV, g);
        
        g.gridx = 0; g.gridy = 1;
        p.add(new JLabel("Phương thức bán"), g);
        
        g.gridx = 1;
        p.add(cboBanHang, g);

        g.gridx = 2;
        p.add(new JLabel("Thanh toán"), g);
        
        g.gridx = 3;
        p.add(cboThanhToan, g);
        
        g.gridx = 4;
        p.add(new JLabel("Mã khuyến mại"), g);

        g.gridx = 5;
        p.add(cboMaKM, g);

        return p;
    }

    // ================= CHI TIẾT ĐƠN =================
    private JPanel panelChiTiet() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createTitledBorder("Chi tiết đơn hàng"));
        p.setBackground(Color.WHITE);

        // ---- Thanh tìm + thêm ----
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        top.setBackground(Color.WHITE);
        top.add(new JLabel("Tìm sản phẩm:"));
        txtTimKiem = new JTextField(20);
        top.add(txtTimKiem);
        top.add(new JLabel("Tên sản phẩm:"));
        txtTenSP = new JTextField(20);
        txtTenSP.setEditable(false);
        top.add(txtTenSP);
        top.add(new JLabel("Số lượng:"));
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1)); //(value, min, max, stepSize)
        spinner.setPreferredSize(new Dimension(40, 25));
        top.add(spinner);
        JButton btnThem = new JButton("+ Thêm");
        top.add(btnThem);

        // ---- Table ----
        String[] cols = {"Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(32);

        // dữ liệu mẫu
        model.addRow(new Object[]{"SP001", "Laptop Dell XPS 13", 25000000, 1, 25000000});
        model.addRow(new Object[]{"SP045", "Chuột Logitech", 500000, 2, 1000000});

        JScrollPane scroll = new JScrollPane(table);

        p.add(top, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);

        return p;
    }

    // ================= TỔNG TIỀN =================
    private JPanel panelTongTien() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);
        
        lblTongTien = new JLabel("Tổng tiền: 23,400,000 đ");
        lblTamTinh = new JLabel("Tạm tính: 24,500,000 đ");
        lblKM = new JLabel("Giảm giá: 100,000 đ");
        JButton btnLuu = new JButton("Lưu đơn hàng");
        
        lblTongTien.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 16f));
        lblTamTinh.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 16f));
        lblKM.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 16f));
        lblTongTien.setForeground(new Color(33, 150, 243));

        left.add(lblTamTinh);
        left.add(Box.createVerticalStrut(10));
        left.add(lblKM);
        left.add(Box.createVerticalStrut(10));
        left.add(new JSeparator(JSeparator.HORIZONTAL));
        left.add(Box.createVerticalStrut(10));
        left.add(lblTongTien);     
        left.add(Box.createVerticalStrut(10));
        left.add(btnLuu);
             
        p.add(left, BorderLayout.EAST);
        return p;
    }

    // ================= GETTER cho Controller =================
    public JTable getTable() {
        return table;
    }

    public JLabel getLblTongTien() {
        return lblTongTien;
    }

    public JTextField getTxtMaDon() {
        return txtMaDon;
    }
}


