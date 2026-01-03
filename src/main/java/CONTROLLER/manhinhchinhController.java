/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import VIEW.HangThanhVienView;
import VIEW.KhachHangView;
import MODEL.DonHang;
import MODEL.LoaiHangModel;
import VIEW.BanHangView;
import MODEL.NhaCungCapModel;
import MODEL.SanPhamModel;
import VIEW.BanHangView;
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

    public manhinhchinhController(manhinhchinh view) {
        this.menu= view;
        menu.addClickQuanLyNhanVien(new clickNhanSuListener());
        menu.addClickQuanLyKhachHang(new clickKhachHangListener());
        menu.addClickHangThanhVien(new clickHangThanhVienListener());
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
    private class clickHangThanhVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            HangThanhVienView htvView = new HangThanhVienView();
            //new HangThanhVienController(htvView); 
            menu.showpanel(htvView);
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
            LoaiHangModel lhModel = new LoaiHangModel();
            new LoaiHangController(lhModel, lhView);
            menu.showpanel(lhView);
        }
    }
    
    private class clickChiTietListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            BanHangView bhView = null;
            try {
                bhView = new BanHangView();
            } catch (Exception ex) {
                Logger.getLogger(manhinhchinhController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DonHang dhModel = new DonHang();
            try {
                new BanHangController(bhView);
            } catch (Exception ex) {
                Logger.getLogger(manhinhchinhController.class.getName()).log(Level.SEVERE, null, ex);
            }
            menu.showpanel(bhView);
        }
    }
    
    private class clickTaoDonListener implements ActionListener{
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
                new NhaCungCapController(nccModel, nccView);
                
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
