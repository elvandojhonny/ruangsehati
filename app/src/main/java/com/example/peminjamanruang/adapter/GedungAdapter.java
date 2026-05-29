package com.example.peminjamanruang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peminjamanruang.R;
import com.example.peminjamanruang.activity.RuangActivity;
import com.example.peminjamanruang.model.Gedung;

import java.util.List;

public class GedungAdapter extends RecyclerView.Adapter<GedungAdapter.ViewHolder> {

    Context context;
    List<Gedung> list;
    String role;

    public GedungAdapter(Context context, List<Gedung> list, String role) {
        this.context = context;
        this.list = list;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_gedung, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Gedung gedung = list.get(position);

        holder.tvNamaGedung.setText(gedung.getNamaGedung());
        holder.tvFakultas.setText(gedung.getFakultas());

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, RuangActivity.class);

            intent.putExtra("id_gedung", gedung.getId());
            intent.putExtra("nama_gedung", gedung.getNamaGedung());
            intent.putExtra("role_mode", role);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaGedung, tvFakultas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaGedung = itemView.findViewById(R.id.tvNamaGedung);
            tvFakultas = itemView.findViewById(R.id.tvFakultas);
        }
    }
}