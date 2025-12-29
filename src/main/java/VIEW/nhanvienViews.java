/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import com.toedter.calendar.JDateChooser;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Admin
 */
public class nhanvienViews extends JPanel {

    private JButton themButton = new JButton("Thêm");
    private JButton suaButton = new JButton("Sửa");
    private JButton xoaButton = new JButton("xóa");
    private JButton resetButton = new JButton("reset");
    private JButton timkiemButton = new JButton("Tìm kiếm");

    public JTextField manhanvienField = new JTextField();
    public JTextField hotenField = new JTextField();
    public JDateChooser ngaysinhChooser = new JDateChooser();
    public String[] gioitinhStrings = new String[]{"Nam", "Nữ", "Khác"};
    public JComboBox<String> gioitinhComboBox = new JComboBox<>(gioitinhStrings);
    public JTextField diachiField = new JTextField();
    public JTextField sodienthoaiField = new JTextField();

    public JTextField timkiemField = new JTextField();

    public DefaultTableModel nhanvienDefaultTableModel;
    public JTable nhanvienJTable;

    public nhanvienViews() {
        setSize(600, 700);
        setLayout(null);

        //manhanvien
        JLabel manhanvienJLabel = new JLabel("Mã nhân viên");
        manhanvienJLabel.setBounds(50, 30, 100, 25);
        add(manhanvienJLabel);
        manhanvienField.setBounds(200, 30, 300, 25);
        add(manhanvienField);

        //hoten
        JLabel hotenJLabel = new JLabel("Họ tên");
        hotenJLabel.setBounds(50, 60, 100, 25);
        add(hotenJLabel);
        hotenField.setBounds(200, 60, 300, 25);
        add(hotenField);

        //ngaysinh
        JLabel ngayJLabel = new JLabel("Ngày sinh");
        ngayJLabel.setBounds(50, 90, 100, 25);
        add(ngayJLabel);

        ngaysinhChooser.setBounds(200, 90, 300, 25);
        add(ngaysinhChooser);

        //gioitinh
        JLabel gioitinhJLabel = new JLabel("Chọn giới tính:");
        gioitinhJLabel.setBounds(50, 120, 100, 25);
        add(gioitinhJLabel);

        gioitinhComboBox.setBounds(200, 120, 300, 25);
        add(gioitinhComboBox);

        //diachi
        JLabel diachiJLabel = new JLabel("Địa chỉ");
        diachiJLabel.setBounds(50, 150, 100, 25);
        add(diachiJLabel);

        diachiField.setBounds(200, 150, 300, 25);
        add(diachiField);

        //sodienthoai
        JLabel sodienthoaiJLabel = new JLabel("Số điện thoại:");
        sodienthoaiJLabel.setBounds(50, 180, 100, 25);
        add(sodienthoaiJLabel);

        sodienthoaiField.setBounds(200, 180, 300, 25);
        add(sodienthoaiField);

        //button
        themButton.setBounds(50, 270, 100, 30);
        add(themButton);

        suaButton.setBounds(170, 270, 100, 30);
        add(suaButton);

        xoaButton.setBounds(290, 270, 100, 30);
        add(xoaButton);

        resetButton.setBounds(410, 270, 100, 30);
        add(resetButton);

        timkiemField.setBounds(50, 310, 300, 25);
        add(timkiemField);

        timkiemButton.setBounds(400, 310, 100, 30);
        add(timkiemButton);

        //table
        String[] nhanvienStrings = {"Mã NV", "Họ tên NV", "Ngày sinh", "Giới tính", "Địa chỉ", "Số điện thoại"};
        nhanvienDefaultTableModel = new DefaultTableModel(nhanvienStrings, 0);
        nhanvienJTable = new JTable(nhanvienDefaultTableModel);
        JScrollPane nhanvienJScrollPane = new JScrollPane(nhanvienJTable);
        nhanvienJScrollPane.setBounds(30, 350, 500, 250);
        add(nhanvienJScrollPane);
    }

    //helo
    public void addThemListener(ActionListener listener) {
        themButton.addActionListener(listener);
    }
;

}
