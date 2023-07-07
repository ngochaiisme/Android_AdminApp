<?php
    include "connect.php";
    $kh_id = $_POST['kh_id'];
    $query = "SELECT * FROM `donhang` WHERE `donhang`.`kh_id`=$kh_id";
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