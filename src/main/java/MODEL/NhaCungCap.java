/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String loaiHinh;
    private String email;
    private String sdt;
    private String diaChi;

    public NhaCungCap() {}

    public NhaCungCap(String maNCC, String tenNCC, String loaiHinh, String email, String sdt, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.loaiHinh = loaiHinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    // Getters & Setters
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
    public String getLoaiHinh() { return loaiHinh; }
    public void setLoaiHinh(String loaiHinh) { this.loaiHinh = loaiHinh; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    @Override
    public String toString() { return maNCC + " - " + tenNCC; }
}