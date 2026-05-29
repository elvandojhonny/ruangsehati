<?php
include 'koneksi.php';

$id_gedung = $_GET['id_gedung'];

$data = [];

$query = mysqli_query($koneksi,
"SELECT * FROM ruang WHERE id_gedung='$id_gedung'");

while($row = mysqli_fetch_assoc($query)){
    $data[] = $row;
}

echo json_encode($data);
?>