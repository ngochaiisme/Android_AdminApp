<?php
// Kết nối đến cơ sở dữ liệu
include 'connect.php';

// Kiểm tra xem id có được truyền vào không

    $phone_number = $_POST['phonenumber'];
    // Chuẩn bị câu truy vấn
    $query = "SELECT admin.admin_username FROM admin WHERE admin.admin_phonenumber ='$phone_number'";

    // Thực thi câu truy vấn
    $result = $conn->query($query);

    // Kiểm tra xem có kết quả hay không
    if ($result->num_rows > 0) {
        // Lấy dòng dữ liệu đầu tiên
        $row = $result->fetch_assoc();
        $kh_hoten = $row["admin_username"];
        // Trả về kết quả
        echo $kh_hoten;
    } else {
        // Không tìm thấy dữ liệu
        echo "Không tìm thấy dữ liệu";
    }

// Đóng kết nối
$conn->close();
?>

