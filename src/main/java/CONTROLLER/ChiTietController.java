package CONTROLLER;

import DAO.ChiTietDAO;
import MODEL.ChiTietDon;
import MODEL.DonHang;
import VIEW.ChiTietView;
import VIEW.TaoDonView;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChiTietController {
    private ChiTietView view;
    private ChiTietDAO dao;
    private ArrayList<DonHang> currentList;

    public ChiTietController(ChiTietView view) {
        this.view = view;
        this.dao = new ChiTietDAO();
        
        loadData(""); 
        
        initEvents();
    }
  
    private void loadData(String keyword) {
        if (keyword.isEmpty()) {
            currentList = dao.getAllDonHang();
        } else {
            currentList = dao.searchDonHang(keyword);
        }
        
        DataTable(currentList);
        ThongKe(currentList);
    }

    private void DataTable(ArrayList<DonHang> list) {
        DefaultTableModel model = view.getModel();
        model.setRowCount(0);
        for (DonHang dh : list) {
            model.addRow(new Object[]{
                dh.getMaDonHang(),
                dh.getNgayGD(),
                dh.getMaNV(),
                dh.getPTban(),
                dh.getPTgiaodich(),
                String.format("%,.0f đ", dh.getTongTien())
            });
        }
    }

    private void ThongKe(ArrayList<DonHang> list) {
        double tongDoanhThu = 0;
        int tongDon = list.size();
        
        for (DonHang dh : list) {
            tongDoanhThu += dh.getTongTien();
        }
        
        double trungBinh = (tongDon > 0) ? (tongDoanhThu / tongDon) : 0;

        view.getLblTongDoanhThu().setText(String.format("%,.0f đ", tongDoanhThu));
        view.getLblTongDon().setText(String.valueOf(tongDon));
        view.getLblDoanhThuTB().setText(String.format("%,.0f đ/đơn", trungBinh));
    }

    private void initEvents() {
        view.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String key = view.getTxtSearch().getText();
                loadData(key);
            }
        });

        view.getBtnXemChiTiet().addActionListener(e -> showChiTietDialog());

        view.getBtnXoa().addActionListener(e -> deleteDonHang());

        view.getBtnSua().addActionListener(e -> {          
                editDonHang(); 
        });
    }

    private void showChiTietDialog() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn đơn hàng cần xem!");
            return;
        }
        
        String maDH = view.getTable().getValueAt(row, 0).toString();
        ArrayList<ChiTietDon> listCT = dao.getChiTietByMaDH(maDH);
        
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
                loadData("");
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại! Vui lòng thử lại.");
            }
        }
    }

    private void editDonHang(){
    int row = view.getTable().getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(view, "Vui lòng chọn đơn hàng cần sửa!");
        return;
    }
    
    String maDH = view.getTable().getValueAt(row, 0).toString();
    
    DonHang dh = dao.getDonHangByMa(maDH);
    ArrayList<ChiTietDon> dsChiTiet = dao.getChiTietByMaDH(maDH);
    
    if (dh == null) return;

    TaoDonView editView = new TaoDonView();
    TaoDonController editController = new TaoDonController(editView);
    
    editController.setEditData(dh, dsChiTiet);
    
    JFrame frame = new JFrame("Chỉnh sửa đơn hàng: " + maDH);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng cửa sổ này
    frame.add(editView);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
            loadData("");
        }
    });
    }
     
}