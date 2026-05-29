package com.example.peminjamanruang.model;

public class ApiResponse {

    private boolean success;
    private String message;

    private String id;
    private String nama;
    private String nim;
    private String email;
    private String prodi;
    private String fakultas;
    private String no_hp;
    private String alamat;

    private String username;
    private String role;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getEmail() {
        return email;
    }

    public String getProdi() {
        return prodi;
    }

    public String getFakultas() {
        return fakultas;
    }

    public String getNoHp() {
        return no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}