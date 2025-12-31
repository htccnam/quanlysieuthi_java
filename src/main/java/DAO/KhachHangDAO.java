package DAO;

import MODEL.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    
    // 1. Thêm (Đã có)
    public boolean addKhachHang(KhachHang kh) throws Exception {
        String sql = "INSERT INTO khachhang(makhachhang, tenkhachhang, sodienthoai, gioitinh, email, ngaysinh) VALUES(?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSdt());
            ps.setString(4, kh.getGioiTinh());
            ps.setString(5, kh.getEmail());
            
            if (kh.getNgaySinh() != null) {
                ps.setDate(6, java.sql.Date.valueOf(kh.getNgaySinh()));
            } else {
                ps.setDate(6, null);
            }
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Sửa (Mới)
    public boolean updateKhachHang(KhachHang kh) throws Exception {
        String sql = "UPDATE khachhang SET tenkhachhang=?, sodienthoai=?, gioitinh=?, email=?, ngaysinh=? WHERE makhachhang=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getGioiTinh());
            ps.setString(4, kh.getEmail());
            
            if (kh.getNgaySinh() != null) {
                ps.setDate(5, java.sql.Date.valueOf(kh.getNgaySinh()));
            } else {
                ps.setDate(5, null);
            }
            
            ps.setString(6, kh.getMaKH()); // Điều kiện WHERE
            
            return ps.executeUpdate() > 0;
        }
    }

    // 3. Xóa (Mới)
    public boolean deleteKhachHang(String maKH) throws Exception {
        String sql = "DELETE FROM khachhang WHERE makhachhang=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        }
    }

    // 4. Tìm kiếm (Mới)
    public List<KhachHang> searchKhachHang(String keyword) throws Exception {
        List<KhachHang> list = new ArrayList<>();
        // Tìm theo Mã hoặc Tên
        String sql = "SELECT * FROM khachhang WHERE makhachhang LIKE ? OR tenkhachhang LIKE ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            String query = "%" + keyword + "%";
            ps.setString(1, query);
            ps.setString(2, query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("makhachhang")); // Lưu ý: Kiểm tra lại tên cột trong DB của bạn
                kh.setHoTen(rs.getString("tenkhachhang"));
                kh.setSdt(rs.getString("sodienthoai"));
                kh.setGioiTinh(rs.getString("gioitinh"));
                kh.setEmail(rs.getString("email"));
                
                java.sql.Date sqlDate = rs.getDate("ngaysinh");
                if (sqlDate != null) {
                    kh.setNgaySinh(sqlDate.toLocalDate());
                }
                list.add(kh);
            }
        }
        return list;
    }

    // 5. Lấy tất cả (Đã có)
    public List<KhachHang> getAllKhachHang() throws Exception {
        return searchKhachHang(""); // Gọi hàm tìm kiếm rỗng để lấy tất cả (Tái sử dụng code)
    }
}