<?php
    include "connect.php";
    $username = $_POST['username'];
    $password = $_POST['password'];
    $kh_hoten = $_POST['kh_hoten'];
    $kh_sdt = $_POST['kh_sdt'];
    $kh_email = $_POST['kh_email'];

    // check data
    $query = 'SELECT * FROM `taikhoan` WHERE `username` = "'.$username.'"';
    $data = mysqli_query($conn, $query);
    $numrow = mysqli_num_rows($data);
    if ($numrow > 0){
        $arr = [
            'success' => false,
            'message' => "Username đa ton tai"
        ];
    } else{
        // insert
        $query1 = "INSERT INTO taikhoan (username, password, type_user) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($query1);
        $type_user = 1;
        $stmt->bind_param("ssi", $username, $password, $type_user);
        if ($stmt->execute() === TRUE) {
            $last_id = $conn->insert_id; // Lấy giá trị auto-increment của acc_id vừa được tạo ra
        } else {
            $arr = [
                'success' => false,
                'message' => "Khong the tao moi"
            ];
            print_r(json_encode($arr));
            exit;
        }

        // Thực hiện câu lệnh insert vào bảng khachhang
        $sql = "INSERT INTO khachhang (kh_hoten, kh_sdt, kh_email, acc_id) VALUES (?, ?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sssi", $kh_hoten, $kh_sdt, $kh_email, $last_id);
        if ($stmt->execute() === TRUE) {
            $arr = [
                'success' => true,
                'message' => "Them moi thanh cong"
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "khong the them moi"
            ];
        }
    }
print_r(json_encode($arr));

$conn->close();
?>