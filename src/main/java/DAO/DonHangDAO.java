/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.ChiTietDon;
import MODEL.DonHang;
import MODEL.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DonHangDAO {
    public boolean insert(DonHang dh) throws SQLException, Exception{
        String sql = "INSERT INTO donhang VALUES (?,?,?,?,?,?,?,?)";
        boolean check = false;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, dh.getMaDonHang());
            ps.setString(2, dh.getMaKH());
            ps.setString(3, dh.getMaNV());
            ps.setString(4, dh.getMaKM());
            ps.setDate(5, new java.sql.Date(dh.getNgayGD().getTime()));
            ps.setString(6, dh.getPTban());
            ps.setString(7, dh.getPTgiaodich());
            ps.setDouble(8, dh.getTongTien());
            if(ps.executeUpdate() > 0){
                check = true;
            }         
        }catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
    public ArrayList<String> getMaNV(){
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT manhanvien FROM nhanvien;";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                list.add(rs.getString("manhanvien"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<String> getMaKH(){
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT makhachhang FROM khachhang;";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                list.add(rs.getString("makhachhang"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } 
    
    public ArrayList<String> getMaKM(){
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT makhuyenmai FROM khuyenmai;";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                list.add(rs.getString("makhuyenmai"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } 
    
    public ArrayList<SanPham> getSP(){
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT masanpham, tensanpham, soluong, giaban FROM sanpham";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                SanPham sp = new SanPham(rs.getString("masanpham"), rs.getString("tensanpham"), rs.getInt("soluong"), rs.getDouble("giaban"));
                list.add(sp);
            }            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    //AI
    public ArrayList<SanPham> timKiemSP(String keyword) {
    ArrayList<SanPham> list = new ArrayList<>();
    String sql = "SELECT masanpham, tensanpham, soluong, giaban FROM sanpham WHERE tensanpham LIKE ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPham(
                    rs.getString("masanpham"), 
                    rs.getString("tensanpham"), 
                    rs.getInt("soluong"), 
                    rs.getDouble("giaban")
                ));
            }
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}

// [MỚI] Hàm lưu chi tiết đơn hàng vào bảng 'chitiethoadon' (Yêu cầu 6)
// Giả sử bảng chitiethoadon có các cột: madonhang, masanpham, soluong, dongia, thanhtien
public boolean insertChiTiet(ChiTietDon ctd) {
    String sql = "INSERT INTO chitietdonhang (madonhang, masanpham, tensanpham, soluong, dongia, thanhtien) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, ctd.getMaDonHang());
        ps.setString(2, ctd.getMaSanPham());
        ps.setString(3, ctd.getTenSanPham());
        ps.setInt(4, ctd.getSoluong());
        ps.setDouble(5, ctd.getGia());
        ps.setDouble(6, ctd.getThanhtien());
        
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean update(DonHang dh, ArrayList<ChiTietDon> dsChiTiet) {
    Connection conn = null;
    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false); // Bắt đầu Transaction

        // 1. Cập nhật bảng DonHang
        String sqlUpdateDon = "UPDATE donhang SET makhachhang=?, manhanvien=?, makhuyenmai=?, ngaylap=?, phuongthucban=?, thanhtoan=?, tongtien=? WHERE madonhang=?";
        PreparedStatement psDon = conn.prepareStatement(sqlUpdateDon);
        psDon.setString(1, dh.getMaKH());
        psDon.setString(2, dh.getMaNV());
        psDon.setString(3, dh.getMaKM());
        psDon.setDate(4, new java.sql.Date(dh.getNgayGD().getTime()));
        psDon.setString(5, dh.getPTban());
        psDon.setString(6, dh.getPTgiaodich());
        psDon.setDouble(7, dh.getTongTien());
        psDon.setString(8, dh.getMaDonHang());
        psDon.executeUpdate();

        // 2. Xóa tất cả ChiTiet cũ của đơn hàng này
        String sqlDelChiTiet = "DELETE FROM chitietdonhang WHERE madonhang=?";
        PreparedStatement psDel = conn.prepareStatement(sqlDelChiTiet);
        psDel.setString(1, dh.getMaDonHang());
        psDel.executeUpdate();

        // 3. Chèn lại danh sách ChiTiet mới từ bảng
        String sqlInsChiTiet = "INSERT INTO chitietdonhang (madonhang, masanpham, tensanpham, soluong, dongia, thanhtien) VALUES (?,?,?,?,?,?)";
        PreparedStatement psIns = conn.prepareStatement(sqlInsChiTiet);
        for (ChiTietDon ct : dsChiTiet) {
            psIns.setString(1, ct.getMaDonHang());
            psIns.setString(2, ct.getMaSanPham());
            psIns.setString(3, ct.getTenSanPham());
            psIns.setInt(4, ct.getSoluong());
            psIns.setDouble(5, ct.getGia());
            psIns.setDouble(6, ct.getThanhtien());
            psIns.addBatch();
        }
        psIns.executeBatch();

        conn.commit(); // Lưu tất cả thay đổi
        return true;
    } catch (Exception e) {
        try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        e.printStackTrace();
        return false;
    } finally {
        try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) { e.printStackTrace(); }
    }
}
}
