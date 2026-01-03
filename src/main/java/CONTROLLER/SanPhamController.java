/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLLER;

/**
 *
 * @author VŨ HÙNG HẢI
 */

import DAO.LoaiHangDAO;
import DAO.NhaCungCapDAO;
import MODEL.LoaiHang;
import MODEL.NhaCungCap;
import MODEL.SanPham;
import MODEL.SanPhamModel;
import VIEW.SanPhamView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat; // Import thư viện định dạng ngày
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class SanPhamController {
    
    private SanPhamView view;
    private SanPhamModel model;
    
    // Tạo định dạng ngày tháng Việt Nam
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public SanPhamController(SanPhamView view) {
        this.view = view;
        this.model = new SanPhamModel();

        // 1. Nạp dữ liệu ComboBox và Bảng
        loadComboBoxData();
        loadTable(model.getList());

        // --- NÚT THÊM ---
        view.addAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lấy dữ liệu
                    String ma = view.txtMaSP.getText();
                    String ten = view.txtTenSP.getText();
                    
                    // ComboBox Đối tượng
                    LoaiHang lh = (LoaiHang) view.cboLoai.getSelectedItem();
                    String maLoai = lh.getMaLoai();
                    
                    NhaCungCap ncc = (NhaCungCap) view.cboNCC.getSelectedItem();
                    String maNCC = ncc.getMaNCC();
                    
                    String xuatXu = view.txtXuatXu.getText();
                    
                    // --- SỬA: Lấy Tình trạng từ ComboBox ---
                    String tinhTrang = view.cboTinhTrang.getSelectedItem().toString();
                    
                    String donVi = view.txtDonViTinh.getText();

                    // Validate
                    if (ma.isEmpty() || ten.isEmpty()) {
                        view.showMessage("Mã và Tên không được để trống!");
                        return;
                    }
                    
                    if (view.txtNgaySX.getDate() == null || view.txtHanSD.getDate() == null) {
                        view.showMessage("Vui lòng chọn Ngày SX và Hạn SD!");
                        return;
                    }
                    java.sql.Date sqlNgaySX = new java.sql.Date(view.txtNgaySX.getDate().getTime());
                    java.sql.Date sqlHanSD = new java.sql.Date(view.txtHanSD.getDate().getTime());

                    int sl = Integer.parseInt(view.txtSoLuong.getText());
                    double giaNhap = Double.parseDouble(view.txtGiaNhap.getText());
                    double giaBan = Double.parseDouble(view.txtGiaBan.getText());

                    if (sl < 0 || giaNhap < 0 || giaBan < 0) {
                        view.showMessage("Số lượng và Giá phải >= 0");
                        return;
                    }

                    if (model.checkExist(ma)) {
                        view.showMessage("Mã sản phẩm này đã tồn tại!");
                        return;
                    }

                    // Thêm
                    SanPham sp = new SanPham(ma, ten, maLoai, maNCC, xuatXu, sl, sqlNgaySX, sqlHanSD, tinhTrang, giaNhap, giaBan, donVi);
                    model.add(sp);
                    
                    updateView();
                    view.showMessage("Thêm thành công!");

                } catch (NumberFormatException ex) {
                    view.showMessage("Lỗi: Số lượng và Giá phải nhập số!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    view.showMessage("Lỗi: " + ex.getMessage());
                }
            }
        });

        // --- NÚT SỬA ---
        view.addEditListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ma = view.txtMaSP.getText();
                    if (ma.isEmpty()) {
                        view.showMessage("Vui lòng chọn sản phẩm để sửa!");
                        return;
                    }
                    
                    String ten = view.txtTenSP.getText();
                    LoaiHang lh = (LoaiHang) view.cboLoai.getSelectedItem();
                    String maLoai = lh.getMaLoai();
                    NhaCungCap ncc = (NhaCungCap) view.cboNCC.getSelectedItem();
                    String maNCC = ncc.getMaNCC();
                    String xuatXu = view.txtXuatXu.getText();
                    
                    // --- SỬA: Lấy từ ComboBox ---
                    String tinhTrang = view.cboTinhTrang.getSelectedItem().toString();
                    
                    String donVi = view.txtDonViTinh.getText();

                    java.sql.Date sqlNgaySX = new java.sql.Date(view.txtNgaySX.getDate().getTime());
                    java.sql.Date sqlHanSD = new java.sql.Date(view.txtHanSD.getDate().getTime());

                    int sl = Integer.parseInt(view.txtSoLuong.getText());
                    double giaNhap = Double.parseDouble(view.txtGiaNhap.getText());
                    double giaBan = Double.parseDouble(view.txtGiaBan.getText());

                    SanPham sp = new SanPham(ma, ten, maLoai, maNCC, xuatXu, sl, sqlNgaySX, sqlHanSD, tinhTrang, giaNhap, giaBan, donVi);
                    model.update(sp);
                    
                    updateView();
                    view.showMessage("Sửa thành công!");

                } catch (Exception ex) {
                    view.showMessage("Lỗi nhập liệu: " + ex.getMessage());
                }
            }
        });

        // --- NÚT XÓA ---
        view.addDeleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = view.txtMaSP.getText();
                if (ma.isEmpty()) {
                    view.showMessage("Vui lòng chọn dòng cần xóa!");
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
                if (confirm == JOptionPane.YES_OPTION) {
                    model.delete(ma);
                    updateView();
                    view.showMessage("Đã xóa!");
                }
            }
        });

        // --- NÚT LÀM MỚI ---
        view.addResetListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                loadTable(model.getList());
            }
        });

        // --- CLICK BẢNG (QUAN TRỌNG: Xử lý định dạng ngày) ---
        view.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row >= 0) {
                    view.txtMaSP.setText(view.table.getValueAt(row, 0).toString());
                    view.txtTenSP.setText(view.table.getValueAt(row, 1).toString());
                    
                    // 1. Chọn lại ComboBox Loại Hàng
                    String maLoaiTable = view.table.getValueAt(row, 2).toString();
                    for(int i=0; i<view.cboLoai.getItemCount(); i++){
                        LoaiHang item = (LoaiHang) view.cboLoai.getItemAt(i);
                        if(item.getMaLoai().equals(maLoaiTable)){
                            view.cboLoai.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // 2. Chọn lại ComboBox NCC
                    String maNCCTable = view.table.getValueAt(row, 3).toString();
                    for(int i=0; i<view.cboNCC.getItemCount(); i++){
                        NhaCungCap item = (NhaCungCap) view.cboNCC.getItemAt(i);
                        if(item.getMaNCC().equals(maNCCTable)){
                            view.cboNCC.setSelectedIndex(i);
                            break;
                        }
                    }

                    view.txtXuatXu.setText(view.table.getValueAt(row, 4).toString());
                    view.txtSoLuong.setText(view.table.getValueAt(row, 5).toString());
                    
                    // 3. XỬ LÝ NGÀY THÁNG (Parse từ String dd-MM-yyyy về Date)
                    try {
                        String sNgaySX = view.table.getValueAt(row, 6).toString();
                        String sHanSD = view.table.getValueAt(row, 7).toString();
                        
                        view.txtNgaySX.setDate(sdf.parse(sNgaySX));
                        view.txtHanSD.setDate(sdf.parse(sHanSD));
                    } catch (Exception ex) {
                        System.out.println("Lỗi parse ngày: " + ex);
                    }

                    // 4. SỬA: Set Tình trạng vào ComboBox
                    String tinhTrangTable = view.table.getValueAt(row, 8).toString();
                    view.cboTinhTrang.setSelectedItem(tinhTrangTable);
                    
                    view.txtGiaNhap.setText(view.table.getValueAt(row, 9).toString());
                    view.txtGiaBan.setText(view.table.getValueAt(row, 10).toString());
                    view.txtDonViTinh.setText(view.table.getValueAt(row, 11).toString());

                    view.txtMaSP.setEditable(false);
                }
            }
        });

        // TÌM KIẾM 
        view.addSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = view.txtTimKiem.getText();
                if (key.isEmpty()) {
                    view.showMessage("Nhập gì đó đi");
                    return;
                }
                loadTable(model.timkiem(key));
            }
        });
    }

    // --- CÁC HÀM HỖ TRỢ ---

    private void resetForm() {
        view.txtMaSP.setText("");
        view.txtTenSP.setText("");
        view.txtXuatXu.setText("");
        view.txtSoLuong.setText("");
        
        // Reset ComboBox về phần tử đầu tiên
        view.cboTinhTrang.setSelectedIndex(0); 
        
        view.txtGiaNhap.setText("");
        view.txtGiaBan.setText("");
        view.txtDonViTinh.setText("");
        view.txtTimKiem.setText("");
        view.txtNgaySX.setDate(null);
        view.txtHanSD.setDate(null);
        view.txtMaSP.setEditable(true);
    }

    // --- SỬA HÀM LOAD TABLE ĐỂ ĐỊNH DẠNG NGÀY ---
    private void loadTable(ArrayList<SanPham> list) {
        view.tableModel.setRowCount(0);
        for (SanPham sp : list) {
            
            // Format ngày tháng ra chuỗi "dd-MM-yyyy"
            String hienThiNgaySX = (sp.getNgaySX() != null) ? sdf.format(sp.getNgaySX()) : "";
            String hienThiHanSD = (sp.getHanSD() != null) ? sdf.format(sp.getHanSD()) : "";
            
            view.tableModel.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getMaLoai(), sp.getMaNCC(),
                sp.getXuatXu(), sp.getSoLuong(),
                hienThiNgaySX, // Đã format
                hienThiHanSD,  // Đã format
                sp.getTinhTrang(), sp.getGiaNhap(), sp.getGiaBan(), sp.getDonViTinh()
            });
        }
    }

    private void updateView() {
        loadTable(model.getList());
        resetForm();
    }

    private void loadComboBoxData() {
        // Load Loại Hàng
        LoaiHangDAO lhDao = new LoaiHangDAO();
        view.cboLoai.removeAllItems();
        for (LoaiHang lh : lhDao.getAll()) {
            view.cboLoai.addItem(lh);
        }
        
        // Load Nhà Cung Cấp
        NhaCungCapDAO nccDao = new NhaCungCapDAO();
        view.cboNCC.removeAllItems();
        for (NhaCungCap ncc : nccDao.getAll()) {
            view.cboNCC.addItem(ncc);
        }
    }
}