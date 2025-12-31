package DAO;

import MODEL.SanPham;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    
    public ArrayList<SanPham> getAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        try {
            Connection c = DBConnection.getConnection();
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM sanpham");
            while (rs.next()) {
                list.add(new SanPham(
                    rs.getString("masanpham"), rs.getString("tensanpham"),
                    rs.getString("maloai"), rs.getString("manhacungcap"),
                    rs.getString("xuatxu"), rs.getInt("soluong"),
                    rs.getDate("ngaysanxuat"), rs.getDate("hansudung"),
                    rs.getString("tinhtrang"), rs.getDouble("gianhap"),
                    rs.getDouble("giaban"), rs.getString("donvitinh")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void insert(SanPham sp) {
        String sql = "INSERT INTO sanpham VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMaLoai());
            ps.setString(4, sp.getMaNCC());
            ps.setString(5, sp.getXuatXu());
            ps.setInt(6, sp.getSoLuong());
            ps.setDate(7, sp.getNgaySX());
            ps.setDate(8, sp.getHanSD());
            ps.setString(9, sp.getTinhTrang());
            ps.setDouble(10, sp.getGiaNhap());
            ps.setDouble(11, sp.getGiaBan());
            ps.setString(12, sp.getDonViTinh());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(SanPham sp) {
        String sql = "UPDATE sanpham SET tensanpham=?, maloai=?, manhacungcap=?, xuatxu=?, soluong=?, ngaysanxuat=?, hansudung=?, tinhtrang=?, gianhap=?, giaban=?, donvitinh=? WHERE masanpham=?";
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getMaLoai());
            ps.setString(3, sp.getMaNCC());
            ps.setString(4, sp.getXuatXu());
            ps.setInt(5, sp.getSoLuong());
            ps.setDate(6, sp.getNgaySX());
            ps.setDate(7, sp.getHanSD());
            ps.setString(8, sp.getTinhTrang());
            ps.setDouble(9, sp.getGiaNhap());
            ps.setDouble(10, sp.getGiaBan());
            ps.setString(11, sp.getDonViTinh());
            ps.setString(12, sp.getMaSP());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(String maSP) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM sanpham WHERE masanpham=?");
            ps.setString(1, maSP);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}