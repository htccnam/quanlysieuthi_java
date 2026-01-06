/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author PC
 */
public class HangThanhVien {
    private String maKH;
    private String tenKH;
    private String tenHang;

    public HangThanhVien() {
    }

    public HangThanhVien(String maKH, String tenKH, String tenHang) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.tenHang = tenHang;
    }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }
}

