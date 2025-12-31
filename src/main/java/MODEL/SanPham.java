/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import java.sql.Date; // Dùng sql.Date để tương thích với Database

public class SanPham {
    private String maSP, tenSP, maLoai, maNCC;
    private String xuatXu;
    private int soLuong;
    private Date ngaySX, hanSD;
    private String tinhTrang;
    private double giaNhap, giaBan;
    private String donViTinh;

    public SanPham() {}

    public SanPham(String maSP, String tenSP, String maLoai, String maNCC, String xuatXu, int soLuong, Date ngaySX, Date hanSD, String tinhTrang, double giaNhap, double giaBan, String donViTinh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.maNCC = maNCC;
        this.xuatXu = xuatXu;
        this.soLuong = soLuong;
        this.ngaySX = ngaySX;
        this.hanSD = hanSD;
        this.tinhTrang = tinhTrang;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.donViTinh = donViTinh;
    }

    // Getter & Setter (Bạn có thể tự sinh bằng Alt+Insert trong Netbeans, nhưng tôi viết gọn ở đây)
    public String getMaSP() { return maSP; } public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; } public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public String getMaLoai() { return maLoai; } public void setMaLoai(String maLoai) { this.maLoai = maLoai; }
    public String getMaNCC() { return maNCC; } public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getXuatXu() { return xuatXu; } public void setXuatXu(String xuatXu) { this.xuatXu = xuatXu; }
    public int getSoLuong() { return soLuong; } public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public Date getNgaySX() { return ngaySX; } public void setNgaySX(Date ngaySX) { this.ngaySX = ngaySX; }
    public Date getHanSD() { return hanSD; } public void setHanSD(Date hanSD) { this.hanSD = hanSD; }
    public String getTinhTrang() { return tinhTrang; } public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
    public double getGiaNhap() { return giaNhap; } public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }
    public double getGiaBan() { return giaBan; } public void setGiaBan(double giaBan) { this.giaBan = giaBan; }
    public String getDonViTinh() { return donViTinh; } public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
}
