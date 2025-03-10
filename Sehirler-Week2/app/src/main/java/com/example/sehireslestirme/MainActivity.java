package com.example.sehireslestirme;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        TextView txt= findViewById(R.id.textView);
        ListView listplaka= findViewById(R.id.listplaka);
        ListView listsehir=findViewById(R.id.listsehir);
        String[] sehirler={"İstanbul","İzmir","Sinop","Antalya","Konya","Samsun","Sivas","Kastamonu","Niğde"};
        int[] plakalar={34,35,57,7,42,55,58,37,51};

        List<Integer> plakaListesi = new ArrayList<>();
        for (int plaka : plakalar) {
            plakaListesi.add(plaka);
        }
        Collections.shuffle(plakaListesi); // Plakaları karıştırıyoruz


        ArrayAdapter<String> datalist=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,sehirler);
        listsehir.setAdapter(datalist);
        ArrayAdapter<Integer> plakaAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, plakaListesi);
        listplaka.setAdapter(plakaAdapter);
        listsehir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String secilenSehir = sehirler[i];  // Seçilen şehir
                Integer dogruPlaka = plakalar[i];  // Şehre ait doğru plaka
                Integer secilenPlaka = plakaListesi.get(i);  // Karıştırılmış plakalardan seçilen plaka

                // Şehir ve plakayı karşılaştır
                if (dogruPlaka.equals(secilenPlaka)) {
                    Toast.makeText(MainActivity.this, "Doğru! " + secilenSehir + " - " + secilenPlaka, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Yanlış! " + secilenSehir + " - " + secilenPlaka, Toast.LENGTH_SHORT).show();
                }

                // Seçilen şehri TextView'e yazma
                txt.setText("Seçilen Şehir: " + secilenSehir + " - Plaka: " + secilenPlaka);
            }
        });

    }
}