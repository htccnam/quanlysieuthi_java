/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.NhaCungCap;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author VŨ HÙNG HẢI
 */


public class NhaCungCapDAO {
    public ArrayList<NhaCungCap> getAll(){
        ArrayList<NhaCungCap> list = new ArrayList<>();
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM nhacungcap";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                list.add(new NhaCungCap(
                        rs.getString("manhacungcap"),
                        rs.getString("tennhacungcap"),
                        rs.getString("loaihinh"),
                        rs.getString("email"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void insert(NhaCungCap ncc){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "INSERT INTO nhacungcap VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, ncc.getMaNCC());
            ps.setString(2, ncc.getTenNCC());
            ps.setString(3, ncc.getLoaiHinh());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getSdt());
            ps.setString(6, ncc.getDiaChi());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update(NhaCungCap ncc){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "UPDATE nhacungcap SET tennhacungcap=?,loaihinh=?,email=?,sodienthoai=?,diachi=? WHERE manhacungcap=?";
            PreparedStatement ps = c.prepareStatement(sql);
             ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getLoaiHinh());
            ps.setString(3, ncc.getEmail());
            ps.setString(4, ncc.getSdt());
            ps.setString(5, ncc.getDiaChi());
            ps.setString(6, ncc.getMaNCC());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(String maNCC){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "DELETE FROM nhacungcap WHERE manhacungcap=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, maNCC);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NhaCungCap checktrung(String ma){
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE manhacungcap = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new NhaCungCap(
                        rs.getString("manhacungcap"),
                        rs.getString("tennhacungcap"),
                        rs.getString("loaihinh"),
                        rs.getString("email"),
                        rs.getString("sodienthoai"),
                        rs.getString("diachi"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<NhaCungCap> timkiem(String keyword){
        ArrayList<NhaCungCap> list = new ArrayList<>();
        try {
            Connection c = DBConnection.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE manhacungcap LIKE ? OR tennhacungcap LIKE ?";
            PreparedStatement ps = c.prepareStatement(sql);
            String searchStr = "%" + keyword +"%";
            ps.setString(1,searchStr);
            ps.setString(2,searchStr);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){ 
                list.add(new NhaCungCap(
                rs.getString("manhacungcap"),
                rs.getString("tennhacungcap"),
                rs.getString("loaihinh"),
                rs.getString("email"),
                rs.getString("sodienthoai"),
                rs.getString("diachi")
                ));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}