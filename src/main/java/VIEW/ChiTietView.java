package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChiTietView extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ChiTietView() {
        // Thiết lập layout chính và màu nền cho JPanel này
        setLayout(new BorderLayout(0, 10));
        setBackground(new Color(245, 247, 249));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. Khởi tạo các phần giao diện
        add(createTopBar(), BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(createStatCards(), BorderLayout.NORTH);
        centerPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    // --- THANH TÌM KIẾM VÀ NÚT CHỨC NĂNG ---
    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        // Ô tìm kiếm
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        left.setBackground(new Color(245, 247, 249));
        JTextField txtSearch = new JTextField();
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setFont(new Font("Arial", Font.BOLD, 17));
        txtSearch.setPreferredSize(new Dimension(350, 40));
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        left.add(lblTimKiem);
        left.add(txtSearch);

        // Nhóm nút bên phải
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlButtons.setOpaque(false);

        JButton btnExport = new JButton("Xuất Excel");
        btnExport.setPreferredSize(new Dimension(120, 40));
        btnExport.setBackground(new Color(22, 163, 74));
        btnExport.setForeground(Color.WHITE);

        JButton btnAdd = new JButton("+ Thêm Đơn Hàng");
        btnAdd.setPreferredSize(new Dimension(180, 40));
        btnAdd.setBackground(new Color(59, 130, 246)); // Màu xanh Blue hiện đại
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);

        pnlButtons.add(btnExport);
        pnlButtons.add(btnAdd);

        topBar.add(left, BorderLayout.WEST);
        topBar.add(pnlButtons, BorderLayout.EAST);

        return topBar;
    }

    // --- CÁC THẺ THỐNG KÊ (CARDS) ---
    private JPanel createStatCards() {
        JPanel pnlCards = new JPanel(new GridLayout(1, 4, 20, 0));
        pnlCards.setOpaque(false);

        pnlCards.add(renderCard("Tổng doanh thu", "128.5M đ", new Color(232, 252, 241), new Color(34, 197, 94)));
        pnlCards.add(renderCard("Đơn hàng mới", "24", new Color(239, 246, 255), new Color(59, 130, 246)));
        pnlCards.add(renderCard("Chờ xử lý", "5", new Color(255, 251, 235), new Color(245, 158, 11)));
        pnlCards.add(renderCard("Khách hàng", "1,203", new Color(250, 245, 255), new Color(168, 85, 247)));

        return pnlCards;
    }

    // --- BẢNG DỮ LIỆU ---
    private JPanel createTablePanel() {
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);
        // Bo góc cho Panel trắng chứa bảng bằng LineBorder và EmptyBorder
        pnlContent.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        String[] cols = {"MÃ ĐƠN HÀNG", "NGÀY GIAO DỊCH", "NHÂN SỰ", "PHƯƠNG THỨC", "TỔNG TIỀN", "HÀNH ĐỘNG"};
        model = new DefaultTableModel(cols, 0);
        
        // Dữ liệu mẫu
        model.addRow(new Object[]{"#ORD-2023-001", "20/10/2023", "Nguyễn Văn A", "Online", "1,500,000 đ", "Edit/Del"});
        model.addRow(new Object[]{"#ORD-2023-002", "20/10/2023", "Trần Thị B", "Tại quầy", "450,000 đ", "Edit/Del"});
        model.addRow(new Object[]{"#ORD-2023-003", "19/10/2023", "Lê Văn H", "Online", "2,100,000 đ", "Edit/Del"});

        table = new JTable(model);
        table.setRowHeight(50); // Hàng cao cho thoáng
        table.setShowVerticalLines(false); // Chỉ để đường kẻ ngang cho hiện đại
        table.setGridColor(new Color(245, 245, 245));
        table.setSelectionBackground(new Color(240, 247, 255));

        // Header của bảng
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setForeground(Color.GRAY);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        pnlContent.add(scroll, BorderLayout.CENTER);

        // Panel phân trang phía dưới (Phụ)
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.add(new JLabel("Hiển thị 1 đến 3 trong số 42 kết quả   "));
        pnlFooter.add(new JButton("<"));
        pnlFooter.add(new JButton("1"));
        pnlFooter.add(new JButton(">"));
        pnlContent.add(pnlFooter, BorderLayout.SOUTH);

        return pnlContent;
    }

    // Hàm tiện ích vẽ từng thẻ card
    private JPanel renderCard(String title, String value, Color bgColor, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(235, 235, 235)),
            new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 22));

        // Giả lập icon tròn nhỏ bên phải
        JPanel icon = new JPanel();
        icon.setPreferredSize(new Dimension(40, 40));
        icon.setBackground(bgColor);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(icon, BorderLayout.EAST);

        return card;
    }
}
