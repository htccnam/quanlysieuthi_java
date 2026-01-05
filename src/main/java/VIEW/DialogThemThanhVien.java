package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class DialogThemThanhVien extends JDialog {
    private JTable tblCandidates;
    private DefaultTableModel model;
    private JButton btnXacNhan;
    private String selectedMaKH = null;

    public DialogThemThanhVien(JFrame parent, String title) {
        super(parent, title, true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Bảng hiển thị danh sách ứng viên
        String[] headers = {"Mã KH", "Tên Khách Hàng", "Tổng Chi Tiêu"};
        model = new DefaultTableModel(headers, 0) {
            @Override // Không cho sửa
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCandidates = new JTable(model);
        tblCandidates.setRowHeight(25);
        add(new JScrollPane(tblCandidates), BorderLayout.CENTER);

        // Nút xác nhận
        btnXacNhan = new JButton("Thêm vào Hạng");
        btnXacNhan.setBackground(new Color(0, 102, 204));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JPanel pnlBot = new JPanel();
        pnlBot.add(btnXacNhan);
        add(pnlBot, BorderLayout.SOUTH);

        // Sự kiện lấy mã khi bấm nút
        btnXacNhan.addActionListener(e -> {
            int row = tblCandidates.getSelectedRow();
            if (row >= 0) {
                selectedMaKH = tblCandidates.getValueAt(row, 0).toString();
                dispose(); // Đóng cửa sổ
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng!");
            }
        });
    }

    public void addCandidate(String ma, String ten, String tien) {
        model.addRow(new Object[]{ma, ten, tien});
    }

    // Hàm để Controller gọi lấy kết quả
    public String getSelectedMaKH() {
        return selectedMaKH;
    }
    
    public void showDialog() {
        this.setVisible(true);
    }
}
