package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class EditUserActivity extends AppCompatActivity {

    EditText etNama, etNim, etEmail,
            etProdi, etFakultas,
            etNoHp, etAlamat,
            etUsername, etPassword;

    Spinner spRole;

    Button btnUpdate;

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_user);

        View btnBack = findViewById(R.id.btnBack);

        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etEmail = findViewById(R.id.etEmail);
        etProdi = findViewById(R.id.etProdi);
        etFakultas = findViewById(R.id.etFakultas);
        etNoHp = findViewById(R.id.etNoHp);
        etAlamat = findViewById(R.id.etAlamat);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        spRole = findViewById(R.id.spRole);

        btnUpdate = findViewById(R.id.btnSimpan);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        String[] role = {"admin", "user"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        role
                );

        spRole.setAdapter(adapter);

        btnUpdate.setText("Update User");

        // ambil data intent
        idUser = getIntent().getStringExtra("id");
        etNama.setText(getIntent().getStringExtra("nama"));
        etNim.setText(getIntent().getStringExtra("nim"));
        etEmail.setText(getIntent().getStringExtra("email"));
        etProdi.setText(getIntent().getStringExtra("prodi"));
        etFakultas.setText(getIntent().getStringExtra("fakultas"));
        etNoHp.setText(getIntent().getStringExtra("no_hp"));
        etAlamat.setText(getIntent().getStringExtra("alamat"));

        etUsername.setText(getIntent().getStringExtra("username"));
        etPassword.setHint("Kosongkan jika tidak ingin ganti password");

        String roleUser = getIntent().getStringExtra("role");

        if (roleUser != null) {

            if (roleUser.equals("admin")) {
                spRole.setSelection(0);
            } else {
                spRole.setSelection(1);
            }
        }

        btnUpdate.setOnClickListener(view -> {

            updateUser();

        });
    }

    private void updateUser() {

        String nama = etNama.getText().toString().trim();
        String nim = etNim.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String prodi = etProdi.getText().toString().trim();
        String fakultas = etFakultas.getText().toString().trim();
        String noHp = etNoHp.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String role = spRole.getSelectedItem().toString();

        if (nama.isEmpty()
                || nim.isEmpty()
                || email.isEmpty()
                || prodi.isEmpty()
                || fakultas.isEmpty()
                || noHp.isEmpty()
                || alamat.isEmpty()
                || username.isEmpty()) {

            Toast.makeText(
                    this,
                    "Semua data wajib diisi",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        Call<ApiResponse> call = apiService.editUser(
                idUser,
                nama,
                nim,
                email,
                prodi,
                fakultas,
                noHp,
                alamat,
                username,
                password,
                role
        );

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    Toast.makeText(
                            EditUserActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                } else {

                    Toast.makeText(
                            EditUserActivity.this,
                            "Update user gagal",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                Toast.makeText(
                        EditUserActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}