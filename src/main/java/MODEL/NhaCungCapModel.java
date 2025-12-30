/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import DAO.NhaCungCapDAO;
import java.util.ArrayList;

public class NhaCungCapModel {
    private NhaCungCapDAO dao = new NhaCungCapDAO();

    public ArrayList<NhaCungCap> getList() { return dao.getAll(); }
    public void add(NhaCungCap ncc) { dao.insert(ncc); }
    public void update(NhaCungCap ncc) { dao.update(ncc); }
    public void delete(String maNCC) { dao.delete(maNCC); }
}
