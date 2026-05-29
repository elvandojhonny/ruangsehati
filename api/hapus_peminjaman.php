<?php
include 'koneksi.php';

$id = $_POST['id'];

$query = mysqli_query($koneksi, "DELETE FROM peminjaman WHERE id='$id'");

if ($query) {
    echo json_encode([
        "success" => true,
        "message" => "Riwayat berhasil dihapus"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Riwayat gagal dihapus"
    ]);
}
?>