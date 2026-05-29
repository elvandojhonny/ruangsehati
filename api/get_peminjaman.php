<?php
include 'koneksi.php';

$data = array();

$query = mysqli_query($koneksi, "SELECT * FROM peminjaman ORDER BY id DESC");

while ($row = mysqli_fetch_assoc($query)) {
    $data[] = $row;
}

echo json_encode($data);
?>