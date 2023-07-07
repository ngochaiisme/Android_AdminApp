<?php
// Kết nối tới cơ sở dữ liệu
include 'connect.php';

// Lấy giá trị dh_id từ phương thức POST
$dh_id = $_POST['dh_id'];

// Truy vấn cơ sở dữ liệu
$sql = "SELECT sanpham.sp_tensp,ctdonhang.sp_soluong, sanpham.sp_giatien, sanpham.sp_linkhinhanh
        FROM ctdonhang join sanpham on ctdonhang.sp_id = sanpham.sp_id
        WHERE ctdonhang.dh_id = $dh_id";

$result = mysqli_query($conn,$sql);

$response = array();

while($row = mysqli_fetch_assoc($result)){
    $item_ctdh = array(
        'sp_tensp' =>$row['sp_tensp'],
        'sp_giatien' => $row['sp_giatien'],
        'sp_soluong' => $row['sp_soluong'],
        'sp_linkhinhanh' => $row['sp_linkhinhanh']
    );
    array_push($response,$item_ctdh);
}
echo json_encode($response)
?>
