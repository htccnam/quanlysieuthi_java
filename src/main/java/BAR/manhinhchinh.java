/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BAR;

import VIEW.KhachHangView;
import VIEW.nhanvienViews;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
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
    JMenu khuyenmaiJMenu;
    JMenuItem quanlykhuyenmai;
    JMenu nhanSuJMenu;
    JMenuItem quanLyNhanVien;
    JMenuItem chucvuItem;

    JPanel containerJPanel;

    public manhinhchinh() {
        setTitle("HỆ THỐNG QUẢN LÝ SIÊU THỊ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1800, 1000);

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
        khachHangJMenu.setVisible(true);
        
        khuyenmaiJMenu = new JMenu("Khuyến mại");
        quanlykhuyenmai= new JMenuItem("Quản lý khuyến mại");
        khuyenmaiJMenu.add(quanlykhuyenmai);
        
        nhanSuJMenu = new JMenu("Nhân sự");
        quanLyNhanVien=new JMenuItem("Quản lý nhân viên");
        chucvuItem=new JMenuItem("Chức vụ");
        nhanSuJMenu.add(quanLyNhanVien);
        nhanSuJMenu.add(chucvuItem);

        JMenuBar bar = new JMenuBar();
        //
        bar.add(hangHoaVaKhoJMenu);
        bar.add(banHangJMenu);
        bar.add(khachHangJMenu);
        bar.add(khuyenmaiJMenu);
        bar.add(nhanSuJMenu);
        setJMenuBar(bar);

        containerJPanel = new JPanel(new java.awt.BorderLayout());

        containerJPanel.setPreferredSize(new Dimension(1200, 900)); // 👈 QUAN TRỌNG
        add(containerJPanel, BorderLayout.CENTER);

    }

    //hàm hiển thị panel đổ xuông màn hình chính;
   // Để hàm này chấp nhận được cả KhachHangView, SanPhamView, NhanVienView...
    public void showpanel(javax.swing.JPanel jp) { 
        
        containerJPanel.removeAll(); // Xóa nội dung cũ đang hiển thị
        containerJPanel.add(jp, java.awt.BorderLayout.CENTER); 
        containerJPanel.revalidate(); // Tính toán lại bố cục
        containerJPanel.repaint();    // Vẽ lại màn hình
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

    public void addClickQuanLyKhuyenMai(ActionListener listener) {
        quanlykhuyenmai.addActionListener(listener);
    }

    public void addClickQuanLyNhanVien(ActionListener listener) {
        quanLyNhanVien.addActionListener(listener);
    }
    public void addClickQuanLyChucVu(ActionListener listener){
        chucvuItem.addActionListener(listener);
    }

}
