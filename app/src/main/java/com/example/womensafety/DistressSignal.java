package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.womensafety.classes.shakeService;

public class DistressSignal extends AppCompatActivity {
    Button startServiceBtn;
    Button stopServiceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distress_signal);
        startServiceBtn=(Button)findViewById(R.id.startServiceBtn);
        stopServiceBtn=(Button)findViewById(R.id.stopServiceBtn);

        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(),shakeService.class));
            }
        });

        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),shakeService.class));

            }
        });
    }
}