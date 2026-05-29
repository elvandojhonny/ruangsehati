<?php

include 'koneksi.php';

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

// HASH PASSWORD
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// cek username sudah ada atau belum
$cek = mysqli_query(
    $koneksi,
    "SELECT * FROM users WHERE username='$username'"
);

if (mysqli_num_rows($cek) > 0) {

    echo json_encode([
        "success" => false,
        "message" => "Username sudah digunakan"
    ]);

    exit;
}

$query = mysqli_query($koneksi, "INSERT INTO users(

    nama,
    nim,
    email,
    prodi,
    fakultas,
    no_hp,
    alamat,

    username,
    password,
    role

) VALUES (

    '$nama',
    '$nim',
    '$email',
    '$prodi',
    '$fakultas',
    '$no_hp',
    '$alamat',

    '$username',
    '$hashedPassword',
    '$role'
)");

if ($query) {

    echo json_encode([
        "success" => true,
        "message" => "User berhasil ditambahkan"
    ]);

} else {

    echo json_encode([
        "success" => false,
        "message" => "User gagal ditambahkan"
    ]);
}
?>