package com.example.finalproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingActivity extends AppCompatActivity {
    Button btnReset,btnExit;
    private Switch musicSwitch;
    private Switch soundSwitch;
    private MusicManager musicManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

          btnReset=findViewById(R.id.btnReset);
          btnExit=findViewById(R.id.btnExit);
           musicSwitch = findViewById(R.id.musicswitch);
           soundSwitch=findViewById(R.id.soundswitch);
        musicManager = MusicManager.getInstance(this);


        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Müzik başlat
                    musicManager.stopMusic();

                } else {
                    // Müzik durdur

                    musicManager.startMusic();
                }

            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    musicManager.stopSound();
                } else {

                    musicManager.playSound();
                }

            }
        });



        btnExit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent= new Intent(SettingActivity.this,MainActivity.class);
                  startActivity(intent);
              }
          });




    }
}