/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;


/**
 *
 * @author VŨ HÙNG HẢI
 */

import MODEL.LoaiHang;
import MODEL.LoaiHangModel;
import VIEW.LoaiHangView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LoaiHangController {
    
    private LoaiHangView view;
    private LoaiHangModel model;

    //HÀM KHỞI TẠO 
    public LoaiHangController(LoaiHangView view) {
        this.view = view;
        this.model = new LoaiHangModel(); // Tự tạo model

        // 1. Chạy lên là nạp bảng ngay
        loadTable(model.getList());

        // 2. Gắn sự kiện cho nút THÊM
        view.addAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaLoai.getText();
                String ten = view.txtTenLoai.getText();

                if (ma.isEmpty() || ten.isEmpty()) {
                    view.showMessage("Vui lòng không để trống!");
                    return;
                }
                
                // Check trùng mã trước khi thêm
                if (model.checkExist(ma)) {
                    view.showMessage("Mã '" + ma + "' đã tồn tại! Vui lòng chọn mã khác.");
                    return;
                }

                LoaiHang lh = new LoaiHang(ma, ten);
                model.add(lh);
                
                loadTable(model.getList()); // Nạp lại bảng
                resetForm();
                view.showMessage("Thêm thành công!");
            }
        });

        // 3. Gắn sự kiện cho nút SỬA
        view.addEditListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaLoai.getText();
                String ten = view.txtTenLoai.getText();

                if (ma.isEmpty()) {
                    view.showMessage("Vui lòng chọn hoặc nhập mã loại cần sửa!");
                    return;
                }

                LoaiHang lh = new LoaiHang(ma, ten);
                model.update(lh);
                
                loadTable(model.getList());
                resetForm();
                view.showMessage("Sửa thành công!");
            }
        });

        // 4. Gắn sự kiện cho nút XÓA
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaLoai.getText();

                if (ma.isEmpty()) {
                    view.showMessage("Vui lòng chọn loại hàng cần xóa!");
                    return;
                }
                
                // Hiện bảng hỏi 
                int confirm = JOptionPane.showConfirmDialog(view, 
                        "Bạn có chắc chắn muốn xóa loại hàng này không?", 
                        "Cảnh báo", 
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.delete(ma); // Xóa trong DB
                    loadTable(model.getList()); // Nạp lại bảng
                    resetForm();
                    view.showMessage("Đã xóa xong!");
                }
            }
        });

        // 5. Gắn sự kiện cho nút LÀM MỚI (Reset)
        view.addResetListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                loadTable(model.getList()); // Nạp lại toàn bộ danh sách (nếu đang tìm kiếm dở)
            }
        });

        // 6. Gắn sự kiện CLICK VÀO BẢNG (Để đổ dữ liệu lên ô nhập)
        view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Lấy dòng được chọn
                int row = view.table.getSelectedRow();
                
                if (row >= 0) {
                    // Lấy dữ liệu từ bảng tại dòng row, cột 0 (Mã) và cột 1 (Tên)
                    String ma = view.table.getValueAt(row, 0).toString();
                    String ten = view.table.getValueAt(row, 1).toString();
                    
                    // Đổ lên ô nhập
                    view.txtMaLoai.setText(ma);
                    view.txtTenLoai.setText(ten);
                    
                    // Khóa ô Mã lại không cho sửa (vì Mã là khóa chính) - Tùy chọn
                    // view.txtMaLoai.setEditable(false); 
                }
            }
        });
        
        // 7. Gắn sự kiện cho nút TÌM KIẾM
        view.addSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = view.txtTimKiem.getText();
                
                if (keyword.isEmpty()) {
                    view.showMessage("Vui lòng nhập từ khóa để tìm!");
                    return;
                }
                
                // Gọi hàm tìm kiếm bên Model
                ArrayList<LoaiHang> ketQua = model.search(keyword);
                
                // Đổ kết quả tìm được vào bảng
                loadTable(ketQua);
            }
        });
    }

    // Đổ danh sách vào bảng
    public void loadTable(ArrayList<LoaiHang> list) {
        view.tableModel.setRowCount(0); // Xóa bảng cũ
        for (LoaiHang lh : list) {
            view.tableModel.addRow(new Object[]{
                lh.getMaLoai(), 
                lh.getTenLoai()
            });
        }
    }
    
    //Xóa trắng các ô nhập
    public void resetForm() {
        view.txtMaLoai.setText("");
        view.txtTenLoai.setText("");
        view.txtTimKiem.setText("");
        view.txtMaLoai.setEditable(true); // Mở khóa ô Mã (nếu trước đó bị khóa)
    }
}
