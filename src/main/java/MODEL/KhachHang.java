/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;



public class KhachHang {
    // Các trường dữ liệu tương ứng với cột bảng khachhang
    private String makhachhang;
    private String tenkhachhang;
    private String sodienthoai;
    private String diachi;
    private int diemtichluy;
    private String taikhoan;
    private String matkhau;

    // Constructor mặc định (no-arg)
    public KhachHang() {
    }

    // Constructor đầy đủ tham số
    public KhachHang(String makhachhang, String tenkhachhang, String sodienthoai,
                     String diachi, int diemtichluy, String taikhoan, String matkhau) {
        this.makhachhang = makhachhang;
        this.tenkhachhang = tenkhachhang;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.diemtichluy = diemtichluy;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    // Getter và Setter cho mỗi thuộc tính
    public String getMakhachhang() { return makhachhang; }
    public void setMakhachhang(String makhachhang) { this.makhachhang = makhachhang; }

    public String getTenkhachhang() { return tenkhachhang; }
    public void setTenkhachhang(String tenkhachhang) { this.tenkhachhang = tenkhachhang; }

    public String getSodienthoai() { return sodienthoai; }
    public void setSodienthoai(String sodienthoai) { this.sodienthoai = sodienthoai; }

    public String getDiachi() { return diachi; }
    public void setDiachi(String diachi) { this.diachi = diachi; }

    public int getDiemtichluy() { return diemtichluy; }
    public void setDiemtichluy(int diemtichluy) { this.diemtichluy = diemtichluy; }

    public String getTaikhoan() { return taikhoan; }
    public void setTaikhoan(String taikhoan) { this.taikhoan = taikhoan; }

    public String getMatkhau() { return matkhau; }
    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }

    // toString(): hiển thị [makhachhang] - [tenkhachhang]
    @Override
    public String toString() {
        return "[" + makhachhang + "] - [" + tenkhachhang + "]";
    }
}

