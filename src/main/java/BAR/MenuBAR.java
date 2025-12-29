/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BAR;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import javax.swing.*;
import CONTROLLER.*;
import MODEL.*;
import VIEW.*;

public class MenuBAR extends JMenuBar {
    private JFrame parent;

    public MenuBAR(JFrame parentFrame) {
        this.parent = parentFrame;
        JMenu menu = new JMenu("Quản lý Siêu Thị");

        JMenuItem itemSP = new JMenuItem("Quản Lý Sản Phẩm");
        JMenuItem itemKH = new JMenuItem("Quản Lý Khách Hàng");
        // Thêm các item khác...

        menu.add(itemSP);
        menu.add(itemKH);
        this.add(menu);

        itemSP.addActionListener(e -> {
            // Mở màn hình quản lý sản phẩm
            SanPhamView v = new SanPhamView();
            SanPhamModel m = new SanPhamModel();
            new SanPhamController(m, v);
            // parent.dispose(); // Tùy chọn: Có tắt màn hình chính đi hay không
        });//
    }
}