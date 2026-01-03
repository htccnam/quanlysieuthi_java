package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class HangThanhVienView extends JPanel {

    public DefaultTableModel modelBac, modelVang, modelKimCuong;
    public JComboBox<String> cboKhachHang; 
    public JLabel lblTongChiTieuValue;
    public JButton btnCheck;
    
    public JButton btnThemBac, btnXoaBac;
    public JButton btnThemVang, btnXoaVang;
    public JButton btnThemKC, btnXoaKC;

    private final Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fontText = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font fontButton = new Font("Segoe UI", Font.BOLD, 13);
    
    private final Color colorPrimary = new Color(0, 102, 204); // Xanh dương đậm
    private final Color colorSuccess = new Color(40, 167, 69); // Xanh lá
    private final Color colorDanger = new Color(220, 53, 69);  // Đỏ
    private final Color bgPanel = new Color(245, 248, 250);    // Nền xám nhạt

    public HangThanhVienView() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(bgPanel);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlTopContainer = new JPanel(new GridLayout(1, 2, 20, 0)); // Chia đôi màn hình
        pnlTopContainer.setOpaque(false);
        pnlTopContainer.setPreferredSize(new Dimension(0, 180)); // Chiều cao cố định

        // --- Cột Trái: Bảng Quy Định ---
        JPanel pnlRules = createStyledPanel("Quy Định Hạng Thành Viên");
        String[] columnsRules = {"Tên Hạng", "Mức Chi Tiêu", "Quyền Lợi"};
        Object[][] dataRules = {
            {"Thành viên mới", "0 - 3.000.000đ", "Không có"},
            {"Bạc (Silver)", "> 3 Triệu", "Giảm 2%"},
            {"Vàng (Gold)", "> 10 Triệu", "Giảm 5%"},
            {"Kim Cương (VIP)", "> 30 Triệu", "Giảm 10%"}
        };
        JTable tblRules = new JTable(new DefaultTableModel(dataRules, columnsRules));
        styleTable(tblRules); 
        tblRules.setEnabled(false); 
        pnlRules.add(new JScrollPane(tblRules));

        // --- Cột Phải: Tra Cứu Chi Tiêu (Form nhập liệu) ---
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
        lblTongChiTieuValue.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Chữ to
        lblTongChiTieuValue.setForeground(colorDanger);
        pnlLookupContent.add(lblTongChiTieuValue, gbc);

        pnlLookup.add(pnlLookupContent);

        // Add 2 cột vào Top Container
        pnlTopContainer.add(pnlRules);
        pnlTopContainer.add(pnlLookup);
        
        this.add(pnlTopContainer, BorderLayout.NORTH);

        // ====================================================================
        // PHẦN 2: TAB QUẢN LÝ (CENTER)
        // ====================================================================
        JTabbedPane tabManager = new JTabbedPane();
        tabManager.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabManager.setBackground(Color.WHITE);

        // Khởi tạo Model
        String[] cols = {"Mã Khách Hàng", "Tên Khách Hàng"};
        modelBac = new DefaultTableModel(cols, 0);
        modelVang = new DefaultTableModel(cols, 0);
        modelKimCuong = new DefaultTableModel(cols, 0);

        // Khởi tạo nút
        btnThemBac = createButton("Thêm vào Bạc", colorSuccess);
        btnXoaBac = createButton("Xóa khỏi Bạc", colorDanger);
        
        btnThemVang = createButton("Thêm vào Vàng", colorSuccess);
        btnXoaVang = createButton("Xóa khỏi Vàng", colorDanger);
        
        btnThemKC = createButton("Thêm vào VIP", colorSuccess);
        btnXoaKC = createButton("Xóa khỏi VIP", colorDanger);

        // Tạo các Tab
        tabManager.addTab("Hạng Bạc (Silver)", createTierTab(modelBac, btnThemBac, btnXoaBac));
        tabManager.addTab("Hạng Vàng (Gold)", createTierTab(modelVang, btnThemVang, btnXoaVang));
        tabManager.addTab("Hạng Kim Cương (VIP)", createTierTab(modelKimCuong, btnThemKC, btnXoaKC));

        this.add(tabManager, BorderLayout.CENTER);
    }


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

    private JPanel createTierTab(DefaultTableModel model, JButton btnAdd, JButton btnDel) {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Bảng
        JTable tbl = new JTable(model);
        styleTable(tbl);
        
        // Nút chức năng nằm dưới cùng
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.add(btnAdd);
        pnlBtn.add(btnDel);
        
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
        
        // Căn giữa
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // 4. Tạo nút đẹp
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