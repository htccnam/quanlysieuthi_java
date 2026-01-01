/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import com.sun.net.httpserver.Headers;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Admin
 */
public class chucvuView extends JPanel{
    public JTextField machucvuField;
    public JTextField tenchucvuField;
    public JButton themButton;
    public JButton suaButton;
    public JButton xoaButton;
    public JButton resetButton;
    
    public JTextField timkiemField;
    public JButton timkiemButton;
    
    public DefaultTableModel chucvuDefaultTableModel;
    public JTable chucvuJTable;
    

    public chucvuView() {
        setLayout(null);
        JLabel machucvuJLabel=new JLabel("Chức vụ:");
        machucvuJLabel.setBounds(300, 50, 200, 40);
        machucvuJLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(machucvuJLabel);
        
        machucvuField=new JTextField();
        machucvuField.setBounds(600, 50, 300, 40);
        machucvuField.setFont(new Font("Arial",Font.BOLD,24));
        add(machucvuField);
        
        JLabel tenchucvuJLabel =new JLabel("Tên chức vụ:");
        tenchucvuJLabel.setBounds(300, 100, 200, 40);
        tenchucvuJLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(tenchucvuJLabel);
        
        tenchucvuField=new JTextField();
        tenchucvuField.setBounds(600, 100, 300, 40);
        tenchucvuField.setFont(new Font("Arial",Font.BOLD,24));
        add(tenchucvuField);
        
        themButton=new JButton("Thêm");
        themButton.setBounds(200, 150, 200, 40);
        themButton.setForeground(Color.white);
        themButton.setBackground(Color.GREEN);
        themButton.setFont(new Font("Arial",Font.ITALIC,23));
        add(themButton);
        
        suaButton=new JButton("Sửa");
        suaButton.setBounds(450, 150, 200, 40);
        suaButton.setFont(new Font("Arial",Font.ITALIC,23));
        suaButton.setForeground(Color.white);
        suaButton.setBackground(Color.blue);
        add(suaButton);
        
        xoaButton=new JButton("Xóa");
        xoaButton.setBounds(700, 150, 200, 40);
        xoaButton.setBackground(Color.red);
        xoaButton.setFont(new Font("Arial",Font.ITALIC,23));
        add(xoaButton);
        
        resetButton=new JButton("Reset");
        resetButton.setBounds(950, 150, 200, 40);
        resetButton.setBackground(Color.yellow);
        resetButton.setFont(new Font("Arial",Font.ITALIC,23));
        add(resetButton);
        
        timkiemField=new JTextField();
        timkiemField.setBounds(300, 200, 300, 40);
        timkiemField.setFont(new Font("Arial",Font.BOLD,24));
        add(timkiemField);
        
        timkiemButton=new JButton("Tìm kiếm");
        timkiemButton.setBounds(700, 200, 200, 40);
        timkiemButton.setBackground(Color.orange);
        timkiemButton.setFont(new Font("Arial",Font.ITALIC,23));
        add(timkiemButton);
        
        String[] tieudeStrings={"mã chức vụ","tên chức vụ"};
        chucvuDefaultTableModel=new DefaultTableModel(tieudeStrings,0);
        chucvuJTable=new JTable(chucvuDefaultTableModel);
        chucvuJTable.setFont(new Font("Arial",Font.BOLD,14));
        chucvuJTable.setForeground(Color.pink);

        JTableHeader chucvuHeader=chucvuJTable.getTableHeader();
        chucvuHeader.setFont(new Font("Arial",Font.BOLD,15));
        chucvuHeader.setForeground(Color.red);
        chucvuHeader.setBackground(Color.yellow);
                
        JScrollPane chucvuJScrollPane=new JScrollPane(chucvuJTable);
        chucvuJScrollPane.setBounds(200, 250, 800, 600);
        add(chucvuJScrollPane);
    }
    
}
