package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
    ActivityScoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityScoreBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       // getSupportActionBar().hide(); // Eğer Action Bar'ı gizlemek istersem, bu satırı açacağım
        // Intent ile gelen verileri alıyoruz
        int totalScore=getIntent().getIntExtra("total",0);
        int correctAnsw=getIntent().getIntExtra("score",0);
        String nickname = getIntent().getStringExtra("nickname");

        // Yanlış cevap sayısını hesaplıyoruz
        int wrong=totalScore-correctAnsw;
        binding.totalQuestions.setText(String.valueOf(totalScore));
        binding.rightAnsw.setText(String.valueOf(correctAnsw));
        binding.wrongAnsw.setText(String.valueOf(wrong));
        SharedPreferences quizPrefs = getSharedPreferences("QuizScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = quizPrefs.edit();
        if (nickname != null && !nickname.isEmpty()) {
            editor.putInt(nickname, correctAnsw); // Kullanıcı adı ile score eşleştir
            editor.apply();
            Log.d("ScoreActivity", "Saved score: " + correctAnsw + " for " + nickname);
        } else {
            Log.e("ScoreActivity", "Nickname is null! Score not saved.");
        }



        binding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ScoreActivity.this,SetsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Finish")
                        .setMessage("Are You Sure Finish?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ScoreActivity.this, ScoreTableActivity.class);
                                intent.putExtra("score", correctAnsw); // Gerekli değil, çünkü ScoreTableActivity SharedPreferences'tan alıyor
                                startActivity(intent);
                                finish(); // ScoreActivity'den çıkış yap
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


    }
}