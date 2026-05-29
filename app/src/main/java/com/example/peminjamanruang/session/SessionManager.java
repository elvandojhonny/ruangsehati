package com.example.peminjamanruang.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.peminjamanruang.activity.LoginActivity;

public class SessionManager {

    private static final String PREF_NAME = "LOGIN_SESSION";

    private static final String LOGIN = "IS_LOGIN";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {

        this.context = context;

        pref = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );

        editor = pref.edit();
    }

    public void createSession(
            String id,
            String nama,
            String nim,
            String email,
            String prodi,
            String fakultas,
            String noHp,
            String alamat,
            String username,
            String role
    ) {

        editor.putBoolean(LOGIN, true);

        editor.putString("id", id);

        editor.putString("nama", nama);
        editor.putString("nim", nim);
        editor.putString("email", email);
        editor.putString("prodi", prodi);
        editor.putString("fakultas", fakultas);
        editor.putString("no_hp", noHp);
        editor.putString("alamat", alamat);

        editor.putString("username", username);
        editor.putString("role", role);

        editor.apply();
    }

    public boolean isLogin() {
        return pref.getBoolean(LOGIN, false);
    }

    public String getId() {
        return pref.getString("id", "");
    }

    public String getNama() {
        return pref.getString("nama", "");
    }

    public String getNim() {
        return pref.getString("nim", "");
    }

    public String getEmail() {
        return pref.getString("email", "");
    }

    public String getProdi() {
        return pref.getString("prodi", "");
    }

    public String getFakultas() {
        return pref.getString("fakultas", "");
    }

    public String getNoHp() {
        return pref.getString("no_hp", "");
    }

    public String getAlamat() {
        return pref.getString("alamat", "");
    }

    public String getUsername() {
        return pref.getString("username", "");
    }

    public String getRole() {
        return pref.getString("role", "");
    }

    public String getUserId() {
        String id = pref.getString("id", "");
        return (id == null || id.isEmpty()) ? null : id;
    }

    public void logout() {

        editor.clear();
        editor.apply();

        Intent intent =
                new Intent(context, LoginActivity.class);

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        context.startActivity(intent);
    }
}