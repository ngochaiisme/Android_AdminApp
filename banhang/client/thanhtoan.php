<?php
    include "connect.php";
    $kh_id = $_POST['kh_id'];
    $dh_tongtien = $_POST['dh_tongtien'];
    $dh_ngaydat = $_POST['dh_ngaydat'];
    $dh_diachi = $_POST['dh_diachi'];
    $chitiet = $_POST['chitiet'];

    if (!empty($kh_id)){
        $query = 'INSERT INTO `donhang`(`kh_id`, `dh_tongtien`, `dh_ngaydat`, `dh_diachi`, `dh_trangthai`) VALUES ("'.$kh_id.'","'.$dh_tongtien.'","'.$dh_ngaydat.'","'.$dh_diachi.'","Đang xử lý")';
        $data = mysqli_query($conn, $query);
        if($data == true){
            $query1 = 'SELECT `dh_id` FROM `donhang` WHERE `kh_id` = '.$kh_id.' ORDER BY `dh_id` DESC LIMIT 1';
            $data1 = mysqli_query($conn,$query1);

            while($row = mysqli_fetch_assoc($data1)){
                $dh_id = ($row);
            }
            if (!empty($dh_id)){
                //chuyển đổi json sang array
                $chitiet = json_decode($chitiet, true);
                foreach ($chitiet as $key => $value){
                    //tạo mới chi tiết đơn hàng
                    $query2 = 'INSERT INTO `ctdonhang`(`dh_id`, `sp_id`, `sp_soluong`) VALUES ('.$dh_id["dh_id"].','.$value["sp_id"].','.$value["gh_soluong"].')';
                    //cập nhập số lượng từng sản phẩm sau khi thanh toán
                    $query3 = "UPDATE `sanpham` SET `sp_soluong` = `sp_soluong` - ". $value['gh_soluong'] ." WHERE `sp_id` = ". $value['sp_id'];
                    //Xóa các sản phẩm trong giỏ hàng
                    $query4 = "DELETE FROM `giohang` WHERE `kh_id` = '$kh_id'";
                    $data = mysqli_query($conn, $query2);
                    $data1 = mysqli_query($conn, $query3);
                    $data2 = mysqli_query($conn, $query4);
                    if ($data == true && $data1 == true && $data2 == true){
                        $arr = [
                            'success' => true,
                            'mesage' => "thanh cong"
                        ];
                    } else{
                        $arr = [
                            'success' => false,
                            'mesage' => "khong thanh cong"
                        ];
                        exit;
                    }
                }
            }
        } else{
            $arr = [
                'success' => false,
                'message' => "khong thanh cong"
            ];
        }
    }

    
print_r(json_encode($arr));

$conn->close();
?>