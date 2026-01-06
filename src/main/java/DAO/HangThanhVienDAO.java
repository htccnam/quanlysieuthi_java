package DAO;

import MODEL.HangThanhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HangThanhVienDAO {
    
    // ==========================================================================
    // PHẦN 1: TƯƠNG TÁC VỚI BẢNG "HANGTHANHVIEN" (QUẢN LÝ DANH SÁCH XẾP HẠNG)
    // ==========================================================================

    // 1. Lấy danh sách hiển thị ra bảng chính (3 cột: Mã, Tên, Hạng)
    // Dữ liệu lấy từ bảng 'hangthanhvien'
    public List<HangThanhVien> getDanhSachXepHang() {
        List<HangThanhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM hangthanhvien";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                list.add(new HangThanhVien(
                    rs.getString("makhachhang"),
                    rs.getString("tenkhachhang"),
                    rs.getString("tenhang")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm thành viên vào bảng xếp hạng
    public boolean themThanhVien(HangThanhVien htv) {
        String sql = "INSERT INTO hangthanhvien(makhachhang, tenkhachhang, tenhang) VALUES(?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, htv.getMaKH());
            ps.setString(2, htv.getTenKH());
            ps.setString(3, htv.getTenHang());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 3. Xóa thành viên khỏi bảng xếp hạng
    public boolean xoaThanhVien(String maKH) {
        String sql = "DELETE FROM hangthanhvien WHERE makhachhang = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // ==========================================================================
    // PHẦN 2: TÍNH TOÁN TỪ BẢNG "DONHANG" (LOGIC CŨ ĐỂ CHECK TIỀN)
    // ==========================================================================

    // 4. Lấy danh sách khách hàng và tổng tiền (Dùng cho chức năng Tra cứu/Kiểm tra nhanh)
    // Hàm này giữ nguyên logic cũ của bạn: LEFT JOIN donhang để tính SUM(tongtien)
    public Vector<Vector<Object>> getKhachHangVaTongTien() {
        Vector<Vector<Object>> data = new Vector<>();
        
        String sql = "SELECT k.makhachhang, k.tenkhachhang, COALESCE(SUM(h.tongtien), 0) as TongChiTieu " +
                     "FROM khachhang k " +
                     "LEFT JOIN donhang h ON k.makhachhang = h.makhachhang " + 
                     "GROUP BY k.makhachhang, k.tenkhachhang";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString(1)); // Mã
                row.add(rs.getString(2)); // Tên
                // Cột 3 là Tổng tiền (để hiển thị lên ComboBox tra cứu)
                row.add(rs.getDouble(3)); 
                data.add(row);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return data;
    }

    // 5. LỌC KHÁCH HÀNG ĐỦ ĐIỀU KIỆN (Dùng cho Dialog Thêm mới)
    // Logic: Tính tổng tiền từ 'donhang', NHƯNG chỉ lấy những người CHƯA CÓ trong 'hangthanhvien'
    public Vector<String> getKhachHangTheoDieuKien(double minTien, double maxTien) {
        Vector<String> listKH = new Vector<>();
        
        String sql = "SELECT k.makhachhang, k.tenkhachhang, COALESCE(SUM(h.tongtien), 0) as TongTien " +
                     "FROM khachhang k " +
                     "LEFT JOIN donhang h ON k.makhachhang = h.makhachhang " +
                     "WHERE k.makhachhang NOT IN (SELECT makhachhang FROM hangthanhvien) " +
                     "GROUP BY k.makhachhang, k.tenkhachhang " +
                     "HAVING SUM(h.tongtien) >= ? AND SUM(h.tongtien) < ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, minTien);
            // Nếu maxTien là số cực lớn (cho hạng Kim Cương), ta set giá trị cao
            ps.setDouble(2, maxTien);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String ma = rs.getString(1);
                String ten = rs.getString(2);
                double tien = rs.getDouble(3);
                // Format chuỗi hiển thị: "MA - TEN (TIỀN)" để hiện trong ComboBox Dialog
                listKH.add(ma + " - " + ten + " (" + String.format("%.0f", tien) + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return listKH;
    }
}