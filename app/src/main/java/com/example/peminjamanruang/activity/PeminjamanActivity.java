package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.adapter.PeminjamanAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Peminjaman;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PeminjamanAdapter adapter;
    private List<Peminjaman> peminjamanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        initView();
        setupRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // AUTO REFRESH SAAT KEMBALI KE HALAMAN
        loadPeminjaman();
    }

    private void initView() {

        View btnBack = findViewById(R.id.btnBack);

        recyclerView = findViewById(R.id.recyclerPeminjaman);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed());
    }

    private void setupRecycler() {

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        peminjamanList = new ArrayList<>();

        adapter = new PeminjamanAdapter(
                this,
                peminjamanList,
                "admin"
        );

        recyclerView.setAdapter(adapter);
    }

    private void loadPeminjaman() {

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        Call<List<Peminjaman>> call =
                apiService.getPeminjaman();

        call.enqueue(new Callback<List<Peminjaman>>() {

            @Override
            public void onResponse(
                    Call<List<Peminjaman>> call,
                    Response<List<Peminjaman>> response
            ) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    peminjamanList.clear();

                    peminjamanList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(
                            PeminjamanActivity.this,
                            "Data peminjaman kosong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<Peminjaman>> call,
                    Throwable t
            ) {

                Toast.makeText(
                        PeminjamanActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}