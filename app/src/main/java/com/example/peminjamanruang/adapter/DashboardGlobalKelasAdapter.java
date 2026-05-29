package com.example.peminjamanruang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.model.Peminjaman;
import com.example.peminjamanruang.util.DashboardGlobalDataHelper;

import java.util.ArrayList;
import java.util.List;

public class DashboardGlobalKelasAdapter
        extends RecyclerView.Adapter<DashboardGlobalKelasAdapter.RowVH> {

    private final LayoutInflater inflater;
    private final List<Peminjaman> data = new ArrayList<>();

    public DashboardGlobalKelasAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void submit(List<Peminjaman> rows) {

        data.clear();

        if (rows != null) {
            data.addAll(rows);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RowVH onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View v = inflater.inflate(
                R.layout.item_dashboard_global_table_row,
                parent,
                false
        );

        return new RowVH(v);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RowVH holder,
            int position
    ) {

        Peminjaman p = data.get(position);

        holder.tvNama.setText(
                emptyDash(p.getNamaRuang())
        );

        holder.tvGedung.setText(
                emptyDash(p.getNamaGedung())
        );

        holder.tvTanggal.setText(
                emptyDash(p.getTanggal())
        );

        // tampilkan jam mulai - jam selesai
        String jamMulai = p.getJamMulai();
        String jamSelesai = p.getJamSelesai();

        if (
                TextUtils.isEmpty(jamMulai) ||
                        TextUtils.isEmpty(jamSelesai)
        ) {

            holder.tvJam.setText("—");

        } else {

            holder.tvJam.setText(
                    jamMulai + " - " + jamSelesai
            );
        }

        DashboardGlobalDataHelper.StatusBucket bucket =
                DashboardGlobalDataHelper.bucketForApiStatus(
                        p.getStatus()
                );

        int labelRes =
                DashboardGlobalDataHelper.statusLabelRes(bucket);

        if (labelRes != 0) {

            holder.tvStatus.setText(labelRes);

        } else {

            String raw = p.getStatus();

            holder.tvStatus.setText(
                    TextUtils.isEmpty(raw) ? "—" : raw
            );
        }

        holder.tvStatus.setBackgroundResource(
                DashboardGlobalDataHelper.statusBackgroundRes(bucket)
        );

        String borrower = p.getNamaPeminjam();

        holder.tvPeminjam.setText(
                TextUtils.isEmpty(borrower)
                        ? "—"
                        : borrower
        );
    }

    private static String emptyDash(String s) {
        return TextUtils.isEmpty(s) ? "—" : s;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class RowVH extends RecyclerView.ViewHolder {

        final TextView tvNama;
        final TextView tvGedung;
        final TextView tvTanggal;
        final TextView tvJam;
        final TextView tvStatus;
        final TextView tvPeminjam;

        RowVH(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNamaKelas);
            tvGedung = itemView.findViewById(R.id.tvGedung);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvJam = itemView.findViewById(R.id.tvJam);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPeminjam = itemView.findViewById(R.id.tvPeminjam);
        }
    }
}