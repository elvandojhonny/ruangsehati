package com.example.peminjamanruang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.adapter.JadwalKelasAdapter;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.Peminjaman;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalKelasAdminActivity extends AppCompatActivity {

    RecyclerView recyclerJadwal;

    JadwalKelasAdapter adapter;

    List<Peminjaman> list;

    TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kelas_admin);

        initView();

        setupRecycler();

        setupClick();

        loadData();
    }

    private void initView(){

        recyclerJadwal =
                findViewById(R.id.recyclerJadwal);

        btnBack =
                findViewById(R.id.btnBack);
    }

    private void setupRecycler(){

        recyclerJadwal.setLayoutManager(
                new LinearLayoutManager(this)
        );

        list = new ArrayList<>();

        adapter =
                new JadwalKelasAdapter(
                        this,
                        list
                );

        recyclerJadwal.setAdapter(adapter);
    }

    private void setupClick(){

        btnBack.setOnClickListener(v ->
                finish()
        );
    }

    private void loadData(){

        ApiService api =
                RetrofitClient
                        .getRetrofitInstance()
                        .create(ApiService.class);

        api.getPeminjaman()
                .enqueue(new Callback<List<Peminjaman>>() {

                    @Override
                    public void onResponse(
                            Call<List<Peminjaman>> call,
                            Response<List<Peminjaman>> response
                    ) {

                        if(response.body() != null){

                            list.clear();

                            list.addAll(response.body());

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<Peminjaman>> call,
                            Throwable t
                    ) {

                    }
                });
    }
}