<?php
include 'connect.php';

// Lấy dữ liệu đầu vào từ request
$inputUsername = $_POST['username'];
$inputPassword = $_POST['password'];

//
$sql = "SELECT * FROM admin WHERE admin_username = '$inputUsername' AND admin_password = '$inputPassword' ";

$result = $conn->query($sql);


if ($result->num_rows > 0) {
    echo "success";
} 
else{
    echo "fail";
}

$conn->close();


?>
