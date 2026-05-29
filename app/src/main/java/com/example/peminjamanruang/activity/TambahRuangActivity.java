package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahRuangActivity extends AppCompatActivity {

    EditText etNamaRuang, etLokasi, etKapasitas;
    Button btnSimpanRuang;

    String idGedung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        );

        setContentView(R.layout.activity_tambah_ruang);

        etNamaRuang = findViewById(R.id.etNamaRuang);
        etLokasi = findViewById(R.id.etLokasi);
        etKapasitas = findViewById(R.id.etKapasitas);
        btnSimpanRuang = findViewById(R.id.btnSimpanRuang);
        View btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        idGedung = getIntent().getStringExtra("id_gedung");

        if (idGedung == null || idGedung.isEmpty()) {
            Toast.makeText(this, "ID Gedung tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnSimpanRuang.setOnClickListener(v -> simpanRuang());
    }

    private void simpanRuang() {

        String namaRuang = etNamaRuang.getText().toString().trim();
        String lokasi = etLokasi.getText().toString().trim();
        String kapasitas = etKapasitas.getText().toString().trim();

        if (namaRuang.isEmpty() || kapasitas.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        api.tambahRuang(
                idGedung,
                namaRuang,
                lokasi,
                kapasitas
        ).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(
                            TambahRuangActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(
                        TambahRuangActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}