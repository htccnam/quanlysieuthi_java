package DAO;

import MODEL.ChiTietDon;
import MODEL.DonHang;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietDAO {

    public ArrayList<DonHang> getAllDonHang() {
        ArrayList<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM donhang ORDER BY ngaylap DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(DataDonHang(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public ArrayList<DonHang> searchDonHang(String keyword) {
        ArrayList<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM donhang WHERE madonhang LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + keyword + "%");           
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(DataDonHang(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public ArrayList<ChiTietDon> getChiTietByMaDH(String maDH) {
        ArrayList<ChiTietDon> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietdonhang WHERE madonhang = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                list.add(new ChiTietDon(
                    rs.getString("madonhang"),
                    rs.getString("masanpham"),
                    rs.getString("tensanpham"), 
                    rs.getInt("soluong"),
                    rs.getDouble("dongia"),
                    rs.getDouble("thanhtien")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean deleteDonHang(String maDH) {
        String sqlChiTiet = "DELETE FROM chitietdonhang WHERE madonhang = ?";
        String sqlDonHang = "DELETE FROM donhang WHERE madonhang = ?";
        
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);//Transaction (1 là lưu tất cả 2 là không lưu gì)
            
            try (PreparedStatement ps1 = conn.prepareStatement(sqlChiTiet)) {
                ps1.setString(1, maDH);
                ps1.executeUpdate();
            }
            
            try (PreparedStatement ps2 = conn.prepareStatement(sqlDonHang)) {
                ps2.setString(1, maDH);
                int result = ps2.executeUpdate();
                
                if (result > 0) {
                    conn.commit();//Thành công thì lưu
                    return true;
                }
            }
            conn.rollback(); // Lỗi thì quay lại
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }
    
    private DonHang DataDonHang(ResultSet rs) throws SQLException {
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