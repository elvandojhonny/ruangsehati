<?php
header("Content-Type: application/json");
include 'koneksi.php';

$id = $_POST['id'] ?? '';

if ($id == '') {
    echo json_encode([
        "success" => false,
        "message" => "ID kosong"
    ]);
    exit;
}

$query = mysqli_query($koneksi, "DELETE FROM users WHERE id='$id'");

if ($query && mysqli_affected_rows($koneksi) > 0) {
    echo json_encode([
        "success" => true,
        "message" => "User berhasil dihapus"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "User tidak ditemukan / gagal dihapus"
    ]);
}
?>