/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.chucvu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public boolean suaChucVu(chucvu cv) throws Exception{
        String sqlString="update chucvu set tenchucvu=? where machucvu=?";
        try(Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString)){
            pr.setString(1, cv.getTenchucvuString());
            pr.setString(2, cv.getTenchucvuString());
            
            if(pr.executeUpdate()>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            throw e;
        }
    }
    public boolean xoaChucVu(String machucvuString) throws Exception{
        String sqlString="delete from chucvu where machucvu=?";
        
        try (Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString);){
            pr.setString(1, machucvuString);
            if(pr.executeUpdate()>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public List<chucvu> timKiemChucVu(String timkiemString) throws Exception{
        List<chucvu> list=new ArrayList<>();
        String noichuoiString="%"+timkiemString+"%";
        String sqlString="select * from chucvu where machucvu like ? or tenchucvu like ?";
        try (Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString);){
            pr.setString(1, noichuoiString);
            pr.setString(2, noichuoiString);
            ResultSet resultSet=pr.executeQuery();
            while(resultSet.next()){
                String machucvuString=resultSet.getString("machucvu");
                String tenchucvuString=resultSet.getString("tenchucvu");
                
                chucvu cv=new chucvu(machucvuString, tenchucvuString);
                list.add(cv);
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }
    public List<chucvu> getAllChucVu() throws Exception{
        List<chucvu> list = new ArrayList<>();
        String sqlString="select * from chucvu";
        try (Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString)){
            ResultSet resultSet=pr.executeQuery();
            while(resultSet.next()){
                String machucvuString=resultSet.getString("machucvu");
                String tenchucvuString=resultSet.getString("tenchucvu");
                
                chucvu cv=new chucvu(machucvuString, tenchucvuString);
                list.add(cv);
            }
 
        } catch (Exception e) {
            throw e;
        }
        return list;
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
    public boolean checkXoaChucVu(String machucvuString){
        String sqlString="select machucvu from nhanvien where machucvu=?";
        
        try (Connection con=DBConnection.getConnection(); PreparedStatement pr=con.prepareStatement(sqlString);){
            pr.setString(1, machucvuString);
            ResultSet resultSet=pr.executeQuery();
            if(resultSet.next()){
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
