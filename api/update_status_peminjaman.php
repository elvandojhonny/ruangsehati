<?php

include 'koneksi.php';
include 'send_notification.php';

$id = $_POST['id'];
$status = $_POST['status'];
$alasan_ditolak = $_POST['alasan_ditolak'];


// ==========================
// AMBIL DATA PEMINJAMAN
// ==========================

$get = mysqli_query(
    $koneksi,
    "SELECT * FROM peminjaman WHERE id='$id'"
);

$data = mysqli_fetch_assoc($get);

$nama_ruang  = trim($data['nama_ruang']);
$tanggal     = trim($data['tanggal']);
$jam_mulai   = trim($data['jam_mulai']);
$jam_selesai = trim($data['jam_selesai']);
$user_id     = trim($data['user_id']);


// =====================================================
// CEK BENTROK JIKA STATUS = DISETUJUI
// =====================================================

if (strtolower($status) == "disetujui") {

    $cek = mysqli_query($koneksi, "
        SELECT *
        FROM peminjaman
        WHERE

            id != '$id'

            AND nama_ruang = '$nama_ruang'

            AND tanggal = '$tanggal'

            AND LOWER(status) = 'disetujui'

            AND (

                ('$jam_mulai' < jam_selesai)

                AND

                ('$jam_selesai' > jam_mulai)

            )
    ");

    if (mysqli_num_rows($cek) > 0) {

        echo json_encode([
            "success" => false,
            "message" => "Kelas sedang digunakan pada jam tersebut"
        ]);

        exit;
    }
}


// ==========================
// UPDATE STATUS
// ==========================

$query = mysqli_query($koneksi, "
    UPDATE peminjaman
    SET
        status='$status',
        alasan_ditolak='$alasan_ditolak'
    WHERE id='$id'
");


// ==========================
// JIKA BERHASIL
// ==========================

if ($query) {

    // ======================
    // AMBIL TOKEN USER
    // ======================

    $getUser = mysqli_query(
        $koneksi,
        "SELECT fcm_token
         FROM users
         WHERE id='$user_id'"
    );

    $user = mysqli_fetch_assoc($getUser);

    if (
        isset($user['fcm_token']) &&
        !empty($user['fcm_token'])
    ) {

        // ======================
        // JUDUL & ISI NOTIF
        // ======================

        $title = "Status Peminjaman";

        if ($status == "Disetujui") {

            $body =
                $nama_ruang .
                " berhasil disetujui";

        } else {

            $body =
                $nama_ruang .
                " ditolak. Alasan: " .
                $alasan_ditolak;
        }

        // ======================
        // KIRIM NOTIF
        // ======================

        sendNotification(
            $user['fcm_token'],
            $title,
            $body
        );
    }

    echo json_encode([
        "success" => true,
        "message" => "Status berhasil diupdate"
    ]);

} else {

    echo json_encode([
        "success" => false,
        "message" => "Status gagal diupdate"
    ]);
}
?>