/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.SanPham;
import MODEL.SanPhamModel;
import VIEW.SanPhamView;
import java.math.BigDecimal;

public class SanPhamController {
    private SanPhamModel model;
    private SanPhamView view;

    public SanPhamController(SanPhamModel m, SanPhamView v) {
        this.model = m;
        this.view = v;
        loadTable();
        
        view.addAddListener(e -> {
            try {
                SanPham sp = new SanPham(
                    view.txtMaSP.getText(),
                    view.txtTenSP.getText(),
                    view.txtMaLoai.getText(),
                    view.txtMaTH.getText(),
                    Integer.parseInt(view.txtSoLuong.getText()),
                    new BigDecimal(view.txtGiaNhap.getText()),
                    new BigDecimal(view.txtGiaBan.getText()),
                    view.txtDVT.getText()
                );
                model.add(sp);
                loadTable(); // Reload lại bảng
            } catch (Exception ex) {
                ex.printStackTrace(); // Xử lý lỗi nhập liệu số
            }
        });
    }

    void loadTable() {
        view.tableModel.setRowCount(0);
        for (SanPham sp : model.getList()) {
            view.tableModel.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getMaLoai(), sp.getMaTH(),
                sp.getSoLuong(), sp.getGiaNhap(), sp.getGiaBan(), sp.getDonViTinh()
            });
        }
    }
}