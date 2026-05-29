<?php
include 'koneksi.php';

$id = $_POST['id'];

$query = mysqli_query($koneksi, "DELETE FROM ruang WHERE id='$id'");

if ($query) {
    echo json_encode([
        "success" => true,
        "message" => "Ruang berhasil dihapus"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Gagal hapus ruang"
    ]);
}
?>