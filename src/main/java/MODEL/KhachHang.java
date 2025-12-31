package MODEL;

import java.time.LocalDate;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private String sdt;
    private String gioiTinh; // Vẫn giữ giới tính vì trong constructor bạn có
    private String email;
    private LocalDate ngaySinh; 
    private int diemtichluy; // Mới
    private String taikhoan; // Mới
    private String matkhau;  // Mới
    
    public KhachHang() {
    }

    public KhachHang(String maKH, String hoTen, String sdt, String gioiTinh, String email, LocalDate ngaySinh, int diemtichluy, String taikhoan, String matkhau) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.diemtichluy = diemtichluy;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    // Getters và Setters
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public int getDiemtichluy() { return diemtichluy; }
    public void setDiemtichluy(int diemtichluy) { this.diemtichluy = diemtichluy; }

    public String getTaikhoan() { return taikhoan; }
    public void setTaikhoan(String taikhoan) { this.taikhoan = taikhoan; }

    public String getMatkhau() { return matkhau; }
    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }

    @Override
    public String toString() {
        // Đã sửa makhachhang -> maKH để không bị lỗi
        return "[" + maKH + "] - [" + hoTen + "]";
    }
}