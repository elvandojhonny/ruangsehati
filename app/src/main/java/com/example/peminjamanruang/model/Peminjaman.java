package com.example.peminjamanruang.model;

public class Peminjaman {

    private String id;
    private String user_id;
    private String nama_peminjam;
    private String nama_ruang;
    private String nama_gedung;
    private String tanggal;
    private String hari;

    private String jam_mulai;
    private String jam_selesai;

    private String keterangan;
    private String status;
    private String alasan_ditolak;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return user_id;
    }

    public String getNamaPeminjam() {
        return nama_peminjam;
    }

    public String getNamaRuang() {
        return nama_ruang;
    }

    public String getNamaGedung() {
        return nama_gedung;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getHari() {
        return hari;
    }

    public String getJamMulai() {
        return jam_mulai;
    }

    public String getJamSelesai() {
        return jam_selesai;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getStatus() {
        return status;
    }

    public String getAlasanDitolak() {
        return alasan_ditolak;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAlasanDitolak(String alasan_ditolak) {
        this.alasan_ditolak = alasan_ditolak;
    }
}