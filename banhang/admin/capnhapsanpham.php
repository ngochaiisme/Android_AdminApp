<?php
// Kết nối đến cơ sở dữ liệu
require_once('connect.php');

// Kiểm tra xem có dữ liệu được gửi từ form hay không
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Lấy giá trị từ form
    $id = $_POST['sp_id'];
    $tensp = $_POST['sp_tensp'];
    $giatien = $_POST['sp_giatien'];
    $cauhinh = $_POST['sp_cauhinh'];
    $soluong = $_POST['sp_soluong'];
    $linkhinhanh = $_POST['sp_linkhinhanh'];
    $loaisp = $_POST['sp_loaisp'];

    // Chuẩn bị truy vấn SQL để cập nhật thông tin sản phẩm
    $sql = "UPDATE sanpham SET
            sp_tensp = '$tensp',
            sp_giatien = '$giatien',
            sp_cauhinh = '$cauhinh',
            sp_soluong = '$soluong',
            sp_linkhinhanh = '$linkhinhanh',
            sp_loaisp = '$loaisp'
            WHERE sp_id = $id";

    // Thực thi truy vấn
    if (mysqli_query($conn, $sql)) {
        echo "Cập nhật sản phẩm thành công";
    } else {
        echo "Lỗi: " . mysqli_error($conn);
    }

    // Đóng kết nối đến cơ sở dữ liệu
    mysqli_close($conn);
} else {
    echo "Lỗi: Phương thức không hợp lệ";
}
?>
