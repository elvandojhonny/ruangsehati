package com.example.peminjamanruang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.adapter.RuangAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Ruang;
import com.example.peminjamanruang.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RuangActivity extends AppCompatActivity {

    private RecyclerView recyclerRuang;
    private RuangAdapter ruangAdapter;
    private List<Ruang> ruangList;

    private Button btnTambahRuang;

    private SessionManager sessionManager;

    private String idGedung;
    private String namaGedung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruang);

        recyclerRuang = findViewById(R.id.recyclerRuang);
        btnTambahRuang = findViewById(R.id.btnTambahRuang);

        View btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed());

        recyclerRuang.setLayoutManager(
                new LinearLayoutManager(this)
        );

        sessionManager = new SessionManager(this);

        // ambil data gedung dari activity sebelumnya
        idGedung = getIntent().getStringExtra("id_gedung");
        namaGedung = getIntent().getStringExtra("nama_gedung");

        if (idGedung == null || idGedung.isEmpty()) {

            Toast.makeText(
                    this,
                    "ID Gedung tidak ditemukan",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        setTitle("Ruang - " + namaGedung);

        // user tidak bisa tambah ruang
        if ("user".equals(sessionManager.getRole())) {
            btnTambahRuang.setVisibility(View.GONE);
        }

        ruangList = new ArrayList<>();

        // kirim namaGedung ke adapter
        ruangAdapter = new RuangAdapter(
                this,
                ruangList,
                sessionManager.getRole(),
                namaGedung
        );

        recyclerRuang.setAdapter(ruangAdapter);

        loadRuang();

        btnTambahRuang.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RuangActivity.this,
                    TambahRuangActivity.class
            );

            intent.putExtra("id_gedung", idGedung);

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRuang();
    }

    private void loadRuang() {

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        apiService.getRuang(idGedung)
                .enqueue(new Callback<List<Ruang>>() {

                    @Override
                    public void onResponse(
                            Call<List<Ruang>> call,
                            Response<List<Ruang>> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            ruangList.clear();

                            ruangList.addAll(response.body());

                            ruangAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(
                                    RuangActivity.this,
                                    "Data ruang gagal dimuat",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Ruang>> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                RuangActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}