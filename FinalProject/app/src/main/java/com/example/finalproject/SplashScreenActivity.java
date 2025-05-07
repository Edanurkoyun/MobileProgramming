package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private MusicManager musicManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Müzik yöneticisini başlatıyoruz
        musicManager = MusicManager.getInstance(this);
        musicManager.startMusic();


        // 2 saniye sonra MainActivity'ye yönlendirme yapıyoruz
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Splash ekranı bitiminde MainActivity'ye yönlendirme
                Intent intent= new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();// Splash ekranını bitiriyoruz
            }
        },2000);// 2 saniye bekleme süresi
    }
}
