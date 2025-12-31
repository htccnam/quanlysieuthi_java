/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.tintuc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class tintucDAO {

    public boolean themTinTuc(tintuc tt) {
        String sqlString = "insert into tintuc values (?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, tt.getMatintucString());
            pr.setString(2, tt.getTieudeString());
            pr.setString(3, tt.getManhanvienString());
            pr.setString(4, tt.getNoidungString());
            pr.setString(5, tt.getLoaitinString());
            pr.setDate(6, Date.valueOf(tt.getNgaydangString()));

            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException exception) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

     public boolean suaTinTuc(tintuc tt) {
        String sql = """
            UPDATE tintuc 
            SET tieude=?, manhanvien=?, noidung=?, loaitin=?, ngaydang=?
            WHERE matintuc=?
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, tt.getTieudeString());
            pr.setString(2, tt.getManhanvienString());
            pr.setString(3, tt.getNoidungString());
            pr.setString(4, tt.getLoaitinString());
            pr.setDate(5, Date.valueOf(tt.getNgaydangString()));
            pr.setString(6, tt.getMatintucString());

            return pr.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi sửa tin tức: " + e.getMessage());
        }
    }
      public boolean xoaTinTuc(String matintuc) {
        String sql = "DELETE FROM tintuc WHERE matintuc=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setString(1, matintuc);
            return pr.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xóa tin tức: " + e.getMessage());
        }
    }
      public List<tintuc> timKiem(String keyword) {
        List<tintuc> list = new ArrayList<>();
        String sql = """
            SELECT * FROM tintuc 
            WHERE matintuc LIKE ? OR tieude LIKE ?
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {

            String key = "%" + keyword + "%";
            pr.setString(1, key);
            pr.setString(2, key);

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                list.add(new tintuc(
                        rs.getString("matintuc"),
                        rs.getString("tieude"),
                        rs.getString("manhanvien"),
                        rs.getString("noidung"),
                        rs.getString("loaitin"),
                        rs.getDate("ngaydang").toLocalDate()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tìm kiếm: " + e.getMessage());
        }
        return list;
    }

    public List<tintuc> getAll() {
        List<tintuc> list = new ArrayList<>();
        String sqlString = "select * from tintuc";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString); ResultSet rs = pr.executeQuery();) {
            while (rs.next()) {
                String matintucString = rs.getString("matintuc");
                String tieudeString = rs.getString("tieude");
                String manhanvienString = rs.getString("manhanvien");
                String noidungString = rs.getString("noidung");
                String loaitinString = rs.getString("loaitin");
                LocalDate ngaydangLocalDate = rs.getDate("ngaydang").toLocalDate();

                tintuc tt = new tintuc(matintucString, tieudeString, manhanvienString, noidungString, loaitinString, ngaydangLocalDate);
                list.add(tt);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }
}
