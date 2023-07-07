<?php
include "connect.php";
if($_GET['key'] && $_GET['reset'])
{
  $email=$_GET['key'];
  $pass=$_GET['reset'];
  $query = 'SELECT `taikhoan`.`acc_id` FROM `taikhoan` JOIN `khachhang` ON `taikhoan`.`acc_id`=`khachhang`.`acc_id` WHERE `kh_email` = "'.$email.'" AND `password` = "'.$pass.'"'; 
  $data = mysqli_query($conn, $query);

  if($data==true)
  {
    $row = mysqli_fetch_assoc($data);
    $acc_id = $row['acc_id'];
    ?>
    <form method="post" action="submit_new.php">
    <input type="hidden" name="email" value="<?php echo $email;?>">
    <input type="hidden" name="acc_id" value="<?php echo $acc_id;?>">
    <p>Enter New password</p>
    <input type="password" name='password'>
    <input type="submit" name="submit_password">
    </form>
    <?php
  }
}
?>