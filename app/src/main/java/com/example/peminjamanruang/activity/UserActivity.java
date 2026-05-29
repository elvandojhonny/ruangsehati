package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.adapter.UserAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerUser;
    UserAdapter userAdapter;
    List<User> userList;

    Button btnTambahUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        View btnBack = findViewById(R.id.btnBack);

        recyclerUser = findViewById(R.id.recyclerUser);
        btnTambahUser = findViewById(R.id.btnTambahUser);

        btnBack.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();

        userAdapter = new UserAdapter(this, userList);
        recyclerUser.setAdapter(userAdapter);

        btnTambahUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, TambahUserActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // otomatis refresh setiap kembali ke halaman ini
        loadUser();
    }

    private void loadUser() {

        ApiService apiService = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        Call<List<User>> call = apiService.getUser();

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    userList.clear();
                    userList.addAll(response.body());

                    userAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(
                            UserActivity.this,
                            "Gagal load user",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Toast.makeText(
                        UserActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}