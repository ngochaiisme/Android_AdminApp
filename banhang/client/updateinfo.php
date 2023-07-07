<?php
    include "connect.php";
    $kh_id = $_POST['kh_id'];
    $username = $_POST['username'];
    $password = $_POST['password'];
    $kh_hoten = $_POST['kh_hoten'];
    $kh_sdt = $_POST['kh_sdt'];
    $kh_email = $_POST['kh_email'];

    // check data
    $query = 'SELECT * FROM `taikhoan` WHERE `acc_id` <> "'.$kh_id.'" AND `username` = "'.$username.'"';
    $data = mysqli_query($conn, $query);
    $numrow = mysqli_num_rows($data);
    $query2 = 'SELECT * FROM `khachhang` WHERE `kh_id` <> "'.$kh_id.'" AND `kh_email` = "'.$kh_email.'"';
    $data1 = mysqli_query($conn, $query2);
    $numrow1 = mysqli_num_rows($data1);

    if ($numrow > 0){
        $arr = [
            'success' => false,
            'message' => "Username đa ton tai"
        ];
    } else if($numrow1 > 0){
        $arr = [
            'success' => false,
            'message' => "Email đa ton tai"
        ];
    } else{
        // insert
        $query1 = "UPDATE `khachhang` SET `kh_hoten`= '$kh_hoten',`kh_sdt`='$kh_sdt',`kh_email`= '$kh_email' WHERE `kh_id`='$kh_id'";
        if (mysqli_query($conn, $query1)) {
            $acc_id = $kh_id; // Lấy giá trị auto-increment của acc_id vừa được tạo ra
        } else {
            $arr = [
                'success' => false,
                'message' => "Khong the cap nhat"
            ];
            print_r(json_encode($arr));
            exit;
        }

        // Thực hiện câu lệnh insert vào bảng khachhang
        $sql = "UPDATE `taikhoan` SET `username`= '$username',`password`= '$password' WHERE `acc_id`= '$acc_id'";
        if (mysqli_query($conn, $sql)) {
            $arr = [
                'success' => true,
                'message' => "Cap nhat thanh cong"
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "khong the cap nhat"
            ];
        }
    }
print_r(json_encode($arr));

$conn->close();
?>