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
import MODEL.NhanVien;

public class NhanVienDAO {
    // Hàm check login dựa trên bảng NhanVien
    public boolean checkLogin(String user, String pass) {
        String sql = "SELECT * FROM nhanvien WHERE taikhoan=? AND matkhau=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}