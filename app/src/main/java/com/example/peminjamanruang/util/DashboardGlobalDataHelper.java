package com.example.peminjamanruang.util;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.model.Peminjaman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Menyiapkan data global dashboard mahasiswa:
 * filter tabel, statistik, dan label status UI.
 */
public final class DashboardGlobalDataHelper {

    public enum StatusBucket {
        DIPINJAM,
        DIGUNAKAN,
        SELESAI,
        OTHER
    }

    private DashboardGlobalDataHelper() {
    }

    public static StatusBucket bucketForApiStatus(String raw) {

        if (raw == null) {
            return StatusBucket.OTHER;
        }

        String s = raw.trim().toLowerCase();

        switch (s) {

            case "menunggu":
            case "pending":
                return StatusBucket.DIPINJAM;

            case "disetujui":
            case "approved":
                return StatusBucket.DIGUNAKAN;

            case "selesai":
            case "done":
            case "completed":
                return StatusBucket.SELESAI;

            default:
                return StatusBucket.OTHER;
        }
    }

    /**
     * Baris yang ditampilkan di tabel global:
     * semua kecuali yang ditolak.
     */
    public static List<Peminjaman> filterRowsForGlobalTable(
            List<Peminjaman> all
    ) {

        if (all == null || all.isEmpty()) {
            return Collections.emptyList();
        }

        List<Peminjaman> out = new ArrayList<>();

        for (Peminjaman p : all) {

            if (p == null) {
                continue;
            }

            String st = p.getStatus();

            if (
                    st != null &&
                            st.trim().equalsIgnoreCase("ditolak")
            ) {
                continue;
            }

            out.add(p);
        }

        Collections.sort(out, ROW_ORDER);

        return out;
    }

    /**
     * Total semua data peminjaman.
     */
    public static int countTotalAll(List<Peminjaman> all) {
        return all == null ? 0 : all.size();
    }

    /**
     * Total aktif:
     * menunggu + disetujui.
     */
    public static int countActiveGlobal(List<Peminjaman> all) {

        if (all == null || all.isEmpty()) {
            return 0;
        }

        int n = 0;

        for (Peminjaman p : all) {

            if (p == null) {
                continue;
            }

            StatusBucket bucket =
                    bucketForApiStatus(p.getStatus());

            if (
                    bucket == StatusBucket.DIPINJAM ||
                            bucket == StatusBucket.DIGUNAKAN
            ) {
                n++;
            }
        }

        return n;
    }

    @StringRes
    public static int statusLabelRes(StatusBucket bucket) {

        switch (bucket) {

            case DIPINJAM:
                return R.string.dashboard_global_status_dipinjam;

            case DIGUNAKAN:
                return R.string.dashboard_global_status_digunakan;

            case SELESAI:
                return R.string.dashboard_global_status_selesai;

            default:
                return 0;
        }
    }

    @DrawableRes
    public static int statusBackgroundRes(StatusBucket bucket) {

        switch (bucket) {

            case DIPINJAM:
                return R.drawable.bg_global_status_dipinjam;

            case DIGUNAKAN:
                return R.drawable.bg_global_status_digunakan;

            case SELESAI:
                return R.drawable.bg_global_status_selesai;

            default:
                return R.drawable.bg_global_status_unknown;
        }
    }

    /**
     * Sorting:
     * tanggal terbaru lalu jam mulai terbaru.
     */
    private static final Comparator<Peminjaman> ROW_ORDER =
            (a, b) -> {

                String ta =
                        a.getTanggal() != null
                                ? a.getTanggal()
                                : "";

                String tb =
                        b.getTanggal() != null
                                ? b.getTanggal()
                                : "";

                int c = tb.compareTo(ta);

                if (c != 0) {
                    return c;
                }

                String ja =
                        a.getJamMulai() != null
                                ? a.getJamMulai()
                                : "";

                String jb =
                        b.getJamMulai() != null
                                ? b.getJamMulai()
                                : "";

                return jb.compareTo(ja);
            };
}