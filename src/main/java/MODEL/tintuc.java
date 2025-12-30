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
public class tintuc {

    private String matintucString;
    private String tieudeString;
    private String manhanvienString;
    private String noidungString;
    private String loaitinString;
    private LocalDate ngaydangString;

    public tintuc(String matintucString, String tieudeString, String manhanvienString, String noidungString, String loaitinString, LocalDate ngaydangString) {
        this.matintucString = matintucString;
        this.tieudeString = tieudeString;
        this.manhanvienString = manhanvienString;
        this.noidungString = noidungString;
        this.loaitinString = loaitinString;
        this.ngaydangString = ngaydangString;
    }

    public tintuc() {
    }

    public String getMatintucString() {
        return matintucString;
    }

    public void setMatintucString(String matintucString) {
        this.matintucString = matintucString;
    }

    public String getTieudeString() {
        return tieudeString;
    }

    public void setTieudeString(String tieudeString) {
        this.tieudeString = tieudeString;
    }

    public String getManhanvienString() {
        return manhanvienString;
    }

    public void setManhanvienString(String manhanvienString) {
        this.manhanvienString = manhanvienString;
    }

    public String getNoidungString() {
        return noidungString;
    }

    public void setNoidungString(String noidungString) {
        this.noidungString = noidungString;
    }

    public String getLoaitinString() {
        return loaitinString;
    }

    public void setLoaitinString(String loaitinString) {
        this.loaitinString = loaitinString;
    }

    public LocalDate getNgaydangString() {
        return ngaydangString;
    }

    public void setNgaydangString(LocalDate ngaydangString) {
        this.ngaydangString = ngaydangString;
    }

}
