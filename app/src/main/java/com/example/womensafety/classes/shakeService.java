package com.example.womensafety.classes;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;


import com.example.womensafety.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.womensafety.App.CHANNEL_ID;
public class shakeService extends Service {
    MediaPlayer mediaPlayer;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("input")
                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
