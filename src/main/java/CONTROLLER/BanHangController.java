/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;


import DAO.DonHangDAO;
import DAO.SanPhamDAO;
import MODEL.ChiTietDonHang;
import MODEL.DonHang;
import MODEL.SanPham;
import VIEW.DonHangView;
import VIEW.ChonSanPhamDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BanHangController {
    private final DonHangView view;
    private final SanPhamDAO sanPhamDAO;
    private final DonHangDAO donHangDAO;
    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0");

    public BanHangController(DonHangView view) {
        this.view = view;
        this.sanPhamDAO = new SanPhamDAO();
        this.donHangDAO = new DonHangDAO();
        init();
    }

    private void init() {
        // Load dữ liệu cho combobox (nên thay bằng DAO KhachHang/NhanVien nếu có)
        loadCombos();

        // Gắn sự kiện nút
        view.getBtnAdd().addActionListener(e -> onAddProduct());
        view.getBtnRemove().addActionListener(e -> onRemoveProduct());
        view.getBtnSave().addActionListener(e -> onSaveOrderAsync());

        // Khi sửa số lượng trong table, tự cập nhật thành tiền
        view.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (col == 2) { // cột Số lượng
                    updateRowThanhTien(row);
                }
            }
        });
    }

    private void loadCombos() {
        // Nếu repo có DAO KhachHang/NhanVien thì gọi thay thế phần này
        JComboBox<String> cbKh = view.getCbKhachHang();
        JComboBox<String> cbNv = view.getCbNhanVien();

        cbKh.removeAllItems();
        cbKh.addItem(null); // cho khách vãng lai
        cbKh.addItem("KH001");
        cbKh.addItem("KH002");

        cbNv.removeAllItems();
        cbNv.addItem("NV001");
        cbNv.addItem("NV002");
    }

    private void onAddProduct() {
        List<SanPham> ds = sanPhamDAO.getAll();
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        ChonSanPhamDialog dlg = new ChonSanPhamDialog(parent, ds);
        dlg.setVisible(true);
        List<SanPham> selected = dlg.getSelected();
        if (selected == null || selected.isEmpty()) return;
        DefaultTableModel model = view.getModel();
        for (SanPham p : selected) {
            int row = findRowByMa(model, p.getMaSP());
            if (row >= 0) {
                // đã có -> tăng số lượng 1
                try {
                    int qty = Integer.parseInt(model.getValueAt(row, 2).toString());
                    qty++;
                    model.setValueAt(qty, row, 2);
                    model.setValueAt(qty * p.getSoLuong(), row, 4);
                } catch (NumberFormatException ex) {
                    // nếu parse lỗi, đặt lại 1
                    model.setValueAt(1, row, 2);
                    model.setValueAt(p.getGiaBan(), row, 4);
                }
            } else {
                model.addRow(new Object[]{
                    p.getMaSP(),
                    p.getTenSP(),
                    1,
                    p.getGiaBan(),
                    p.getGiaBan()
                });
            }
        }
    }

    private int findRowByMa(DefaultTableModel model, String ma) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Object val = model.getValueAt(i, 0);
            if (val != null && ma.equals(val.toString())) return i;
        }
        return -1;
    }

    private void onRemoveProduct() {
        int r = view.getTable().getSelectedRow();
        if (r >= 0) {
            ((DefaultTableModel) view.getTable().getModel()).removeRow(r);
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateRowThanhTien(int row) {
        DefaultTableModel model = view.getModel();
        try {
            Object qtyObj = model.getValueAt(row, 2);
            Object priceObj = model.getValueAt(row, 3);
            int qty = Integer.parseInt(qtyObj.toString());
            double price = Double.parseDouble(priceObj.toString());
            if (qty < 0) qty = 0;
            double thanhTien = qty * price;
            model.setValueAt(thanhTien, row, 4);
        } catch (Exception ex) {
            // nếu parse lỗi, bỏ qua để người dùng sửa lại
        }
    }

    private void onSaveOrderAsync() {
        // chạy lưu trên background để không block UI
        new SwingWorker<Void, Void>() {
            private Exception error;

            @Override
            protected Void doInBackground() {
                try {
                    onSaveOrder();
                } catch (Exception ex) {
                    this.error = ex;
                }
                return null;
            }

            @Override
            protected void done() {
                if (error != null) {
                    JOptionPane.showMessageDialog(view, "Lưu đơn hàng thất bại: " + error.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Lưu đơn hàng thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                }
            }
        }.execute();
    }

    private void onSaveOrder() throws SQLException, Exception {
        String maDon = view.getTxtMaDonHang().getText().trim();
        if (maDon.isEmpty()) throw new IllegalArgumentException("Mã đơn hàng không được để trống");

        String maKh = (String) view.getCbKhachHang().getSelectedItem();
        String maNv = (String) view.getCbNhanVien().getSelectedItem();
        String noiNhan = view.getTxtNoiNhanHang().getText().trim();
        String trangThai = (String) view.getCbTrangThai().getSelectedItem();

        DefaultTableModel model = view.getModel();
        if (model.getRowCount() == 0) throw new IllegalArgumentException("Đơn hàng chưa có sản phẩm");

        // Build DonHang và danh sách ChiTietDonHang
        DonHang dh = new DonHang(maDon, maKh, maNv, new Date(), noiNhan, trangThai);
        List<ChiTietDonHang> items = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maSp = model.getValueAt(i, 0).toString();
            int qty;
            double price;
            try {
                qty = Integer.parseInt(model.getValueAt(i, 2).toString());
            } catch (Exception ex) {
                throw new IllegalArgumentException("Số lượng không hợp lệ ở dòng " + (i + 1));
            }
            try {
                price = Double.parseDouble(model.getValueAt(i, 3).toString());
            } catch (Exception ex) {
                throw new IllegalArgumentException("Đơn giá không hợp lệ ở dòng " + (i + 1));
            }
            if (qty <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0 ở dòng " + (i + 1));

            items.add(new ChiTietDonHang(maDon, maSp, qty, price));
        }

        // Gọi DAO để lưu (DAO phải xử lý transaction)
        donHangDAO.saveWithDetails(dh, items);

        // Nếu cần cập nhật tồn kho, DonHangDAO.saveWithDetails nên thực hiện cập nhật trong cùng transaction.
    }

    private void clearForm() {
        view.getTxtMaDonHang().setText("");
        view.getTxtNoiNhanHang().setText("");
        DefaultTableModel model = view.getModel();
        while (model.getRowCount() > 0) model.removeRow(0);
    }
}

