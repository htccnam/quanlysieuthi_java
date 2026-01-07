package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.BiConsumer;

public class DoiQuaView extends JFrame {

    // Components Public để Controller gọi
    public JComboBox<String> cboKhachHang;
    public JLabel lblPoints;
    public DefaultTableModel modelHistory;
    
    // Callback để xử lý sự kiện đổi quà (Controller sẽ truyền logic vào đây)
    private BiConsumer<String, Integer> onRedeemAction; 

    public DoiQuaView() {
        setTitle("Đổi Điểm Tích Lũy Nhận Quà");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng cửa sổ này, không tắt app
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. TOP: CHỌN KHÁCH HÀNG ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pnlTop.setBackground(new Color(240, 248, 255));
        pnlTop.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        JLabel lblChon = new JLabel("Chọn Khách Hàng:");
        lblChon.setFont(new Font("Arial", Font.BOLD, 14));
        
        // ComboBox thay cho Label tĩnh
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setPreferredSize(new Dimension(300, 30));
        
        lblPoints = new JLabel("Điểm khả dụng: 0");
        lblPoints.setFont(new Font("Arial", Font.BOLD, 16));
        lblPoints.setForeground(new Color(204, 0, 0));

        pnlTop.add(lblChon);
        pnlTop.add(cboKhachHang);
        pnlTop.add(Box.createHorizontalStrut(50));
        pnlTop.add(lblPoints);

        // --- 2. CENTER: KHO QUÀ (Chỉ 2 món) ---
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        pnlCenter.setBorder(new TitledBorder("Danh Sách Quà Tặng"));
        pnlCenter.setBackground(Color.WHITE);

        // Chỉ thêm 2 món quà theo yêu cầu
        pnlCenter.add(createGiftCard("Gấu Bông Siêu Thị", 50, "Còn 10 cái"));
        pnlCenter.add(createGiftCard("Bộ Cốc Thủy Tinh", 150, "Còn 5 bộ"));

        // --- 3. RIGHT: LỊCH SỬ ĐỔI QUÀ ---
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setPreferredSize(new Dimension(350, 0));
        pnlRight.setBorder(new TitledBorder("Lịch Sử Đổi Điểm (Của Khách Này)"));

        String[] cols = {"Tên Quà", "Điểm Trừ", "Ngày Đổi"};
        modelHistory = new DefaultTableModel(cols, 0);
        JTable tblHistory = new JTable(modelHistory);
        
        pnlRight.add(new JScrollPane(tblHistory), BorderLayout.CENTER);

        // --- TỔNG HỢP ---
        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);
    }

    // Hàm tạo Card quà
    private JPanel createGiftCard(String tenQua, int diemCan, String tonKho) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(200, 250));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(tenQua);
        lblName.setFont(new Font("Arial", Font.BOLD, 16));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPoint = new JLabel(diemCan + " Điểm");
        lblPoint.setForeground(Color.RED);
        lblPoint.setFont(new Font("Arial", Font.BOLD, 20));
        lblPoint.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStock = new JLabel(tonKho);
        lblStock.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRedeem = new JButton("ĐỔI NGAY");
        btnRedeem.setBackground(new Color(0, 153, 76));
        btnRedeem.setForeground(Color.WHITE);
        btnRedeem.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRedeem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Logic bấm nút: Gọi Callback về Controller
        btnRedeem.addActionListener(e -> {
            if (onRedeemAction != null) {
                onRedeemAction.accept(tenQua, diemCan);
            }
        });

        card.add(Box.createVerticalStrut(20));
        card.add(lblName);
        card.add(Box.createVerticalStrut(10));
        card.add(lblPoint);
        card.add(Box.createVerticalStrut(10));
        card.add(lblStock);
        card.add(Box.createVerticalStrut(20));
        card.add(btnRedeem);

        return card;
    }
    
    // Hàm để Controller đăng ký sự kiện đổi quà
    public void setOnRedeemAction(BiConsumer<String, Integer> action) {
        this.onRedeemAction = action;
    }
}