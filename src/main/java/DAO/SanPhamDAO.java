package DAO;

import java.sql.*;
import java.util.ArrayList;
import MODEL.SanPham;
import java.math.BigDecimal;

public class SanPhamDAO {
    
    // 1. Lấy danh sách
    public ArrayList<SanPham> getAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        try {
            Connection c = DBConnection.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM sanpham");
            while (rs.next()) {
                list.add(new SanPham(
                    rs.getString("masanpham"),
                    rs.getString("tensanpham"),
                    rs.getString("maloaihang"),
                    rs.getString("mathuonghieu"),
                    rs.getInt("soluong"),
                    rs.getBigDecimal("gianhap"),
                    rs.getBigDecimal("giaban"),
                    rs.getString("donvitinh")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới
    public void insert(SanPham sp) {
        String sql = "INSERT INTO sanpham VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMaLoai());
            ps.setString(4, sp.getMaTH());
            ps.setInt(5, sp.getSoLuong());
            ps.setBigDecimal(6, sp.getGiaNhap());
            ps.setBigDecimal(7, sp.getGiaBan());
            ps.setString(8, sp.getDonViTinh());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 3. Cập nhật (Sửa)
    public void update(SanPham sp) {
        String sql = "UPDATE sanpham SET tensanpham=?, maloaihang=?, mathuonghieu=?, soluong=?, gianhap=?, giaban=?, donvitinh=? WHERE masanpham=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getMaLoai());
            ps.setString(3, sp.getMaTH());
            ps.setInt(4, sp.getSoLuong());
            ps.setBigDecimal(5, sp.getGiaNhap());
            ps.setBigDecimal(6, sp.getGiaBan());
            ps.setString(7, sp.getDonViTinh());
            ps.setString(8, sp.getMaSP()); // Điều kiện WHERE
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 4. Xóa
    public void delete(String maSP) {
        String sql = "DELETE FROM sanpham WHERE masanpham=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}