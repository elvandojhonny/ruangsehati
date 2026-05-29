package com.example.peminjamanruang.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Peminjaman;
import com.example.peminjamanruang.model.Ruang;
import com.example.peminjamanruang.model.User;
import com.example.peminjamanruang.session.SessionManager;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdminActivity extends AppCompatActivity {

    SessionManager session;

    TextView tvAdminName;
    TextView tvTotalRuang;
    TextView tvTotalPinjam;
    TextView tvTotalUser;

    ProgressBar progressGedung;
    ProgressBar progressPinjam;

    ImageView imgProfile;

    TextView btnBack;

    MaterialCardView cardRuang;
    MaterialCardView cardPeminjaman;
    MaterialCardView cardUser;
    MaterialCardView cardMonitoring;
    MaterialCardView cardProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        session = new SessionManager(this);

        initView();

        setupClick();

        setUserData();

        loadDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDashboard();
    }

    private void initView() {

        tvAdminName =
                findViewById(R.id.tvAdminName);

        tvTotalRuang =
                findViewById(R.id.tvTotalRuang);

        tvTotalPinjam =
                findViewById(R.id.tvTotalPinjam);

        tvTotalUser =
                findViewById(R.id.tvTotalUser);

        progressGedung =
                findViewById(R.id.progressGedung);

        progressPinjam =
                findViewById(R.id.progressPinjam);

        imgProfile =
                findViewById(R.id.imgProfile);

        btnBack =
                findViewById(R.id.btnBack);

        cardRuang =
                findViewById(R.id.cardRuang);

        cardPeminjaman =
                findViewById(R.id.cardPeminjaman);

        cardUser =
                findViewById(R.id.cardUser);

        cardMonitoring =
                findViewById(R.id.cardMonitoring);

        cardProfile =
                findViewById(R.id.cardProfile);
    }

    private void setupClick() {

        btnBack.setOnClickListener(v ->
                finish()
        );

        imgProfile.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ProfileAdminActivity.class
                        )
                )
        );

        cardProfile.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ProfileAdminActivity.class
                        )
                )
        );

        cardRuang.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            this,
                            GedungActivity.class
                    );

            intent.putExtra("mode", "admin");

            startActivity(intent);
        });

        cardPeminjaman.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                PeminjamanActivity.class
                        )
                )
        );

        cardUser.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                UserActivity.class
                        )
                )
        );

        cardMonitoring.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                JadwalKelasAdminActivity.class
                        )
                )
        );
    }

    private void setUserData() {

        String username =
                session.getUsername();

        if (username == null || username.isEmpty()) {
            username = "Admin";
        }

        tvAdminName.setText(
                "Halo, " + username
        );
    }

    private void loadDashboard() {

        ApiService api =
                RetrofitClient
                        .getRetrofitInstance()
                        .create(ApiService.class);

        // TOTAL GEDUNG

        api.getAllRuang().enqueue(
                new Callback<List<Ruang>>() {

                    @Override
                    public void onResponse(
                            Call<List<Ruang>> call,
                            Response<List<Ruang>> response
                    ) {

                        int total = 0;

                        if (response.body() != null) {
                            total = response.body().size();
                        }

                        animateNumber(
                                tvTotalRuang,
                                total,
                                " Ruang"
                        );

                        progressGedung.setProgress(
                                Math.min(total * 10, 100)
                        );
                    }

                    @Override
                    public void onFailure(
                            Call<List<Ruang>> call,
                            Throwable t
                    ) {

                        animateNumber(
                                tvTotalRuang,
                                0,
                                " Ruang"
                        );
                    }
                });

        // TOTAL USER

        api.getUser().enqueue(
                new Callback<List<User>>() {

                    @Override
                    public void onResponse(
                            Call<List<User>> call,
                            Response<List<User>> response
                    ) {

                        int total = 0;

                        if (response.body() != null) {
                            total = response.body().size();
                        }

                        animateNumber(
                                tvTotalUser,
                                total,
                                " User"
                        );
                    }

                    @Override
                    public void onFailure(
                            Call<List<User>> call,
                            Throwable t
                    ) {

                        animateNumber(
                                tvTotalUser,
                                0,
                                " User"
                        );
                    }
                });

        // TOTAL PEMINJAMAN

        api.getPeminjaman().enqueue(
                new Callback<List<Peminjaman>>() {

                    @Override
                    public void onResponse(
                            Call<List<Peminjaman>> call,
                            Response<List<Peminjaman>> response
                    ) {

                        int total = 0;

                        if (response.body() != null) {
                            total = response.body().size();
                        }

                        animateNumber(
                                tvTotalPinjam,
                                total,
                                " Peminjaman"
                        );

                        progressPinjam.setProgress(
                                Math.min(total * 5, 100)
                        );
                    }

                    @Override
                    public void onFailure(
                            Call<List<Peminjaman>> call,
                            Throwable t
                    ) {

                        animateNumber(
                                tvTotalPinjam,
                                0,
                                " Peminjaman"
                        );
                    }
                });
    }

    private void animateNumber(
            TextView tv,
            int target,
            String suffix
    ) {

        ValueAnimator animator =
                ValueAnimator.ofInt(0, target);

        animator.setDuration(700);

        animator.addUpdateListener(animation -> {

            int value =
                    (int) animation.getAnimatedValue();

            tv.setText(value + suffix);
        });

        animator.start();
    }
}