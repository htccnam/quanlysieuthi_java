/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws Exception {
       try{
            // Cập nhật tên DB
        String url = "jdbc:mysql://localhost:3306/quanlysieuthi"; 
        String user = "root";
        String pass = ""; // Điền pass nếu có
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
           
       }catch (SQLException exception){
           throw new RuntimeException("sai kết nối database");
           
       }catch (ClassNotFoundException exception){
           throw new RuntimeException("không tìm thấy driver");
       }
    }
}
