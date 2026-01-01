/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VIEW;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class chucvuView extends JPanel{

    public chucvuView() {
        setLayout(null);
        JLabel machucvuJLabel=new JLabel("Chức vụ:");
        machucvuJLabel.setBounds(500, 50, 200, 40);
        machucvuJLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(machucvuJLabel);
    }
    
}
