<?php
    include('connect.php');

    // Kiểm tra xem các trường đã được gửi đến chưa
    if (isset($_POST['username']) && isset($_POST['newpassword'])) {
        // Sử dụng câu lệnh chuẩn bị để tránh SQL injection
        $sql = "UPDATE admin SET admin_password = ? WHERE admin_username = ?";
        
        // Chuẩn bị câu lệnh SQL
        $stmt = mysqli_prepare($conn, $sql);

        // Kiểm tra lỗi khi chuẩn bị câu lệnh SQL
        if ($stmt) {
            // Gán giá trị vào các tham số
            mysqli_stmt_bind_param($stmt, "ss", $_POST['newpassword'], $_POST['username']);
            
            // Thực thi câu lệnh SQL
            if (mysqli_stmt_execute($stmt)) {
                echo "success";
            } else {
                echo "failure";
            }
            
            // Đóng câu lệnh chuẩn bị
            mysqli_stmt_close($stmt);
        } else {
            echo "failure";
        }
    } else {
        echo "failure";
    }
    
    // Đóng kết nối
    mysqli_close($conn);
?>
