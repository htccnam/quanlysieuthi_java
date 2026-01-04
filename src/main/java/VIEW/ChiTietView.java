package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChiTietView extends JPanel {
    // Các component cần truy cập từ Controller
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JButton btnXuatExcel, btnSua, btnXoa, btnXemChiTiet;
    
    // Các Label thống kê để Controller set text
    private JLabel lblTongDoanhThu, lblTongDon, lblDoanhThuTB;

    public ChiTietView() {
        setLayout(new BorderLayout(0, 10));
        setBackground(new Color(245, 247, 249));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. Thanh công cụ (Tìm kiếm + Nút bấm)
        add(createTopBar(), BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        
        // 2. Các ô thống kê (3 ô theo yêu cầu)
        centerPanel.add(createStatCards(), BorderLayout.NORTH);
        
        // 3. Bảng dữ liệu (Cột đã sửa)
        centerPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    // --- 1. THANH CÔNG CỤ ---
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        // Bên trái: Tìm kiếm
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        left.setOpaque(false);
        
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 35));
        
        left.add(lblTimKiem);
        left.add(txtSearch);

        // Bên phải: Các nút chức năng (Sửa, Xóa, Xem, Xuất)
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        // Nút Xem Chi Tiết
        btnXemChiTiet = new JButton("Xem CT");
        styleButton(btnXemChiTiet, new Color(59, 130, 246)); // Xanh dương

        // Nút Sửa
        btnSua = new JButton("Sửa");
        styleButton(btnSua, new Color(245, 158, 11)); // Màu Cam

        // Nút Xóa
        btnXoa = new JButton("Xóa");
        styleButton(btnXoa, new Color(239, 68, 68)); // Màu Đỏ

        // Nút Xuất Excel (Giữ lại nếu cần)
        btnXuatExcel = new JButton("Xuất Excel");
        styleButton(btnXuatExcel, new Color(22, 163, 74)); // Xanh lá

        right.add(btnXemChiTiet);
        right.add(btnSua);
        right.add(btnXoa);
        right.add(btnXuatExcel);

        topBar.add(left, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);

        return topBar;
    }

    // Hàm phụ trợ trang trí nút bấm
    private void styleButton(JButton btn, Color bgColor) {
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // --- 2. CÁC THẺ THỐNG KÊ (3 Ô) ---
    private JPanel createStatCards() {
        // GridLayout 1 hàng 3 cột, khoảng cách 20px
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 20, 0)); 
        pnlCards.setOpaque(false);

        // Khởi tạo các Label và Card
        // Card 1: Tổng doanh thu
        lblTongDoanhThu = new JLabel("0 đ");
        pnlCards.add(renderCard("Tổng doanh thu", lblTongDoanhThu, new Color(232, 252, 241), new Color(34, 197, 94)));

        // Card 2: Tổng số đơn
        lblTongDon = new JLabel("0");
        pnlCards.add(renderCard("Tổng số đơn hàng", lblTongDon, new Color(239, 246, 255), new Color(59, 130, 246)));

        // Card 3: Doanh thu trung bình / đơn
        lblDoanhThuTB = new JLabel("0 đ/đơn");
        pnlCards.add(renderCard("Doanh thu TB/Đơn", lblDoanhThuTB, new Color(255, 251, 235), new Color(245, 158, 11)));

        return pnlCards;
    }

    // Hàm vẽ giao diện từng Card
    private JPanel renderCard(String title, JLabel lblVal, Color bgColor, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        // Viền nhẹ và padding
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        lblVal.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblVal.setForeground(Color.DARK_GRAY);

        // Icon màu bên phải trang trí
        JPanel icon = new JPanel();
        icon.setPreferredSize(new Dimension(5, 40)); // Chỉ là 1 vạch màu
        icon.setBackground(accentColor);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblVal, BorderLayout.CENTER);
        card.add(icon, BorderLayout.EAST);

        return card;
    }

    // --- 3. BẢNG DỮ LIỆU ---
    private JPanel createTablePanel() {
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        pnlContent.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        // Cập nhật tên cột theo yêu cầu số 3
        String[] cols = {
            "MÃ ĐƠN HÀNG", 
            "NGÀY GIAO DỊCH", 
            "NHÂN SỰ", 
            "PHƯƠNG THỨC BÁN", 
            "THANH TOÁN", 
            "TỔNG TIỀN"
        };
        
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(245, 245, 245));
        table.setSelectionBackground(new Color(240, 247, 255));
        
        // Font Header
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        pnlContent.add(scroll, BorderLayout.CENTER);
        return pnlContent;
    }

    // --- GETTER CHO CONTROLLER ---
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JTextField getTxtSearch() { return txtSearch; }
    
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JButton getBtnXuatExcel() { return btnXuatExcel; }
    
    public JLabel getLblTongDoanhThu() { return lblTongDoanhThu; }
    public JLabel getLblTongDon() { return lblTongDon; }
    public JLabel getLblDoanhThuTB() { return lblDoanhThuTB; }
}