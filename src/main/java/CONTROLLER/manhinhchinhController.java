/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import VIEW.KhachHangView;
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
import VIEW.tintucView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class manhinhchinhController {
    final manhinhchinh menu;
    final DonHang dh = new DonHang();

    public manhinhchinhController(manhinhchinh view) {
        this.menu= view;
        menu.addClickQuanLyNhanVien(new clickNhanSuListener());
        menu.addClickQuanLyKhachHang(new clickKhachHangListener());
        menu.addClickQuanLyChucVu(new clickChucVu());
        menu.addClickQuanLyTinTuc(new clickQuanLyTinTuc());
        menu.addClickPhanLoaiHang(new clickPhanLoaiHangListener());
        menu.addClickTaoDonMoi(new clickTaoDonListener());
        menu.addClickChiTiet(new clickChiTietListener());
        menu.addClickNhaCungCap(new clickNhaCungCapListener());
        menu.addClickDanhSachSanPham(new clickSanPhamListener());
        menu.setVisible(true);
    }
    
    private class clickNhanSuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            nhanvienViews nhanvien=new nhanvienViews();
            new nhanvienController(nhanvien); 
            menu.showpanel(nhanvien);
        }
        
    }
    
    private class clickKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            KhachHangView khachhang = new KhachHangView();
            new KhachHangController(khachhang);
            menu.showpanel(khachhang);
        }
        
    }
      
    private class clickChucVu implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            chucvuView chucvu=new chucvuView();
            chucvuController controller=new chucvuController(chucvu);
            menu.showpanel(chucvu);
        }
        
    }
    private class clickQuanLyTinTuc implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            tintucView tintuc=new tintucView();
            tintucController ttController=new tintucController(tintuc);
            menu.showpanel(tintuc);
        }
        
    }
    private class clickPhanLoaiHangListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            LoaiHangView lhView = new LoaiHangView();
            new LoaiHangController(lhView);
            menu.showpanel(lhView);
        }
    }
    
    private class clickChiTietListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ChiTietView ctView = new ChiTietView();
            new ChiTietController(ctView, dh.getMaDonHang());
            menu.showpanel(ctView);
        }
    }
    
    private class clickTaoDonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            TaoDonView tdView = new TaoDonView();           
            menu.showpanel(tdView);
        }
    }
    
    private class clickNhaCungCapListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Tạo View - Model - Controller cho Nhà Cung Cấp
                NhaCungCapView nccView = new NhaCungCapView();
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
                SanPhamModel spModel = new SanPhamModel();
                new SanPhamController(spModel, spView);
                
                menu.showpanel(spView);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(menu, "Lỗi: " + ex.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        manhinhchinh manhinh=new manhinhchinh();
        manhinhchinhController controller=new manhinhchinhController(manhinh);
        manhinh.setVisible(true);
        
    }
}
