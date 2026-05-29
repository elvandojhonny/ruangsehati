package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;

public class EditRuangActivity extends AppCompatActivity {

    EditText etNamaRuang, etLokasi, etKapasitas;
    Button btnSimpanRuang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        );

        setContentView(R.layout.activity_edit_ruang);

        etNamaRuang = findViewById(R.id.etNamaRuang);
        etLokasi = findViewById(R.id.etLokasi);
        etKapasitas = findViewById(R.id.etKapasitas);
        btnSimpanRuang = findViewById(R.id.btnSimpanRuang);
        View btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        btnSimpanRuang.setText("Update Ruang");

        btnSimpanRuang.setOnClickListener(view -> {
            Toast.makeText(this, "Ruang berhasil diupdate", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}