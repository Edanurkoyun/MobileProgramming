package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
   private Button btnLogin;
   private EditText editNickname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLogin = findViewById(R.id.btnlogin);
        editNickname=findViewById(R.id.editnickname);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = editNickname.getText().toString().trim();
                if (!nickname.isEmpty()) {
                    SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("nickname", nickname);
                    editor.apply();
                    Log.d("LoginActivity", "Saved nickname: " + nickname);


                    // MainActivity'ye geçiş yap
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("nickname", nickname); // Nickname’i taşı
                    startActivity(intent);
                    String storedNickname = prefs.getString("nickname", "Unknown Player");
                    intent.putExtra("nickname", storedNickname);

                    Log.d("LoginActivity", "Sent nickname to ScoreActivity: " + nickname);

                } else {
                    Toast.makeText(LoginActivity.this, "Please enter a nickname", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}