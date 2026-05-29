<?php
include 'koneksi.php';

$id = $_POST['id'];
$nama_ruang = $_POST['nama_ruang'];
$tanggal = $_POST['tanggal'];
$jam_mulai = $_POST['jam_mulai'];
$jam_selesai = $_POST['jam_selesai'];

$query = mysqli_query($koneksi, "
SELECT * FROM peminjaman
WHERE nama_ruang='$nama_ruang'
AND tanggal='$tanggal'
AND status='Disetujui'
AND id != '$id'
AND (
    ('$jam_mulai' BETWEEN jam_mulai AND jam_selesai)
    OR
    ('$jam_selesai' BETWEEN jam_mulai AND jam_selesai)
    OR
    (jam_mulai BETWEEN '$jam_mulai' AND '$jam_selesai')
)
");

if(mysqli_num_rows($query) > 0){

    echo json_encode([
        "success" => false,
        "message" => "Jadwal bentrok"
    ]);

}else{

    echo json_encode([
        "success" => true,
        "message" => "Jadwal tersedia"
    ]);
}
?>