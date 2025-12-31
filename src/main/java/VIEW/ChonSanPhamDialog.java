package VIEW;

import MODEL.SanPham;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChonSanPhamDialog extends JDialog {

    private JTable tblSanPham;
    private JTextField txtTimKiem;
    private JButton btnTim, btnChon, btnThoat;

    private SanPham selectedProduct = null;
    private int selectedQuantity = 0;
    private Connection conn;

    public ChonSanPhamDialog(Frame parent, boolean modal, Connection connection) {
        super(parent, modal);
        this.conn = connection;
        initComponents();
        loadData("");
    }

    private void initComponents() {
        setTitle("Chọn Sản Phẩm");

        JLabel lblTim = new JLabel("Từ khóa:");
        txtTimKiem = new JTextField(15);
        btnTim = new JButton("Tìm");
        btnChon = new JButton("Chọn");
        btnThoat = new JButton("Thoát");

        tblSanPham = new JTable();
        tblSanPham.setModel(new DefaultTableModel(
            new Object[]{"Mã SP", "Tên SP", "Giá bán", "SL tồn"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblSanPham);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblTim)
                    .addComponent(txtTimKiem)
                    .addComponent(btnTim))
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnChon)
                    .addComponent(btnThoat))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTim)
                    .addComponent(txtTimKiem)
                    .addComponent(btnTim))
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChon)
                    .addComponent(btnThoat))
        );

        setContentPane(panel);
        pack();
        setLocationRelativeTo(getParent());

        btnTim.addActionListener(e -> loadData(txtTimKiem.getText().trim()));

        btnChon.addActionListener(e -> chonSanPham());

        btnThoat.addActionListener(e -> dispose());
    }

    // ================= LOGIC CHỌN SẢN PHẨM =================
    private void chonSanPham() {
        int row = tblSanPham.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm.");
            return;
        }

        try {
            String maSP = tblSanPham.getValueAt(row, 0).toString();
            String tenSP = tblSanPham.getValueAt(row, 1).toString();
            double giaBan = Double.parseDouble(
                tblSanPham.getValueAt(row, 2).toString()
            );

            String qtyStr = JOptionPane.showInputDialog(this, "Nhập số lượng:");
            int qty = Integer.parseInt(qtyStr);

            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải > 0");
                return;
            }

            // ✔ Tạo SanPham ĐÚNG theo model của bạn
            SanPham sp = new SanPham();
            sp.setMaSP(maSP);
            sp.setTenSP(tenSP);
            sp.setGiaBan(giaBan);

            selectedProduct = sp;
            selectedQuantity = qty;
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
        }
    }

    // ================= LOAD DATA =================
    private void loadData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT MaSP, TenSP, GiaBan, SoLuong
            FROM SanPham
        """;

        if (!keyword.isEmpty()) {
            sql += " WHERE MaSP LIKE ? OR TenSP LIKE ?";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (!keyword.isEmpty()) {
                String key = "%" + keyword + "%";
                ps.setString(1, key);
                ps.setString(2, key);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getDouble("GiaBan"),
                    rs.getInt("SoLuong")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải sản phẩm");
        }
    }

    // ================= GETTER =================
    public SanPham getSelectedProduct() {
        return selectedProduct;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }
}
