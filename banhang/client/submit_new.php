<?php
include "connect.php";
if(isset($_POST['submit_password']) && $_POST['acc_id'])
{
  $email=$_POST['email'];
  $pass=$_POST['password'];
  $acc_id=$_POST['acc_id'];
  $query = 'UPDATE `taikhoan` SET `password`="'.$pass.'" WHERE `acc_id`="'.$acc_id.'"';
  $data = mysqli_query($conn, $query);
  if ($data==true){
    echo "Da doi mat khau thanh cong";
  }
}
?>