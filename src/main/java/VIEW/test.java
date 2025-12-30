/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import BAR.manhinhchinh;
import CONTROLLER.nhanvienController;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class test {
    public static void main(String[] args) {
        
//        JFrame jFrame= new JFrame();
//        nhanvienViews nhanvienViews1=new nhanvienViews();
//        new nhanvienController( nhanvienViews1);
//        jFrame.add(nhanvienViews1);
//        jFrame.setSize(1000, 1000);
//        
//        jFrame.setVisible(true);

            manhinhchinh m= new manhinhchinh();
            nhanvienViews v=new nhanvienViews();
            nhanvienController c=new nhanvienController(v);
            m.showpanel(v);
            m.setVisible(true);
    }
}
