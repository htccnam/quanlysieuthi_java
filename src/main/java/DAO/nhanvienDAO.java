/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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

    public boolean themNhanVien(nhanvien nhanvien) throws Exception{
        if (nhanvien == null) {
            return false;
        }

        String sqlString = "insert into nhanvien values (?,?,?,?,?,?)";
        try (Connection nhanvienConnection = DBConnection.getConnection(); PreparedStatement nhanvienPreparedStatement = nhanvienConnection.prepareStatement(sqlString);) {

            nhanvienPreparedStatement.setString(1, nhanvien.getMaNhanVienString());
            nhanvienPreparedStatement.setString(2, nhanvien.getTenNhanVienString());
            nhanvienPreparedStatement.setDate(3, Date.valueOf(nhanvien.getNgaySinhDate()));
            nhanvienPreparedStatement.setString(4, nhanvien.getGioiTinhString());
            nhanvienPreparedStatement.setString(5, nhanvien.getDiaChiString());
            nhanvienPreparedStatement.setString(6, nhanvien.getSoDienThoaiString());

            if (nhanvienPreparedStatement.executeUpdate() > 0) {
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
        String sqlString = "update nhanvien set tennhanvien=?,ngaysinh=?,gioitinh=?,diachi=?,sodienthoai=? where manhanvien=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, nhanvien.getTenNhanVienString());
            pr.setDate(2, Date.valueOf(nhanvien.getNgaySinhDate()));
            pr.setString(3, nhanvien.getGioiTinhString());
            pr.setString(4, nhanvien.getDiaChiString());
            pr.setString(5, nhanvien.getSoDienThoaiString());
            pr.setString(6, nhanvien.getMaNhanVienString());
            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch(SQLException exception){
            throw exception;
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
        } catch(SQLException exception){
            throw exception;
        }
    }

    public List<nhanvien> timKiemNhanVien(String manhanvienString) {
        List<nhanvien> list=new ArrayList<>();
        String tukhoaString="%"+manhanvienString+"%";
        String SqlString = "select * from nhanvien where manhanvien like ? or tennhanvien like ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(SqlString); ) {
            pr.setString(1, tukhoaString);
            pr.setString(2, tukhoaString);
            
            try(ResultSet result=pr.executeQuery()){
                while(result.next()){
                    String manhanvienString1=result.getString("manhanvien");
                    String tennhanvienString=result.getString("tennhanvien");
                    Date ngaysinhDate=result.getDate("ngaysinh");
                    String gioitinhString=result.getString("gioitinh");
                    String diachiString=result.getString("diachi");
                    String sodienthoaiString=result.getString("sodienthoai");
                    
                    nhanvien nv=new nhanvien(manhanvienString1, tennhanvienString, ngaysinhDate.toLocalDate() , gioitinhString, diachiString, sodienthoaiString);
                    list.add(nv);
                }
                
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Lỗi tìm kiếm"+exception.getMessage());

        }catch (Exception exception){
            throw new RuntimeException("lỗi tìm kiếm"+exception.getMessage());
        }
        return list;
    }

    public List<nhanvien> getAllNhanVien() {
        List<nhanvien> list = new ArrayList<>();
        String sqlString = "select * from nhanvien";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString); ResultSet rs = pr.executeQuery();) {
            while (rs.next()) {
                String manhanvienString = rs.getString("manhanvien");
                String tennhanvienString = rs.getString("tennhanvien");
                Date ngaysinhDate = rs.getDate("ngaysinh");
                String gioitinhString = rs.getString("gioitinh");
                String diachiString = rs.getString("diachi");
                String sodienthoaiString = rs.getString("sodienthoai");

                nhanvien nv = new nhanvien(manhanvienString, tennhanvienString, ngaysinhDate.toLocalDate(), gioitinhString, diachiString, sodienthoaiString);
                list.add(nv);
            }

        } catch (SQLException exception) {
            throw new RuntimeException("lỗi select all nhân viên");
        } catch (Exception exception) {
            throw new RuntimeException("lỗi : " + exception.getMessage());
        }
        return list;
    }
}
