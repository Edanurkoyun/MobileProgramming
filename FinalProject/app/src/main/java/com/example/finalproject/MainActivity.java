package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CardView history,science,mathematics,geography;
    TextView categoryname;
    Button btnsettings;

    private MusicManager musicManager;


    @SuppressLint("MissingInflatedId")
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


        history=findViewById(R.id.history);
       science=findViewById(R.id.science);
       mathematics=findViewById(R.id.mathematics);
       geography=findViewById(R.id.geography);
       categoryname=findViewById(R.id.textView);
       btnsettings=findViewById(R.id.btnsettings);





       history.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(MainActivity.this,SetsActivity.class);
               startActivity(intent);
               categoryname.setText("History");

           }
       });

       science.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             //  Intent intent= new Intent(MainActivity.this,SetsActivity.class);
             //  startActivity(intent);
               categoryname.setText("Science");
               Toast.makeText(getApplicationContext(), "Please complete the History section first.", Toast.LENGTH_SHORT).show();
               science.setEnabled(false);

           }
       });



       mathematics.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            //   Intent intent= new Intent(MainActivity.this,SetsActivity.class);
           //    startActivity(intent);
               categoryname.setText("Mathematics");
               Toast.makeText(getApplicationContext(), "Please complete the History section first.", Toast.LENGTH_SHORT).show();
               mathematics.setEnabled(false);

           }
       });
       geography.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             //  Intent intent= new Intent(MainActivity.this,SetsActivity.class);
            //   startActivity(intent);
               categoryname.setText("Geography");
               Toast.makeText(getApplicationContext(), "Please complete the History section first.", Toast.LENGTH_SHORT).show();
               geography.setEnabled(false);

           }
       });

       btnsettings.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(MainActivity.this,SettingActivity.class);
               startActivity(intent);

           }
       });




    }







}