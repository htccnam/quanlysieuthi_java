/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.nhanvienmodel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            nhanvienPreparedStatement.setDate(3, nhanvien.getNgaySinhDate());
            nhanvienPreparedStatement.setString(4, nhanvien.getGioiTinhString());
            nhanvienPreparedStatement.setString(5, nhanvien.getDiaChiString());
            nhanvienPreparedStatement.setString(6, nhanvien.getSoDienThoaiString());

            if (nhanvienPreparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("lỗi thêm nhân viên");

        }
    }
}
