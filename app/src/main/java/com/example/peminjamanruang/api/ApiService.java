package com.example.peminjamanruang.api;

import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.model.Gedung;
import com.example.peminjamanruang.model.Peminjaman;
import com.example.peminjamanruang.model.Ruang;
import com.example.peminjamanruang.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // ================= LOGIN =================
    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    // ================= USER =================
    @GET("get_user.php")
    Call<List<User>> getUser();

    @FormUrlEncoded
    @POST("tambah_user.php")
    Call<ApiResponse> tambahUser(
            @Field("nama") String nama,
            @Field("nim") String nim,
            @Field("email") String email,
            @Field("prodi") String prodi,
            @Field("fakultas") String fakultas,
            @Field("no_hp") String noHp,
            @Field("alamat") String alamat,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("edit_user.php")
    Call<ApiResponse> editUser(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("nim") String nim,
            @Field("email") String email,
            @Field("prodi") String prodi,
            @Field("fakultas") String fakultas,
            @Field("no_hp") String noHp,
            @Field("alamat") String alamat,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("hapus_user.php")
    Call<ApiResponse> hapusUser(
            @Field("id") String id
    );

    @GET("get_user_by_id.php")
    Call<User> getUserById(
            @Query("id") String id
    );

    // ================= GEDUNG =================
    @GET("get_gedung.php")
    Call<List<Gedung>> getGedung();

    // ================= RUANG =================

    // USER → ruang per gedung
    @GET("get_ruang.php")
    Call<List<Ruang>> getRuang(
            @Query("id_gedung") String idGedung
    );

    // ADMIN → semua ruang
    @GET("get_ruang_all.php")
    Call<List<Ruang>> getAllRuang();

    @FormUrlEncoded
    @POST("tambah_ruang.php")
    Call<ApiResponse> tambahRuang(
            @Field("id_gedung") String idGedung,
            @Field("nama_ruang") String namaRuang,
            @Field("lokasi") String lokasi,
            @Field("kapasitas") String kapasitas
    );

    @FormUrlEncoded
    @POST("hapus_ruang.php")
    Call<ApiResponse> hapusRuang(
            @Field("id") String id
    );

    // ================= GEDUNG TAMBAH =================
    @FormUrlEncoded
    @POST("tambah_gedung.php")
    Call<ApiResponse> tambahGedung(
            @Field("nama_gedung") String namaGedung,
            @Field("fakultas") String fakultas
    );

    // ================= PEMINJAMAN =================
    @FormUrlEncoded
    @POST("tambah_peminjaman.php")
    Call<ApiResponse> tambahPeminjaman(
            @Field("user_id") String userId,
            @Field("nama_peminjam") String namaPeminjam,
            @Field("nama_gedung") String namaGedung,
            @Field("nama_ruang") String namaRuang,
            @Field("tanggal") String tanggal,
            @Field("hari") String hari,
            @Field("jam_mulai") String jamMulai,
            @Field("jam_selesai") String jamSelesai,
            @Field("keterangan") String keterangan
    );

    @GET("get_peminjaman.php")
    Call<List<Peminjaman>> getPeminjaman();

    @GET("get_peminjaman_user.php")
    Call<List<Peminjaman>> getPeminjamanUser(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("update_status_peminjaman.php")
    Call<ApiResponse> updateStatusPeminjaman(
            @Field("id") String id,
            @Field("status") String status,
            @Field("alasan_ditolak") String alasanDitolak
    );

    @FormUrlEncoded
    @POST("save_token.php")
    Call<ApiResponse> saveToken(
            @Field("user_id") String userId,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("cek_jadwal_bentrok.php")
    Call<ApiResponse> cekBentrokJadwal(
            @Field("id") String id,
            @Field("nama_ruang") String namaRuang,
            @Field("tanggal") String tanggal,
            @Field("jam_mulai") String jamMulai,
            @Field("jam_selesai") String jamSelesai
    );

    @FormUrlEncoded
    @POST("hapus_peminjaman.php")
    Call<ApiResponse> hapusPeminjaman(
            @Field("id") String id
    );

    @GET("hapus_peminjaman_lama.php")
    Call<ApiResponse> hapusPeminjamanLama();
}