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
import com.example.peminjamanruang.adapter.GedungAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Gedung;
import com.example.peminjamanruang.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GedungActivity extends AppCompatActivity {

    RecyclerView recyclerGedung;

    GedungAdapter adapter;

    List<Gedung> gedungList;

    SessionManager sessionManager;

    Button btnTambahGedung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gedung);

        sessionManager = new SessionManager(this);

        initView();

        setupRecycler();

        setupButton();

        loadGedung();
    }

    // ================= AUTO REFRESH =================
    @Override
    protected void onResume() {
        super.onResume();

        loadGedung();
    }

    private void initView() {

        View btnBack = findViewById(R.id.btnBack);

        recyclerGedung = findViewById(R.id.recyclerGedung);

        btnTambahGedung = findViewById(R.id.btnTambahGedung);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void setupRecycler() {

        recyclerGedung.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerGedung.setHasFixedSize(true);

        gedungList = new ArrayList<>();

        adapter = new GedungAdapter(
                this,
                gedungList,
                sessionManager.getRole()
        );

        recyclerGedung.setAdapter(adapter);
    }

    private void setupButton() {

        if (!sessionManager.getRole().equals("admin")) {

            btnTambahGedung.setVisibility(View.GONE);
        }

        btnTambahGedung.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                TambahGedungActivity.class
                        )
                )
        );
    }

    private void loadGedung() {

        ApiService api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        api.getGedung().enqueue(new Callback<List<Gedung>>() {

            @Override
            public void onResponse(
                    Call<List<Gedung>> call,
                    Response<List<Gedung>> response
            ) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    gedungList.clear();

                    gedungList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(
                            GedungActivity.this,
                            "Data gedung gagal dimuat",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<Gedung>> call,
                    Throwable t
            ) {

                Toast.makeText(
                        GedungActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}