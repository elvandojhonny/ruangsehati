<?php
include 'koneksi.php';

$nama_gedung = $_POST['nama_gedung'];
$fakultas = $_POST['fakultas'];

$query = mysqli_query($koneksi,
    "INSERT INTO gedung (nama_gedung, fakultas)
     VALUES ('$nama_gedung', '$fakultas')");

if($query){
    echo json_encode([
        "success" => true,
        "message" => "Gedung berhasil ditambahkan"
    ]);
}else{
    echo json_encode([
        "success" => false,
        "message" => mysqli_error($koneksi)
    ]);
}
?>