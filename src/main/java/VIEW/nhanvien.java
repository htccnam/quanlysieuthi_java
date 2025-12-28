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
public class nhanvien extends JFrame {
    private JButton themButton=new JButton("Thêm");
    private JButton suaButton=new JButton("Sửa");
    private JButton xoaButton=new JButton("xóa");
    private JButton resetButton=new JButton("reset");
    private JButton timkiemButton=new JButton("Tìm kiếm");
    
    private JTextField timkiemField=new JTextField();

    public nhanvien() {
        setTitle("quản lý nhân viên");
        setSize(600, 700);
        setLayout(null);

        //manhanvien
        JLabel manhanvienJLabel = new JLabel("Mã nhân viên");
        manhanvienJLabel.setBounds(50, 30, 100, 25);
        add(manhanvienJLabel);
        JTextField manhanvienField = new JTextField();
        manhanvienField.setBounds(200, 30, 300, 25);
        add(manhanvienField);

        //hoten
        JLabel hotenJLabel = new JLabel("Họ tên");
        hotenJLabel.setBounds(50, 60, 100, 25);
        add(hotenJLabel);
        JTextField hotenField = new JTextField();
        hotenField.setBounds(200, 60, 300, 25);
        add(hotenField);

        //ngaysinh
        JLabel ngayJLabel = new JLabel("Ngày sinh");
        ngayJLabel.setBounds(50, 90, 100, 25);
        add(ngayJLabel);
        JDateChooser ngaysinhChooser = new JDateChooser();
        ngaysinhChooser.setBounds(200, 90, 300, 25);
        add(ngaysinhChooser);
        
        //gioitinh
        JLabel gioitinhJLabel=new JLabel("Chọn giới tính:");
        gioitinhJLabel.setBounds(50, 120, 100, 25);
        add(gioitinhJLabel);
        
        String[] gioitinhStrings=new String[]{"Nam","Nữ","Khác"};
        JComboBox<String> gioitinhComboBox =new JComboBox<>(gioitinhStrings);
        gioitinhComboBox.setBounds(200, 120, 300, 25);
        add(gioitinhComboBox);
        
        //diachi
        JLabel diachiJLabel=new JLabel("Địa chỉ");
        diachiJLabel.setBounds(50, 150, 100, 25);
        add(diachiJLabel);
        JTextField diachiField=new JTextField();
        diachiField.setBounds(200, 150, 300, 25);
        add(diachiField);
        
        //sodienthoai
        JLabel sodienthoaiJLabel=new JLabel("Số điện thoại:");
        sodienthoaiJLabel.setBounds(50, 180, 100, 25);
        add(sodienthoaiJLabel);
        
        JTextField sodienthoaiField=new JTextField();
        sodienthoaiField.setBounds(200, 180, 300, 25);
        add(sodienthoaiField);
        
        //taikhoan
        JLabel taikhoanJLabel=new JLabel("Tài khoản:");
        taikhoanJLabel.setBounds(50, 210, 100, 25);
        add(taikhoanJLabel);
        
        JTextField taikhoanField=new JTextField();
        taikhoanField.setBounds(200, 210, 300, 25);
        add(taikhoanField);
        
        //matkhau
        JLabel matkhauJLabel=new JLabel("Mật khẩu:");
        matkhauJLabel.setBounds(50, 240, 100, 25);
        add(matkhauJLabel);
        
        JTextField matkhauField=new JTextField();
        matkhauField.setBounds(200, 240, 300, 25);
        add(matkhauField);
        
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
        String[] nhanvienStrings={"Mã NV","Họ tên NV","Ngày sinh","Giới tính","Địa chỉ","Số điện thoại","Tài khoản","Mật khẩu"};
        DefaultTableModel nhanvienDefaultTableModel = new DefaultTableModel(nhanvienStrings,0);
        JTable nhanJTable= new JTable(nhanvienDefaultTableModel);
        JScrollPane nhanvienJScrollPane= new JScrollPane(nhanJTable);
        nhanvienJScrollPane.setBounds(30, 350, 500, 250);
        add(nhanvienJScrollPane);
    }

    public void addThemListener(ActionListener listener){themButton.addActionListener(listener);};

}
