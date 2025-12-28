/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import java.sql.*;
import java.util.ArrayList;
import MODEL.SanPham;
import java.math.BigDecimal;

public class SanPhamDAO {
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
    
    // Bạn tự viết thêm hàm update() và delete() tương tự nhé
}
