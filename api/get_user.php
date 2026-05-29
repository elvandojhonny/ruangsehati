<?php

include 'koneksi.php';

$data = mysqli_query($koneksi, "SELECT * FROM users ORDER BY id DESC");

$result = array();

while ($row = mysqli_fetch_assoc($data)) {

    $result[] = [

        "id"        => $row['id'],
        "nama"      => $row['nama'],
        "nim"       => $row['nim'],
        "email"     => $row['email'],
        "prodi"     => $row['prodi'],
        "fakultas"  => $row['fakultas'],
        "no_hp"     => $row['no_hp'],
        "alamat"    => $row['alamat'],

        "username"  => $row['username'],
        "password"  => $row['password'],
        "role"      => $row['role']
    ];
}

echo json_encode($result);

?>