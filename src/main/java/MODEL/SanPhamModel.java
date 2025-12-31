package MODEL;

import DAO.SanPhamDAO;
import java.util.ArrayList;

public class SanPhamModel {
    private SanPhamDAO dao = new SanPhamDAO();

    public ArrayList<SanPham> getList() { return dao.getAll(); }
    public void add(SanPham sp) { dao.insert(sp); }
    public void update(SanPham sp) { dao.update(sp); }
    public void delete(String maSP) { dao.delete(maSP); }
}