package com.example.sensorornek;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class AccelerometerActivity extends AppCompatActivity {

    private TextView accelData;
    private Handler handler;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accelerometer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accelData = findViewById(R.id.accelData);

        // Rastgele sayılar için Random nesnesi
        random = new Random();

        // Handler ile belirli aralıklarla veriyi güncelleme
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simulateAccelerometerData();
                handler.postDelayed(this, 1000); // 1 saniye arayla veriyi güncelle
            }
        }, 1000); // Başlangıçta 1 saniye sonra başla
    }
    private void simulateAccelerometerData() {
        // X, Y, Z değerlerini rastgele üret
        float x = random.nextFloat() * 10 - 5;  // -5 ile 5 arasında rastgele değer
        float y = random.nextFloat() * 10 - 5;
        float z = random.nextFloat() * 10 - 5;

        // Veriyi ekranda göster
        String data = "X: " + x + "\nY: " + y + "\nZ: " + z;
        accelData.setText(data);
    }
}