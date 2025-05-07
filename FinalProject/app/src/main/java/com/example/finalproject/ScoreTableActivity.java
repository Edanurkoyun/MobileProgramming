package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
        leaderboardListView.setAdapter(adapter);

    }
}