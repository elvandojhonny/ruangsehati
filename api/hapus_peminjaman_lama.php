<?php
include 'koneksi.php';

$query = mysqli_query($koneksi, "
DELETE FROM peminjaman
WHERE CONCAT(tanggal,' ',jam_selesai)
< DATE_SUB(NOW(), INTERVAL 48 HOUR)
");

if($query){
    echo json_encode([
        "success" => true
    ]);
}else{
    echo json_encode([
        "success" => false
    ]);
}
?>