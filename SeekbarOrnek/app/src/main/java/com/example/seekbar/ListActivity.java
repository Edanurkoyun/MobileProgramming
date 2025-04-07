package com.example.seekbar;

import android.content.Intent;
import android.os.Bundle;
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

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> liste;
    private ArrayAdapter<String> adapter;

    private Button btngeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView=findViewById(R.id.listv);
        btngeri=findViewById(R.id.btngeri);
        String ilAdi = getIntent().getStringExtra("ilAdi");
        long sure = getIntent().getLongExtra("sure", 0);
        liste = new ArrayList<>();
        liste.add(ilAdi + "  " + sure + " ");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, liste);
        listView.setAdapter(adapter);

        btngeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}