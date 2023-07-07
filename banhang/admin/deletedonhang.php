<?php
    // Kết nối đến cơ sở dữ liệu
    include 'connect.php';

    // Lấy giá trị của path parameter 'id'
    $id = $_GET['id'];

    // Xóa sản phẩm có id tương ứng trong cơ sở dữ liệu
    $query = "DELETE FROM donhang WHERE dh_id = '$id'";
    $result = mysqli_query($conn, $query);

    // Kiểm tra và trả về kết quả xóa sản phẩm
    if($result) {
        $response['status'] = true;
        $response['message'] = "Xóa sản phẩm thành công";
    } else {
        $response['status'] = false;
        $response['message'] = "Xóa sản phẩm thất bại";
    }
    echo json_encode($response);
?>