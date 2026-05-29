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
import com.example.peminjamanruang.activity.EditUserActivity;
import com.example.peminjamanruang.api.ApiService;
import com.example.peminjamanruang.api.RetrofitClient;
import com.example.peminjamanruang.model.ApiResponse;
import com.example.peminjamanruang.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> userList;
    private final Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);

        // DATA USER
        holder.tvNama.setText(user.getNama());
        holder.tvNim.setText(user.getNim());

        holder.tvProdi.setText(user.getProdi());
        holder.tvFakultas.setText(user.getFakultas());
        holder.tvEmail.setText(user.getEmail());

        holder.tvRole.setText(user.getRole().toUpperCase());

        // WARNA ROLE
        if (user.getRole().equalsIgnoreCase("admin")) {

            holder.tvRole.setBackgroundResource(R.drawable.bg_role_admin);

        } else {

            holder.tvRole.setBackgroundResource(R.drawable.bg_role_user);
        }

        // EDIT
        holder.btnEdit.setOnClickListener(view -> {

            Intent intent = new Intent(context, EditUserActivity.class);

            intent.putExtra("id", user.getId());

            intent.putExtra("nama", user.getNama());
            intent.putExtra("nim", user.getNim());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("prodi", user.getProdi());
            intent.putExtra("fakultas", user.getFakultas());
            intent.putExtra("no_hp", user.getNoHp());
            intent.putExtra("alamat", user.getAlamat());

            intent.putExtra("username", user.getUsername());
            intent.putExtra("password", user.getPassword());
            intent.putExtra("role", user.getRole());

            context.startActivity(intent);
        });

        // HAPUS
        holder.btnHapus.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Hapus User");
            builder.setMessage("Yakin ingin menghapus user ini?");

            builder.setPositiveButton("Ya", (dialog, which) -> {

                ApiService api = RetrofitClient
                        .getRetrofitInstance()
                        .create(ApiService.class);

                api.hapusUser(user.getId())
                        .enqueue(new Callback<ApiResponse>() {

                            @Override
                            public void onResponse(Call<ApiResponse> call,
                                                   Response<ApiResponse> response) {

                                if (response.body() != null
                                        && response.body().isSuccess()) {

                                    userList.remove(position);

                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(
                                            position,
                                            userList.size()
                                    );

                                    Toast.makeText(
                                            context,
                                            "User berhasil dihapus",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                } else {

                                    Toast.makeText(
                                            context,
                                            "Gagal hapus user",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call,
                                                  Throwable t) {

                                Toast.makeText(
                                        context,
                                        "Error: " + t.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });
            });

            builder.setNegativeButton("Batal", null);

            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvNim, tvRole;
        TextView tvProdi, tvFakultas, tvEmail;

        Button btnEdit, btnHapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvNim = itemView.findViewById(R.id.tvNim);

            tvRole = itemView.findViewById(R.id.tvRole);

            tvProdi = itemView.findViewById(R.id.tvProdi);
            tvFakultas = itemView.findViewById(R.id.tvFakultas);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}