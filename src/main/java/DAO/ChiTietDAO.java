package DAO;

import MODEL.ChiTietDon;
import MODEL.DonHang;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietDAO {

    // 1. Lấy tất cả đơn hàng (cho bảng chính)
    public ArrayList<DonHang> getAllDonHang() {
        ArrayList<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM donhang ORDER BY ngaylap DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRowToDonHang(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Tìm kiếm đơn hàng
    public ArrayList<DonHang> searchDonHang(String keyword) {
        ArrayList<DonHang> list = new ArrayList<>();
        // Tìm theo Mã Đơn hoặc Tên Nhân Viên
        String sql = "SELECT * FROM donhang WHERE madonhang LIKE ? OR manhanvien LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonHang(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 3. Lấy danh sách sản phẩm của 1 đơn hàng (Cho chức năng Xem Chi Tiết)
    public ArrayList<ChiTietDon> getChiTietByMaDH(String maDH) {
        ArrayList<ChiTietDon> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietdonhang WHERE madonhang = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Lưu ý: Constructor ChiTietDon phải khớp với dữ liệu bạn lấy
                list.add(new ChiTietDon(
                    rs.getString("madonhang"),
                    rs.getString("masanpham"),
                    rs.getString("tensanpham"), // Đảm bảo bảng chitiethoadon có cột này hoặc phải JOIN bảng SanPham
                    rs.getInt("soluong"),
                    rs.getDouble("dongia"),
                    rs.getDouble("thanhtien")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 4. Xóa đơn hàng
    public boolean deleteDonHang(String maDH) {
        String sqlChiTiet = "DELETE FROM chitiethoadon WHERE madonhang = ?";
        String sqlDonHang = "DELETE FROM donhang WHERE madonhang = ?";
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction
            
            // Xóa chi tiết trước
            try (PreparedStatement ps1 = conn.prepareStatement(sqlChiTiet)) {
                ps1.setString(1, maDH);
                ps1.executeUpdate();
            }
            
            // Xóa đơn hàng sau
            try (PreparedStatement ps2 = conn.prepareStatement(sqlDonHang)) {
                ps2.setString(1, maDH);
                int result = ps2.executeUpdate();
                
                if (result > 0) {
                    conn.commit(); // Thành công thì lưu
                    return true;
                }
            }
            conn.rollback(); // Lỗi thì quay lại
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    // Hàm phụ trợ map dữ liệu tránh lặp code
    private DonHang mapRowToDonHang(ResultSet rs) throws SQLException {
        return new DonHang(
            rs.getString("madonhang"), rs.getString("makhachhang"),
            rs.getString("manhanvien"), rs.getString("makhuyenmai"),
            rs.getDate("ngaylap"), rs.getString("phuongthucban"),
            rs.getString("thanhtoan"), rs.getDouble("tongtien")
        );
    }
    
    public DonHang getDonHangByMa(String maDH) {
    String sql = "SELECT * FROM donhang WHERE madonhang = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maDH);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new DonHang(
                rs.getString("madonhang"), rs.getString("makhachhang"),
                rs.getString("manhanvien"), rs.getString("makhuyenmai"),
                rs.getDate("ngaylap"), rs.getString("phuongthucban"),
                rs.getString("thanhtoan"), rs.getDouble("tongtien")
            );
        }
    } catch (Exception e) { e.printStackTrace(); }
    return null;
    }
}