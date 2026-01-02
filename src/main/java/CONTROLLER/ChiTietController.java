package CONTROLLER;

import VIEW.ChiTietView;
import DAO.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ChiTietController {

    private ChiTietView view;
    private String maDonHang;

    public ChiTietController(ChiTietView view, String maDonHang) {
        this.view = view;
        this.maDonHang = maDonHang;

        loadThongTinDonHang();
        loadChiTiet();

        view.getBtnDong().addActionListener(e ->
                SwingUtilities.getWindowAncestor(view).dispose()
        );
    }

    /* ===== LOAD THÔNG TIN ĐƠN ===== */
    private void loadThongTinDonHang() {
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM DonHang WHERE MaDonHang = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maDonHang);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                view.getLblMaDon().setText("Mã đơn: " + rs.getInt("MaDonHang"));
                view.getLblNgayLap().setText("Ngày lập: " + rs.getDate("NgayLap"));
                view.getLblMaNV().setText("Nhân viên: " + rs.getString("MaNV"));
                view.getLblTongTien().setText("Tổng tiền: " + rs.getDouble("TongTien"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== LOAD CHI TIẾT ===== */
    private void loadChiTiet() {
        DefaultTableModel model =
                (DefaultTableModel) view.getTblChiTiet().getModel();
        model.setRowCount(0);

        try {
            Connection c = DBConnection.getConnection();
            String sql = """
                SELECT ct.MaSP, sp.TenSanPham, ct.SoLuong, ct.DonGia,
                       (ct.SoLuong * ct.DonGia) AS ThanhTien
                FROM ChiTietDonHang ct
                JOIN SanPham sp ON ct.MaSP = sp.MaSanPham
                WHERE ct.MaDonHang = ?
            """;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maDonHang);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("MaSP"),
                        rs.getString("TenSanPham"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("DonGia"),
                        rs.getDouble("ThanhTien")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
