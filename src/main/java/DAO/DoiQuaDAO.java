package DAO;

import java.sql.*;
import java.util.Vector;

public class DoiQuaDAO {
    
    // 1. Lấy điểm hiện tại của khách hàng
    public int getDiemHienTai(String maKH) {
        int diem = 0;
        String sql = "SELECT diemtichluy FROM khachhang WHERE makhachhang = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                diem = rs.getInt("diemtichluy");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return diem;
    }

    // 2. Thực hiện Đổi quà (Trừ điểm + Ghi lịch sử) - Dùng Transaction để an toàn
    public boolean doiQua(String maKH, String tenQua, int diemTru) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Bắt đầu Transaction

            // Bước 1: Trừ điểm khách hàng
            String sqlUpdate = "UPDATE khachhang SET diemtichluy = diemtichluy - ? WHERE makhachhang = ?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, diemTru);
            psUpdate.setString(2, maKH);
            psUpdate.executeUpdate();

            // Bước 2: Ghi vào lịch sử
            String sqlInsert = "INSERT INTO lichsudoiqua (makhachhang, tenqua, diemtru, ngaydoi) VALUES(?, ?, ?, ?)";
            PreparedStatement psInsert = con.prepareStatement(sqlInsert);
            psInsert.setString(1, maKH);
            psInsert.setString(2, tenQua);
            psInsert.setInt(3, diemTru);
            psInsert.setDate(4, new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
            psInsert.executeUpdate();

            con.commit(); // Xác nhận thành công
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (SQLException ex) {} // Hoàn tác nếu lỗi
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); con.close(); } catch (SQLException ex) {}
        }
    }

    // 3. Lấy danh sách khách hàng để đổ vào ComboBox
    public Vector<String> getListKhachHang() {
        Vector<String> list = new Vector<>();
        String sql = "SELECT makhachhang, tenkhachhang FROM khachhang";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
    // 4. Lấy lịch sử đổi quà của khách
    public Vector<Vector<Object>> getLichSuDoi(String maKH) {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT tenqua, diemtru, ngaydoi FROM lichsudoiqua WHERE makhachhang = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("tenqua"));
                row.add(rs.getInt("diemtru"));
                row.add(rs.getDate("ngaydoi"));
                data.add(row);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return data;
    }
}
