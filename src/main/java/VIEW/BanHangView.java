/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import DAO.DBConnection;
import DAO.DonHangDAO;
import MODEL.DonHang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.JTextField;

import javax.swing.JComboBox;

import javax.swing.table.DefaultTableModel;

public class BanHangView extends JPanel {
    private JTable tblDonHang;
    private JButton btnLamMoi, btnXemChiTiet;

    public BanHangView() throws Exception {
        initComponents();
        loadData();
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("Danh sách Đơn hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        btnLamMoi = new JButton("Làm mới");
        btnXemChiTiet = new JButton("Xem chi tiết");

        tblDonHang = new JTable();
        tblDonHang.setModel(new DefaultTableModel(new Object[]{"Mã đơn","Ngày lập","Mã KH","Nhân viên","Tổng tiền"}, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tblDonHang);

        // Bố cục GroupLayout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Ngang: Tiêu đề + 2 nút ở trên, bảng dưới full
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblTitle)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXemChiTiet))
                .addComponent(scrollPane)
        );
        // Dọc: hàng trên cùng là nhóm nút, tiếp là bảng
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXemChiTiet))
                .addComponent(scrollPane)
        );
    }

    // Tải dữ liệu đơn hàng vào bảng (ví dụ gọi từ CSDL hoặc service)
    private void loadData() throws Exception {
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        model.setRowCount(0);
        // Giả sử có phương thức lấy danh sách đơn hàng từ CSDL
        DonHangDAO dao = new DonHangDAO(DBConnection.getConnection());
        ArrayList<DonHang> ds = dao.getAllDonHang();

        for (DonHang dh : ds) {
            model.addRow(new Object[]{
                dh.getMaDonHang(),
                dh.getNgayLap(),
                dh.getMaKH(),
                dh.getMaNV(),
                dh.getTongTien()
            });
        }
    }

    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnXemChiTiet() { return btnXemChiTiet; }
    public JTable getTblDonHang() { return tblDonHang; }
}

