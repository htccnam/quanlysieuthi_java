package CONTROLLER;

import DAO.DonHangDAO;
import MODEL.ChiTietDon;
import MODEL.DonHang;
import MODEL.SanPham;
import VIEW.TaoDonView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TaoDonController {

    private DonHangDAO dao;
    private TaoDonView view;

    public TaoDonController(TaoDonView view) {
        this.view = view;
        this.dao = new DonHangDAO();

        view.setNVToComboBox(dao.getMaNV());
        view.setKHToComboBox(dao.getMaKH());
        view.setKMToComboBox(dao.getMaKM());
        view.loadDataTable(dao.getSP());

        initEvents();
        capNhatHangThanhVien();
    }

    private void initEvents() {
        view.getBtnThem().addActionListener(e -> themSanPham());

        view.getBtnXoa().addActionListener(e -> xoaSanPham());

        view.getTxtTimKiem().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemSanPham();
            }
        });

        view.getBtnLuu().addActionListener(e -> luuDonHang());

        //sự kiện cập nhật của view ChiTiet
        view.getBtnLuu().addActionListener(e -> {
            if (view.getBtnLuu().getText().equals("Cập nhật")) {
                xuLyCapNhat();
            } else {
                luuDonHang();
            }
        });

        view.getCboKH().addActionListener(e -> {
            capNhatHangThanhVien();
            tinhTongTien();
        });

        view.getCboMaKM().addActionListener((e) -> {
            tinhTongTien();
        });       
    }

    //LOGIC 
    private void capNhatHangThanhVien() {
        Object selected = view.getCboKH().getSelectedItem();
        if (selected != null) {
            String maKH = selected.toString();
            String tenHang = dao.getHang(maKH);
            view.getTxtHang().setText(tenHang);
            view.getTxtHang().setEditable(false);
        }
    }

    private void themSanPham() {
        int rowTrai = view.getTableSanPham().getSelectedRow();
        if (rowTrai == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm ở bảng bên trái!");
            return;
        }

        int slMua = (int) view.getSpinner().getValue();
        if (slMua <= 0) {
            JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0");
            return;
        }

        DefaultTableModel modelTrai = view.getModelTableTrai();
        int slTon = Integer.parseInt(modelTrai.getValueAt(rowTrai, 3).toString());
        if (slMua > slTon) {
            JOptionPane.showMessageDialog(view, "Không đủ hàng trong kho (Còn: " + slTon + ")");
            return;
        }

        String maSP = modelTrai.getValueAt(rowTrai, 0).toString();
        String tenSP = modelTrai.getValueAt(rowTrai, 1).toString();
        double donGia = Double.parseDouble(modelTrai.getValueAt(rowTrai, 2).toString());
        double thanhTien = donGia * slMua;

        DefaultTableModel modelPhai = view.getModelTablePhai();
        modelPhai.addRow(new Object[]{maSP, tenSP, donGia, slMua, thanhTien});

        modelTrai.setValueAt(slTon - slMua, rowTrai, 3);

        tinhTongTien();
    }

    private void xoaSanPham() {
        int rowPhai = view.getTable().getSelectedRow();
        if (rowPhai == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm bên bảng phải để xóa!");
            return;
        }

        view.getModelTablePhai().removeRow(rowPhai);

        tinhTongTien();
    }

    private void tinhTongTien() {
        DefaultTableModel modelPhai = view.getModelTablePhai();
        double tamTinh = 0;
        double hang = 0;
        double km = 0;

        for (int i = 0; i < modelPhai.getRowCount(); i++) {
            tamTinh += Double.parseDouble(modelPhai.getValueAt(i, 4).toString());
        }

        if (view.getCboMaKM().getSelectedItem().toString().equalsIgnoreCase("KM01")) {
            km = tamTinh * 0.1;
        }
        if (view.getTxtHang().getText().equalsIgnoreCase("Bạc")) {
            hang = tamTinh * 0.02;
        } else if (view.getTxtHang().getText().equalsIgnoreCase("Vàng")) {
            hang = tamTinh * 0.05;
        } else if (view.getTxtHang().getText().equalsIgnoreCase("Kim cương")) {
            hang = tamTinh * 0.1;
        }
        double giamGia = hang + km;
        double tongTien = tamTinh - giamGia;
        view.getLblKM().setText(String.format("Giảm giá: %,.0f đ", giamGia));
        view.getLblTamTinh().setText(String.format("Tạm tính: %,.0f đ", tamTinh));
        view.getLblTongTien().setText(String.format("Tổng tiền: %,.0f đ", tongTien));

        //Lưu lại giá trị vào bộ nhớ tạm
        view.getLblTongTien().putClientProperty("value", tongTien);
    }

    private void timKiemSanPham() {
        String tuKhoa = view.getTxtTimKiem().getText();
        ArrayList<SanPham> list = dao.timKiemSP(tuKhoa);
        view.loadDataTable(list);
    }

    private void luuDonHang() {
    try {
        if (view.getModelTablePhai().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng thêm sản phẩm vào đơn hàng!");
            return;
        }
      
        if(view.getTxtMaDon().getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã đơn hàng!");
            return;
        }
        
        java.util.Date uDate = view.getNgayGD().getDate();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        
        Object tt = view.getLblTongTien().getClientProperty("value");
        double tongTien = (tt != null) ? (double) tt : 0;

            DonHang dh = new DonHang(
                    view.getTxtMaDon().getText(),
                    view.getCboKH().getSelectedItem().toString(),
                    view.getCboNV().getSelectedItem().toString(),
                    view.getCboMaKM().getSelectedItem().toString(),
                    sDate,
                    view.getCboBanHang().getSelectedItem().toString(),
                    view.getCboThanhToan().getSelectedItem().toString(),
                    tongTien
            );

            if (dao.insert(dh)) {
                DefaultTableModel modelPhai = view.getModelTablePhai();
                boolean checkCT = true;

                for (int i = 0; i < modelPhai.getRowCount(); i++) {
                    String maSP = modelPhai.getValueAt(i, 0).toString();
                    int slMua = Integer.parseInt(modelPhai.getValueAt(i, 3).toString());

                    ChiTietDon ctd = new ChiTietDon(
                            dh.getMaDonHang(),
                            maSP,
                            modelPhai.getValueAt(i, 1).toString(),
                            slMua,
                            Double.parseDouble(modelPhai.getValueAt(i, 2).toString()),
                            Double.parseDouble(modelPhai.getValueAt(i, 4).toString())
                    );

                    if (dao.insertChiTiet(ctd)) {
                        dao.truSoLuongKho(maSP, slMua);
                    } else {
                        checkCT = false;
                        break;
                    }
                }

                if (checkCT) {
                    JOptionPane.showMessageDialog(view, "Lưu đơn hàng thành công!");
                    modelPhai.setRowCount(0);
                    view.getTxtMaDon().setText("");
                    view.loadDataTable(dao.getSP());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
        }
    }

    public void setEditData(DonHang dh, ArrayList<ChiTietDon> dsChiTiet) {
        view.getTxtMaDon().setText(dh.getMaDonHang());
        view.getTxtMaDon().setEditable(false);
        view.getCboKH().setSelectedItem(dh.getMaKH());
        view.getCboNV().setSelectedItem(dh.getMaNV());
        view.getNgayGD().setDate(dh.getNgayGD());
        view.getCboBanHang().setSelectedItem(dh.getPTban());
        view.getCboThanhToan().setSelectedItem(dh.getPTgiaodich());

        DefaultTableModel modelPhai = view.getModelTablePhai();
        modelPhai.setRowCount(0);
        for (ChiTietDon ct : dsChiTiet) {
            modelPhai.addRow(new Object[]{
                ct.getMaSanPham(), ct.getTenSanPham(), ct.getGia(), ct.getSoluong(), ct.getThanhtien()
            });
        }

        tinhTongTien();

        view.getBtnLuu().setText("Cập nhật");
    }

    private void xuLyCapNhat() {
        try {
            java.util.Date uDate = view.getNgayGD().getDate();
            java.sql.Date sDate = new java.sql.Date(uDate.getTime());

            Object val = view.getLblTongTien().getClientProperty("value");
            double tongTien = (val != null) ? (double) val : 0;

            DonHang dh = new DonHang(
                    view.getTxtMaDon().getText(),
                    view.getCboKH().getSelectedItem().toString(),
                    view.getCboNV().getSelectedItem().toString(),
                    view.getCboMaKM().getSelectedItem().toString(),
                    sDate,
                    view.getCboBanHang().getSelectedItem().toString(),
                    view.getCboThanhToan().getSelectedItem().toString(),
                    tongTien
            );

            ArrayList<ChiTietDon> dsMoi = new ArrayList<>();
            DefaultTableModel modelPhai = view.getModelTablePhai();
            for (int i = 0; i < modelPhai.getRowCount(); i++) {
                dsMoi.add(new ChiTietDon(
                        dh.getMaDonHang(),
                        modelPhai.getValueAt(i, 0).toString(),
                        modelPhai.getValueAt(i, 1).toString(),
                        Integer.parseInt(modelPhai.getValueAt(i, 3).toString()),
                        Double.parseDouble(modelPhai.getValueAt(i, 2).toString()),
                        Double.parseDouble(modelPhai.getValueAt(i, 4).toString())
                ));
            }

            if (dao.update(dh, dsMoi)) {
                JOptionPane.showMessageDialog(view, "Cập nhật đơn hàng thành công!");
                SwingUtilities.getWindowAncestor(view).dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi cập nhật: " + ex.getMessage());
        }
    }
}
