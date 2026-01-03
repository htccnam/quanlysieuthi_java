/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.chucvu;
import MODEL.nhanvien;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class nhanvienDAO {

    public boolean themNhanVien(nhanvien nhanvien) throws Exception {
        if (nhanvien == null) {
            return false;
        }

        String sqlString = "insert into nhanvien values (?,?,?,?,?,?,?,?)";
        try (Connection nhanvienConnection = DBConnection.getConnection(); PreparedStatement pr = nhanvienConnection.prepareStatement(sqlString);) {

            pr.setString(1, nhanvien.getManhanvienString());
            pr.setString(2, nhanvien.getTennhanvienString());
            pr.setDate(3, Date.valueOf(nhanvien.getNgaysinhDate()));
            pr.setString(4, nhanvien.getGioitinhString());
            pr.setString(5, nhanvien.getSodienthoaiString());
            pr.setString(6, nhanvien.getEmailString());
            pr.setString(7, nhanvien.getDiachiString());
            pr.setString(8, nhanvien.getMachucvuString());
            
            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw e;

        }
    }

    public boolean suaNhanVien(nhanvien nhanvien) throws Exception {
        if (nhanvien == null) {
            return false;
        }
        String sqlString = "update nhanvien set tennhanvien=?,ngaysinh=?,gioitinh=?,sodienthoai=?,email=?,diachi=?,machucvu=? where manhanvien=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            
            pr.setString(1, nhanvien.getTennhanvienString());
            pr.setDate(2, Date.valueOf(nhanvien.getNgaysinhDate()));
            pr.setString(3, nhanvien.getGioitinhString());
            pr.setString(4, nhanvien.getSodienthoaiString());
            pr.setString(5, nhanvien.getEmailString());
            pr.setString(6, nhanvien.getDiachiString());
            pr.setString(7, nhanvien.getMachucvuString());
            pr.setString(8, nhanvien.getManhanvienString());
            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean xoaNhanVien(String manhanvienString) throws Exception {
        String sqlString = "delete from nhanvien where manhanvien=?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString)) {
            pr.setString(1, manhanvienString);

            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            throw exception;
        }
    }

    public List<nhanvien> timKiemNhanVien(String manhanvienString) throws Exception {
        List<nhanvien> list = new ArrayList<>();
        String tukhoaString = "%" + manhanvienString + "%";
        String SqlString = "select * from nhanvien where manhanvien like ? or tennhanvien like ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(SqlString);) {
            pr.setString(1, tukhoaString);
            pr.setString(2, tukhoaString);

            try (ResultSet result = pr.executeQuery()) {
                while (result.next()) {
                    String manvString = result.getString("manhanvien");
                    String tennhanvienString = result.getString("tennhanvien");
                    LocalDate ngaysinhDate = result.getDate("ngaysinh").toLocalDate();
                    String gioitinhString = result.getString("gioitinh");
                    String sodienthoaiString = result.getString("sodienthoai");
                    String emailString = result.getString("email");
                    String diachiString = result.getString("diachi");
                    String machucvuString = result.getString("machucvu");

                    nhanvien nv = new nhanvien(manvString, tennhanvienString, ngaysinhDate, gioitinhString, sodienthoaiString, emailString, diachiString, machucvuString);
                    list.add(nv);
                }

            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public List<nhanvien> getAllNhanVien() throws Exception {
        List<nhanvien> list = new ArrayList<>();
        String sqlString = "select * from nhanvien";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString); ResultSet rs = pr.executeQuery();) {
            while (rs.next()) {
                String manhanvienString = rs.getString("manhanvien");
                String tennhanvienString = rs.getString("tennhanvien");
                LocalDate ngaysinhDate = rs.getDate("ngaysinh").toLocalDate();
                String gioitinhString = rs.getString("gioitinh");
                String sodienthoaiString = rs.getString("sodienthoai");
                String emailString = rs.getString("email");
                String diachiString = rs.getString("diachi");
                String machucvuString = rs.getString("machucvu");

                nhanvien nv = new nhanvien(manhanvienString, tennhanvienString, ngaysinhDate, gioitinhString, sodienthoaiString, emailString, diachiString, machucvuString);
                list.add(nv);
            }

        } catch (Exception e) {
            throw e;
        }
        return list;
    }
}
