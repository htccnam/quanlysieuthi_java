/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import BAR.manhinhchinh;
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
    public static void main(String[] args) {
        new manhinhchinhController();
    }
}
