package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahGedungActivity extends AppCompatActivity {

    EditText etNamaGedung, etFakultas;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_gedung);

        etNamaGedung = findViewById(R.id.etNamaGedung);
        etFakultas = findViewById(R.id.etFakultas);
        btnSimpan = findViewById(R.id.btnSimpanGedung);
        View btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        btnSimpan.setOnClickListener(v -> {

            String nama = etNamaGedung.getText().toString().trim();
            String fakultas = etFakultas.getText().toString().trim();

            if (nama.isEmpty()) {
                etNamaGedung.setError("Nama gedung wajib diisi");
                etNamaGedung.requestFocus();
                return;
            }

            if (fakultas.isEmpty()) {
                etFakultas.setError("Fakultas wajib diisi");
                etFakultas.requestFocus();
                return;
            }

            ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);

            api.tambahGedung(nama, fakultas).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(TambahGedungActivity.this,
                                "Gedung berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahGedungActivity.this,
                                "Gagal menambah gedung",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(TambahGedungActivity.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}