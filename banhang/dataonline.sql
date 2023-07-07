-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 07, 2023 lúc 06:40 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dataonline`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admin`
--

CREATE TABLE `admin` (
  `admin_username` varchar(20) NOT NULL,
  `admin_phonenumber` varchar(20) NOT NULL,
  `admin_email` varchar(50) NOT NULL,
  `admin_password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `admin`
--

INSERT INTO `admin` (`admin_username`, `admin_phonenumber`, `admin_email`, `admin_password`) VALUES
('abc', '0123456789', 'abc@gmail.com', 'abc'),
('abc2', '01234567892', 'abc@gmail.com2', 'abc2'),
('', '', '', ''),
('1234', '2314', '2134', '1234'),
('fgdfgd', 'dfgsdg', 'fgdfg', 'sdfgdsfg'),
('342134', 'qerwqer', 'rqwerqwer', 'qerwqerq'),
('342134erwer', 'qerwqerqwerererqwer', 'rqwerqwerqerwer', 'qerwqerqqwerqerqwerq'),
('fff', 'fff', 'fff', 'fff'),
('ffff', 'ffff', 'ffff', 'fff'),
('kkk', 'kkk', 'kkk', 'kkk'),
('12345', '1234', '1234@gmail.com', '234'),
('hhh', '1234321', 'hhh@gmail.com', 'hhh'),
('1234567', '123', '13241234@gmail.com', '333'),
('nhan', '0387870932', 'nhannguyen@gmail.com', 'zzz'),
('lll', '12345678987', 'lll@gmail.com', 'lll'),
('admin002', '0999999999', 'admin@gmail.com', 'admin'),
('admin', '0911385806', 'dngochai0511@gmail.com', 'admin');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctdonhang`
--

CREATE TABLE `ctdonhang` (
  `dh_id` int(11) NOT NULL,
  `sp_id` int(11) NOT NULL,
  `sp_soluong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctdonhang`
--

INSERT INTO `ctdonhang` (`dh_id`, `sp_id`, `sp_soluong`) VALUES
(16, 2, 5),
(16, 5, 9),
(16, 8, 8),
(18, 2, 3),
(19, 4, 5),
(20, 6, 10),
(21, 1, 1),
(22, 1, 4),
(23, 8, 4),
(24, 3, 7),
(25, 3, 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `dh_id` int(11) NOT NULL,
  `kh_id` int(11) NOT NULL,
  `dh_tongtien` varchar(50) NOT NULL,
  `dh_diachi` varchar(200) NOT NULL,
  `dh_trangthai` varchar(50) NOT NULL,
  `dh_ngaydat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`dh_id`, `kh_id`, `dh_tongtien`, `dh_diachi`, `dh_trangthai`, `dh_ngaydat`) VALUES
(16, 1, '490410000', 'KTX khu A', 'Đã giao hàng', '13/06/2023 09:18:21'),
(18, 13, '3000000', 'as', 'Đã giao hàng', '14/06/2023 09:00:48'),
(19, 14, '20950000', 'KTX', 'Đã giao hàng', '01/07/2023 09:25:59'),
(24, 14, '188930000', 'ktx', 'Đã giao hàng', '02/07/2023 07:34:17');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giohang`
--

CREATE TABLE `giohang` (
  `gh_id` int(11) NOT NULL,
  `kh_id` int(11) NOT NULL,
  `gh_masp` int(11) NOT NULL,
  `gh_soluong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `giohang`
--

INSERT INTO `giohang` (`gh_id`, `kh_id`, `gh_masp`, `gh_soluong`) VALUES
(4, 10, 1, 5),
(8, 1, 2, 5),
(9, 1, 5, 9),
(10, 1, 8, 8),
(12, 13, 2, 3),
(21, 0, 1, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `kh_id` int(11) NOT NULL,
  `kh_hoten` varchar(255) NOT NULL,
  `kh_sdt` varchar(10) NOT NULL,
  `kh_email` varchar(255) NOT NULL,
  `acc_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`kh_id`, `kh_hoten`, `kh_sdt`, `kh_email`, `acc_id`) VALUES
(1, 'abc123', 'kkkabc', 'abc@gmail.com', 1),
(2, 'nnn', '123', 'abccc', 2),
(3, 'a', '1', 'aa', 3),
(4, 'n', '1', 'f', 4),
(5, 'nn', '356352', 'gmail.com', 5),
(6, 'a', '1', 'a', 6),
(7, 'dung', '094636533', 'dung@gmail.com', 7),
(8, 'thanhnga', '983626252', 'nga@gmail.com', 8),
(9, 'hoang', '892626633', 'hoang@gmail.com', 9),
(10, 'hung123', '758736399', 'hung@gmalil', 10),
(11, 'Dương Ngọc Châu', '123', '123@gmail.com', 11),
(13, 'Duong Ngoc Hai', '0911385806', 'ngochaiisme12345@gmail.com', 12),
(14, 'Duong Ngoc Hai', '0123456789', 'dngochai0511@gmail.com', 13),
(15, 'abc', '0911373434', 'a@gmail.com', 14);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `sp_id` int(11) NOT NULL,
  `sp_tensp` varchar(255) NOT NULL,
  `sp_giatien` double NOT NULL,
  `sp_cauhinh` text NOT NULL,
  `sp_soluong` int(11) NOT NULL,
  `sp_linkhinhanh` text NOT NULL,
  `sp_loaisp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`sp_id`, `sp_tensp`, `sp_giatien`, `sp_cauhinh`, `sp_soluong`, `sp_linkhinhanh`, `sp_loaisp`) VALUES
(1, 'Điện thoại Vivo Y35', 6500000, 'Màn hình: IPS LCD6.58\"Full HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 50 MP & Phụ 2 MP, 2 MP\r\nCamera trước: 16 MP\r\nChip: Snapdragon 680\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM Hỗ trợ 4G\r\nPin, Sạc: 5000 mAh44 W', 2, 'https://firebasestorage.googleapis.com/v0/b/fir-storage-1-c3c14.appspot.com/o/29_06_2023__15_31_33?alt=media&token=8357a44b-0b4d-46a1-9601-96ad58ebd499', 0),
(2, 'Laptop Apple MacBook Air M1', 1000000, 'CPU: Apple M1\r\nRAM: 8 GB\r\nỔ cứng: 256 GB SSD\r\nMàn hình: 13.3\"Retina (2560 x 1600)\r\nCard màn hình: Card tích hợp7 nhân GPU\r\nCổng kết nối: Jack tai nghe 3.5 mm2 x Thunderbolt 3 (USB-C)\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Mac OS\r\nThiết kế: Vỏ kim loại nguyên khối\r\nKích thước, khối lượng: Dài 304.1 mm - Rộng 212.4 mm - Dày 4.1 mm đến 16.1 mm - Nặng 1.29 kg', 7, 'https://firebasestorage.googleapis.com/v0/b/fir-storage-1-c3c14.appspot.com/o/06_06_2023__20_41_59?alt=media&token=6aa8722c-49eb-4e37-930b-31dcc00ce05b', 0),
(3, 'iPhone 14 512GB cau hinh sieu xin sieu cap vip pri', 26990000, 'Màn hình: OLED6.1\"Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 512 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3279 mAh20 W', 0, 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289670/iPhone-14-thumb-do-600x600.jpg', 1),
(4, 'Laptop itel ABLE 1S N4020/4GB/256GB/Win11', 4190000, 'CPU: CeleronN40201.1GHz\r\nRAM: 4 GBDDR4 (Onboard)2400 MHz\r\nỔ cứng: 256 GB SSD M.2 SATA 3 (Có thể tháo ra lắp thanh khác tối đa 1 TB)Hỗ trợ thêm 1 khe cắm HDD SATA (nâng cấp tối đa 1 TB)\r\nMàn hình: 14\"HD (1366 x 768)\r\nCard màn hình: Card tích hợp Intel UHD 600\r\nCổng kết nối: 2 x USB 3.0HDMILAN (RJ45)Jack tai nghe 3.5 mm\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 336.2 mm - Rộng 223 mm - Dày 19.9 mm - Nặng 1.4 kg\r\nThời điểm ra mắt: 2021', 95, 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/44/265523/TimerThumb/itel-able-1s-n4020-71006300027-(16).jpg', 0),
(5, 'Điện thoại Samsung Galaxy A34 5G 256GB', 9490000, 'Màn hình: Super AMOLED6.6\"Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 48 MP & Phụ 8 MP, 5 MP\r\nCamera trước: 13 MP\r\nChip: MediaTek Dimensity 1080 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh25 W', 2, 'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/298377/samsung-galaxy-a34-5g-bac-thumb-600x600.jpg', 1),
(6, 'Laptop HP 15s fq5078TU i5 1235U/8GB/512GB/Win11', 15490000, 'CPU: i51235U1.3GHz\r\nRAM: 8 GBDDR4 2 khe (1 khe 4 GB + 1 khe 4 GB)3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe\r\nMàn hình: 15.6\"Full HD (1920 x 1080)\r\nCard màn hình: Card tích hợp Intel Iris Xe\r\nCổng kết nối: HDMIUSB Type-CJack tai nghe 3.5 mm2 x USB 3.2\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 358.5 mm - Rộng 242 mm - Dày 17.9 mm - Nặng 1.69 kg\r\nThời điểm ra mắt: 2022', 10, 'https://cdn.tgdd.vn/Products/Images/44/284139/TimerThumb/hp-15s-fq5078tu-i5-6k798pa-(10).jpg', 0),
(7, 'Điện thoại Xiaomi Redmi 12C 64GB', 2890000, 'Màn hình: IPS LCD6.71\"HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 50 MP & Phụ QVGA\r\nCamera trước: 5 MP\r\nChip: MediaTek Helio G85\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nSIM: 2 Nano SIMHỗ trợ 4G\r\nPin, Sạc: 5000 mAh10 W', 2, 'https://cdn.tgdd.vn/Products/Images/42/303575/xiaomi-redmi-12c-grey-thumb-600x600.jpg', 1),
(8, 'Laptop Apple MacBook Pro 16 M1 Pro 2021 10', 50000000, 'CPU: Apple M1 Pro200GB/s\r\nRAM: 16 GB\r\nỔ cứng: 512 GB SSD\r\nMàn hình: 16.2\"Liquid Retina XDR display (3456 x 2234)120Hz\r\nCard màn hình: Card tích hợp16 nhân GPU\r\nCổng kết nối: HDMIJack tai nghe 3.5 mm3 x Thunderbolt 4 USB-C\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Mac OS\r\nThiết kế: Vỏ kim loại nguyên khối\r\nKích thước, khối lượng: Dài 355.7 mm - Rộng 248.1 mm - Dày 16.8 mm - Nặng 2.1 kg\r\nThời điểm ra mắt: 10/2021', 6, 'https://cdn.tgdd.vn/Products/Images/44/282885/TimerThumb/apple-macbook-pro-m2-2022-(24).jpg', 0),
(43, 'XiaoMe', 550000, 'Ram 16GB', 30, 'https://firebasestorage.googleapis.com/v0/b/fir-storage-1-c3c14.appspot.com/o/29_05_2023__22_41_05?alt=media&token=7fa9b53a-dad5-4996-ad90-4037c0af5821', 1),
(48, 'san pham moi', 5000000, 'Cau hinh rat tot', 10, 'https://firebasestorage.googleapis.com/v0/b/fir-storage-1-c3c14.appspot.com/o/03_07_2023__10_01_10?alt=media&token=99e826e0-94c8-409e-aa7d-7aaa5fd8dcee', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `acc_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `type_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`acc_id`, `username`, `password`, `type_user`) VALUES
(1, 'nguyentiennhan', 'nguyentiennhan', 1),
(2, 'abdgd', '123', 1),
(3, 'Rose', '1', 1),
(4, 'd', '1', 1),
(5, 'nn', '123', 1),
(6, 'abcd', '1', 1),
(7, 'dung123', '123', 1),
(8, 'nga', '123', 1),
(9, 'hoang', '123', 1),
(10, 'hung123', '123', 1),
(11, 'dnh', '123', 1),
(12, 'dnh123', 'dnh', 1),
(13, 'duongngochai', '123', 1),
(14, '123', '123', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`dh_id`);

--
-- Chỉ mục cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`gh_id`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`kh_id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`sp_id`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`acc_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `dh_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT cho bảng `giohang`
--
ALTER TABLE `giohang`
  MODIFY `gh_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `kh_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `sp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `acc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `fk_gh_sp` FOREIGN KEY (`gh_masp`) REFERENCES `sanpham` (`sp_id`);

--
-- Các ràng buộc cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD CONSTRAINT `fk_kh_acc` FOREIGN KEY (`acc_id`) REFERENCES `taikhoan` (`acc_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
