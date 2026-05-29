package com.example.peminjamanruang.model;

public class Ruang {

    private String id;
    private String nama_ruang;
    private String lokasi;
    private String kapasitas;

    public Ruang(String nama_ruang, String lokasi, String kapasitas) {
        this.nama_ruang = nama_ruang;
        this.lokasi = lokasi;
        this.kapasitas = kapasitas;
    }

    public String getId() {
        return id;
    }

    public String getNamaRuang() {
        return nama_ruang;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getKapasitas() {
        return kapasitas;
    }
}