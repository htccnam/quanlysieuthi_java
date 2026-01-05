package MODEL;

import java.sql.Date;

public class DonHang {
    private String maDonHang, maKM, PTban, PTgiaodich, maKH, maNV;
    private Date ngayGD;
    private double tongTien;

    public DonHang() {}

    public DonHang(String maDonHang, String maKH, String maNV, String maKM, Date ngayGD, String PTban, String PTgiaodich, double tongTien) {
        this.maDonHang = maDonHang;    
        this.maKM = maKM;
        this.PTban = PTban;
        this.PTgiaodich = PTgiaodich;
        this.ngayGD = ngayGD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getPTban() {
        return PTban;
    }

    public void setPTban(String PTban) {
        this.PTban = PTban;
    }

    public String getPTgiaodich() {
        return PTgiaodich;
    }

    public void setPTgiaodich(String PTgiaodich) {
        this.PTgiaodich = PTgiaodich;
    }

    public Date getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(Date ngayGD) {
        this.ngayGD = ngayGD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    
}
