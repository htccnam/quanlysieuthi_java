package DAO;

import java.sql.*;
import java.util.Vector;

public class HangThanhVienDAO {
    
    // 1. Lấy danh sách khách hàng, tiền và TRẠNG THÁI
    public Vector<Vector<Object>> getKhachHangVaTongTien() {
        Vector<Vector<Object>> data = new Vector<>();
        
        // Thêm k.TrangThaiHang vào câu SELECT
        String sql = "SELECT k.makhachhang, k.tenkhachhang, k.TrangThaiHang, COALESCE(SUM(h.tongtien), 0) as TongChiTieu " +
                     "FROM khachhang k " +
                     "LEFT JOIN donhang h ON k.makhachhang = h.makhachhang " + 
                     "GROUP BY k.makhachhang, k.tenkhachhang, k.TrangThaiHang";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString(1)); // Mã
                row.add(rs.getString(2)); // Tên
                row.add(rs.getInt(3));    // Trạng Thái (0 hoặc 1)
                row.add(rs.getDouble(4)); // Tổng Tiền
                data.add(row);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }

    // 2. CẬP NHẬT TRẠNG THÁI (Dùng cho cả Thêm và Xóa)
    // status = 1 (Hiện/Thêm), status = 0 (Ẩn/Xóa)
    public boolean updateTrangThaiHang(String maKH, int status) {
        String sql = "UPDATE khachhang SET TrangThaiHang = ? WHERE makhachhang = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, status);
            ps.setString(2, maKH);
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}