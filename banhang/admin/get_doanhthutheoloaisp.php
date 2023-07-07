<?php
include 'connect.php';

$ngaybatdau = $_POST['ngaybatdau'];
$ngayketthuc = $_POST['ngayketthuc'];

$query = "  SELECT sanpham.sp_loaisp,SUM(sanpham.sp_giatien*ctdonhang.sp_soluong) as doanhthu
                FROM sanpham join ctdonhang on sanpham.sp_id = ctdonhang.sp_id
                WHERE ctdonhang.dh_id in (
                        SELECT dh_id
                        from donhang
                         WHERE STR_TO_DATE(dh_ngaydat, '%d/%m/%Y %H:%i:%s')>= '$ngaybatdau'
                            AND STR_TO_DATE(dh_ngaydat,'%d/%m/%Y %H:%i:%s')<='$ngayketthuc'
                            AND dh_trangthai = 'Đã giao hàng')
                Group by sanpham.sp_loaisp";

$result = mysqli_query($conn, $query);
$list_result = array();
while ($row = mysqli_fetch_assoc($result)) {
    $rs = array(
        'loaisp' => $row['sp_loaisp'],
        'doanhthu' => $row['doanhthu']

    );
    array_push($list_result, $rs);
}
echo json_encode($list_result);
?>