/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import VIEW.KhachHangView;
import VIEW.nhanvienViews;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Admin
 */
public class manhinhchinhController {
    final manhinhchinh menu;

    public manhinhchinhController( ) {
        this.menu=new manhinhchinh();
        menu.addClickQuanLyNhanVien(new clickNhanSuListener());
        menu.addClickQuanLyKhachHang(new clickKhachHangListener());
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
    // --- LISTENER 2: XỬ LÝ KHI BẤM NÚT KHÁCH HÀNG ---
    private class clickKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            KhachHangView khachhang = new KhachHangView();
            new KhachHangController(khachhang);
            menu.showpanel(khachhang);
        }
    }
    public static void main(String[] args) {
        new manhinhchinhController();
    }
}
