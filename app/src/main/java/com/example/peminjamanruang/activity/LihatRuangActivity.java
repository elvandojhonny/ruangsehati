package com.example.peminjamanruang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.peminjamanruang.R;

public class LihatRuangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_lihat_ruang);

        View btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed());

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // contoh tombol pinjam
        View btnPinjam = findViewById(R.id.btnPinjam);

        btnPinjam.setOnClickListener(v -> {

            // ambil data dari intent/activity sebelumnya
            String namaGedung =
                    getIntent().getStringExtra("nama_gedung");

            String namaRuang =
                    getIntent().getStringExtra("nama_ruang");

            Intent intent = new Intent(
                    LihatRuangActivity.this,
                    PeminjamanUserActivity.class
            );

            intent.putExtra(
                    "nama_gedung",
                    namaGedung
            );

            intent.putExtra(
                    "nama_ruang",
                    namaRuang
            );

            startActivity(intent);
        });
    }
}