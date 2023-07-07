<?php
    include "connect.php";
    $username = $_POST['username'];
    $password = $_POST['password'];


    $query = 'SELECT `khachhang`.`kh_id`, `taikhoan`.`username`, `taikhoan`.`password`, `khachhang`.`kh_hoten`, `khachhang`.`kh_sdt`, `khachhang`.`kh_email` FROM `taikhoan` JOIN `khachhang` ON `taikhoan`.`acc_id`=`khachhang`.`acc_id` WHERE `username` = "'.$username.'" AND `password` = "'.$password.'"';
    $data = mysqli_query($conn, $query);
    $result = array();
    $result1 = array();
    while($row = mysqli_fetch_assoc($data)){
        $result[] = ($row);
        $idkh = $row['kh_id'];
    }
    if(!empty($result)){
        $query1 = 'SELECT `sanpham`.`sp_id`, `sanpham`.`sp_tensp`, `sanpham`.`sp_giatien`, `sanpham`.`sp_linkhinhanh`, `giohang`.`gh_soluong` FROM `giohang` JOIN `sanpham` ON `giohang`.`gh_masp`=`sanpham`.`sp_id` WHERE `kh_id` = "'.$idkh.'"';
        $data1 = mysqli_query($conn, $query1);
        while($row1 = mysqli_fetch_assoc($data1)){
            $result1[] = ($row1);
        }
        $arr = [
            'success' => true,
            'message' => "thanh cong",
            'result' => $result,
            'result1' => $result1
        ];

    } else {
        $arr = [
            'success' => false,
            'message' => "khong thanh cong",
            'result' => $result,
            'result1' => $result1
        ];
    }
    print_r(json_encode($arr));
?>