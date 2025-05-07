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
    DBHelper dbHelper;

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
        dbHelper = new DBHelper(this);
        // getSupportActionBar().hide(); // Eğer Action Bar'ı gizlemek istersem, bu satırı açacağım
        // Intent ile gelen verileri alıyoruz
        int totalScore=getIntent().getIntExtra("total",0);
        int correctAnsw=getIntent().getIntExtra("score",0);


        // Yanlış cevap sayısını hesaplıyoruz
        int wrong=totalScore-correctAnsw;
        binding.totalQuestions.setText(String.valueOf(totalScore));
        binding.rightAnsw.setText(String.valueOf(correctAnsw));
        binding.wrongAnsw.setText(String.valueOf(wrong));
        // Kullanıcının nickname'ini SharedPreferences ile alıyoruz
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String nickname = sharedPreferences.getString("nickname", "Unknown");

        // Skoru SQLite veritabanına ekliyoruz
        dbHelper.addScore(nickname, correctAnsw);





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