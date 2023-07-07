<?php
    include "connect.php";
    $sp_id = $_POST['sp_id'];
    if (empty($sp_id)){
        $arr = [
            'success' => false,
            'message' => "khong thanh cong"
        ];
    }else {
        $query = "SELECT * FROM `sanpham` WHERE `sp_id` = '".$sp_id."'";
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
    }
    
    print_r(json_encode($arr));
?>