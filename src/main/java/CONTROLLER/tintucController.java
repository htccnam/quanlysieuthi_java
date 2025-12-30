/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import DAO.nhanvienDAO;
import MODEL.nhanvien;
import MODEL.tintuc;
import VIEW.tintucView;
import java.util.List;

/**
 *
 * @author Admin
 */
public class tintucController {
    tintuc t;
    tintucView view;
    nhanvienDAO nvDAO=new nhanvienDAO();
    

    public tintucController(tintucView view) {
        this.view=view;
        this.nvDAO=new nhanvienDAO();
        
        load_nhanvien();
    }
    private void load_nhanvien(){
        nhanvien nv=new nhanvien();
        List<nhanvien> list=nvDAO.getAllNhanVien();
        
        for(nhanvien nhanvien: list){
            view.manhanvienBox.addItem(nhanvien.getMaNhanVienString().toString().trim());
        }
    }
}
