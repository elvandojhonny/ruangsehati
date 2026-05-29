package com.example.peminjamanruang.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanUserActivity extends AppCompatActivity {

    private EditText etNamaPeminjam;
    private EditText etNamaGedung;
    private EditText etTanggal;
    private EditText etHari;
    private EditText etJamMulai;
    private EditText etJamSelesai;
    private EditText etKeterangan;

    private AutoCompleteTextView etNamaRuang;

    private Button btnSimpanPeminjaman;

    private SessionManager sessionManager;

    private final Locale localeID = new Locale("id", "ID");

    String[] daftarRuang = {
            "Lab Komputer 1",
            "Lab Komputer 2",
            "Ruang A1",
            "Ruang A2",
            "Ruang B1",
            "Ruang B2"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman_user);

        Calendar selected = Calendar.getInstance();

        View btnBack = findViewById(R.id.btnBack);

        etNamaPeminjam = findViewById(R.id.etNamaPeminjam);
        etNamaGedung = findViewById(R.id.etNamaGedung);
        etNamaRuang = findViewById(R.id.etNamaRuang);
        etTanggal = findViewById(R.id.etTanggal);
        etHari = findViewById(R.id.etHari);
        etJamMulai = findViewById(R.id.etJamMulai);
        etJamSelesai = findViewById(R.id.etJamSelesai);
        etKeterangan = findViewById(R.id.etKeterangan);

        btnSimpanPeminjaman =
                findViewById(R.id.btnSimpanPeminjaman);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed());

        sessionManager = new SessionManager(this);

        etNamaPeminjam.setText(sessionManager.getUsername());

        ArrayAdapter<String> adapterRuang =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        daftarRuang
                );

        etNamaRuang.setAdapter(adapterRuang);
        etNamaRuang.setThreshold(1);

        // ambil data intent
        String gedungIntent =
                getIntent().getStringExtra("nama_gedung");

        String ruangIntent =
                getIntent().getStringExtra("nama_ruang");

        // set nama gedung otomatis
        if (gedungIntent != null) {
            etNamaGedung.setText(gedungIntent);
            etNamaGedung.setEnabled(false);
        }

        // set nama ruang otomatis
        if (ruangIntent != null) {
            etNamaRuang.setText(ruangIntent);
            etNamaRuang.setEnabled(false);
        }

        // pilih tanggal
        etTanggal.setOnClickListener(v -> {

            Calendar now = Calendar.getInstance();

            DatePickerDialog dlg =
                    new DatePickerDialog(
                            this,
                            (view, year, month, dayOfMonth) -> {

                                selected.set(Calendar.YEAR, year);
                                selected.set(Calendar.MONTH, month);
                                selected.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat df =
                                        new SimpleDateFormat(
                                                "dd MMM yyyy",
                                                localeID
                                        );

                                SimpleDateFormat dayf =
                                        new SimpleDateFormat(
                                                "EEEE",
                                                localeID
                                        );

                                etTanggal.setText(
                                        df.format(selected.getTime())
                                );

                                etHari.setText(
                                        dayf.format(selected.getTime())
                                );
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );

            dlg.show();
        });

        // pilih jam mulai
        etJamMulai.setOnClickListener(v ->
                showTimePicker(etJamMulai));

        // pilih jam selesai
        etJamSelesai.setOnClickListener(v ->
                showTimePicker(etJamSelesai));

        // simpan peminjaman
        btnSimpanPeminjaman.setOnClickListener(v ->
                simpanPeminjaman());
    }

    private void showTimePicker(EditText target) {

        Calendar now = Calendar.getInstance();

        TimePickerDialog dlg =
                new TimePickerDialog(
                        this,
                        (view, hourOfDay, minute) -> {

                            String jam = String.format(
                                    Locale.getDefault(),
                                    "%02d:%02d",
                                    hourOfDay,
                                    minute
                            );

                            target.setText(jam);
                        },
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );

        dlg.show();
    }

    private void simpanPeminjaman() {

        String userId =
                sessionManager.getId();

        String namaPeminjam =
                etNamaPeminjam.getText().toString().trim();

        String namaGedung =
                etNamaGedung.getText().toString().trim();

        String namaRuang =
                etNamaRuang.getText().toString().trim();

        String tanggal =
                etTanggal.getText().toString().trim();

        String hari =
                etHari.getText().toString().trim();

        String jamMulai =
                etJamMulai.getText().toString().trim();

        String jamSelesai =
                etJamSelesai.getText().toString().trim();

        String keterangan =
                etKeterangan.getText().toString().trim();

        if (
                namaGedung.isEmpty() ||
                        namaRuang.isEmpty() ||
                        tanggal.isEmpty() ||
                        hari.isEmpty() ||
                        jamMulai.isEmpty() ||
                        jamSelesai.isEmpty() ||
                        keterangan.isEmpty()
        ) {

            Toast.makeText(
                    this,
                    "Semua data wajib diisi",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ApiService apiService =
                RetrofitClient
                        .getRetrofitInstance()
                        .create(ApiService.class);

        Call<ApiResponse> call =
                apiService.tambahPeminjaman(
                        userId,
                        namaPeminjam,
                        namaGedung,
                        namaRuang,
                        tanggal,
                        hari,
                        jamMulai,
                        jamSelesai,
                        keterangan
                );

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(
                    Call<ApiResponse> call,
                    Response<ApiResponse> response
            ) {

                if (
                        response.isSuccessful()
                                && response.body() != null
                ) {

                    ApiResponse apiResponse =
                            response.body();

                    Toast.makeText(
                            PeminjamanUserActivity.this,
                            apiResponse.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();

                    if (apiResponse.isSuccess()) {
                        finish();
                    }

                } else {

                    Toast.makeText(
                            PeminjamanUserActivity.this,
                            "Gagal mengirim data",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<ApiResponse> call,
                    Throwable t
            ) {

                Toast.makeText(
                        PeminjamanUserActivity.this,
                        "Terjadi kesalahan koneksi",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}