package com.example.peminjamanruang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.adapter.DashboardGlobalKelasAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Peminjaman;
import com.example.peminjamanruang.session.SessionManager;
import com.example.peminjamanruang.util.DashboardGlobalDataHelper;
import com.example.peminjamanruang.util.DashboardGlobalTableDummy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardUserActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private TextView tvTotal;
    private TextView tvAktif;
    private TextView tvGlobalTableEmpty;

    private MaterialCardView heroCard;

    private BottomNavigationView bottomNav;

    private RecyclerView recyclerGlobalKelas;

    private DashboardGlobalKelasAdapter globalKelasAdapter;

    private SessionManager session;

    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);

        session = new SessionManager(this);

        api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        initView();

        setupUser();

        setupClick();

        setupBottomNav();

        setupGlobalTable();

        loadGlobalDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadGlobalDashboard();
    }

    private void initView() {

        tvWelcome = findViewById(R.id.tvWelcome);

        tvTotal = findViewById(R.id.tvTotal);

        tvAktif = findViewById(R.id.tvAktif);

        tvGlobalTableEmpty =
                findViewById(R.id.tvGlobalTableEmpty);

        heroCard = findViewById(R.id.heroCard);

        bottomNav = findViewById(R.id.bottomNav);

        recyclerGlobalKelas =
                findViewById(R.id.recyclerGlobalKelas);
    }

    private void setupUser() {

        String nama = session.getNama();

        if (nama == null || nama.isEmpty()) {
            nama = "Mahasiswa";
        }

        tvWelcome.setText(
                "Halo, " + nama
        );
    }

    private void setupClick() {

        heroCard.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                GedungActivity.class
                        )
                )
        );
    }

    private void setupBottomNav() {

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            }

            if (id == R.id.nav_ruang) {

                startActivity(
                        new Intent(
                                this,
                                GedungActivity.class
                        )
                );

                return true;
            }

            if (id == R.id.nav_riwayat) {

                startActivity(
                        new Intent(
                                this,
                                RiwayatPeminjamanUserActivity.class
                        )
                );

                return true;
            }

            if (id == R.id.nav_profile) {

                startActivity(
                        new Intent(
                                this,
                                ProfileActivity.class
                        )
                );

                return true;
            }

            return false;
        });
    }

    private void setupGlobalTable() {

        globalKelasAdapter =
                new DashboardGlobalKelasAdapter(this);

        recyclerGlobalKelas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerGlobalKelas.setAdapter(
                globalKelasAdapter
        );
    }

    private void loadGlobalDashboard() {

        String userId = session.getId();

        if (userId == null || userId.isEmpty()) {

            applyGlobalPeminjaman(
                    Collections.emptyList()
            );

            return;
        }

        api.getPeminjaman().enqueue(
                new Callback<List<Peminjaman>>() {

                    @Override
                    public void onResponse(
                            Call<List<Peminjaman>> call,
                            Response<List<Peminjaman>> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            applyGlobalPeminjaman(
                                    response.body()
                            );

                            return;
                        }

                        tryFallbackOrEmpty();
                    }

                    @Override
                    public void onFailure(
                            Call<List<Peminjaman>> call,
                            Throwable t
                    ) {

                        Log.e(
                                "API_ERROR",
                                String.valueOf(t.getMessage())
                        );

                        tryFallbackOrEmpty();
                    }
                }
        );
    }

    private void tryFallbackOrEmpty() {

        List<Peminjaman> dummy =
                DashboardGlobalTableDummy.loadIfEnabled();

        if (!dummy.isEmpty()) {

            applyGlobalPeminjaman(dummy);

        } else {

            applyGlobalPeminjaman(
                    Collections.emptyList()
            );
        }
    }

    private void applyGlobalPeminjaman(
            List<Peminjaman> raw
    ) {

        List<Peminjaman> safe =
                raw != null
                        ? raw
                        : Collections.emptyList();

        String myId = session.getId();

        // =========================
        // TOTAL PEMINJAMAN SAYA
        // =========================

        int totalSaya = 0;

        // =========================
        // PEMINJAMAN AKTIF SAYA
        // =========================

        int aktifSaya = 0;

        for (Peminjaman p : safe) {

            if (p.getUserId() == null) {
                continue;
            }

            if (p.getUserId().equals(myId)) {

                totalSaya++;

                if (p.getStatus() != null
                        && p.getStatus()
                        .equalsIgnoreCase("Disetujui")) {

                    aktifSaya++;
                }
            }
        }

        tvTotal.setText(
                String.valueOf(totalSaya)
        );

        tvAktif.setText(
                String.valueOf(aktifSaya)
        );

        // =========================
        // GLOBAL TABLE
        // tetap semua user
        // =========================

        List<Peminjaman> tableRows =
                DashboardGlobalDataHelper
                        .filterRowsForGlobalTable(safe);

        globalKelasAdapter.submit(tableRows);

        boolean empty = tableRows.isEmpty();

        tvGlobalTableEmpty.setVisibility(
                empty ? View.VISIBLE : View.GONE
        );
    }
}