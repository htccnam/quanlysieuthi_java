package DAO;

import MODEL.*;
import java.sql.*;
import java.util.ArrayList;

public class DonHangDAO {

    private Connection conn;

    public DonHangDAO(Connection conn) {
        this.conn = conn;
    }

    // 1️⃣ Thêm đơn hàng + trả về mã đơn
    public int insertDonHang(DonHang dh) throws SQLException {
        String sql = """
            INSERT INTO DonHang (NgayLap, MaKH, MaNV, TongTien)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement ps = conn.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
        );

        ps.setDate(1, dh.getNgayLap());
        ps.setString(2, dh.getMaKH());
        ps.setString(3, dh.getMaNV());
        ps.setDouble(4, dh.getTongTien());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    // 2️⃣ Thêm chi tiết đơn hàng
    public void insertChiTiet(ChiTietDonHang ct) throws SQLException {
        String sql = """
            INSERT INTO ChiTietDonHang
            (MaDonHang, MaSP, SoLuong, DonGia)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, ct.getMaDonHang());
        ps.setString(2, ct.getMaSP());
        ps.setInt(3, ct.getSoLuong());
        ps.setDouble(4, ct.getDonGia());
        ps.executeUpdate();
    }

    // 3️⃣ Lấy danh sách đơn hàng (cho DonHangView)
    public ArrayList<DonHang> getAllDonHang() throws SQLException {
        ArrayList<DonHang> list = new ArrayList<>();

        String sql = "SELECT * FROM DonHang ORDER BY MaDonHang DESC";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            DonHang dh = new DonHang(
                rs.getInt("MaDonHang"),
                rs.getDate("NgayLap"),
                rs.getString("MaKH"),
                rs.getString("MaNV"),
                rs.getDouble("TongTien")
            );
            list.add(dh);
        }
        return list;
    }

    // 4️⃣ Tính tổng tiền từ chi tiết
    public double tinhTongTien(int maDonHang) throws SQLException {
        String sql = """
            SELECT SUM(SoLuong * DonGia) AS Tong
            FROM ChiTietDonHang
            WHERE MaDonHang = ?
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, maDonHang);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble("Tong");
        }
        return 0;
    }
}
