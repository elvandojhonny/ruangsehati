package com.example.peminjamanruang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.session.SessionManager;
import com.google.android.material.button.MaterialButton;

public class ProfileAdminActivity extends AppCompatActivity {

    TextView tvNama, tvUsername, tvRole;

    MaterialButton btnLogout;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        session = new SessionManager(this);

        initView();
        setData();
        setupClick();
    }

    private void initView() {

        tvNama = findViewById(R.id.tvNama);
        tvUsername = findViewById(R.id.tvUsername);
        tvRole = findViewById(R.id.tvRole);

        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setData() {

        tvNama.setText(session.getNama());
        tvUsername.setText(session.getUsername());
        tvRole.setText("Administrator");
    }

    private void setupClick() {

        btnLogout.setOnClickListener(v -> {

            session.logout();

            Intent intent =
                    new Intent(this, LoginActivity.class);

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        });
    }
}