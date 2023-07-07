<?php
    include 'connect.php';

    $query = "SELECT * FROM sanpham";
    $result = mysqli_query($conn,$query);

    $list_sanpham = array();
    while($row = mysqli_fetch_assoc($result)){
        $sanpham = array(
            'id' => $row['sp_id'],
            'tensp' => $row ['sp_tensp'],
            'giatien' => $row['sp_giatien'],
            'cauhinh' => $row['sp_cauhinh'],
            'soluong' => $row['sp_soluong'],
            'linkhinhanh' => $row['sp_linkhinhanh'],
            'loaisp' => $row['sp_loaisp']
        );
        array_push($list_sanpham,$sanpham);
    }

    echo json_encode($list_sanpham);
?>