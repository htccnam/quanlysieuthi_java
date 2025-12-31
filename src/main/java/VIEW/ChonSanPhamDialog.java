/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;


import MODEL.SanPham;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChonSanPhamDialog extends JDialog {
    private DefaultTableModel model;
    public ChonSanPhamDialog(Frame owner, List<SanPham> ds) {
        super(owner,true);
        setTitle("Chọn sản phẩm"); setSize(700,400); setLocationRelativeTo(owner);
        String[] cols = {"Chọn","Mã SP","Tên SP","Tồn kho","Giá"};
        model = new DefaultTableModel(cols,0) {
            @Override public Class<?> getColumnClass(int c){
                if(c==0) return Boolean.class;
                if(c==3) return Integer.class;
                if(c==4) return Double.class;
                return String.class;
            }
            @Override public boolean isCellEditable(int r,int c){ return c==0; }
        };
        for(SanPham p: ds) model.addRow(new Object[]{false,p.getMaSP(),p.getTenSP(),p.getSoLuong(),p.getGiaBan()});
        JTable tbl = new JTable(model);
        add(new JScrollPane(tbl), BorderLayout.CENTER);
        JPanel p = new JPanel(); JButton ok = new JButton("OK"); JButton cancel = new JButton("Hủy");
        p.add(ok); p.add(cancel); add(p, BorderLayout.SOUTH);
        ok.addActionListener(e-> setVisible(false));
        cancel.addActionListener(e-> { for(int i=0;i<model.getRowCount();i++) model.setValueAt(false,i,0); setVisible(false); });
    }
    public List<SanPham> getSelected(){
        List<SanPham> sel = new ArrayList<>();
        for(int i=0;i<model.getRowCount();i++){
            Boolean b = (Boolean) model.getValueAt(i,0);
            if(Boolean.TRUE.equals(b)){
                sel.add(new SanPham(model.getValueAt(i,1).toString(),
                                    model.getValueAt(i,2).toString(),
                                    (Integer)model.getValueAt(i,3),
                                    (Double)model.getValueAt(i,4)));
            }
        }
        return sel;
    }
}

