/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */
import DAO.SanPhamDAO;
import java.util.ArrayList;

public class SanPhamModel {
    private SanPhamDAO dao = new SanPhamDAO();
    public ArrayList<SanPham> getList() { return dao.getAll(); }
    public void add(SanPham sp) { dao.insert(sp); }
    // Thêm hàm update, delete gọi sang DAO
}
