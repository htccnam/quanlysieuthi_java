package DAO;

import MODEL.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    
    // 1. Thêm Khách Hàng 
    public boolean addKhachHang(KhachHang kh) throws Exception {
        String sql = "INSERT INTO khachhang(makhachhang, tenkhachhang, sodienthoai, gioitinh, email, ngaysinh, diemtichluy) VALUES(?,?,?,?,?,?,?)";
        
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
            
            ps.setInt(7, 0); // Mặc định 0 điểm
            
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Sửa Khách Hàng (Giữ nguyên: Không sửa điểm ở đây)
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
            
            ps.setString(6, kh.getMaKH());
            
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

    // 4. Tìm kiếm & Lấy danh sách 
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
                
                kh.setMaKH(rs.getString("makhachhang"));
                kh.setHoTen(rs.getString("tenkhachhang"));
                kh.setSdt(rs.getString("sodienthoai"));
                kh.setGioiTinh(rs.getString("gioitinh"));
                kh.setEmail(rs.getString("email"));
                
                java.sql.Date sqlDate = rs.getDate("ngaysinh");
                if (sqlDate != null) {
                    kh.setNgaySinh(sqlDate.toLocalDate());
                }
                
                // Lấy trực tiếp điểm hiện tại trong kho 
                kh.setDiemtichluy(rs.getInt("diemtichluy"));
                
                list.add(kh);
            }
        }
        return list;
    }

    public List<KhachHang> getAllKhachHang() throws Exception {
        return searchKhachHang(""); 
    }
    
    // 5. Cộng điểm khi mua hàng :
    // gọi hàm này ở chức năng 
    // Logic: 10.000 VND = 1 điểm 
    public boolean congDiemTichLuy(String maKH, double tongTienDonHang) {
        // Quy đổi: 10.000đ = 10 điểm => Chia 1000
        int diemCong = (int) (tongTienDonHang / 1000); 
        
        String sql = "UPDATE khachhang SET diemtichluy = diemtichluy + ? WHERE makhachhang = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, diemCong);
            ps.setString(2, maKH);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}