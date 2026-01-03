/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import MODEL.khuyenmai;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class khuyenmaiDAO {

    public boolean themKhuyenMai(khuyenmai km) throws Exception {
        String sqlString = "insert into khuyenmai values (?,?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, km.getMakhuyenmaiString());
            pr.setString(2, km.getTenkhuyenmaiString());
            pr.setString(3, km.getMotaString());
            pr.setDate(4, Date.valueOf(km.getNgaytaoDate()));

            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean suaKhuyenMai(khuyenmai km) throws Exception {
        String sqlString = "update khuyenmai set tenkhuyenmai=? , mota=? , ngaytao=? where makhuyenmai=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, km.getTenkhuyenmaiString());
            pr.setString(2, km.getMotaString());
            pr.setDate(3, Date.valueOf(km.getNgaytaoDate()));
            pr.setString(4, km.getMakhuyenmaiString());

            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean xoaKhuyenMai(String makhuyenmaiString) throws Exception {
        String sqlString = "delete from khuyenmai where makhuyenmai=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, makhuyenmaiString);

            if (pr.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<khuyenmai> timKiemKhuyenMai(String texttimkiemString) throws Exception {
        List<khuyenmai> list = new ArrayList<>();
        String noichuoiString = "%" + texttimkiemString + "%";
        String sqlString = "select * from khuyenmai where makhuyenmai like ? or tenkhuyenmai like ?";

        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, noichuoiString);
            pr.setString(2, noichuoiString);

            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                String makhuyenmaiString = resultSet.getString("makhuyenmai");
                String tenkhuyenmaiString = resultSet.getString("tenkhuyenmai");
                String motaString = resultSet.getString("mota");
                LocalDate ngaytaoDate = resultSet.getDate("ngaytao").toLocalDate();

                khuyenmai km = new khuyenmai(makhuyenmaiString, tenkhuyenmaiString, motaString, ngaytaoDate);
                list.add(km);
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public List<khuyenmai> getAllKhuyenMai() throws Exception {
        List<khuyenmai> list = new ArrayList<>();
        String sqlString = "select * from khuyenmai";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            ResultSet resultset = pr.executeQuery();
            while (resultset.next()) {
                String makhuyenmaiString = resultset.getString("makhuyenmai");
                String tenkhuyenmaiString = resultset.getString("tenkhuyenmai");
                String motaString = resultset.getString("mota");
                LocalDate ngaytaoDate = resultset.getDate("ngaytao").toLocalDate();

                khuyenmai km = new khuyenmai(makhuyenmaiString, tenkhuyenmaiString, motaString, ngaytaoDate);
                list.add(km);
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public boolean checkTrungMaKhuyenMai(String makhuyenmaiString) throws Exception {
        String sqlString = "select makhuyenmai from khuyenmai where makhuyenmai=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pr = con.prepareStatement(sqlString);) {
            pr.setString(1, makhuyenmaiString);

            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){ 
            throw e;
        }

    }
}
