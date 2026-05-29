package com.example.peminjamanruang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.model.Peminjaman;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JadwalKelasAdapter
        extends RecyclerView.Adapter<JadwalKelasAdapter.ViewHolder> {

    Context context;

    List<Peminjaman> list;

    public JadwalKelasAdapter(
            Context context,
            List<Peminjaman> list
    ) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(context)
                .inflate(
                        R.layout.item_jadwal_kelas,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Peminjaman item = list.get(position);

        holder.tvGedung.setText(
                item.getNamaGedung()
        );

        holder.tvKelas.setText(
                item.getNamaRuang()
        );

        holder.tvJam.setText(
                item.getJamMulai()
                        + "-"
                        + item.getJamSelesai()
        );

        holder.tvPeminjam.setText(
                item.getNamaPeminjam()
        );

        // STATUS REALTIME

        try {

            SimpleDateFormat sdf =
                    new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm",
                            Locale.getDefault()
                    );

            Date selesai =
                    sdf.parse(
                            item.getTanggal()
                                    + " "
                                    + item.getJamSelesai()
                    );

            Date sekarang = new Date();

            if (
                    selesai != null &&
                            sekarang.after(selesai)
            ) {

                holder.tvStatus.setText("Selesai");

                holder.tvStatus.setTextColor(
                        Color.LTGRAY
                );

            } else {

                holder.tvStatus.setText("Dipinjam");

                holder.tvStatus.setTextColor(
                        Color.parseColor("#4CAF50")
                );
            }

        } catch (Exception e){

            holder.tvStatus.setText(
                    item.getStatus()
            );
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvGedung;
        TextView tvKelas;
        TextView tvJam;
        TextView tvStatus;
        TextView tvPeminjam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGedung =
                    itemView.findViewById(R.id.tvGedung);

            tvKelas =
                    itemView.findViewById(R.id.tvKelas);

            tvJam =
                    itemView.findViewById(R.id.tvJam);

            tvStatus =
                    itemView.findViewById(R.id.tvStatus);

            tvPeminjam =
                    itemView.findViewById(R.id.tvPeminjam);
        }
    }
}