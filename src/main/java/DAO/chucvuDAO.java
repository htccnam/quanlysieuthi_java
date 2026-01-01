/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.chucvu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class chucvuDAO {
    public boolean themChucVu(chucvu cv) throws Exception{
        String sqlString="insert into chucvu values (?,?)";
        try (Connection con =DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString);){
            pr.setString(1, cv.getMachucvuString());
            pr.setString(2, cv.getTenchucvuString());
            
            if(pr.executeUpdate()>0) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public boolean checkTrungMaChucVu(String machucvuString){
        String sqlString="select machucvu from chucvu where machucvu=?";
        try (Connection con=DBConnection.getConnection(); PreparedStatement pr =con.prepareStatement(sqlString); ){
            pr.setString(1, machucvuString);
            ResultSet result=pr.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
