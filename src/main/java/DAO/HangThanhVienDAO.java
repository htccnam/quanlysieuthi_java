package DAO;

import java.sql.*;
import java.util.Vector;

public class HangThanhVienDAO {
    
    // --- HÀM 1: LẤY DANH SÁCH KHÁCH HÀNG & TỔNG TIỀN ---
    // Hàm này dùng câu lệnh LEFT JOIN để kết hợp bảng KhachHang và HoaDon
    // Nó tự động tính tổng tiền (SUM) cho từng khách.
    // Nếu khách chưa mua gì, COALESCE sẽ trả về 0.
    public Vector<Vector<Object>> getKhachHangVaTongTien() {
        Vector<Vector<Object>> data = new Vector<>();
        
        // Câu lệnh SQL chuẩn (Đã bỏ cột Email để tránh lỗi)
        String sql = "SELECT k.makhachhang, k.tenkhachhang, COALESCE(SUM(h.TongTien), 0) as TongChiTieu " +
                     "FROM khachhang k " +
                     "LEFT JOIN HoaDon h ON k.makhachhang = h.makhachhang " +
                     "GROUP BY k.makhachhang, k.tenkhachhang";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                // Lấy dữ liệu theo thứ tự cột trong câu SELECT
                row.add(rs.getString(1)); // Cột 1: Mã Khách Hàng
                row.add(rs.getString(2)); // Cột 2: Tên Khách Hàng
                row.add(rs.getDouble(3)); // Cột 3: Tổng Tiền
                data.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra cửa sổ Output nếu có sai sót SQL
        }
        return data;
    }

    // --- HÀM 2: HỦY HẠNG THÀNH VIÊN (RESET) ---
    // Logic: Để hủy hạng (ví dụ từ Vàng về Thường), ta cần xóa lịch sử chi tiêu
    // tức là xóa các hóa đơn của khách đó trong bảng HoaDon.
    // Khi tổng tiền về 0, hệ thống sẽ tự động xếp khách về hạng Thường.
    public boolean resetHangThanhVien(String maKH) {
        String sql = "DELETE FROM HoaDon WHERE makhachhang = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, maKH);
            
            // executeUpdate trả về số dòng bị ảnh hưởng.
            // Nếu > 0 nghĩa là có hóa đơn bị xóa -> Thành công.
            // Nếu = 0 nghĩa là khách này chưa có hóa đơn nào -> Coi như thành công (vì vốn dĩ đã là 0đ).
            ps.executeUpdate(); 
            return true; 
            
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu lỗi kết nối hoặc lỗi SQL
        }
    }
}