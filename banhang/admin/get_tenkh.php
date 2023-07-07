<?php
// Kết nối đến cơ sở dữ liệu
include 'connect.php';

// Kiểm tra xem id có được truyền vào không
if (isset($_GET['id'])) {
    $id = $_GET['id'];

    // Chuẩn bị câu truy vấn
    $query = "SELECT kh_hoten FROM khachhang WHERE kh_id = $id";

    // Thực thi câu truy vấn
    $result = $conn->query($query);

    // Kiểm tra xem có kết quả hay không
    if ($result->num_rows > 0) {
        // Lấy dòng dữ liệu đầu tiên
        $row = $result->fetch_assoc();
        $kh_hoten = $row["kh_hoten"];

        // Trả về kết quả
        echo $kh_hoten;
    } else {
        // Không tìm thấy dữ liệu
        echo "Không tìm thấy dữ liệu";
    }
} else {
    // Id không được truyền vào
    echo "Vui lòng cung cấp id";
}

// Đóng kết nối
$conn->close();
?>

