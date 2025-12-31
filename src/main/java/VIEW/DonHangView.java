/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.JComboBox;

import javax.swing.table.DefaultTableModel;

public class DonHangView extends JPanel {
    private JTextField txtMaDonHang, txtNoiNhanHang;
    private JComboBox<String> cbKhachHang, cbNhanVien, cbTrangThai;
    private DefaultTableModel model;
    private JTable table;
    private JButton btnAdd, btnRemove, btnSave;

    public DonHangView() {
        setLayout(new BorderLayout(8,8));
        // form top (GridBagLayout)
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0; form.add(new JLabel("Mã đơn hàng:"), gbc);
        gbc.gridx=1; txtMaDonHang = new JTextField(18); form.add(txtMaDonHang, gbc);

        gbc.gridx=0; gbc.gridy=1; form.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridx=1; cbKhachHang = new JComboBox<>(); form.add(cbKhachHang, gbc);

        gbc.gridx=0; gbc.gridy=2; form.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx=1; cbNhanVien = new JComboBox<>(); form.add(cbNhanVien, gbc);

        gbc.gridx=0; gbc.gridy=3; form.add(new JLabel("Nơi nhận hàng:"), gbc);
        gbc.gridx=1; txtNoiNhanHang = new JTextField(18); form.add(txtNoiNhanHang, gbc);

        gbc.gridx=0; gbc.gridy=4; form.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx=1; cbTrangThai = new JComboBox<>(new String[]{"Chờ xử lý","Hoàn thành"}); form.add(cbTrangThai, gbc);

        add(form, BorderLayout.NORTH);

        // table center
        String[] cols = {"Mã SP","Tên SP","Số lượng","Đơn giá","Thành tiền"};
        model = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int r,int c){ return c==2; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons
        JPanel btnPanel = new JPanel();
        btnAdd = new JButton("Thêm sản phẩm");
        btnRemove = new JButton("Xóa sản phẩm");
        btnSave = new JButton("Lưu đơn hàng");
        btnPanel.add(btnAdd); btnPanel.add(btnRemove); btnPanel.add(btnSave);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // getters
    public JTextField getTxtMaDonHang(){ return txtMaDonHang; }
    public JComboBox<String> getCbKhachHang(){ return cbKhachHang; }
    public JComboBox<String> getCbNhanVien(){ return cbNhanVien; }
    public JTextField getTxtNoiNhanHang(){ return txtNoiNhanHang; }
    public JComboBox<String> getCbTrangThai(){ return cbTrangThai; }
    public DefaultTableModel getModel(){ return model; }
    public JTable getTable(){ return table; }
    public JButton getBtnAdd(){ return btnAdd; }
    public JButton getBtnRemove(){ return btnRemove; }
    public JButton getBtnSave(){ return btnSave; }
}

