<?php
    include('connect.php');

    $id = $_POST['dh_id'];
    $trangthaimoi = $_POST['dh_trangthaimoi'];

    $sql = "UPDATE donhang SET
    dh_trangthai = '$trangthaimoi'
    WHERE dh_id = '$id'";

    if (mysqli_query($conn, $sql)) {
        echo "success";
    } else {
        echo "failure";
    }
    mysqli_close($conn);
?>