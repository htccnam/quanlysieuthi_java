/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.LoaiHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VŨ HÙNG HẢI
 */
public class LoaiHangDAO {
    public ArrayList<LoaiHang> getAll(){
        ArrayList<LoaiHang> list = new ArrayList<>();
        try {
            Connection c =DBConnection.getConnection();
            String sql = "SELECT * FROM loaihang";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String ma = rs.getString("maloai");
                String ten = rs.getString("tenloai");
                list.add(new LoaiHang(ma,ten));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void insert(LoaiHang lh){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "INSERT INTO loaihang VALUES(?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, lh.getMaLoai());
            ps.setString(2,lh.getTenLoai());
            ps.executeUpdate();        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update(LoaiHang lh){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "UPDATE loaihang SET tenloai=? WHERE maloai=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1,lh.getTenLoai());
            ps.setString(2,lh.getMaLoai());
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    public void delete(String ma){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "DELETE FROM loaihang where maloai=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1,ma);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public LoaiHang checktrung(String id){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM loaihang where maloai=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               return new LoaiHang(rs.getString("maloai"), rs.getString("tenloai"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<LoaiHang> timkiem(String keyword){
        ArrayList<LoaiHang> list = new ArrayList();
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM loaihang WHERE tenloai LIKE ? OR maloai LIKE ?";
            PreparedStatement ps = c.prepareStatement(sql);
            String searchStr = "%" + keyword + "%";
            ps.setString(1, searchStr);
             ps.setString(2, searchStr);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 list.add(new LoaiHang(rs.getString("maloai"),rs.getString("tenloai")));
                 
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}