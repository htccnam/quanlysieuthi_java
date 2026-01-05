/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import MODEL.DonHang;
import MODEL.LoaiHangModel;
import MODEL.NhaCungCapModel;
import MODEL.SanPhamModel;
import VIEW.ChiTietView;
import MODEL.DonHang;
import VIEW.LoaiHangView;
import VIEW.NhaCungCapView;
import VIEW.SanPhamView;
import VIEW.TaoDonView;
import VIEW.chucvuView;
import VIEW.nhanvienViews;
import VIEW.khuyenmaiView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Admin
 */
public class manhinhchinhController {

    final manhinhchinh menu;
    final DonHang dh = new DonHang();

    public manhinhchinhController(manhinhchinh view) {
        this.menu = view;
        menu.addClickQuanLyNhanVien(new clickNhanSuListener());
        menu.addClickQuanLyChucVu(new clickChucVu());
        menu.addClickQuanLyKhuyenMai(new clickQuanLyKhuyenMai());
        menu.addClickPhanLoaiHang(new clickPhanLoaiHangListener());
        menu.addClickTaoDonMoi(new clickTaoDonListener());
        menu.addClickChiTiet(new clickChiTietListener());
        menu.addClickNhaCungCap(new clickNhaCungCapListener());
        menu.addClickDanhSachSanPham(new clickSanPhamListener());
        menu.setVisible(true);
    }

    private class clickNhanSuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nhanvienViews nhanvien = new nhanvienViews();
//            new nhanvienController(nhanvien);
            menu.showpanel(nhanvien);
            SwingUtilities.invokeLater(() -> {
            new nhanvienController(nhanvien);
        });
        }

    }

    private class clickChucVu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chucvuView chucvu = new chucvuView();
            chucvuController controller = new chucvuController(chucvu);
            menu.showpanel(chucvu);
        }

    }

    private class clickQuanLyKhuyenMai implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            khuyenmaiView km = new khuyenmaiView();
            khuyenmaiController kmController = new khuyenmaiController(km);
            menu.showpanel(km);
        }

    }

    private class clickPhanLoaiHangListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoaiHangView lhView = new LoaiHangView();
            new LoaiHangController(lhView);
            menu.showpanel(lhView);
        }
    }

    private class clickChiTietListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ChiTietView ctView = new ChiTietView();   
            new ChiTietController(ctView);
            menu.showpanel(ctView);
        }
    }

    private class clickTaoDonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TaoDonView tdView = new TaoDonView();  
            new TaoDonController(tdView);
            menu.showpanel(tdView);
        }
    }

    private class clickNhaCungCapListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Tạo View - Model - Controller cho Nhà Cung Cấp
                NhaCungCapView nccView = new NhaCungCapView();
                NhaCungCapModel nccModel = new NhaCungCapModel();
                new NhaCungCapController(nccView);

                // Hiển thị
                menu.showpanel(nccView);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(menu, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private class clickSanPhamListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SanPhamView spView = new SanPhamView();
                new SanPhamController(spView);
                
                menu.showpanel(spView);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(menu, "Lỗi: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            manhinhchinh manhinh = new manhinhchinh();
            new manhinhchinhController(manhinh);
            manhinh.setLocationRelativeTo(null);
            manhinh.setVisible(true);
        });

    }
}
