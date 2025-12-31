-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 30, 2025 lúc 03:35 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlysieuthi`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaihang`
--

CREATE TABLE `loaihang` (
  `maloai` varchar(50) NOT NULL,
  `tenloai` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loaihang`
--

INSERT INTO `loaihang` (`maloai`, `tenloai`) VALUES
('L01', 'Rau củ'),
('L02', 'Đồ uống'),
('L03', 'Bánh kẹo');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `manhacungcap` varchar(50) NOT NULL,
  `tennhacungcap` varchar(100) DEFAULT NULL,
  `loaihinh` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sodienthoai` varchar(20) DEFAULT NULL,
  `diachi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`manhacungcap`, `tennhacungcap`, `loaihinh`, `email`, `sodienthoai`, `diachi`) VALUES
('NCC01', 'Vinamilk', 'Doanh nghiệp', 'contact@vinamilk.com', '028123456', 'TP.HCM'),
('NCC02', 'Nông trại A', 'Cá nhân', 'nongtrai@gmail.com', '0912345678', 'Đà Lạt');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `manhanvien` varchar(50) NOT NULL,
  `tennhanvien` varchar(50) DEFAULT NULL,
  `ngaysinh` date DEFAULT NULL,
  `gioitinh` varchar(10) DEFAULT NULL,
  `diachi` varchar(255) DEFAULT NULL,
  `sodienthoai` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`manhanvien`, `tennhanvien`, `ngaysinh`, `gioitinh`, `diachi`, `sodienthoai`) VALUES
('NV01', 'Nguyễn Văn A', '1990-05-15', 'Nam', 'Quận 1, TP.HCM', '0909123456'),
('NV02', 'Hoàng Hải Nam', '1995-08-20', 'Nam', 'Bắc Ninh', '0912345678');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `masanpham` varchar(50) NOT NULL,
  `tensanpham` varchar(100) DEFAULT NULL,
  `maloai` varchar(50) DEFAULT NULL,
  `manhacungcap` varchar(50) DEFAULT NULL,
  `xuatxu` varchar(100) DEFAULT NULL,
  `soluong` int(11) DEFAULT 0,
  `ngaysanxuat` date DEFAULT NULL,
  `hansudung` date DEFAULT NULL,
  `tinhtrang` varchar(50) DEFAULT NULL,
  `gianhap` decimal(10,0) DEFAULT 0,
  `giaban` decimal(10,0) DEFAULT 0,
  `donvitinh` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`masanpham`, `tensanpham`, `maloai`, `manhacungcap`, `xuatxu`, `soluong`, `ngaysanxuat`, `hansudung`, `tinhtrang`, `gianhap`, `giaban`, `donvitinh`) VALUES
('SP01', 'Sữa tươi tiệt trùng', 'L02', 'NCC01', 'Việt Nam', 100, '2023-10-01', '2024-04-01', 'Tốt', 25000, 30000, 'Hộp'),
('SP02', 'Cà chua Đà Lạt', 'L01', 'NCC02', 'Việt Nam', 50, '2023-12-29', '2024-01-05', 'Tốt', 15000, 20000, 'Kg');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `loaihang`
--
ALTER TABLE `loaihang`
  ADD PRIMARY KEY (`maloai`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`manhacungcap`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`manhanvien`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`masanpham`),
  ADD KEY `maloai` (`maloai`),
  ADD KEY `manhacungcap` (`manhacungcap`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`maloai`) REFERENCES `loaihang` (`maloai`),
  ADD CONSTRAINT `sanpham_ibfk_2` FOREIGN KEY (`manhacungcap`) REFERENCES `nhacungcap` (`manhacungcap`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
