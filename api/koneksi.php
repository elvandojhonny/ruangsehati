<?php
$koneksi = mysqli_connect("localhost", "root", "", "db_peminjaman_ruang");

if (!$koneksi) {
    die("Koneksi gagal: " . mysqli_connect_error());
}
?>