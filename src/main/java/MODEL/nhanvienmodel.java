/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class nhanvienmodel {
    private String maNhanVienString;
    private String tenNhanVienString;
    private LocalDate ngaySinhDate;
    private String gioiTinhString,diaChiString,soDienThoaiString;

    public nhanvienmodel() {
    }

    public nhanvienmodel(String maNhanVienString, String tenNhanVienString, LocalDate ngaySinhDate, String gioiTinhString, String diaChiString, String soDienThoaiString) {
        this.maNhanVienString = maNhanVienString;
        this.tenNhanVienString = tenNhanVienString;
        this.ngaySinhDate = ngaySinhDate;
        this.gioiTinhString = gioiTinhString;
        this.diaChiString = diaChiString;
        this.soDienThoaiString = soDienThoaiString;
    }

    public String getMaNhanVienString() {
        return maNhanVienString;
    }

    public void setMaNhanVienString(String maNhanVienString) {
        this.maNhanVienString = maNhanVienString;
    }

    public String getTenNhanVienString() {
        return tenNhanVienString;
    }

    public void setTenNhanVienString(String tenNhanVienString) {
        this.tenNhanVienString = tenNhanVienString;
    }

    public LocalDate getNgaySinhDate() {
        return ngaySinhDate;
    }

    public void setNgaySinhDate(LocalDate ngaySinhDate) {
        this.ngaySinhDate = ngaySinhDate;
    }

    public String getGioiTinhString() {
        return gioiTinhString;
    }

    public void setGioiTinhString(String gioiTinhString) {
        this.gioiTinhString = gioiTinhString;
    }

    public String getDiaChiString() {
        return diaChiString;
    }

    public void setDiaChiString(String diaChiString) {
        this.diaChiString = diaChiString;
    }

    public String getSoDienThoaiString() {
        return soDienThoaiString;
    }

    public void setSoDienThoaiString(String soDienThoaiString) {
        this.soDienThoaiString = soDienThoaiString;
    }
    
}
