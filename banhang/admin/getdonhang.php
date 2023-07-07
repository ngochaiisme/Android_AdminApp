<?php
    include 'connect.php';

    $query = "SELECT * FROM donhang";
    $result = mysqli_query($conn,$query);

    $list_donhang = array();
    while($row = mysqli_fetch_assoc($result)){
        $donhang = array(
            'dh_id' => $row['dh_id'],
            'kh_id' => $row ['kh_id'],
            'dh_tongtien' => $row['dh_tongtien'],
            'dh_diachi' => $row['dh_diachi'],
            'dh_trangthai' => $row['dh_trangthai'],
            'dh_thoigiandathang' => $row['dh_ngaydat'],
        );
        array_push($list_donhang,$donhang);
    }

    echo json_encode($list_donhang);
?>