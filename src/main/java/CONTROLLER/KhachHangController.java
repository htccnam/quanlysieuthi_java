package CONTROLLER;

import DAO.KhachHangDAO;
import MODEL.KhachHang;
import VIEW.KhachHangView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;

public class KhachHangController {
    private KhachHangView view;
    private KhachHangDAO dao;

    public KhachHangController(KhachHangView view) {
        this.view = view;
        this.dao = new KhachHangDAO();
        
        // Gắn sự kiện cho các nút
        this.view.addThemListener(new AddListener());
        this.view.addSuaListener(new UpdateListener());
        this.view.addXoaListener(new DeleteListener());
        this.view.addTimKiemListener(new SearchListener());
        this.view.addLamMoiListener(new ResetListener());
        
        // Gắn sự kiện click bảng
        this.view.addTableMouseListener(new TableClickListener());
        
        loadDataToTable();
        this.view.setVisible(true);
    }

    private void loadDataToTable() {
        try {
            List<KhachHang> list = dao.getAllKhachHang(); 
            view.hienThiBang(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- CÁC CLASS LẮNG NGHE SỰ KIỆN (INNER CLASSES) ---

    // 1. Thêm
    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                KhachHang k = view.getKhachHangFromForm();
                if(dao.addKhachHang(k)) {
                    view.showMessage("Thêm thành công!");
                    loadDataToTable();
                    view.resetForm();
                }
            } catch (Exception ex) {
                view.showMessage("Lỗi: " + ex.getMessage());
            }
        }
    }

    // 2. Sửa
    class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                KhachHang k = view.getKhachHangFromForm();
                if(dao.updateKhachHang(k)) {
                    view.showMessage("Sửa thành công!");
                    loadDataToTable();
                    view.resetForm();
                } else {
                    view.showMessage("Không tìm thấy mã khách hàng để sửa!");
                }
            } catch (Exception ex) {
                view.showMessage("Lỗi: " + ex.getMessage());
            }
        }
    }

    // 3. Xóa
    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String maKH = view.getMaKHDangChon();
            if(maKH.isEmpty()) {
                view.showMessage("Vui lòng chọn khách hàng cần xóa!");
                return;
            }
            
            int confirm = view.showConfirm("Bạn có chắc chắn muốn xóa khách hàng " + maKH + "?");
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    if(dao.deleteKhachHang(maKH)) {
                        view.showMessage("Xóa thành công!");
                        loadDataToTable();
                        view.resetForm();
                    }
                } catch (Exception ex) {
                    view.showMessage("Lỗi xóa: " + ex.getMessage());
                }
            }
        }
    }

    // 4. Tìm kiếm
    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String keyword = view.getTuKhoaTimKiem();
                List<KhachHang> list = dao.searchKhachHang(keyword);
                view.hienThiBang(list);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 5. Làm mới
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.resetForm();
            loadDataToTable(); // Load lại toàn bộ danh sách
        }
    }
    
    // 6. Sự kiện Click vào bảng (Để đổ dữ liệu lên form)
    class TableClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.fillFormFromSelectedRow();
        }
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}