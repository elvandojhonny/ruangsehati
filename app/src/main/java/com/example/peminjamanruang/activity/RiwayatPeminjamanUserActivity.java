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
import com.example.peminjamanruang.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPeminjamanUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PeminjamanAdapter adapter;
    List<Peminjaman> peminjamanList;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_peminjaman_user);

        View btnBack = findViewById(R.id.btnBack);

        recyclerView = findViewById(R.id.recyclerPeminjaman);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new SessionManager(this);

        peminjamanList = new ArrayList<>();

        adapter = new PeminjamanAdapter(this, peminjamanList, "user");

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // otomatis refresh saat halaman aktif kembali
        loadRiwayat();
    }

    private void loadRiwayat() {

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        Call<List<Peminjaman>> call =
                apiService.getPeminjamanUser(sessionManager.getId());

        call.enqueue(new Callback<List<Peminjaman>>() {

            @Override
            public void onResponse(Call<List<Peminjaman>> call,
                                   Response<List<Peminjaman>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    peminjamanList.clear();

                    peminjamanList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {

                    peminjamanList.clear();

                    adapter.notifyDataSetChanged();

                    Toast.makeText(
                            RiwayatPeminjamanUserActivity.this,
                            "Belum ada riwayat peminjaman",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<Peminjaman>> call, Throwable t) {

                Toast.makeText(
                        RiwayatPeminjamanUserActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}