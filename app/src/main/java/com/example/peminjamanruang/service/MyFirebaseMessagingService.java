package com.example.peminjamanruang.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.peminjamanruang.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService
        extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {

        Toast.makeText(
                this,
                "FCM MASUK",
                Toast.LENGTH_LONG
        ).show();

        super.onMessageReceived(message);

        Log.d("FCM_DEBUG", "Notif masuk");

        String title = "";
        String body = "";

        // =========================
        // AMBIL ISI NOTIF
        // =========================

        if (message.getNotification() != null) {

            title = message
                    .getNotification()
                    .getTitle();

            body = message
                    .getNotification()
                    .getBody();
        }

        // =========================
        // CHANNEL NOTIF
        // =========================

        String channelId = "peminjaman_channel";

        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE
                        );

        if (
                Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.O
        ) {

            NotificationChannel channel =
                    new NotificationChannel(
                            channelId,
                            "Peminjaman",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            channel.setDescription(
                    "Notifikasi peminjaman ruang"
            );

            manager.createNotificationChannel(
                    channel
            );
        }

        // =========================
        // BUILD NOTIF
        // =========================

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        this,
                        channelId
                )

                        .setSmallIcon(R.mipmap.ic_launcher)

                        .setContentTitle(title)

                        .setContentText(body)

                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        )

                        .setAutoCancel(true);

        // =========================
        // TAMPILKAN NOTIF
        // =========================

        manager.notify(
                (int) System.currentTimeMillis(),
                builder.build()
        );
    }
}