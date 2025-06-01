package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalproject.Adapters.SetAdapter;
import com.example.finalproject.Models.SetModel;
import com.example.finalproject.databinding.ActivitySetsBinding;

import java.util.ArrayList;



public class SetsActivity extends AppCompatActivity {
 ActivitySetsBinding binding;
 ArrayList<SetModel> list;

 Button backbtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewBinding kullanılarak layout dosyasını bağlama işlemi yapılıyor
        binding = ActivitySetsBinding.inflate(getLayoutInflater());
        // Kodun içinden daha kolay ve daha basit erişmek için kullandım
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.set_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backbtn=findViewById(R.id.backbtn);

        list = new ArrayList<>();
        // RecyclerView için LinearLayoutManager kullanıyoruz
        // RecyclerView için bir layout yöneticisi belirliyoruz
        // LinearLayoutManager, verileri dikey veya yatay olarak sıralamak için kullanılır
        // Burada dikey bir liste görünümü oluşturmak için kullanılıyor
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        binding.setRecy.setLayoutManager(linearLayoutManager);

        // SET'leri listeye ekliyoruz
        list.add(new SetModel("SET-1"));
        list.add(new SetModel("SET-2"));
        list.add(new SetModel("SET-3"));
        list.add(new SetModel("SET-4"));
        list.add(new SetModel("SET-5"));
        list.add(new SetModel("SET-6"));
        list.add(new SetModel("SET-7"));
        list.add(new SetModel("SET-8"));
        list.add(new SetModel("SET-9"));
        list.add(new SetModel("SET-10"));



        // SetAdapter sınıfını kullanarak verileri RecyclerView'da göstermek için adaptörü tanımlıyoruz
        SetAdapter adapter= new SetAdapter(this,list);
        binding.setRecy.setAdapter(adapter);

        // Geri butonuna tıklama işlemi
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SetsActivity.this,MainActivity.class);

                startActivity(intent);
            }
        });



    }
}