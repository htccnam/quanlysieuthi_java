/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class loginDAO {

    public boolean checkMatKhauTaiKhoan(String taikhoanString, String matkhauString) throws Exception {
        String sqlString = "select taikhoan from taikhoan where taikhoan=? and matkhau=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, matkhauString);
            pr.setString(2, matkhauString);
            ResultSet resultset = pr.executeQuery();
            if (resultset.next()) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
