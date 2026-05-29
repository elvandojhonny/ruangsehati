<?php

include 'koneksi.php';

$user_id = $_GET['user_id'];

$data = array();

$query = mysqli_query(
    $koneksi,
    "SELECT
        p.*,
        g.nama_gedung
    FROM peminjaman p

    LEFT JOIN ruang r
    ON p.nama_ruang = r.nama_ruang

    LEFT JOIN gedung g
    ON r.id_gedung = g.id

    WHERE p.user_id = '$user_id'

    ORDER BY p.id DESC"
);

while ($row = mysqli_fetch_assoc($query)) {

    $data[] = array(
        "id" => $row['id'],
        "user_id" => $row['user_id'],
        "nama_peminjam" => $row['nama_peminjam'],
        "nama_ruang" => $row['nama_ruang'],
        "nama_gedung" => $row['nama_gedung'],
        "tanggal" => $row['tanggal'],
        "hari" => $row['hari'],
        "jam_mulai" => $row['jam_mulai'],
        "jam_selesai" => $row['jam_selesai'],
        "keterangan" => $row['keterangan'],
        "status" => $row['status'],
        "alasan_ditolak" => $row['alasan_ditolak']
    );
}

echo json_encode($data);

?>