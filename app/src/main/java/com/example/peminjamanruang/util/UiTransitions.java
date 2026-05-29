package com.example.peminjamanruang.util;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peminjamanruang.R;

public final class UiTransitions {

    private UiTransitions() {
    }

    public static void startWithFade(@NonNull AppCompatActivity from, @NonNull Intent intent, boolean finishFrom) {
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (finishFrom) {
            from.finish();
        }
    }
}
