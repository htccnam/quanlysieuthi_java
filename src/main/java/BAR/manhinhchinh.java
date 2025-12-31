/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BAR;

import java.awt.BorderLayout;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class manhinhchinh extends JFrame {

    JMenu hangHoaVaKhoJMenu;
    JMenuItem danhSachSanPham, phanLoaiHang, nhaCungCap;
    JMenu banHangJMenu;
    JMenuItem taoDonMoi, ChiTietDonHang;
    JMenu khachHangJMenu;
    JMenuItem quanLyKhachHang;
    JMenu tinTucJMenu;
    JMenuItem quanLyTinTuc;
    JMenu nhanSuJMenu;
    JMenuItem quanLyNhanVien;

    JPanel containerJPanel;

    public manhinhchinh() {
        setTitle("HỆ THỐNG QUẢN LÝ SIÊU THỊ");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 1000);

        hangHoaVaKhoJMenu = new JMenu("Hàng Hóa và kho");
        danhSachSanPham = new JMenuItem("danh sách sản phẩm");
        phanLoaiHang = new JMenuItem("phân loại hàng");
        nhaCungCap = new JMenuItem("Nhà cung cấp");
        hangHoaVaKhoJMenu.add(danhSachSanPham);
        hangHoaVaKhoJMenu.add(phanLoaiHang);
        hangHoaVaKhoJMenu.add(nhaCungCap);

        banHangJMenu = new JMenu("Bán hàng");
        taoDonMoi = new JMenuItem("tạo đơn mới");
        ChiTietDonHang = new JMenuItem("chi tiết đơn hàng");
        banHangJMenu.add(taoDonMoi);
        banHangJMenu.add(ChiTietDonHang);

        khachHangJMenu = new JMenu("Khách hàng");
        quanLyKhachHang= new JMenuItem("quản lý khách hàng");
        khachHangJMenu.add(quanLyKhachHang);
        khachHangJMenu.setVisible(false);
        
        tinTucJMenu = new JMenu("Tin tức");
        quanLyTinTuc= new JMenuItem("Quản lý tin tức");
        tinTucJMenu.add(quanLyTinTuc);
        
        nhanSuJMenu = new JMenu("Nhân sự");
        quanLyNhanVien=new JMenuItem("Quản lý nhân viên");
        nhanSuJMenu.add(quanLyNhanVien);

        JMenuBar bar = new JMenuBar();
        //
        bar.add(hangHoaVaKhoJMenu);
        bar.add(banHangJMenu);
        bar.add(khachHangJMenu);
        bar.add(tinTucJMenu);
        bar.add(nhanSuJMenu);
        setJMenuBar(bar);

        containerJPanel = new JPanel(new BorderLayout());
        add(containerJPanel, BorderLayout.CENTER);

    }

    //hàm hiển thị panel đổ xuông màn hình chính;
    public void showpanel(JPanel jP) {
        containerJPanel.removeAll();
        containerJPanel.add(jP);
        containerJPanel.revalidate();
        containerJPanel.repaint();
    }

    public void addClickDanhSachSanPham(ActionListener listener) {
        danhSachSanPham.addActionListener(listener);
    }

    public void addClickPhanLoaiHang(ActionListener listener) {
        phanLoaiHang.addActionListener(listener);
    }

    public void addClickNhaCungCap(ActionListener listener) { 
            nhaCungCap.addActionListener(listener); 
        }

    public void addClickTaoDonMoi(ActionListener listener) {
        taoDonMoi.addActionListener(listener);
    }
    
    public void addClickChiTiet(ActionListener listener) {
        ChiTietDonHang.addActionListener(listener);
    }

    public void addClickQuanLyKhachHang(ActionListener listener) {
        quanLyKhachHang.addActionListener(listener);
    }

    public void addClickQuanLyTinTuc(ActionListener listener) {
        quanLyTinTuc.addActionListener(listener);
    }

    public void addClickQuanLyNhanVien(ActionListener listener) {
        quanLyNhanVien.addActionListener(listener);
    }

}
