/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

import MODEL.NhaCungCap;
import MODEL.NhaCungCapModel;
import VIEW.NhaCungCapView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author VŨ HÙNG HẢI
 */

public class NhaCungCapController {
    
    private NhaCungCapView view;
    private NhaCungCapModel model;
    
    public NhaCungCapController(NhaCungCapView view){
        this.view = view;
        this.model = new NhaCungCapModel();
        loadTable(model.getList());
        // thêm
        view.addAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaNCC.getText();
                String ten = view.txtTenNCC.getText();
                String loai = view.txtLoaiHinh.getText();
                String email = view.txtEmail.getText();
                String sdt = view.txtSDT.getText();
                String dc = view.txtDiaChi.getText();
                
                if(ma.isEmpty() || ten.isEmpty()){
                    view.showMessage("Mã và tên không được để trống");
                    return;
                }
                if(model.checkExist(ma)){
                    view.showMessage("Mã nhà cung cấp này đã tồn tại");
                    return;
                }
                NhaCungCap ncc = new NhaCungCap(ma,ten,loai,email,sdt,dc);
                model.add(ncc);
                updateView();
                view.showMessage("Thêm thành công");
            }
        });
        // sửa
        view.addEditListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaNCC.getText();
                String ten = view.txtTenNCC.getText();
                String loai = view.txtLoaiHinh.getText();
                String email = view.txtEmail.getText();
                String sdt = view.txtSDT.getText();
                String dc = view.txtDiaChi.getText();
                
                if(ma.isEmpty()){
                    view.showMessage("Vui long chon nha cung cap de sua");
                    return;
                }
                NhaCungCap ncc = new NhaCungCap(ma,ten,loai,email,sdt,dc);
                model.update(ncc);
                updateView();
                view.showMessage("Sửa thành công");
            }
        });
        // xóa
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaNCC.getText();
                
                if(ma.isEmpty()){
                    view.showMessage("Vui lòng chọn dòng cần xóa");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa ?");
                if(confirm == JOptionPane.YES_OPTION){
                    model.delete(ma);
                    updateView();
                    view.showMessage("Đã xóa");
                }
                        
            }
        });
        
       // làm mới 
       view.addResetListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                loadTable(model.getList());
            }
        });
       
       // click bảng
       view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if(row >=0){
                    view.txtMaNCC.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTenNCC.setText(view.table.getValueAt(row, 1).toString());
                    view.txtLoaiHinh.setText(view.table.getValueAt(row, 2).toString());
                    view.txtEmail.setText(view.table.getValueAt(row,3).toString());
                    view.txtSDT.setText(view.table.getValueAt(row, 4).toString());
                    view.txtDiaChi.setText(view.table.getValueAt(row, 5).toString());
                    
                    view.txtMaNCC.setEditable(false);
                }
            }      
       });
       //tim kiem
       view.addSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = view.txtTimKiem.getText();
               if(key.isEmpty()){
                   view.showMessage("Nhap gi do di");
                   return;
                   }
                loadTable(model.timkiem(key));
            }
       });
    }
    private void resetForm() {
        view.txtMaNCC.setText("");
        view.txtTenNCC.setText("");
        view.txtLoaiHinh.setText("");
        view.txtEmail.setText("");
        view.txtSDT.setText("");
        view.txtDiaChi.setText("");
        view.txtTimKiem.setText("");
        view.txtMaNCC.setEditable(true); // Mở khóa lại ô Mã
            }
    // ddor du lieu vao bang
    private void loadTable(ArrayList<NhaCungCap> list) {
        view.tableModel.setRowCount(0);
        for(NhaCungCap ncc : list){
            view.tableModel.addRow(new Object[]{
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getLoaiHinh(),
                ncc.getEmail(),
                ncc.getSdt(),
                ncc.getDiaChi()
            });
        }
    }
     private void updateView() {
         loadTable(model.getList());
        resetForm();
                
            }
}