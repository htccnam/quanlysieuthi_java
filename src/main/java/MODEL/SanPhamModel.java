package MODEL;

import DAO.SanPhamDAO;
import java.util.ArrayList;

public class SanPhamModel {
    private SanPhamDAO dao = new SanPhamDAO();

    public ArrayList<SanPham> getList() { return dao.getAll(); }
    public void add(SanPham sp) { dao.insert(sp); }
    public void update(SanPham sp) { dao.update(sp); }
    public void delete(String maSP) { dao.delete(maSP); }
    public ArrayList<SanPham> timkiem(String keyword){
        return dao.timkiem(keyword);
    }
    public boolean checkExist(String ma){
        SanPham sp  = dao.checktrung(ma);
        if (sp != null) {
            return true; 
        } else {
            return false; 
        }
    }
    }
