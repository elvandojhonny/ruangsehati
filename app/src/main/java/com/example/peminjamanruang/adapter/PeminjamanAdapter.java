package com.example.peminjamanruang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.model.Peminjaman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanAdapter
        extends RecyclerView.Adapter<PeminjamanAdapter.ViewHolder> {

    private final Context context;
    private final List<Peminjaman> peminjamanList;
    private final String role;

    public PeminjamanAdapter(
            Context context,
            List<Peminjaman> peminjamanList,
            String role
    ) {

        this.context = context;
        this.peminjamanList = peminjamanList;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_peminjaman, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Peminjaman peminjaman = peminjamanList.get(position);

        holder.tvNamaPeminjam.setText(
                peminjaman.getNamaPeminjam()
        );

        holder.tvGedung.setText(
                "Gedung: " + peminjaman.getNamaGedung()
        );

        holder.tvNamaRuang.setText(
                "Ruang: " + peminjaman.getNamaRuang()
        );

        holder.tvTanggal.setText(
                "Tanggal: " + peminjaman.getTanggal()
        );

        holder.tvHari.setText(
                "Hari: " + peminjaman.getHari()
        );

        holder.tvJam.setText(
                "Jam: "
                        + peminjaman.getJamMulai()
                        + " - "
                        + peminjaman.getJamSelesai()
        );

        holder.tvKeterangan.setText(
                "Keterangan: " + peminjaman.getKeterangan()
        );

        holder.tvStatus.setText(
                "Status: " + peminjaman.getStatus()
        );

        if (peminjaman.getAlasanDitolak() == null
                || peminjaman.getAlasanDitolak().isEmpty()) {

            holder.tvAlasan.setText("Alasan: -");

        } else {

            holder.tvAlasan.setText(
                    "Alasan: "
                            + peminjaman.getAlasanDitolak()
            );
        }

        // =========================
        // TOMBOL HAPUS
        // =========================

        if (role.equals("admin")) {

            holder.btnHapus.setVisibility(View.VISIBLE);

        } else {

            holder.btnHapus.setVisibility(View.GONE);
        }

        holder.btnHapus.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Hapus Riwayat")
                    .setMessage("Yakin ingin menghapus riwayat ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {

                        ApiService apiService = RetrofitClient
                                .getRetrofitInstance()
                                .create(ApiService.class);

                        Call<ApiResponse> call =
                                apiService.hapusPeminjaman(
                                        peminjaman.getId()
                                );

                        call.enqueue(new Callback<ApiResponse>() {

                            @Override
                            public void onResponse(
                                    Call<ApiResponse> call,
                                    Response<ApiResponse> response
                            ) {

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    int currentPosition =
                                            holder.getAdapterPosition();

                                    peminjamanList.remove(currentPosition);

                                    notifyItemRemoved(currentPosition);

                                    notifyItemRangeChanged(
                                            currentPosition,
                                            peminjamanList.size()
                                    );

                                    Toast.makeText(
                                            context,
                                            response.body().getMessage(),
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
                                        context,
                                        t.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        // =========================
        // TOMBOL ADMIN
        // =========================

        if (role.equals("admin")) {

            if (peminjaman.getStatus()
                    .equalsIgnoreCase("Menunggu")) {

                holder.btnSetuju.setVisibility(View.VISIBLE);
                holder.btnTolak.setVisibility(View.VISIBLE);

            } else {

                holder.btnSetuju.setVisibility(View.GONE);
                holder.btnTolak.setVisibility(View.GONE);
            }

        } else {

            holder.btnSetuju.setVisibility(View.GONE);
            holder.btnTolak.setVisibility(View.GONE);
        }

        // =========================
        // SETUJUI
        // =========================

        holder.btnSetuju.setOnClickListener(view -> {

            int currentPosition = holder.getAdapterPosition();

            ApiService apiService = RetrofitClient
                    .getRetrofitInstance()
                    .create(ApiService.class);

            // =========================
            // CEK BENTROK JADWAL
            // =========================

            Call<ApiResponse> cekCall =
                    apiService.cekBentrokJadwal(
                            peminjaman.getId(),
                            peminjaman.getNamaRuang(),
                            peminjaman.getTanggal(),
                            peminjaman.getJamMulai(),
                            peminjaman.getJamSelesai()
                    );

            cekCall.enqueue(new Callback<ApiResponse>() {

                @Override
                public void onResponse(
                        Call<ApiResponse> call,
                        Response<ApiResponse> response
                ) {

                    if(response.body() != null){

                        if(!response.body().isSuccess()){

                            new AlertDialog.Builder(context)
                                    .setTitle("Jadwal Bentrok")
                                    .setMessage(
                                            "Kelas ini sudah digunakan pada jam tersebut"
                                    )
                                    .setPositiveButton("OK", null)
                                    .show();

                            return;
                        }

                        // =========================
                        // LANJUT SETUJUI
                        // =========================

                        Call<ApiResponse> updateCall =
                                apiService.updateStatusPeminjaman(
                                        peminjaman.getId(),
                                        "Disetujui",
                                        "-"
                                );

                        updateCall.enqueue(new Callback<ApiResponse>() {

                            @Override
                            public void onResponse(
                                    Call<ApiResponse> call,
                                    Response<ApiResponse> response
                            ) {

                                peminjaman.setStatus("Disetujui");
                                peminjaman.setAlasanDitolak("-");

                                holder.btnSetuju.setVisibility(View.GONE);
                                holder.btnTolak.setVisibility(View.GONE);

                                notifyItemChanged(currentPosition);

                                Toast.makeText(
                                        context,
                                        "Peminjaman disetujui",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onFailure(
                                    Call<ApiResponse> call,
                                    Throwable t
                            ) {

                                Toast.makeText(
                                        context,
                                        t.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(
                        Call<ApiResponse> call,
                        Throwable t
                ) {

                    Toast.makeText(
                            context,
                            t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });

        // =========================
        // TOLAK
        // =========================

        holder.btnTolak.setOnClickListener(v -> {

            int currentPosition =
                    holder.getAdapterPosition();

            EditText etAlasan =
                    new EditText(context);

            new AlertDialog.Builder(context)
                    .setTitle("Alasan Penolakan")
                    .setView(etAlasan)

                    .setPositiveButton("Simpan",
                            (dialog, which) -> {

                                String alasan =
                                        etAlasan
                                                .getText()
                                                .toString()
                                                .trim();

                                ApiService apiService =
                                        RetrofitClient
                                                .getRetrofitInstance()
                                                .create(ApiService.class);

                                Call<ApiResponse> call =
                                        apiService.updateStatusPeminjaman(
                                                peminjaman.getId(),
                                                "Ditolak",
                                                alasan
                                        );

                                call.enqueue(
                                        new Callback<ApiResponse>() {

                                            @Override
                                            public void onResponse(
                                                    Call<ApiResponse> call,
                                                    Response<ApiResponse> response
                                            ) {

                                                if (response.isSuccessful()
                                                        && response.body() != null) {

                                                    peminjaman.setStatus("Ditolak");

                                                    peminjaman.setAlasanDitolak(
                                                            alasan
                                                    );

                                                    holder.btnSetuju
                                                            .setVisibility(View.GONE);

                                                    holder.btnTolak
                                                            .setVisibility(View.GONE);

                                                    notifyItemChanged(
                                                            currentPosition
                                                    );

                                                    Toast.makeText(
                                                            context,
                                                            "Peminjaman ditolak",
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
                                                        context,
                                                        t.getMessage(),
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        });
                            })

                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return peminjamanList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvNamaPeminjam;
        TextView tvGedung;
        TextView tvNamaRuang;
        TextView tvTanggal;
        TextView tvHari;
        TextView tvJam;
        TextView tvKeterangan;
        TextView tvStatus;
        TextView tvAlasan;

        Button btnSetuju;
        Button btnTolak;
        Button btnHapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaPeminjam =
                    itemView.findViewById(R.id.tvNamaPeminjam);

            tvGedung =
                    itemView.findViewById(R.id.tvGedung);

            tvNamaRuang =
                    itemView.findViewById(R.id.tvNamaRuang);

            tvTanggal =
                    itemView.findViewById(R.id.tvTanggal);

            tvHari =
                    itemView.findViewById(R.id.tvHari);

            tvJam =
                    itemView.findViewById(R.id.tvJam);

            tvKeterangan =
                    itemView.findViewById(R.id.tvKeterangan);

            tvStatus =
                    itemView.findViewById(R.id.tvStatus);

            tvAlasan =
                    itemView.findViewById(R.id.tvAlasan);

            btnSetuju =
                    itemView.findViewById(R.id.btnSetuju);

            btnTolak =
                    itemView.findViewById(R.id.btnTolak);

            btnHapus =
                    itemView.findViewById(R.id.btnHapus);
        }
    }
}