package MODEL;

import java.sql.Date;

public class DonHang {
    private String maDonHang;
    private Date ngayLap;
    private String maKH;
    private String maNV;
    private double tongTien;

    public DonHang() {}

    public DonHang(String maDonHang, Date ngayLap, String maKH, String maNV, double tongTien) {
        this.maDonHang = maDonHang;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    // Getter / Setter
    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}
