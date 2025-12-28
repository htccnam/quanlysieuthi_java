/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import java.sql.Date; // Dùng sql.Date để khớp với DB

public class NhanVien {
    private String maNV, tenNV;
    private Date ngaySinh; // Hoặc String nếu bạn muốn đơn giản hóa việc nhập liệu
    private String gioitinh, diachi, sdt, taiKhoan, matKhau;

    public NhanVien() {}

    public NhanVien(String maNV, String tenNV, Date ngaySinh, String gioitinh, String diachi, String sdt, String taiKhoan, String matKhau) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.sdt = sdt;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    // Getter & Setter
    public String getMaNV() { return maNV; }
    public String getTenNV() { return tenNV; }
    public String getTaiKhoan() { return taiKhoan; }
    public String getMatKhau() { return matKhau; }
    // ... (Tự tạo thêm getter/setter cho các trường còn lại)
}