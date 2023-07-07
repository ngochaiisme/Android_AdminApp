<?php
include 'connect.php';

$ngaybatdau = $_POST['ngaybatdau'];
$ngayketthuc = $_POST['ngayketthuc'];


$query = "  SELECT sanpham.sp_tensp, ctdonhang.sp_soluong
            FROM ctdonhang join sanpham on ctdonhang.sp_id = sanpham.sp_id
            WHERE ctdonhang.dh_id in (
	            SELECT dh_id
                FROM donhang
                WHERE   STR_TO_DATE(dh_ngaydat, '%d/%m/%Y %H:%i:%s')>= '$ngaybatdau'
                        AND STR_TO_DATE(dh_ngaydat,'%d/%m/%Y %H:%i:%s')<= '$ngayketthuc'
                        AND dh_trangthai = 'Đã giao hàng')";
$result = mysqli_query($conn, $query);
$list_result = array();
while ($row = mysqli_fetch_assoc($result)) {
    $rs = array(
        'sp_tensp' => $row['sp_tensp'],
        'sp_soluong' => $row['sp_soluong']
    );
    array_push($list_result, $rs);
}
echo json_encode($list_result);
?>