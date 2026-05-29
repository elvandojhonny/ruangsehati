<?php
include 'koneksi.php';

$id_gedung   = $_POST['id_gedung'];
$nama_ruang  = $_POST['nama_ruang'];
$lokasi     = $_POST['lokasi'];
$kapasitas   = $_POST['kapasitas'];

$query = mysqli_query($koneksi,
"INSERT INTO ruang (id_gedung, nama_ruang,lokasi, kapasitas)
 VALUES ('$id_gedung','$nama_ruang','lokasi','$kapasitas')");

if($query){
    echo json_encode([
        "success" => true,
        "message" => "Ruang berhasil ditambahkan"
    ]);
}else{
    echo json_encode([
        "success" => false,
        "message" => mysqli_error($koneksi)
    ]);
}
?>