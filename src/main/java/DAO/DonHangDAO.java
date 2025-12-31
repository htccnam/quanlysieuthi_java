/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;




import MODEL.ChiTietDonHang;
import MODEL.DonHang;
import MODEL.SanPham;

import java.sql.*;
import java.util.List;


public class DonHangDAO {

   
    public void saveWithDetails(DonHang dh, List<ChiTietDonHang> items) throws SQLException, Exception {
        String insertDon = "INSERT INTO donhang(madonhang, makhachhang, manhanvien, ngaylap, noinhanhang, trangthai) VALUES (?, ?, ?, ?, ?, ?)";
        String insertCt = "INSERT INTO chitietdonhang(madonhang, masanpham, soluong, dongia, thanhtien) VALUES (?, ?, ?, ?, ?)";
        String selectStock = "SELECT tonkho FROM sanpham WHERE masanpham = ? FOR UPDATE";
        String updateStock = "UPDATE sanpham SET tonkho = ? WHERE masanpham = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Kiểm tra mã đơn đã tồn tại
            if (existsOrder(conn, dh.getMaDonHang())) {
                throw new SQLException("Mã đơn hàng đã tồn tại: " + dh.getMaDonHang());
            }

            // 2. Insert donhang
            try (PreparedStatement psDon = conn.prepareStatement(insertDon)) {
                psDon.setString(1, dh.getMaDonHang());
                psDon.setString(2, dh.getMaKhachHang());
                psDon.setString(3, dh.getMaNhanVien());
                psDon.setTimestamp(4, new Timestamp(dh.getNgayLap().getTime()));
                psDon.setString(5, dh.getNoiNhan());
                psDon.setString(6, dh.getTrangThai());
                psDon.executeUpdate();
            }

            // 3. Với mỗi chi tiết: kiểm tra tồn kho (khóa dòng), chèn chi tiết, cập nhật tồn kho
            try (PreparedStatement psSelectStock = conn.prepareStatement(selectStock);
                 PreparedStatement psInsertCt = conn.prepareStatement(insertCt);
                 PreparedStatement psUpdateStock = conn.prepareStatement(updateStock)) {

                for (ChiTietDonHang ct : items) {
                    String maSp = ct.getMaSanPham();
                    int qty = ct.getSoLuong();

                    // lấy tồn kho hiện tại (FOR UPDATE để khóa hàng)
                    psSelectStock.setString(1, maSp);
                    try (ResultSet rs = psSelectStock.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Không tìm thấy sản phẩm: " + maSp);
                        }
                        int tonKho = rs.getInt(1);
                        if (tonKho < qty) {
                            throw new SQLException("Sản phẩm " + maSp + " không đủ tồn kho (còn " + tonKho + ", yêu cầu " + qty + ")");
                        }

                        // chèn chi tiết
                        psInsertCt.setString(1, ct.getMaDonHang());
                        psInsertCt.setString(2, maSp);
                        psInsertCt.setInt(3, qty);
                        psInsertCt.setDouble(4, ct.getDonGia());
                        psInsertCt.setDouble(5, ct.getThanhTien());
                        psInsertCt.executeUpdate();

                        // cập nhật tồn kho
                        int newStock = tonKho - qty;
                        psUpdateStock.setInt(1, newStock);
                        psUpdateStock.setString(2, maSp);
                        psUpdateStock.executeUpdate();
                    }
                }
            }

            // 4. Commit nếu mọi thứ OK
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { /* ignore */ }
            }
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) { /* ignore */ }
            }
        }
    }

    /**
     * Kiểm tra tồn tại đơn hàng theo madonhang.
     * @param conn connection đang mở (sử dụng trong transaction)
     * @param maDon mã đơn
     * @return true nếu tồn tại
     * @throws SQLException
     */
    private boolean existsOrder(Connection conn, String maDon) throws SQLException {
        String sql = "SELECT 1 FROM donhang WHERE madonhang = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDon);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}

