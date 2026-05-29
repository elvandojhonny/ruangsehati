<?php

include 'koneksi.php';

$email = $_POST['email'];
$password = $_POST['password'];

$query = mysqli_query(
    $koneksi,
    "SELECT * FROM users WHERE email='$email'"
);

if (mysqli_num_rows($query) > 0) {

    $data = mysqli_fetch_assoc($query);

    if (password_verify($password, $data['password'])) {

        echo json_encode([

            "success" => true,
            "message" => "Login berhasil",

            "id" => $data['id'],

            "nama" => $data['nama'],
            "nim" => $data['nim'],
            "email" => $data['email'],
            "prodi" => $data['prodi'],
            "fakultas" => $data['fakultas'],
            "no_hp" => $data['no_hp'],
            "alamat" => $data['alamat'],

            "username" => $data['username'],
            "role" => $data['role']

        ]);

    } else {

        echo json_encode([

            "success" => false,
            "message" => "Password salah"

        ]);
    }

} else {

    echo json_encode([

        "success" => false,
        "message" => "Email tidak ditemukan"

    ]);
}
?>