package MODEL;

public class ChiTietDonHang {
    private int maDonHang;
    private String maSP;
    private int soLuong;
    private double donGia;

    public ChiTietDonHang() {}

    public ChiTietDonHang(int maDonHang, String maSP, int soLuong, double donGia) {
        this.maDonHang = maDonHang;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getMaDonHang() { return maDonHang; }
    public void setMaDonHang(int maDonHang) { this.maDonHang = maDonHang; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() {
        return soLuong * donGia;
    }
}
