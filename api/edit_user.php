<?php

include 'koneksi.php';

$id         = $_POST['id'];

$nama       = $_POST['nama'];
$nim        = $_POST['nim'];
$email      = $_POST['email'];
$prodi      = $_POST['prodi'];
$fakultas   = $_POST['fakultas'];
$no_hp      = $_POST['no_hp'];
$alamat     = $_POST['alamat'];

$username   = $_POST['username'];
$password   = $_POST['password'];
$role       = $_POST['role'];

if ($password != "") {

    // PASSWORD BARU
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    $query = mysqli_query($koneksi, "UPDATE users SET

    nama        = '$nama',
    nim         = '$nim',
    email       = '$email',
    prodi       = '$prodi',
    fakultas    = '$fakultas',
    no_hp       = '$no_hp',
    alamat      = '$alamat',

    username    = '$username',
    password    = '$hashedPassword',
    role        = '$role'

    WHERE id='$id'
    ");

} else {

    // PASSWORD LAMA TETAP
    $query = mysqli_query($koneksi, "UPDATE users SET

    nama        = '$nama',
    nim         = '$nim',
    email       = '$email',
    prodi       = '$prodi',
    fakultas    = '$fakultas',
    no_hp       = '$no_hp',
    alamat      = '$alamat',

    username    = '$username',
    role        = '$role'

    WHERE id='$id'
    ");
}

if ($query) {

    echo json_encode([
        "success" => true,
        "message" => "Berhasil diupdate"
    ]);

} else {

    echo json_encode([
        "success" => false,
        "message" => "Gagal diupdate"
    ]);
}

?>