package com.example.peminjamanruang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.User;
import com.example.peminjamanruang.session.SessionManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    SessionManager session;

    TextView tvName, tvRole;

    EditText etNama, etNim, etEmail, etNoHp, etAlamat;

    MaterialButton btnEdit, btnLogout;

    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(this);

        initView();
        loadUserFromDatabase();
        setupAction();
    }

    private void initView() {

        tvName = findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);

        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etEmail = findViewById(R.id.etEmail);
        etNoHp = findViewById(R.id.etNoHp);
        etAlamat = findViewById(R.id.etAlamat);

        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);

        findViewById(R.id.btnBack).setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void loadUserFromDatabase() {

        String userId = session.getUserId();

        if (userId == null || userId.trim().isEmpty()) {

            Toast.makeText(this,
                    "Session tidak valid",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            return;
        }

        ApiService api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        api.getUserById(userId).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (!response.isSuccessful() || response.body() == null) {

                    Toast.makeText(ProfileActivity.this,
                            "Gagal load data user",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                User user = response.body();

                tvName.setText(user.getNama());

                String role = session.getRole();

                if (role != null && role.equalsIgnoreCase("admin")) {
                    tvRole.setText("Administrator");
                } else {
                    tvRole.setText("Mahasiswa");
                }

                etNama.setText(user.getNama());
                etNim.setText(user.getNim());
                etEmail.setText(user.getEmail());
                etNoHp.setText(user.getNoHp());
                etAlamat.setText(user.getAlamat());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(ProfileActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        isEdit = false;

        etNama.setEnabled(false);
        etEmail.setEnabled(false);
        etNoHp.setEnabled(false);
        etAlamat.setEnabled(false);

        btnEdit.setText("Edit Profil");
    }

    private void setupAction() {

        btnEdit.setOnClickListener(v -> {

            isEdit = !isEdit;

            etNama.setEnabled(isEdit);
            etEmail.setEnabled(isEdit);
            etNoHp.setEnabled(isEdit);
            etAlamat.setEnabled(isEdit);

            btnEdit.setText(
                    isEdit
                            ? "Simpan Perubahan"
                            : "Edit Profil"
            );

            if (!isEdit) {

                Toast.makeText(this,
                        "Fitur Edit akan segera hadir update berikutnya",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {

            session.logout();

            Intent intent =
                    new Intent(this, LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
        });
    }
}