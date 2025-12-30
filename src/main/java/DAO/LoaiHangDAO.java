/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.LoaiHang;
import java.sql.*;
import java.util.ArrayList;

public class LoaiHangDAO {
    
    public ArrayList<LoaiHang> getAll() {
        ArrayList<LoaiHang> list = new ArrayList<>();
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM loaihang";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new LoaiHang(
                    rs.getString("maloai"),
                    rs.getString("tenloai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void insert(LoaiHang lh) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO loaihang VALUES(?,?)");
            ps.setString(1, lh.getMaLoai());
            ps.setString(2, lh.getTenLoai());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(LoaiHang lh) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE loaihang SET tenloai=? WHERE maloai=?");
            ps.setString(1, lh.getTenLoai());
            ps.setString(2, lh.getMaLoai());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(String maLoai) {
        try {
            Connection c = DBConnection.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM loaihang WHERE maloai=?");
            ps.setString(1, maLoai);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}