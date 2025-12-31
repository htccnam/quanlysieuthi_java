package DAO;

import MODEL.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    
    // 1. Thêm Khách Hàng (Cập nhật SQL mới)
    public boolean addKhachHang(KhachHang kh) throws Exception {
        // Lưu ý: Tôi vẫn để cột 'gioitinh' vào vì trong Model bạn có. 
        // Nếu DB không có cột này thì bạn xóa đi nhé.
        String sql = "INSERT INTO khachhang(makhachhang, tenkhachhang, sodienthoai, gioitinh, email, ngaysinh, diemtichluy, taikhoan, matkhau) VALUES(?,?,?,?,?,?,?,?,?)";
        
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
            
            ps.setInt(7, kh.getDiemtichluy());
            ps.setString(8, kh.getTaikhoan());
            ps.setString(9, kh.getMatkhau());
            
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Sửa Khách Hàng (Update theo makhachhang)
    public boolean updateKhachHang(KhachHang kh) throws Exception {
        String sql = "UPDATE khachhang SET tenkhachhang=?, sodienthoai=?, gioitinh=?, email=?, ngaysinh=?, diemtichluy=?, taikhoan=?, matkhau=? WHERE makhachhang=?";
        
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
            
            ps.setInt(6, kh.getDiemtichluy());
            ps.setString(7, kh.getTaikhoan());
            ps.setString(8, kh.getMatkhau());
            
            ps.setString(9, kh.getMaKH()); // Điều kiện WHERE
            
            return ps.executeUpdate() > 0;
        }
    }

    // 3. Xóa Khách Hàng
    public boolean deleteKhachHang(String maKH) throws Exception {
        String sql = "DELETE FROM khachhang WHERE makhachhang=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        }
    }

    // 4. Tìm kiếm & Lấy danh sách (Mapping cột mới)
    public List<KhachHang> searchKhachHang(String keyword) throws Exception {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE makhachhang LIKE ? OR tenkhachhang LIKE ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            String query = "%" + keyword + "%";
            ps.setString(1, query);
            ps.setString(2, query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                
                // --- MAP ĐÚNG TÊN CỘT TRONG DATABASE CỦA BẠN ---
                kh.setMaKH(rs.getString("makhachhang"));
                kh.setHoTen(rs.getString("tenkhachhang"));
                kh.setSdt(rs.getString("sodienthoai"));
                kh.setGioiTinh(rs.getString("gioitinh")); // Kiểm tra xem DB có cột này không
                kh.setEmail(rs.getString("email"));
                
                java.sql.Date sqlDate = rs.getDate("ngaysinh");
                if (sqlDate != null) {
                    kh.setNgaySinh(sqlDate.toLocalDate());
                }
                
                // Lấy 3 trường mới
                kh.setDiemtichluy(rs.getInt("diemtichluy"));
                kh.setTaikhoan(rs.getString("taikhoan"));
                kh.setMatkhau(rs.getString("matkhau"));
                
                list.add(kh);
            }
        }
        return list;
    }

    public List<KhachHang> getAllKhachHang() throws Exception {
        return searchKhachHang(""); 
    }
}