<?php

header("Content-Type: application/json");

include 'koneksi.php';

$user_id       = $_POST['user_id'];
$nama_peminjam = $_POST['nama_peminjam'];
$nama_gedung   = $_POST['nama_gedung'];
$nama_ruang    = $_POST['nama_ruang'];
$tanggal       = $_POST['tanggal'];
$hari          = $_POST['hari'];
$jam_mulai     = $_POST['jam_mulai'];
$jam_selesai   = $_POST['jam_selesai'];
$keterangan    = $_POST['keterangan'];

$query = mysqli_query(
    $koneksi,
    "INSERT INTO peminjaman
    (
        user_id,
        nama_peminjam,
        nama_gedung,
        nama_ruang,
        tanggal,
        hari,
        jam_mulai,
        jam_selesai,
        keterangan
    )
    VALUES
    (
        '$user_id',
        '$nama_peminjam',
        '$nama_gedung',
        '$nama_ruang',
        '$tanggal',
        '$hari',
        '$jam_mulai',
        '$jam_selesai',
        '$keterangan'
    )"
);

if ($query) {

    // ================= DEBUG NOTIF =================

    try {

        include 'send_notification.php';

        $admin = mysqli_query(
            $koneksi,
            "SELECT fcm_token
             FROM users
             WHERE role='admin'
             LIMIT 1"
        );

        $dataAdmin = mysqli_fetch_assoc($admin);

        if (
            $dataAdmin &&
            !empty($dataAdmin['fcm_token'])
        ) {

            $hasilNotif = sendNotification(
                $dataAdmin['fcm_token'],
                "Peminjaman Baru",
                "$nama_peminjam mengajukan kelas $nama_ruang"
            );

        } else {

            $hasilNotif = "TOKEN ADMIN KOSONG";
        }

    } catch (Exception $e) {

        $hasilNotif = $e->getMessage();
    }

    echo json_encode([

        "success" => true,

        "message" => "Kelas berhasil diajukan",

        "debug_notif" => $hasilNotif
    ]);

} else {

    echo json_encode([

        "success" => false,

        "message" => mysqli_error($koneksi)
    ]);
}
?>