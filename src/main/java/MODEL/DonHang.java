package MODEL;

import java.sql.Date;

public class DonHang {
    private String maDonHang, maNhanSu, maKM, PTban, PTgiaodich;
    private Date ngayGD;
    private String maKH;
    private String maNV;
    private double tongTien;

    public DonHang() {}

    public DonHang(String maDonHang, String maNhanSu, String maKM, String PTban, String PTgiaodich, Date ngayGD, String maKH, String maNV, double tongTien) {
        this.maDonHang = maDonHang;
        this.maNhanSu = maNhanSu;
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

    public String getMaNhanSu() {
        return maNhanSu;
    }

    public void setMaNhanSu(String maNhanSu) {
        this.maNhanSu = maNhanSu;
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
