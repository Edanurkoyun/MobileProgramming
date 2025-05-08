package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ScoreTableActivity extends AppCompatActivity {

    ListView leaderboardListView;

    ArrayAdapter<String> adapter;
    DBHelper dbHelper;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_table);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        leaderboardListView = findViewById(R.id.leaderboardListView);
        dbHelper = new DBHelper(this); // Veritabanını başlat

        // Veritabanından sıralanmış skor listesini al
        List<String> scores = dbHelper.getScores();

        // 🟢 Tüm kayıtlı kullanıcıları SharedPreferences içinden çek
        CustomAdapter adapter = new CustomAdapter(this, scores);
        leaderboardListView.setAdapter(adapter);
 btnback=findViewById(R.id.btnback);
 btnback.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent=new Intent(ScoreTableActivity.this,MainActivity.class);
         startActivity(intent);
     }
 });






    }
}