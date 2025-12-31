/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author HP
 */


public class ChiTietDonHang {
    private String maDonHang;
    private String maSanPham;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietDonHang() { }

    public ChiTietDonHang(String maDonHang, String maSanPham, int soLuong, double donGia) {
        this.maDonHang = maDonHang;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        recalcThanhTien();
    }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) {
        this.donGia = donGia;
        recalcThanhTien();
    }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }

    private void recalcThanhTien() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    @Override
    public String toString() {
        return "ChiTietDonHang{" +
                "maDonHang='" + maDonHang + '\'' +
                ", maSanPham='" + maSanPham + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}

