package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.BiConsumer;

public class DoiQuaView extends JFrame {


    public JComboBox<String> cboKhachHang;
    public JLabel lblPoints;
    
    public JButton btnDoiGau, btnDoiCoc; 
    public DefaultTableModel modelHistory;
    public JTable tblHistory;
    

    private BiConsumer<String, Integer> onRedeemAction; 

    public DoiQuaView() {
        setTitle("Đổi Điểm Tích Lũy Nhận Quà");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

  
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pnlTop.setBackground(new Color(240, 248, 255));
        pnlTop.setBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        JLabel lblChon = new JLabel("Chọn Khách Hàng:");
        lblChon.setFont(new Font("Arial", Font.BOLD, 14));
        
        cboKhachHang = new JComboBox<>();
        cboKhachHang.setPreferredSize(new Dimension(300, 30));
        
        lblPoints = new JLabel("Điểm khả dụng: 0 điểm");
        lblPoints.setFont(new Font("Arial", Font.BOLD, 16));
        lblPoints.setForeground(new Color(204, 0, 0));

        pnlTop.add(lblChon);
        pnlTop.add(cboKhachHang);
        pnlTop.add(Box.createHorizontalStrut(50));
        pnlTop.add(lblPoints);


        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        pnlCenter.setBorder(new TitledBorder("Danh Sách Quà Tặng"));
        

        btnDoiGau = new JButton("ĐỔI NGAY");
        btnDoiCoc = new JButton("ĐỔI NGAY");

        pnlCenter.add(createGiftCard("Gấu Bông Siêu Thị", 50, btnDoiGau));
        pnlCenter.add(createGiftCard("Bộ Cốc Thủy Tinh", 150, btnDoiCoc));


        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setPreferredSize(new Dimension(400, 0));
        pnlRight.setBorder(new TitledBorder("Lịch Sử Đổi Điểm"));


        String[] cols = {"Tên Quà", "Điểm Trừ", "Ngày Đổi"};
        modelHistory = new DefaultTableModel(cols, 0);
        tblHistory = new JTable(modelHistory);
        
        pnlRight.add(new JScrollPane(tblHistory), BorderLayout.CENTER);

        add(pnlTop, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.EAST);
    }

    private JPanel createGiftCard(String ten, int diem, JButton btnAction) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(220, 250));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(ten);
        lblName.setFont(new Font("Arial", Font.BOLD, 16));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPoint = new JLabel(diem + " Điểm");
        lblPoint.setForeground(Color.RED);
        lblPoint.setFont(new Font("Arial", Font.BOLD, 20));
        lblPoint.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        btnAction.setBackground(new Color(0, 153, 76));
        btnAction.setForeground(Color.WHITE);
        btnAction.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
        

        btnAction.addActionListener(e -> {
            if (onRedeemAction != null) {
                onRedeemAction.accept(ten, diem);
            }
        });

        card.add(Box.createVerticalStrut(20));
        card.add(lblName);
        card.add(Box.createVerticalStrut(10));
        card.add(lblPoint);
        card.add(Box.createVerticalStrut(20));
        card.add(btnAction);

        return card;
    }
    

    public void setOnRedeemAction(BiConsumer<String, Integer> action) {
        this.onRedeemAction = action;
    }
}