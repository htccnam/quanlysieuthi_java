package MODEL;

import java.sql.Date;

public class DonHang {
    private int maDonHang;
    private Date ngayLap;
    private String maKH;
    private String maNV;
    private double tongTien;

    public DonHang() {}

    public DonHang(int maDonHang, Date ngayLap, String maKH, String maNV, double tongTien) {
        this.maDonHang = maDonHang;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    // Getter / Setter
    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}
