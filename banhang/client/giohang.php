<?php
    include "connect.php";
    $kh_id = $_POST['kh_id'];
    $gh_masp = $_POST['gh_masp'];
    $gh_soluong = $_POST['gh_soluong'];
    $flag = $_POST['flag'];

    if ($flag == 1){
        $query1 = "INSERT INTO `giohang`(`kh_id`, `gh_masp`, `gh_soluong`) VALUES (?,?,?)";
        $stmt = $conn->prepare($query1);
        $stmt->bind_param("iii", $kh_id, $gh_masp, $gh_soluong);
        if ($stmt->execute() === TRUE) {
            $arr = [
                'success' => true,
                'message' => "Them thanh cong"
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "Khong the tao moi"
            ];
        }
    } else if ($flag == 2){
        $query2 = "UPDATE `giohang` SET `gh_soluong`= '$gh_soluong' WHERE `kh_id`= '$kh_id' AND `gh_masp`= '$gh_masp'";
        if (mysqli_query($conn, $query2)) {
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
    } else{
        $query3 = "DELETE FROM `giohang` WHERE `kh_id` = '$kh_id' AND `gh_masp` = '$gh_masp'";
        if (mysqli_query($conn, $query3)) {
            $arr = [
                'success' => true,
                'message' => "Xoa thanh cong"
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "khong the xoa"
            ];
        }
    }
print_r(json_encode($arr));

$conn->close();
?>