package com.example.peminjamanruang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.activity.EditRuangActivity;
import com.example.peminjamanruang.activity.PeminjamanUserActivity;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.model.Ruang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RuangAdapter
        extends RecyclerView.Adapter<RuangAdapter.ViewHolder> {

    private final Context context;
    private final List<Ruang> ruangList;
    private final String role;

    // tambah ini
    private final String namaGedung;

    // constructor baru
    public RuangAdapter(
            Context context,
            List<Ruang> ruangList,
            String role,
            String namaGedung
    ) {

        this.context = context;
        this.ruangList = ruangList;
        this.role = role;
        this.namaGedung = namaGedung;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ruang, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Ruang ruang = ruangList.get(position);

        holder.tvNamaRuang.setText(ruang.getNamaRuang());

        holder.tvLokasi.setText(
                "Lokasi: " + ruang.getLokasi()
        );

        holder.tvKapasitas.setText(
                "Kapasitas: " + ruang.getKapasitas()
        );

        if (role.equals("admin")) {

            holder.btnEditRuang.setVisibility(View.VISIBLE);
            holder.btnHapusRuang.setVisibility(View.VISIBLE);
            holder.btnAjukan.setVisibility(View.GONE);

        } else {

            holder.btnEditRuang.setVisibility(View.GONE);
            holder.btnHapusRuang.setVisibility(View.GONE);
            holder.btnAjukan.setVisibility(View.VISIBLE);
        }

        holder.btnEditRuang.setOnClickListener(v -> {

            context.startActivity(
                    new Intent(context, EditRuangActivity.class)
            );
        });

        holder.btnHapusRuang.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Hapus Ruang")
                    .setMessage("Yakin ingin menghapus ruang ini?")
                    .setPositiveButton("Ya",
                            (dialog, which) -> {

                                hapusRuangDariServer(
                                        ruang.getId(),
                                        position
                                );
                            })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        // tombol ajukan
        holder.btnAjukan.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    PeminjamanUserActivity.class
            );

            // kirim nama gedung
            intent.putExtra(
                    "nama_gedung",
                    namaGedung
            );

            // kirim nama ruang
            intent.putExtra(
                    "nama_ruang",
                    ruang.getNamaRuang()
            );

            context.startActivity(intent);
        });
    }

    private void hapusRuangDariServer(
            String id,
            int position
    ) {

        ApiService api = RetrofitClient
                .getRetrofitInstance()
                .create(ApiService.class);

        api.hapusRuang(id)
                .enqueue(new Callback<ApiResponse>() {

                    @Override
                    public void onResponse(
                            Call<ApiResponse> call,
                            Response<ApiResponse> response
                    ) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            if (response.body().isSuccess()) {

                                ruangList.remove(position);

                                notifyItemRemoved(position);

                                Toast.makeText(
                                        context,
                                        response.body().getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {

                                Toast.makeText(
                                        context,
                                        response.body().getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ApiResponse> call,
                            Throwable t
                    ) {

                        Toast.makeText(
                                context,
                                "Gagal hapus: " + t.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return ruangList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvNamaRuang, tvLokasi, tvKapasitas;

        Button btnEditRuang,
                btnHapusRuang,
                btnAjukan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaRuang =
                    itemView.findViewById(R.id.tvNamaRuang);

            tvLokasi =
                    itemView.findViewById(R.id.tvLokasi);

            tvKapasitas =
                    itemView.findViewById(R.id.tvKapasitas);

            btnEditRuang =
                    itemView.findViewById(R.id.btnEditRuang);

            btnHapusRuang =
                    itemView.findViewById(R.id.btnHapusRuang);

            btnAjukan =
                    itemView.findViewById(R.id.btnAjukan);
        }
    }
}