<?php
include "connect.php";

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';

$email = $_POST['email'];
$query = 'SELECT `khachhang`.`kh_id`, `taikhoan`.`username`, `taikhoan`.`password`, `khachhang`.`kh_hoten`, `khachhang`.`kh_sdt`, `khachhang`.`kh_email` FROM `taikhoan` JOIN `khachhang` ON `taikhoan`.`acc_id`=`khachhang`.`acc_id` WHERE `khachhang`.`kh_email` = "'.$email.'"';
    $data = mysqli_query($conn, $query);
    $result = array();
    while($row = mysqli_fetch_assoc($data)){
        $result[] = ($row);
    }
    if (empty($result)){
        $arr = [
            'success' => false,
            'message' => "Mail khong chinh xac",
            'result' => $result
        ];
    } else{
        // send email
        //print_r($result);
        $email=($result[0]["kh_email"]);
        $pass=($result[0]["password"]);

    $link="<a href='http://192.168.43.28/banhang/client/reset_pass.php?key=".$email."&reset=".$pass."'>Click To Reset password</a>";
    
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;                  
    // GMAIL username
    $mail->Username = "dung321cba@gmail.com";
    // GMAIL password
    $mail->Password = "moffjgbfyuxfusji";
    $mail->SMTPSecure = "ssl";  
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From= 'dung321cba@gmail.com'; //mail nguoi nhan
    $mail->FromName='CuaHangAnKhang';
    $mail->AddAddress($email, 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body    = $link;
    if($mail->Send())
    {
      //echo "Check Your Email and Click on the link sent to your email";
      $arr = [
        'success' => true,
        'message' => "Vui long kiem tra mail cua ban",
        'result' => $result
      ];
    }
    else
    {
        $arr = [
            'success' => false,
            'message' => "Gui mail khong thanh cong",
            'result' => $result
          ];
      //echo "Mail Error - >".$mail->ErrorInfo;
    }
        //$email=md5($row['email']);
        //$pass=md5($row['password']);
    }

    print_r(json_encode($arr));
?>