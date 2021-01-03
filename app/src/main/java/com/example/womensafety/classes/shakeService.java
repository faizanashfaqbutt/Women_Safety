package com.example.womensafety.classes;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;


import com.example.womensafety.R;
import com.example.womensafety.sendDistressCall;
import com.squareup.seismic.ShakeDetector;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.womensafety.App.CHANNEL_ID;
public class shakeService extends Service implements ShakeDetector.Listener {
    boolean shaked=false;
    MediaPlayer mediaPlayer;
    SensorManager sensor_manager;
    Sensor acc_sensor;
    boolean is_sensor_available,not_first_time=false;
    float current_x, current_y, current_z, last_x, last_y, last_z, x_diff, y_diff, z_diff;
    float threshold=5f;
    Vibrator vibrator;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
//        sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        if(sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
//            acc_sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//            Toast.makeText(this, "sensor available", Toast.LENGTH_SHORT).show();
//            is_sensor_available=true;
//        }else {
//            Toast.makeText(this, "no sensor available", Toast.LENGTH_SHORT).show();
//            is_sensor_available=false;
//        }

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

    @Override
    public void hearShake() {
        if(!shaked) {
            shaked=true;
            mediaPlayer.start();
            startActivity(new Intent(this.getApplicationContext(), sendDistressCall.class));
        }
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        current_x=event.values[0];
//        current_y=event.values[1];
//        current_z=event.values[2];
//        Toast.makeText(this, String.valueOf(current_x), Toast.LENGTH_SHORT).show();
//        if (not_first_time){
//            x_diff= Math.abs(last_x-current_x);
//            y_diff= Math.abs(last_y-current_y);
//            z_diff= Math.abs(last_z-current_z);
//            if((x_diff>threshold && y_diff>threshold)||(x_diff>threshold && z_diff>threshold)||(y_diff>threshold && z_diff>threshold)){
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//                    vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
//                    Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();
//                }else{
//                    vibrator.vibrate(500);
//                    Toast.makeText(this, "das", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//        last_x=current_x;
//        last_y=current_y;
//        last_z=current_z;
//        not_first_time=true;
//    }

//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
}
