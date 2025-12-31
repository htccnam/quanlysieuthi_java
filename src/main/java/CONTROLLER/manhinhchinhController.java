/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import MODEL.DonHang;
import MODEL.LoaiHangModel;
import VIEW.DonHangView;
import VIEW.LoaiHangView;
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
        menu.addClickPhanLoaiHang(new clickPhanLoaiHangListener());
        menu.addClickTaoDonMoi(new clickTaoDonListener());
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
    private class clickPhanLoaiHangListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            LoaiHangView lhView = new LoaiHangView();
            LoaiHangModel lhModel = new LoaiHangModel();
            new LoaiHangController(lhModel, lhView);
            menu.showpanel(lhView);
        }
    }
    
    private class clickTaoDonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DonHangView dhView = new DonHangView();
            DonHang dhModel = new DonHang();
            new BanHangController(dhView);
            menu.showpanel(dhView);
        }
    }
    
    public static void main(String[] args) {
        new manhinhchinhController();
    }
}
