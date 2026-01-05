/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class khuyenmai {

    private String makhuyenmaiString;
    private String tenkhuyenmaiString;
    private String motaString;
    private LocalDate ngaytaoDate;

    public khuyenmai() {
    }

    public khuyenmai(String makhuyenmaiString, String tenkhuyenmaiString, String motaString, LocalDate ngaytaoDate) {
        this.makhuyenmaiString = makhuyenmaiString;
        this.tenkhuyenmaiString = tenkhuyenmaiString;
        this.motaString = motaString;
        this.ngaytaoDate = ngaytaoDate;
    }

    public String getMakhuyenmaiString() {
        return makhuyenmaiString;
    }

    public void setMakhuyenmaiString(String makhuyenmaiString) {
        this.makhuyenmaiString = makhuyenmaiString;
    }

    public String getTenkhuyenmaiString() {
        return tenkhuyenmaiString;
    }

    public void setTenkhuyenmaiString(String tenkhuyenmaiString) {
        this.tenkhuyenmaiString = tenkhuyenmaiString;
    }

    public String getMotaString() {
        return motaString;
    }

    public void setMotaString(String motaString) {
        this.motaString = motaString;
    }

    public LocalDate getNgaytaoDate() {
        return ngaytaoDate;
    }

    public void setNgaytaoDate(LocalDate ngaytaoDate) {
        this.ngaytaoDate = ngaytaoDate;
    }
    
}
