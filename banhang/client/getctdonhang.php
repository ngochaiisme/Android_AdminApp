<?php
    include "connect.php";
    $dh_id = $_POST['dh_id'];
    $query = "SELECT `sanpham`.`sp_tensp`, `sanpham`.`sp_giatien`, `sanpham`.`sp_linkhinhanh`, `ctdonhang`.`sp_soluong` FROM `ctdonhang` JOIN `sanpham` ON `ctdonhang`.`sp_id`=`sanpham`.`sp_id` WHERE `ctdonhang`.`dh_id`=$dh_id";
    $data = mysqli_query($conn, $query);
    $result = array();
    while($row = mysqli_fetch_assoc($data)){
        $result[] = ($row);
    }
    if(!empty($result)){
        $arr = [
            'success' => true,
            'message' => "thanh cong",
            'result' => $result
        ];

    } else {
        $arr = [
            'success' => false,
            'message' => "khong thanh cong",
            'result' => $result
        ];
    }
    print_r(json_encode($arr));
?>