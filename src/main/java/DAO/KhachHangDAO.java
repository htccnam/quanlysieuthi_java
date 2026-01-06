package DAO;

import MODEL.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    
    // 1. Thêm Khách Hàng (Đã sửa logic: Điểm mặc định = 0)
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
            
            // LOGIC MỚI: Luôn set điểm tích lũy = 0 cho khách mới
            // Không lấy từ kh.getDiemtichluy() nữa để tránh việc nhập tay
            ps.setInt(7, 0); 
            
            return ps.executeUpdate() > 0;
        }
    }

    // 2. Sửa Khách Hàng (Đã sửa logic: Không cho phép sửa điểm thủ công)
    public boolean updateKhachHang(KhachHang kh) throws Exception {
        // LOGIC MỚI: Loại bỏ 'diemtichluy=?' ra khỏi câu lệnh UPDATE
        // Điểm chỉ được cập nhật tự động qua đơn hàng, không được sửa ở đây
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
            
            // Không setInt cho điểm tích lũy nữa
            
            ps.setString(6, kh.getMaKH()); // Điều kiện WHERE là tham số thứ 6
            
            return ps.executeUpdate() > 0;
        }
    }

    // 3. Xóa Khách Hàng (Giữ nguyên)
    public boolean deleteKhachHang(String maKH) throws Exception {
        String sql = "DELETE FROM khachhang WHERE makhachhang=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        }
    }

    // 4. Tìm kiếm & Lấy danh sách (Đã sửa logic: Tính điểm từ Tổng tiền đơn hàng)
    public List<KhachHang> searchKhachHang(String keyword) throws Exception {
        List<KhachHang> list = new ArrayList<>();
        
        // LOGIC MỚI: JOIN với bảng donhang để tính điểm
        // 10.000 VNĐ = 1 Điểm
        // Sử dụng GROUP BY để gom nhóm theo khách hàng
        String sql = "SELECT k.makhachhang, k.tenkhachhang, k.sodienthoai, k.gioitinh, k.email, k.ngaysinh, " +
                     "FLOOR(COALESCE(SUM(d.tongtien), 0) / 10000) as DiemTuDong " +
                     "FROM khachhang k " +
                     "LEFT JOIN donhang d ON k.makhachhang = d.makhachhang " + // Giả sử bảng đơn hàng tên là 'donhang'
                     "WHERE k.makhachhang LIKE ? OR k.tenkhachhang LIKE ? " +
                     "GROUP BY k.makhachhang, k.tenkhachhang, k.sodienthoai, k.gioitinh, k.email, k.ngaysinh";
        
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
                
                // Lấy điểm đã được tính toán tự động từ SQL
                kh.setDiemtichluy(rs.getInt("DiemTuDong"));
                
                list.add(kh);
            }
        }
        return list;
    }

    // Giữ nguyên hàm này
    public List<KhachHang> getAllKhachHang() throws Exception {
        return searchKhachHang(""); 
    }
}