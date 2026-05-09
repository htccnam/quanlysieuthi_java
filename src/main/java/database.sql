-- I. TẠO DATABASE
CREATE DATABASE quanlysieuthi

-- I.1 HÃY CHẠY TẠO DATABASE VÀ LOAD LẠI TRANG CHỦ XAMPP VÀO DATABASE quanlysieuthi và chạy phần dưới
USE quanlysieuthi
-- 1.1 taikhoan
CREATE TABLE taikhoan(
                         taikhoan varchar(50) PRIMARY KEY,
                         matkhau varchar(50)
);

-- 2.Tạo bảng chức vụ
CREATE TABLE chucvu(
                       machucvu varchar(50) PRIMARY KEY,
                       tenchucvu varchar(50)
);
-- 3. TẠO BẢNG NHÂN VIÊN
CREATE TABLE nhanvien (
                          manhanvien VARCHAR(50) PRIMARY KEY,
                          tennhanvien VARCHAR(50),
                          ngaysinh DATE,
                          gioitinh VARCHAR(10),
                          sodienthoai VARCHAR(50),
                          email VARCHAR(50),
                          diachi VARCHAR(255),
                          machucvu VARCHAR(50)  ,

                          FOREIGN KEY (machucvu) REFERENCES
                              chucvu(machucvu)
);



-- 4. bảng khuyenmai
CREATE TABLE khuyenmai(
                          makhuyenmai varchar(50) PRIMARY KEY,
                          tenkhuyenmai varchar(50) ,
                          mota varchar(200),
                          sotiengiam int,
                          ngaytao date
);




-- 4. TẠO BẢNG KHÁCH HÀNG
CREATE TABLE IF NOT EXISTS khachhang (
                                         makhachhang VARCHAR(20) PRIMARY KEY,
    tenkhachhang VARCHAR(100) NOT NULL,
    gioitinh VARCHAR(10),
    ngaysinh DATE,
    diachi VARCHAR(255),
    email VARCHAR(100),
    sdt INT,
    diemtichluy INT DEFAULT 0,
    hangthanhvien VARCHAR(50) DEFAULT "Chưa xếp hạng",
    diemhientai INT DEFAULT 0


    );

-- 5. TẠO BẢNG LOẠI HÀNG (Danh mục)
CREATE TABLE loaihang (
                          maloai VARCHAR(50) PRIMARY KEY,
                          tenloai VARCHAR(100)
);

-- 6. TẠO BẢNG nhacungcap
CREATE TABLE nhacungcap (
                            manhacungcap VARCHAR(50) PRIMARY KEY,
                            tennhacungcap VARCHAR(100),
                            loaihinh VARCHAR(50),
                            email VARCHAR(100),
                            sodienthoai VARCHAR(20),
                            diachi VARCHAR(255)
);

-- 7. TẠO BẢNG SẢN PHẨM
CREATE TABLE sanpham (
                         masanpham VARCHAR(50) PRIMARY KEY,
                         tensanpham VARCHAR(100),
                         maloai VARCHAR(50),
                         manhacungcap VARCHAR(50),
                         xuatxu VARCHAR(100),
                         soluong INT DEFAULT 0,
                         ngaysanxuat DATE,
                         hansudung DATE,
                         tinhtrang VARCHAR(50),
                         gianhap DECIMAL(10,0) DEFAULT 0,
                         giaban DECIMAL(10,0) DEFAULT 0,
                         donvitinh VARCHAR(20),
                         FOREIGN KEY (maloai) REFERENCES loaihang(maloai),
                         FOREIGN KEY (manhacungcap) REFERENCES nhacungcap(manhacungcap)
);

-- 9. TẠO BẢNG ĐƠN HÀNG
CREATE TABLE donhang (
                         madonhang VARCHAR(50) PRIMARY KEY,
                         makhachhang VARCHAR(50), -- Khách mua (có thể null nếu khách vãng lai)
                         manhanvien VARCHAR(50),  -- Nhân viên bán đơn này
                         makhuyenmai VARCHAR(50),
                         ngaylap DATETIME DEFAULT CURRENT_TIMESTAMP,
                         phuongthucban VARCHAR(50),-- onl, off
                         thanhtoan VARCHAR(50), -- tiền mặt, chuyển khoản
                         tongtien DECIMAL(10,0) DEFAULT 0,

                         FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
                         FOREIGN KEY (manhanvien) REFERENCES nhanvien(manhanvien),
                         FOREIGN KEY (makhuyenmai) REFERENCES khuyenmai(makhuyenmai)
);

-- 10. TẠO BẢNG CHI TIẾT ĐƠN HÀNG
CREATE TABLE chitietdonhang (
                                madonhang VARCHAR(50),
                                masanpham VARCHAR(50),
                                tensanpham VARCHAR(50),
                                soluong INT,
                                dongia DECIMAL(10,0), -- Giá tại thời điểm bán
                                thanhtien DECIMAL(10,0),

                                FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
                                FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham)
);


-- 11.Tạo bảng lịch sử đổi quà :
CREATE TABLE lichsu_doiqua (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               ma_khachhang VARCHAR(20) NOT NULL,
                               ten_qua VARCHAR(255) NOT NULL,
                               diem_da_doi INT NOT NULL,
                               ngay_doi DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (ma_khachhang) REFERENCES khachhang(makhachhang) ON DELETE CASCADE ON UPDATE CASCADE
);

-- II..Thêm data cho table
-- =========================
-- 1. TÀI KHOẢN
-- =========================
INSERT INTO taikhoan (taikhoan, matkhau) VALUES
    ('admin','admin');

-- =========================
-- 2. CHỨC VỤ
-- =========================
INSERT INTO chucvu (machucvu, tenchucvu) VALUES
                                             ('CV01', 'Quản lý'),
                                             ('CV02', 'Nhân viên bán hàng'),
                                             ('CV03', 'Thu ngân'),
                                             ('CV04', 'Kho'),
                                             ('CV05', 'Kế toán');

-- =========================
-- 3. NHÂN VIÊN
-- =========================
INSERT INTO nhanvien
(manhanvien, tennhanvien, ngaysinh, gioitinh, sodienthoai, email, diachi, machucvu)
VALUES
    ('NV01', 'Hoàng Hải Nam', '1995-03-12', 'Nam', '0901111111', 'nam@gmail.com', 'Hà Nội', 'CV01'),
    ('NV02', 'Nguyễn Thị A', '1997-07-21', 'Nữ', '0902222222', 'a@gmail.com', 'TP HCM', 'CV02'),
    ('NV03', 'Nguyễn Việt Hoàng', '1993-11-05', 'Nam', '0903333333', 'hoang@gmail.com', 'Đà Nẵng', 'CV03'),
    ('NV04', 'Lê Tấn Khang', '1998-02-18', 'Nam', '0904444444', 'khang@gmail.com', 'Hải Phòng', 'CV04'),
    ('NV05', 'Vũ Hùng Hải', '1996-09-30', 'Nam', '0905555555', 'hai@gmail.com', 'Cần Thơ', 'CV05');

-- =========================
-- 4. KHUYẾN MÃI
-- =========================
INSERT INTO khuyenmai
(makhuyenmai, tenkhuyenmai, mota, sotiengiam, ngaytao)
VALUES
    ('KM01', 'Giảm giá 10%', 'Giảm 10% cho tất cả sản phẩm', 10000, '2025-01-01'),
    ('KM02', 'Tết 2025', 'Khuyến mãi dịp Tết Nguyên Đán', 20000, '2025-01-15'),
    ('KM03', 'Khách hàng thân thiết', 'Ưu đãi cho khách hàng VIP', 30000, '2025-02-01'),
    ('KM04', 'Mua 1 tặng 1', 'Áp dụng cho sản phẩm chọn lọc', 50000, '2025-02-10'),
    ('KM05', 'Black Friday', 'Giảm sâu cuối năm', 100000, '2025-11-25');

-- =========================
-- 5. LOẠI HÀNG
-- =========================
INSERT INTO loaihang (maloai, tenloai) VALUES
                                           ('L01', 'Rau củ'),
                                           ('L02', 'Đồ uống'),
                                           ('L03', 'Bánh kẹo');

-- =========================
-- 6. NHÀ CUNG CẤP
-- =========================
INSERT INTO nhacungcap
(manhacungcap, tennhacungcap, loaihinh, email, sodienthoai, diachi)
VALUES
    ('NCC01', 'Vinamilk', 'Doanh nghiệp', 'contact@vinamilk.com', '028123456', 'TP.HCM'),
    ('NCC02', 'Nông trại A', 'Cá nhân', 'nongtrai@gmail.com', '0912345678', 'Đà Lạt');

-- =========================
-- 7. SẢN PHẨM
-- =========================
INSERT INTO sanpham
(masanpham, tensanpham, maloai, manhacungcap, xuatxu, soluong,
 ngaysanxuat, hansudung, tinhtrang, gianhap, giaban, donvitinh)
VALUES
    ('SP01', 'Sữa tươi tiệt trùng', 'L02', 'NCC01', 'Việt Nam', 100,
     '2023-10-01', '2024-04-01', 'Tốt', 25000, 30000, 'Hộp'),
    ('SP02', 'Cà chua Đà Lạt', 'L01', 'NCC02', 'Việt Nam', 50,
     '2023-12-29', '2024-01-05', 'Tốt', 15000, 20000, 'Kg');

-- =========================
-- 8. KHÁCH HÀNG
-- =========================
INSERT INTO khachhang
(makhachhang, tenkhachhang, gioitinh, ngaysinh, diachi, email, sdt, diemtichluy)
VALUES
    ('KH001', 'Nguyễn Đình Chiểu', 'Nam', '1990-01-01', 'Hà Nội', 'nguyendinhchieu@gmail.com', 2312631, 100),
    ('KH002', 'Đặng Trần Tùng', 'Nam', '1995-05-20', 'Đà Nẵng', 'trantung@gmail.com', 123557, 50),
    ('KH003', 'Lê Tuấn Kiệt', 'Nam', '2005-06-09', 'Lạng Sơn', 'ltk@gmail.com', 91326688, 6000);










