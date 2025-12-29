/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.nhanvien_dao;
import MODEL.nhanvienmodel;
import VIEW.nhanvienViews;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Admin
 */
public class nhanvienController {
    private nhanvienmodel model;
    private final nhanvienViews views;
    private nhanvien_dao nhanviendao;
    private int selectedRow=-1;

    public nhanvienController(nhanvienmodel m , nhanvienViews v) {
        this.vi
        nhanviendao=new nhanvien_dao();
        v.addThemListener(new themNhanVienlistener());
    }
    
    private class themNhanVienlistener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             String textMaNhanVienString =views.manhanvien
        }
        
    }
}
