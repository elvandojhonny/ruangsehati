package com.example.peminjamanruang.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.session.SessionManager;
import com.example.peminjamanruang.util.UiTransitions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LEN = 4;

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private MaterialButton btnLogin;

    private TextView tvTentangApp;
    private TextView tvForgotPassword;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        sessionManager = new SessionManager(this);

        setupImeListeners();
        setupClicks();

        // =============================
        // IZIN NOTIF ANDROID 13+
        // =============================

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissions(
                    new String[]{
                            android.Manifest.permission.POST_NOTIFICATIONS
                    },
                    100
            );
        }
    }

    private void initView() {

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        tvTentangApp = findViewById(R.id.tvTentangApp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
    }

    private void setupImeListeners() {

        etEmail.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                etPassword.requestFocus();

                return true;
            }

            return false;
        });

        etPassword.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {

                attemptLogin();

                return true;
            }

            return false;
        });
    }

    private void setupClicks() {

        tvTentangApp.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    AboutAppActivity.class
            );

            UiTransitions.startWithFade(
                    LoginActivity.this,
                    intent,
                    false
            );
        });

        tvForgotPassword.setOnClickListener(v ->
                showForgotPasswordDialog()
        );

        btnLogin.setOnClickListener(v ->
                attemptLogin()
        );
    }

    private void showForgotPasswordDialog() {

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.forgot_password_title)
                .setMessage(R.string.forgot_password_message)
                .setPositiveButton(R.string.forgot_password_ok,
                        (d, w) -> d.dismiss())

                .setNeutralButton(R.string.forgot_password_contact,
                        (d, w) -> openAdminEmail())

                .show();
    }

    private void openAdminEmail() {

        String addr = getString(R.string.admin_support_email);

        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:" + addr));

        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.app_name) + " — reset kata sandi"
        );

        try {

            startActivity(Intent.createChooser(intent, null));

        } catch (ActivityNotFoundException e) {

            Toast.makeText(
                    this,
                    "Tidak ada aplikasi email terpasang",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void attemptLogin() {

        String email = textOf(etEmail);
        String password = textOf(etPassword);

        if (!validate(email, password)) {
            return;
        }

        submitLogin(email, password);
    }

    private boolean validate(String email, String password) {

        if (email.isEmpty()) {

            etEmail.setError("Email wajib diisi");

            etEmail.requestFocus();

            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            etEmail.setError("Format email tidak valid");

            etEmail.requestFocus();

            return false;
        }

        if (password.isEmpty()) {

            etPassword.setError("Password wajib diisi");

            etPassword.requestFocus();

            return false;
        }

        if (password.length() < MIN_PASSWORD_LEN) {

            etPassword.setError("Password terlalu pendek");

            etPassword.requestFocus();

            return false;
        }

        etEmail.setError(null);
        etPassword.setError(null);

        return true;
    }

    private static String textOf(TextInputEditText et) {

        return et.getText() != null
                ? et.getText().toString().trim()
                : "";
    }

    private void submitLogin(String email, String password) {

        Log.d("LOGIN_DEBUG", "Submit login for: " + email);

        ApiService api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        api.login(email, password)
                .enqueue(new Callback<ApiResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse> call,
                            Response<ApiResponse> response
                    ) {

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Response tidak valid",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        ApiResponse data = response.body();

                        if (!data.isSuccess()) {

                            Toast.makeText(
                                    LoginActivity.this,
                                    data.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        String role = data.getRole() != null
                                ? data.getRole().trim().toLowerCase()
                                : "";

                        // =========================
                        // SAVE SESSION
                        // =========================

                        sessionManager.createSession(
                                data.getId(),
                                data.getNama(),
                                data.getNim(),
                                data.getEmail(),
                                data.getProdi(),
                                data.getFakultas(),
                                data.getNoHp(),
                                data.getAlamat(),
                                data.getUsername(),
                                role
                        );

                        // =========================
                        // SAVE FIREBASE TOKEN
                        // =========================

                        FirebaseMessaging.getInstance()
                                .getToken()
                                .addOnCompleteListener(task -> {

                                    if (!task.isSuccessful()) {

                                        Log.e(
                                                "FCM_TOKEN",
                                                "Gagal mendapatkan token"
                                        );

                                        return;
                                    }

                                    String token = task.getResult();

                                    Log.d(
                                            "FCM_TOKEN",
                                            "TOKEN = " + token
                                    );

                                    api.saveToken(
                                            data.getId(),
                                            token
                                    ).enqueue(new Callback<ApiResponse>() {

                                        @Override
                                        public void onResponse(
                                                Call<ApiResponse> call,
                                                Response<ApiResponse> response
                                        ) {

                                            Log.d(
                                                    "FCM_TOKEN",
                                                    "Token berhasil disimpan"
                                            );
                                        }

                                        @Override
                                        public void onFailure(
                                                Call<ApiResponse> call,
                                                Throwable t
                                        ) {

                                            Log.e(
                                                    "FCM_TOKEN",
                                                    "Gagal simpan token: "
                                                            + t.getMessage()
                                            );
                                        }
                                    });
                                });

                        Toast.makeText(
                                LoginActivity.this,
                                "Login berhasil sebagai " + role,
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent intent;

                        if (role.equals("admin")) {

                            intent = new Intent(
                                    LoginActivity.this,
                                    DashboardAdminActivity.class
                            );

                        } else {

                            intent = new Intent(
                                    LoginActivity.this,
                                    DashboardUserActivity.class
                            );
                        }

                        UiTransitions.startWithFade(
                                LoginActivity.this,
                                intent,
                                true
                        );
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ApiResponse> call,
                            @NonNull Throwable t
                    ) {

                        Log.e(
                                "LOGIN_ERROR",
                                String.valueOf(t.getMessage())
                        );

                        Toast.makeText(
                                LoginActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}