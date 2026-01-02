package CONTROLLER;

import DAO.DBConnection;
import DAO.DonHangDAO;
import MODEL.DonHang;
import VIEW.BanHangView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BanHangController {

    private BanHangView view;
    private DonHangDAO dao;

    public BanHangController(BanHangView view) throws Exception {
        this.view = view;
        this.dao = new DonHangDAO(DBConnection.getConnection());

        view.getBtnLamMoi().addActionListener(e -> loadData());
        view.getBtnXemChiTiet().addActionListener(e -> xemChiTiet());
    }

    // ================= LOAD DANH SÁCH ĐƠN HÀNG =================
    private void loadData() {
        try {
            DefaultTableModel model =
                    (DefaultTableModel) view.getTblDonHang().getModel();
            model.setRowCount(0);

            ArrayList<DonHang> ds = dao.getAllDonHang();
            for (DonHang dh : ds) {
                model.addRow(new Object[]{
                        dh.getMaDonHang(),
                        dh.getNgayLap(),
                        dh.getMaKH(),
                        dh.getMaNV(),
                        dh.getTongTien()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi tải dữ liệu:\n" + ex.getMessage());
        }
    }

    // ================= XEM CHI TIẾT =================
    private void xemChiTiet() {
        int row = view.getTblDonHang().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn một đơn hàng");
            return;
        }

        int maDonHang = Integer.parseInt(
                view.getTblDonHang().getValueAt(row, 0).toString()
        );

        JOptionPane.showMessageDialog(view,
                "Mã đơn hàng: " + maDonHang +
                "\n(Chức năng xem chi tiết sẽ mở dialog sau)");
    }
}
