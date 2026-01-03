/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import DAO.LoaiHangDAO;
import java.util.ArrayList;

/**
 *
 * @author VŨ HÙNG HẢI
 */

public class LoaiHangModel {
    private LoaiHangDAO dao = new LoaiHangDAO();
    public ArrayList<LoaiHang> getList(){
        return dao.getAll();
}
    public void add(LoaiHang lh){
        dao.insert(lh);
    }
    public void update(LoaiHang lh){
        dao.update(lh);
    }
    public void delete(String ma){
        dao.delete(ma);
    }
    public boolean checkExist(String ma){
        LoaiHang lh = dao.checktrung(ma);
        return lh!=null;
    }
    public ArrayList<LoaiHang> search(String keyword){
        return dao.timkiem(keyword);
    }
}