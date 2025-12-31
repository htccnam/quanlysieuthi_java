package CONTROLLER;

import VIEW.TaoDonView;
import DAO.*;
import MODEL.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;

public class TaoDonController {

    private TaoDonView view;

    public TaoDonController(TaoDonView view) {
        this.view = view;

        loadSanPham();

        view.getBtnThem().addActionListener(e -> themSanPham());
        view.getBtnXoa().addActionListener(e -> xoaSanPham());
        view.getBtnLuu().addActionListener(e -> luuDonHang());
    }

    /* ================= LOAD SẢN PHẨM ================= */
    private void loadSanPham() {
        DefaultTableModel model =
                (DefaultTableModel) view.getTblSanPham().getModel();
        model.setRowCount(0);

        SanPhamDAO spDAO = new SanPhamDAO();
        for (SanPham sp : spDAO.getAll()) {
            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getGiaBan(),
                    sp.getSoLuong()
            });
        }
    }

    /* ================= THÊM SẢN PHẨM ================= */
    private void themSanPham() {
        int row = view.getTblSanPham().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm!");
            return;
        }

        String slStr = JOptionPane.showInputDialog("Nhập số lượng:");
        if (slStr == null) return;

        int sl;
        try {
            sl = Integer.parseInt(slStr);
            if (sl <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Số lượng không hợp lệ!");
            return;
        }

        DefaultTableModel spModel =
                (DefaultTableModel) view.getTblSanPham().getModel();
        DefaultTableModel cartModel =
                (DefaultTableModel) view.getTblGioHang().getModel();

        int tonKho = (int) spModel.getValueAt(row, 3);
        if (sl > tonKho) {
            JOptionPane.showMessageDialog(view, "Không đủ tồn kho!");
            return;
        }

        String maSP = spModel.getValueAt(row, 0).toString();
        String tenSP = spModel.getValueAt(row, 1).toString();
        double gia = (double) spModel.getValueAt(row, 2);

        cartModel.addRow(new Object[]{
                maSP, tenSP, sl, gia, sl * gia
        });

        tinhTongTien();
    }

    /* ================= XOÁ SẢN PHẨM ================= */
    private void xoaSanPham() {
        int row = view.getTblGioHang().getSelectedRow();
        if (row >= 0) {
            ((DefaultTableModel) view.getTblGioHang().getModel())
                    .removeRow(row);
            tinhTongTien();
        }
    }

    /* ================= TÍNH TỔNG TIỀN ================= */
    private double tinhTongTien() {
        double sum = 0;
        DefaultTableModel model =
                (DefaultTableModel) view.getTblGioHang().getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            sum += (double) model.getValueAt(i, 4);
        }

        view.getLblTongTien().setText("Tổng tiền: " + sum);
        return sum;
    }

    /* ================= LƯU ĐƠN HÀNG ================= */
    private void luuDonHang() {
    if (view.getTblGioHang().getRowCount() == 0) {
        JOptionPane.showMessageDialog(view, "Đơn hàng chưa có sản phẩm!");
        return;
    }

    try {
        DonHangDAO dhDAO =
                new DonHangDAO(DBConnection.getConnection());

        double tongTien = tinhTongTien();

        DonHang dh = new DonHang();
        dh.setNgayLap(new Date(System.currentTimeMillis()));
        dh.setMaKH(null); // không có khách hàng
        dh.setMaNV(view.getTxtMaNV().getText());
        dh.setTongTien(tongTien);

        int maDonHang = dhDAO.insertDonHang(dh);

        DefaultTableModel model =
                (DefaultTableModel) view.getTblGioHang().getModel();
        SanPhamDAO spDAO = new SanPhamDAO();

        for (int i = 0; i < model.getRowCount(); i++) {
            String maSP = model.getValueAt(i, 0).toString();
            int sl = (int) model.getValueAt(i, 2);
            double gia = (double) model.getValueAt(i, 3);

            ChiTietDonHang ct =
                    new ChiTietDonHang(maDonHang, maSP, sl, gia);
            dhDAO.insertChiTiet(ct);

            // Trừ tồn kho
            for (SanPham sp : spDAO.getAll()) {
                if (sp.getMaSP().equals(maSP)) {
                    sp.setSoLuong(sp.getSoLuong() - sl);
                    spDAO.update(sp);
                    break;
                }
            }
        }

        JOptionPane.showMessageDialog(view, "Tạo đơn hàng thành công!");
        loadSanPham();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Lỗi lưu đơn hàng!");
    }
}

}
