<?php

include 'koneksi.php';

$user_id = $_POST['user_id'];
$token   = $_POST['token'];

$query = mysqli_query($koneksi,
    "UPDATE users
     SET fcm_token='$token'
     WHERE id='$user_id'"
);

if ($query) {

    echo json_encode([
        "success" => true
    ]);

} else {

    echo json_encode([
        "success" => false
    ]);
}
?>