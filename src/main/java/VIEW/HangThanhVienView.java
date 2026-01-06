package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class HangThanhVienView extends JPanel {

    // --- COMPONENT PUBLIC (Để Controller gọi) ---
    // 1. Phần Tra cứu & Quy định
    public JComboBox<String> cboKhachHang; 
    public JLabel lblTongChiTieuValue;
    public JButton btnCheck;

    // 2. Phần Danh sách xếp hạng chính thức
    public DefaultTableModel modelXepHang;
    public JTable tblXepHang;
    
    // 3. Nút chức năng CRUD
    public JButton btnThem, btnXoa;

    // --- STYLE ---
    private final Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontText = new Font("Segoe UI", Font.PLAIN, 14);
    private final Color colorPrimary = new Color(0, 102, 204); 
    private final Color colorSuccess = new Color(40, 167, 69); 
    private final Color colorDanger = new Color(220, 53, 69);
    private final Color bgPanel = new Color(245, 248, 250);

    public HangThanhVienView() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(bgPanel);
        this.setBorder(new EmptyBorder(15, 15, 15, 15));

        // ====================================================================
        // PHẦN 1: TOP PANEL (QUY ĐỊNH & TRA CỨU) - Giữ nguyên chức năng cũ
        // ====================================================================
        JPanel pnlTop = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlTop.setOpaque(false);
        pnlTop.setPreferredSize(new Dimension(0, 160));

        // --- Cột Trái: Bảng Quy Định ---
        JPanel pnlRules = createStyledPanel("Quy Định Hạng");
        String[] columnsRules = {"Tên Hạng", "Mức Chi Tiêu", "Quyền Lợi"};
        Object[][] dataRules = {
            {"Bạc (Silver)", "  500.000đ -> 1 Triệu ", "Giảm 2%"},
            {"Vàng (Gold)", " 1 Triệu -> 3 Triệu ", "Giảm 5%"},
            {"Kim Cương (VIP)", "> 3 Triệu", "Giảm 10%"}
        };
        JTable tblRules = new JTable(new DefaultTableModel(dataRules, columnsRules));
        styleTable(tblRules);
        tblRules.setEnabled(false);
        pnlRules.add(new JScrollPane(tblRules));

        // --- Cột Phải: Tra Cứu Chi Tiêu (Check tổng tiền) ---
        JPanel pnlLookup = createStyledPanel("Tra Cứu Chi Tiêu Nhanh");
        JPanel pnlLookupContent = new JPanel(new GridBagLayout());
        pnlLookupContent.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Dòng 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblChon = new JLabel("Khách Hàng:");
        lblChon.setFont(fontLabel);
        pnlLookupContent.add(lblChon, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setFont(fontText);
        cboKhachHang.setBackground(Color.WHITE);
        pnlLookupContent.add(cboKhachHang, gbc);
        
        // Dòng 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        btnCheck = createButton("Kiểm tra", colorPrimary);
        btnCheck.setPreferredSize(new Dimension(100, 30));
        pnlLookupContent.add(btnCheck, gbc);
        
        gbc.gridx = 1; 
        lblTongChiTieuValue = new JLabel("0 VNĐ");
        lblTongChiTieuValue.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTongChiTieuValue.setForeground(colorDanger);
        pnlLookupContent.add(lblTongChiTieuValue, gbc);

        pnlLookup.add(pnlLookupContent);
        
        pnlTop.add(pnlRules);
        pnlTop.add(pnlLookup);
        
        this.add(pnlTop, BorderLayout.NORTH);

        // ====================================================================
        // PHẦN 2: CENTER PANEL (DANH SÁCH XẾP HẠNG CHÍNH THỨC)
        // ====================================================================
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        pnlCenter.setOpaque(false);
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("DANH SÁCH THÀNH VIÊN ĐÃ XẾP HẠNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(colorPrimary);
        pnlCenter.add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu (3 Cột: Mã, Tên, Hạng)
        String[] cols = {"Mã Khách Hàng", "Tên Khách Hàng", "Hạng Thành Viên"};
        modelXepHang = new DefaultTableModel(cols, 0);
        tblXepHang = new JTable(modelXepHang);
        styleTable(tblXepHang);
        
        pnlCenter.add(new JScrollPane(tblXepHang), BorderLayout.CENTER);
        
        this.add(pnlCenter, BorderLayout.CENTER);

        // ====================================================================
        // PHẦN 3: BOTTOM PANEL (NÚT CHỨC NĂNG CRUD)
        // ====================================================================
        JPanel pnlBot = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBot.setOpaque(false);
        pnlBot.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        btnThem = createButton("Thêm Xếp Hạng", colorSuccess);
        btnXoa = createButton("Xóa Xếp Hạng", colorDanger);
        
        pnlBot.add(btnThem);
        pnlBot.add(btnXoa);
        
        this.add(pnlBot, BorderLayout.SOUTH);
    }

    // --- HELPER METHODS ---
    private JPanel createStyledPanel(String title) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                " " + title + " ", 
                TitledBorder.LEADING, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 15), colorPrimary
        );
        pnl.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 15, 10, 15)));
        return pnl;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(fontText);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(232, 242, 255));
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(colorPrimary);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}