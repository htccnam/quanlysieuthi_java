package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class HangThanhVienView extends JPanel {

    // --- CÁC COMPONENT PUBLIC (Để Controller gọi) ---
    public DefaultTableModel modelBac, modelVang, modelKimCuong;
    public JTable tblBac, tblVang, tblKC; // Biến bảng để Controller lấy dòng chọn
    
    public JComboBox<String> cboKhachHang; 
    public JLabel lblTongChiTieuValue;
    public JButton btnCheck;
    
    // Khai báo đủ cặp nút Thêm - Xóa cho từng hạng
    public JButton btnThemBac, btnXoaBac;
    public JButton btnThemVang, btnXoaVang;
    public JButton btnThemKC, btnXoaKC;

    // --- STYLE CONSTANTS (Giao diện đẹp) ---
    private final Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontText = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font fontButton = new Font("Segoe UI", Font.BOLD, 13);
    
    private final Color colorPrimary = new Color(0, 102, 204); // Xanh dương đậm
    private final Color colorSuccess = new Color(40, 167, 69); // Xanh lá (Nút Thêm)
    private final Color colorDanger = new Color(220, 53, 69);  // Đỏ (Nút Xóa)
    private final Color bgPanel = new Color(245, 248, 250);    // Nền xám nhạt

    public HangThanhVienView() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(bgPanel);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ====================================================================
        // PHẦN 1: PANEL TRÊN CÙNG (Gồm Quy định & Tra cứu)
        // ====================================================================
        JPanel pnlTopContainer = new JPanel(new GridLayout(1, 2, 20, 0)); 
        pnlTopContainer.setOpaque(false);
        pnlTopContainer.setPreferredSize(new Dimension(0, 180));

        // --- Cột Trái: Bảng Quy Định ---
        JPanel pnlRules = createStyledPanel("Quy Định Hạng Thành Viên");
        String[] columnsRules = {"Tên Hạng", "Mức Chi Tiêu", "Quyền Lợi"};
        Object[][] dataRules = {
            {"Thành viên mới", "0 - 500.000đ", "Không có"},
            {"Bạc (Silver)", "> 500.000đ", "Giảm 2%"},
            {"Vàng (Gold)", "> 1 Triệu", "Giảm 5%"},
            {"Kim Cương (VIP)", "> 3 Triệu", "Giảm 10%"}
        };
        JTable tblRules = new JTable(new DefaultTableModel(dataRules, columnsRules));
        styleTable(tblRules);
        tblRules.setEnabled(false); 
        pnlRules.add(new JScrollPane(tblRules));

        // --- Cột Phải: Tra Cứu Chi Tiêu ---
        JPanel pnlLookup = createStyledPanel("Tra Cứu Chi Tiêu Khách Hàng");
        JPanel pnlLookupContent = new JPanel(new GridBagLayout());
        pnlLookupContent.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Dòng 1: Chọn khách hàng
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblChon = new JLabel("Khách Hàng:");
        lblChon.setFont(fontLabel);
        pnlLookupContent.add(lblChon, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1;
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setFont(fontText);
        cboKhachHang.setPreferredSize(new Dimension(200, 35));
        cboKhachHang.setBackground(Color.WHITE);
        pnlLookupContent.add(cboKhachHang, gbc);
        
        // Dòng 2: Nút kiểm tra & Kết quả
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        btnCheck = createButton("Kiểm tra", colorPrimary);
        pnlLookupContent.add(btnCheck, gbc);
        
        gbc.gridx = 1; 
        lblTongChiTieuValue = new JLabel("0 VNĐ");
        lblTongChiTieuValue.setFont(new Font("Segoe UI", Font.BOLD, 22)); 
        lblTongChiTieuValue.setForeground(colorDanger);
        pnlLookupContent.add(lblTongChiTieuValue, gbc);

        pnlLookup.add(pnlLookupContent);

        pnlTopContainer.add(pnlRules);
        pnlTopContainer.add(pnlLookup);
        
        this.add(pnlTopContainer, BorderLayout.NORTH);

        // ====================================================================
        // PHẦN 2: TAB QUẢN LÝ (CENTER)
        // ====================================================================
        JTabbedPane tabManager = new JTabbedPane();
        tabManager.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabManager.setBackground(Color.WHITE);

        // Khởi tạo Model (Chỉ 2 cột Mã và Tên)
        String[] cols = {"Mã Khách Hàng", "Tên Khách Hàng"};
        modelBac = new DefaultTableModel(cols, 0);
        modelVang = new DefaultTableModel(cols, 0);
        modelKimCuong = new DefaultTableModel(cols, 0);

        // Khởi tạo các nút chức năng (Thêm - Xóa)
        btnThemBac = createButton("Thêm vào Bạc", colorSuccess);
        btnXoaBac = createButton("Hủy Hạng Bạc", colorDanger);
        
        btnThemVang = createButton("Thêm vào Vàng", colorSuccess);
        btnXoaVang = createButton("Hủy Hạng Vàng", colorDanger);
        
        btnThemKC = createButton("Thêm vào VIP", colorSuccess);
        btnXoaKC = createButton("Hủy Hạng VIP", colorDanger);

        // Tạo các Tab chứa bảng và nút
        tabManager.addTab("Hạng Bạc (Silver)", createTierTab(modelBac, btnThemBac, btnXoaBac));
        tabManager.addTab("Hạng Vàng (Gold)", createTierTab(modelVang, btnThemVang, btnXoaVang));
        tabManager.addTab("Hạng Kim Cương (VIP)", createTierTab(modelKimCuong, btnThemKC, btnXoaKC));

        this.add(tabManager, BorderLayout.CENTER);
    }

    // --- HÀM HỖ TRỢ TẠO GIAO DIỆN ---

    private JPanel createStyledPanel(String title) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                " " + title + " ", 
                TitledBorder.LEADING, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 16), colorPrimary
        );
        pnl.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 15, 10, 15)));
        return pnl;
    }

    // Tạo nội dung cho từng Tab (Bảng + Nút Thêm + Nút Xóa)
    private JPanel createTierTab(DefaultTableModel model, JButton btnAdd, JButton btnDel) {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Bảng
        JTable tbl = new JTable(model);
        styleTable(tbl);
        
        // Gán tham chiếu bảng ra biến ngoài để Controller dùng
        if (model == modelBac) tblBac = tbl;
        if (model == modelVang) tblVang = tbl;
        if (model == modelKimCuong) tblKC = tbl;
        
        // Panel chứa nút chức năng
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.add(btnAdd); // Nút Thêm
        pnlBtn.add(btnDel); // Nút Xóa
        
        pnl.add(new JScrollPane(tbl), BorderLayout.CENTER);
        pnl.add(pnlBtn, BorderLayout.SOUTH);
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
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(fontButton);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}