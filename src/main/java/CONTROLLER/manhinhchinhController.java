/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
import MODEL.LoaiHangModel;
import MODEL.tintuc;
import VIEW.LoaiHangView;
import VIEW.nhanvienViews;
import VIEW.tintucView;
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
        menu.addClickQuanLyTinTuc(new clickQuanLyTinTuc());
        menu.addClickPhanLoaiHang(new clickPhanLoaiHangListener());
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
    public static void main(String[] args) {
        new manhinhchinhController();
    }
}
