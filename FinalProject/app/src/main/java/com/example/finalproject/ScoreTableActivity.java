package com.example.finalproject;

import android.content.Intent;
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
import java.util.Map;

class PlayerScore {
    String nickname;
    int score;

    public PlayerScore(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }
}

public class ScoreTableActivity extends AppCompatActivity {
    ListView leaderboardListView;
    ArrayList<PlayerScore> scoreList;
    ArrayAdapter<String> adapter;

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
        scoreList = new ArrayList<>();
        SharedPreferences userPrefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String nickname = userPrefs.getString("nickname", "Unknown Player"); // Varsayılan değer eklendi
        Log.d("ScoreTableActivity", "Nickname: " + nickname);

        SharedPreferences quizPrefs = getSharedPreferences("QuizScores", MODE_PRIVATE);
        int latestScore = quizPrefs.getInt("latestScore", 0); // Varsayılan değer olarak 0
        Log.d("ScoreTableActivity", "Latest Score: " + latestScore);



        // Skora göre azalan sırala
        scoreList.add(new PlayerScore(nickname, latestScore));

        // 🟢 SharedPreferences içindeki tüm kayıtlı skorları ekle
        Map<String, ?> allScores = quizPrefs.getAll();
        for (Map.Entry<String, ?> entry : allScores.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                scoreList.add(new PlayerScore(entry.getKey(), (Integer) entry.getValue()));
            }
        }

        // 🔄 Skora göre azalan sırala
        Collections.sort(scoreList, new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore o1, PlayerScore o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });


        // String listesi oluştur ve ListView'a ata
        ArrayList<String> displayList = new ArrayList<>();
        for (PlayerScore p : scoreList) {
            displayList.add(p.getNickname() + " - " + p.getScore() + " puan");
        }



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        leaderboardListView.setAdapter(adapter);

    }
}