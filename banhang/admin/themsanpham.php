<?php
// Kết nối đến cơ sở dữ liệu
require_once('connect.php');

// Kiểm tra xem có dữ liệu được gửi từ form hay không
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Lấy giá trị từ form
    $tensp = $_POST['sp_tensp'];
    $giatien = $_POST['sp_giatien'];
    $cauhinh = $_POST['sp_cauhinh'];
    $soluong = $_POST['sp_soluong'];
    $linkhinhanh = $_POST['sp_linkhinhanh'];
    $loaisp = $_POST['sp_loaisp'];

    // Chuẩn bị truy vấn SQL để chèn dữ liệu vào bảng sanpham
    $sql = "INSERT INTO sanpham (sp_tensp, sp_giatien, sp_cauhinh, sp_soluong, sp_linkhinhanh, sp_loaisp) 
            VALUES ('$tensp', '$giatien', '$cauhinh', '$soluong', '$linkhinhanh', '$loaisp')";

    // Thực thi truy vấn
    if (mysqli_query($conn, $sql)) {
        echo "Thêm sản phẩm thành công";
    } else {
        echo "Lỗi: " . mysqli_error($conn);
    }

    // Đóng kết nối đến cơ sở dữ liệu
    mysqli_close($conn);
}else{
    echo "Lỗi: " . mysqli_error($conn);
}

?>