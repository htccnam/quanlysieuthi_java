/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import CONTROLLER.tintucController;
import com.mysql.cj.xdevapi.Table;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class tintucView extends JPanel {

    public JTextField matintucField = new JTextField();
    public JTextField tieudeField = new JTextField();
    public JComboBox<String> manhanvienBox = new JComboBox<>();
    public JTextField noidungField = new JTextField();
    public JComboBox<String> loaitinBox = new JComboBox<>();
    public JDateChooser dateChooser = new JDateChooser();

    public JButton themButton = new JButton();
    public JButton suaButton = new JButton();
    public JButton xoaButton = new JButton();
    public JButton resetButton = new JButton();

    public JTextField timkiemField = new JTextField();
    public JButton timkiemButton = new JButton();

    public DefaultTableModel tintucDefaultTableModel;
    public JTable tintucJTable;

    public tintucView() {
        setLayout(null);

        //matintuc
        JLabel matintucJLabel = new JLabel("Mã tin tức:");
        matintucJLabel.setBounds(50, 50, 100, 35);
        add(matintucJLabel);

        matintucField.setBounds(200, 50, 200, 35);
        add(matintucField);

        //tiêu đề
        JLabel tieudeJLabel = new JLabel("Tiêu đề:");
        tieudeJLabel.setBounds(50, 100, 100, 35);
        add(tieudeJLabel);

        tieudeField.setBounds(200, 100, 200, 35);
        add(tieudeField);

        //manhanvien
        JLabel manhanvienJLabel = new JLabel("Mã nhân viên:");
        manhanvienJLabel.setBounds(50, 150, 100, 35);
        add(manhanvienJLabel);

        manhanvienBox.setBounds(200, 150, 200, 35);
        add(manhanvienBox);

        //noidung
        JLabel noidungJLabel = new JLabel("Nội dung:");
        noidungJLabel.setBounds(50, 200, 100, 35);
        add(noidungJLabel);

        noidungField.setBounds(200, 200, 200, 35);
        add(noidungField);

        //loaitin
        JLabel loaitinJLabel = new JLabel("Loại tin:");
        loaitinJLabel.setBounds(50, 250, 100, 35);
        add(loaitinJLabel);

        String[] strings = new String[]{"khuyến mại", "thông báo", "tuyển dụng", "khác"};
        loaitinBox = new JComboBox<>(strings);
        loaitinBox.setBounds(200, 250, 200, 35);
        add(loaitinBox);

        //ngày tạo
        JLabel ngaytaoJLabel = new JLabel("Ngày tạo:");
        ngaytaoJLabel.setBounds(50, 300, 100, 35);
        add(ngaytaoJLabel);

        dateChooser.setBounds(200, 300, 200, 35);
        add(dateChooser);

        //button
        themButton.setText("Thêm");
        themButton.setBounds(50, 350, 100, 30);
        themButton.setBackground(Color.GREEN);
        add(themButton);

        suaButton.setText("Sửa");
        suaButton.setBounds(170, 350, 100, 30);
        suaButton.setBackground(Color.BLUE);
        suaButton.setForeground(Color.white);
        add(suaButton);

        xoaButton.setText("Xóa");
        xoaButton.setBounds(290, 350, 100, 30);
        xoaButton.setBackground(Color.red);
        add(xoaButton);

        resetButton.setText("reset");
        resetButton.setBounds(410, 350, 100, 30);
        resetButton.setBackground(Color.yellow);
        add(resetButton);

        timkiemField.setBounds(50, 400, 100, 35);
        add(timkiemField);
        timkiemButton.setText("Tìm kiếm");
        timkiemButton.setBounds(200, 400, 100, 30);
        timkiemButton.setBackground(Color.gray);
        add(timkiemButton);

        String[] colStrings = {"Mã tin tức", "Tiêu đề", "Mã nhân viên", "Nội dung", "Loại tin", "Ngày đăng"};
        tintucDefaultTableModel = new DefaultTableModel(colStrings, 0);
        tintucJTable = new JTable(tintucDefaultTableModel);
        tintucJTable.setBackground(Color.pink);
        JScrollPane tintucPane = new JScrollPane(tintucJTable);
        tintucPane.setBounds(50, 450, 500, 400);
        add(tintucPane);
    }

    public void cellClickListener(MouseListener listener) {
        tintucJTable.addMouseListener(listener);
    }

    public void addThemActionListener(ActionListener listener) {
        themButton.addActionListener(listener);
    }

    public void addSuaActionListener(ActionListener listener) {
        suaButton.addActionListener(listener);
    }
    public void addXoaActionListener(ActionListener listener){
        xoaButton.addActionListener(listener);
    }
    public void addResetActionListener(ActionListener listener){
        resetButton.addActionListener(listener);
    }
    public void addTimKiemActionLister(ActionListener listener){
        timkiemButton.addActionListener(listener);
    }
}
