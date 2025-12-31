/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author HP
 */


import java.util.Date;

public class DonHang {
    private String maDonHang;
    private String maKhachHang;
    private String maNhanVien;
    private Date ngayLap;
    private String noiNhan;
    private String trangThai;

    public DonHang() { }

    public DonHang(String maDonHang, String maKhachHang, String maNhanVien, Date ngayLap, String noiNhan, String trangThai) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.noiNhan = noiNhan;
        this.trangThai = trangThai;
    }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public String getNoiNhan() { return noiNhan; }
    public void setNoiNhan(String noiNhan) { this.noiNhan = noiNhan; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return "DonHang{" +
                "maDonHang='" + maDonHang + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", ngayLap=" + ngayLap +
                ", noiNhan='" + noiNhan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}

