package com.example.sensorornek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button accelbtn,compbtn,gyrobtn,humibtn,lightbtn,magnbtn,pressbtn,proxbtn,thermbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        accelbtn=findViewById(R.id.accelbtn);
        compbtn=findViewById(R.id.compbtn);
        gyrobtn=findViewById(R.id.gyrobtn);
        humibtn=findViewById(R.id.humibtn);
        lightbtn=findViewById(R.id.lightbtn);
        magnbtn=findViewById(R.id.magnbtn);
        pressbtn=findViewById(R.id.pressbtn);
        proxbtn=findViewById(R.id.proxbtn);
        thermbtn=findViewById(R.id.thermbtn);

        accelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AccelerometerActivity.class);
                startActivity(intent);
            }
        });
        compbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CompassActivity.class);
                startActivity(intent);
            }
        });
        gyrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GyroscopeActivity.class);
                startActivity(intent);
            }
        });
        humibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HumidityActivity.class);
                startActivity(intent);
            }
        });
        lightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LightActivity.class);
                startActivity(intent);
            }
        });
        pressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PressureActivity.class);
                startActivity(intent);
            }
        });
        proxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProximityActivity.class);
                startActivity(intent);
            }
        });
        thermbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ThermoActivity.class);
                startActivity(intent);
            }
        });

    }
}