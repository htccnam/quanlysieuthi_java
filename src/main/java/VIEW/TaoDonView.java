package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaoDonView extends JPanel {

    private JTextField txtMaDon, txtMaNV, txtNoiNhan;
    private JTable tblSanPham, tblGioHang;
    private JButton btnThem, btnXoa, btnLuu;
    private JLabel lblTongTien;

    public TaoDonView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        /* ===== THÔNG TIN ĐƠN HÀNG ===== */
        JPanel pnlTop = new JPanel(new GridLayout(2, 4, 10, 5));

        txtMaDon = new JTextField();
        txtMaNV = new JTextField();
        txtNoiNhan = new JTextField();

        pnlTop.add(new JLabel("Mã đơn hàng:"));
        pnlTop.add(txtMaDon);
        pnlTop.add(new JLabel("Mã nhân viên:"));
        pnlTop.add(txtMaNV);

        pnlTop.add(new JLabel("Nơi nhận hàng:"));
        pnlTop.add(txtNoiNhan);
        pnlTop.add(new JLabel()); // filler
        pnlTop.add(new JLabel());

        add(pnlTop, BorderLayout.NORTH);

        /* ===== BẢNG SẢN PHẨM & GIỎ ===== */
        tblSanPham = new JTable(new DefaultTableModel(
                new Object[]{"Mã SP", "Tên SP", "Giá bán", "Tồn kho"}, 0
        ));

        tblGioHang = new JTable(new DefaultTableModel(
                new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0
        ));

        btnThem = new JButton("➕ Thêm >>");
        btnXoa = new JButton("❌ Xoá");

        JPanel pnlCenter = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlCenter.add(new JScrollPane(tblSanPham));

        JPanel pnlBtn = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlBtn.add(btnThem);
        pnlBtn.add(btnXoa);
        pnlCenter.add(pnlBtn);

        pnlCenter.add(new JScrollPane(tblGioHang));
        add(pnlCenter, BorderLayout.CENTER);

        /* ===== FOOTER ===== */
        JPanel pnlBottom = new JPanel(new BorderLayout());
        lblTongTien = new JLabel("Tổng tiền: 0");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));

        btnLuu = new JButton("💾 Lưu đơn hàng");

        pnlBottom.add(lblTongTien, BorderLayout.WEST);
        pnlBottom.add(btnLuu, BorderLayout.EAST);

        add(pnlBottom, BorderLayout.SOUTH);
    }

    /* ===== GETTER ===== */
    public JTextField getTxtMaDon() { return txtMaDon; }
    public JTextField getTxtMaNV() { return txtMaNV; }
    public JTextField getTxtNoiNhan() { return txtNoiNhan; }

    public JTable getTblSanPham() { return tblSanPham; }
    public JTable getTblGioHang() { return tblGioHang; }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnLuu() { return btnLuu; }

    public JLabel getLblTongTien() { return lblTongTien; }
}
