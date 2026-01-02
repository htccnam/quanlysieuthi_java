package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChiTietView extends JPanel {

    private JLabel lblMaDon, lblNgayLap, lblMaNV, lblTongTien;
    private JTable tblChiTiet;
    private JButton btnDong;

    public ChiTietView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        /* ====== PANEL THÔNG TIN ====== */
        JPanel pnlInfo = new JPanel(new GridLayout(2, 2, 10, 10));

        lblMaDon = new JLabel("Mã đơn:");
        lblNgayLap = new JLabel("Ngày lập:");
        lblMaNV = new JLabel("Nhân viên:");
        lblTongTien = new JLabel("Tổng tiền:");

        pnlInfo.add(lblMaDon);
        pnlInfo.add(lblNgayLap);
        pnlInfo.add(lblMaNV);
        pnlInfo.add(lblTongTien);

        /* ====== BẢNG CHI TIẾT ====== */
        tblChiTiet = new JTable();
        tblChiTiet.setModel(new DefaultTableModel(
                new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });

        JScrollPane scroll = new JScrollPane(tblChiTiet);

        /* ====== NÚT ====== */
        btnDong = new JButton("Đóng");
        JPanel pnlBtn = new JPanel();
        pnlBtn.add(btnDong);

        add(pnlInfo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlBtn, BorderLayout.SOUTH);
    }

    /* ===== GETTER ===== */
    public JTable getTblChiTiet() {
        return tblChiTiet;
    }

    public JButton getBtnDong() {
        return btnDong;
    }

    public JLabel getLblMaDon() {
        return lblMaDon;
    }

    public JLabel getLblNgayLap() {
        return lblNgayLap;
    }

    public JLabel getLblMaNV() {
        return lblMaNV;
    }

    public JLabel getLblTongTien() {
        return lblTongTien;
    }
}
