package CONTROLLER;

import DAO.ChiTietDAO;
import MODEL.ChiTietDon;
import MODEL.DonHang;
import VIEW.ChiTietView;
import VIEW.TaoDonView;
import com.mysql.cj.xdevapi.Row;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietController {
    private ChiTietView view;
    private ChiTietDAO dao;
    private ArrayList<DonHang> currentList; // Lưu danh sách hiện tại để tính toán

    public ChiTietController(ChiTietView view) {
        this.view = view;
        this.dao = new ChiTietDAO();
        
        // Load dữ liệu ban đầu
        loadDataAndStats(""); 
        
        // Khởi tạo các sự kiện
        initEvents();
    }

    // Hàm trung tâm: Load dữ liệu -> Đổ bảng -> Tính thống kê
    private void loadDataAndStats(String keyword) {
        if (keyword.isEmpty()) {
            currentList = dao.getAllDonHang();
        } else {
            currentList = dao.searchDonHang(keyword); // Yêu cầu 1
        }
        
        fillTable(currentList);
        calculateStatistics(currentList); // Yêu cầu 2
    }

    private void fillTable(ArrayList<DonHang> list) {
        DefaultTableModel model = view.getModel();
        model.setRowCount(0);
        for (DonHang dh : list) {
            model.addRow(new Object[]{
                dh.getMaDonHang(),
                dh.getNgayGD(),
                dh.getMaNV(),
                dh.getPTban(),
                dh.getPTgiaodich(), // Phương thức thanh toán
                String.format("%,.0f đ", dh.getTongTien())
            });
        }
    }

    // YÊU CẦU 2: Sự kiện cho 3 ô thống kê
    private void calculateStatistics(ArrayList<DonHang> list) {
        double tongDoanhThu = 0;
        int tongDon = list.size();
        
        for (DonHang dh : list) {
            tongDoanhThu += dh.getTongTien();
        }
        
        double trungBinh = (tongDon > 0) ? (tongDoanhThu / tongDon) : 0;

        // Set text cho view
        view.getLblTongDoanhThu().setText(String.format("%,.0f đ", tongDoanhThu));
        view.getLblTongDon().setText(String.valueOf(tongDon));
        view.getLblDoanhThuTB().setText(String.format("%,.0f đ/đơn", trungBinh));
    }

    private void initEvents() {
        // YÊU CẦU 1: Tìm kiếm
        view.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String key = view.getTxtSearch().getText();
                loadDataAndStats(key);
            }
        });

        // YÊU CẦU 3: Xem chi tiết đơn
        view.getBtnXemChiTiet().addActionListener(e -> showChiTietDialog());

        // YÊU CẦU 5: Xóa đơn hàng
        view.getBtnXoa().addActionListener(e -> deleteDonHang());

        // YÊU CẦU 4: Sửa đơn hàng
        view.getBtnSua().addActionListener(e -> editDonHang());
    }

    // --- LOGIC XỬ LÝ YÊU CẦU 3 ---
    private void showChiTietDialog() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn đơn hàng cần xem!");
            return;
        }
        
        String maDH = view.getTable().getValueAt(row, 0).toString();
        ArrayList<ChiTietDon> listCT = dao.getChiTietByMaDH(maDH);
        
        // Tạo bảng phụ để hiển thị trong Popup
        String[] cols = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel modelPopup = new DefaultTableModel(cols, 0);
        for (ChiTietDon ct : listCT) {
            modelPopup.addRow(new Object[]{
                ct.getMaSanPham(), ct.getTenSanPham(), ct.getSoluong(), 
                String.format("%,.0f", ct.getGia()), 
                String.format("%,.0f", ct.getThanhtien())
            });
        }
        
        JTable tablePopup = new JTable(modelPopup);
        JScrollPane scroll = new JScrollPane(tablePopup);
        scroll.setPreferredSize(new Dimension(500, 300));
        
        JOptionPane.showMessageDialog(view, scroll, "Chi tiết đơn hàng: " + maDH, JOptionPane.PLAIN_MESSAGE);
    }

    // --- LOGIC XỬ LÝ YÊU CẦU 5 ---
    private void deleteDonHang() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn đơn hàng cần xóa!");
            return;
        }

        String maDH = view.getTable().getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc muốn xóa đơn hàng " + maDH + "?\nHành động này không thể hoàn tác!",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDonHang(maDH)) {
                JOptionPane.showMessageDialog(view, "Đã xóa thành công!");
                loadDataAndStats(""); // Load lại bảng và tính lại thống kê
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại! Vui lòng thử lại.");
            }
        }
    }

    // --- LOGIC XỬ LÝ YÊU CẦU 4 ---
    private void editDonHang() {
    int row = view.getTable().getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(view, "Vui lòng chọn đơn hàng cần sửa!");
        return;
    }
    
    // 1. Lấy mã đơn hàng từ bảng
    String maDH = view.getTable().getValueAt(row, 0).toString();
    
    // 2. Lấy dữ liệu đầy đủ từ Database
    DonHang dh = dao.getDonHangByMa(maDH);
    ArrayList<ChiTietDon> dsChiTiet = dao.getChiTietByMaDH(maDH);
    
    if (dh == null) return;

    // 3. Khởi tạo màn hình TaoDonView
    TaoDonView editView = new TaoDonView();
    TaoDonController editController = new TaoDonController(editView);
    
    // 4. Đổ dữ liệu cũ vào form vừa mở
    editController.setEditData(dh, dsChiTiet);
    
    // 5. Hiển thị Frame
    JFrame frame = new JFrame("Chỉnh sửa đơn hàng: " + maDH);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng cửa sổ này
    frame.add(editView);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    // 6. Lắng nghe khi frame đóng để cập nhật lại bảng danh sách chính
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
            loadDataAndStats(""); // Refresh lại Dashboard
        }
    });
    }
     
}