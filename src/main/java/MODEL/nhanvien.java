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
public class nhanvien {
   private String manhanvienString;
   private String tennhanvienString;
   private LocalDate ngaysinhDate;
   private String gioitinhString;
   private String sodienthoaiString;
   private String emailString;
   private String diachiString;
   private String machucvuString;

    public nhanvien() {
    }

    public nhanvien(String manhanvienString, String tennhanvienString, LocalDate ngaysinhDate, String gioitinhString, String sodienthoaiString, String emailString, String diachiString, String machucvuString) {
        this.manhanvienString = manhanvienString;
        this.tennhanvienString = tennhanvienString;
        this.ngaysinhDate = ngaysinhDate;
        this.gioitinhString = gioitinhString;
        this.sodienthoaiString = sodienthoaiString;
        this.emailString = emailString;
        this.diachiString = diachiString;
        this.machucvuString = machucvuString;
    }

    public String getManhanvienString() {
        return manhanvienString;
    }

    public void setManhanvienString(String manhanvienString) {
        this.manhanvienString = manhanvienString;
    }

    public String getTennhanvienString() {
        return tennhanvienString;
    }

    public void setTennhanvienString(String tennhanvienString) {
        this.tennhanvienString = tennhanvienString;
    }

    public LocalDate getNgaysinhDate() {
        return ngaysinhDate;
    }

    public void setNgaysinhDate(LocalDate ngaysinhDate) {
        this.ngaysinhDate = ngaysinhDate;
    }

    public String getGioitinhString() {
        return gioitinhString;
    }

    public void setGioitinhString(String gioitinhString) {
        this.gioitinhString = gioitinhString;
    }

    public String getSodienthoaiString() {
        return sodienthoaiString;
    }

    public void setSodienthoaiString(String sodienthoaiString) {
        this.sodienthoaiString = sodienthoaiString;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getDiachiString() {
        return diachiString;
    }

    public void setDiachiString(String diachiString) {
        this.diachiString = diachiString;
    }

    public String getMachucvuString() {
        return machucvuString;
    }

    public void setMachucvuString(String machucvuString) {
        this.machucvuString = machucvuString;
    }

    
   
}
