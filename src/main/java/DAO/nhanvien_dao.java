/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.nhanvienmodel;
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
public class nhanvien_dao {

    public boolean themNhanVien(nhanvienmodel nhanvien) throws Exception {
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
            throw new RuntimeException("lỗi thêm nhân viên :" + e.getMessage());

        }
    }

    public List<nhanvienmodel> getAllNhanVien() {
        List<nhanvienmodel> list=new ArrayList<>();
        String sqlString = "select * from nhanvien";

        try (Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString);ResultSet rs=pr.executeQuery();) {
            while(rs.next()){
                String manhanvienString=rs.getString("manhanvien");
                String tennhanvienString=rs.getString("tennhanvien");
                Date ngaysinhDate=rs.getDate("ngaysinh");
                String gioitinhString=rs.getString("gioitinh");
                String diachiString=rs.getString("diachi");
                String sodienthoaiString=rs.getString("sodienthoai");
                
                nhanvienmodel nv=new nhanvienmodel(manhanvienString, tennhanvienString, ngaysinhDate.toLocalDate(), gioitinhString, diachiString, sodienthoaiString);
                list.add(nv);
            }

        } catch (SQLException exception) {
            throw new RuntimeException("lỗi select all nhân viên");
        }catch(Exception exception){
            throw new RuntimeException("lỗi : " +exception.getMessage());
        }
        return list;
    }
}
