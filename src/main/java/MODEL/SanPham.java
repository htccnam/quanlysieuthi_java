/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */
import java.math.BigDecimal;

public class SanPham {
    private String maSP, tenSP, maLoai, maTH, donViTinh;
    private int soLuong;
    private BigDecimal giaNhap, giaBan; // Trong DB là DECIMAL, Java nên dùng BigDecimal hoặc Double

    public SanPham() {}

    public SanPham(String maSP, String tenSP, String maLoai, String maTH, int soLuong, BigDecimal giaNhap, BigDecimal giaBan, String donViTinh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maLoai = maLoai;
        this.maTH = maTH;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.donViTinh = donViTinh;
    }

    // Getters
    public String getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public String getMaLoai() { return maLoai; }
    public String getMaTH() { return maTH; }
    public int getSoLuong() { return soLuong; }
    public BigDecimal getGiaNhap() { return giaNhap; }
    public BigDecimal getGiaBan() { return giaBan; }
    public String getDonViTinh() { return donViTinh; }
}
