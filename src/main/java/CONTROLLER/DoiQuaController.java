package CONTROLLER;

import DAO.DoiQuaDAO;
import VIEW.DoiQuaView;
import java.util.Vector;
import javax.swing.JOptionPane;

public class DoiQuaController {
    private DoiQuaView view;
    private DoiQuaDAO dao;
    private String currentMaKH = null; 

    public DoiQuaController() {
        this.view = new DoiQuaView();
        this.dao = new DoiQuaDAO();
        
      
        loadKhachHang();
        
      
        view.cboKhachHang.addActionListener(e -> onCustomerSelected());
        
     
        view.setOnRedeemAction((tenQua, diemCan) -> xuLyDoiQua(tenQua, diemCan));
        
     
        view.setVisible(true);
    }

    private void loadKhachHang() {
        Vector<String> list = dao.getListKhachHang();
        view.cboKhachHang.removeAllItems();
        view.cboKhachHang.addItem("-- Chọn Khách Hàng --");
        for (String s : list) {
            view.cboKhachHang.addItem(s);
        }
    }

    private void onCustomerSelected() {
        String selected = (String) view.cboKhachHang.getSelectedItem();
        if (selected == null || selected.startsWith("--")) {
            currentMaKH = null;
            view.lblPoints.setText("Điểm khả dụng: 0");
            view.modelHistory.setRowCount(0);
            return;
        }
        
        
        currentMaKH = selected.split(" - ")[0];
        
   
        refreshData();
    }
    
    private void refreshData() {
        if (currentMaKH == null) return;
        
    
        int diem = dao.getDiemHienTai(currentMaKH);
        view.lblPoints.setText("Điểm khả dụng: " + diem);
        
     
        Vector<Vector<Object>> history = dao.getLichSuDoi(currentMaKH);
        view.modelHistory.setRowCount(0);
        for(Vector<Object> row : history) {
            view.modelHistory.addRow(row);
        }
    }

    private void xuLyDoiQua(String tenQua, int diemCan) {
        if (currentMaKH == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng trước!");
            return;
        }
        
        int diemHienCo = dao.getDiemHienTai(currentMaKH);
        
        if (diemHienCo < diemCan) {
            JOptionPane.showMessageDialog(view, 
                "Không đủ điểm! Cần thêm " + (diemCan - diemHienCo) + " điểm.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Xác nhận đổi " + diemCan + " điểm lấy '" + tenQua + "'?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            // Gọi DAO thực hiện trừ điểm và lưu lịch sử
            if (dao.doiQua(currentMaKH, tenQua, diemCan)) {
                JOptionPane.showMessageDialog(view, "Đổi quà thành công!");
                refreshData(); //Cập nhật lại số điểm và bảng lịch sử
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi hệ thống khi đổi quà!");
            }
        }
    }
}