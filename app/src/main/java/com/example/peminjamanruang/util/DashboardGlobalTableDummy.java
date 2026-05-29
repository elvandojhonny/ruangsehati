package com.example.peminjamanruang.util;

import com.example.peminjamanruang.BuildConfig;
import com.example.peminjamanruang.model.Peminjaman;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Data contoh untuk pengujian UI saat backend kosong.
 * Set {@link #ENABLED_FOR_UI_PREVIEW} menjadi {@code true} hanya saat development.
 */
public final class DashboardGlobalTableDummy {

    /** Ubah ke true di debug jika ingin melihat isi tabel tanpa API. */
    public static final boolean ENABLED_FOR_UI_PREVIEW = false;

    private static final String JSON = "["
            + "{\"id\":\"d1\",\"nama_peminjam\":\"Ayu Lestari\",\"nama_ruang\":\"Lab Multimedia 2\","
            + "\"nama_gedung\":\"Gedung Teknik\",\"tanggal\":\"2026-05-10\",\"jam\":\"08:00–10:00\","
            + "\"status\":\"menunggu\"},"
            + "{\"id\":\"d2\",\"nama_peminjam\":\"Raka Pratama\",\"nama_ruang\":\"Ruang Seminar Utama\","
            + "\"nama_gedung\":\"Gedung Rektorat\",\"tanggal\":\"2026-05-08\",\"jam\":\"13:00–15:00\","
            + "\"status\":\"disetujui\"},"
            + "{\"id\":\"d3\",\"nama_peminjam\":\"Mira Handayani\",\"nama_ruang\":\"Kelas 301\","
            + "\"nama_gedung\":\"Gedung A\",\"tanggal\":\"2026-05-05\",\"jam\":\"10:00–12:00\","
            + "\"status\":\"selesai\"}"
            + "]";

    private DashboardGlobalTableDummy() {
    }

    public static List<Peminjaman> loadIfEnabled() {
        if (!BuildConfig.DEBUG || !ENABLED_FOR_UI_PREVIEW) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Peminjaman>>() {
        }.getType();
        List<Peminjaman> list = new Gson().fromJson(JSON, listType);
        return list != null ? list : Collections.emptyList();
    }
}
