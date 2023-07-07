<?php
include 'connect.php';

// Lấy dữ liệu đầu vào từ request
$inputUsername = $_POST['username'];
$inputPhonenumber = $_POST['phonenumber'];
$inputEmail = $_POST['email'];
$inputPassword = $_POST['password'];

//
$sql_1 = "SELECT * FROM admin WHERE admin_username = '$inputUsername' ";
$sql_2 = "SELECT * FROM admin WHERE admin_phonenumber = '$inputPhonenumber' ";
$sql_3 = "SELECT * FROM admin WHERE admin_email = '$inputEmail' ";

$result_1 = $conn->query($sql_1);
$result_2 = $conn->query($sql_2);
$result_3 = $conn->query($sql_3);

if ($result_1->num_rows > 0) {
    echo "Username đã tồn tại.";
    $conn->close();
    return;
} 
if ($result_2->num_rows > 0) {
    echo "Phonenumber đã tồn tại.";
    $conn->close();
    return;
} 

if ($result_3->num_rows > 0) {
    echo "Email đã tồn tại.";
    $conn->close();
    return;
} 

$sql_4 = "INSERT into Admin values ('$inputUsername','$inputPhonenumber','$inputEmail','$inputPassword')";

if ($conn->query($sql_4) === TRUE) {
    echo "Tạo tài khoản thành công";
} else {
    echo "Error";
}

?>
