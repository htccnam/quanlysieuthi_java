/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import DAO.NhaCungCapDAO;
import java.util.ArrayList;

/**
 *
 * @author VŨ HÙNG HẢI
 */



public class NhaCungCapModel {
    private NhaCungCapDAO dao = new NhaCungCapDAO();
    public ArrayList<NhaCungCap> getList(){
       return dao.getAll();
    }
    public void add(NhaCungCap ncc){
        dao.insert(ncc);
    }
    public void update(NhaCungCap ncc){
        dao.update(ncc);
    }
    public void delete(String ma){
        dao.delete(ma);
    }
    public boolean checkExist(String ma){
        NhaCungCap ncc = dao.checktrung(ma);
        if(ncc!=null){
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<NhaCungCap> timkiem(String keyword){
        return dao.timkiem(keyword);
    }
}
