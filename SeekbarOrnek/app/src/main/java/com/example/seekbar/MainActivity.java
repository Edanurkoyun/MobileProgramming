package com.example.seekbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private long startTime=0;
    private TextView txtime,txtseek;
    private Button btnbasla,btnonayla;
    private EditText edittxt;
    private SeekBar seekbar;

    private Handler handler = new Handler();
    private Runnable runnable;

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


        String[] iller = {
                "", "Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya",
                "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu",
                "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır",
                "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun",
                "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
                "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya",
                "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş",
                "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop",
                "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak",
                "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale",
                "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük",
                "Kilis", "Osmaniye", "Düzce"
        };

        txtime=findViewById(R.id.txttime);
        btnbasla=findViewById(R.id.btnbasla);
        btnonayla=findViewById(R.id.btnonayla);
        edittxt=findViewById(R.id.edittxt);
        seekbar=findViewById(R.id.seekBar);
        txtseek=findViewById(R.id.txtseek);



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbar.setMax(81); // 0-81 arası
                txtseek.setText(String.valueOf(seekBar.getProgress()));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnonayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secilenindex=seekbar.getProgress();
                String dogruil=iller[secilenindex];
                String kullanicitahmin=String.valueOf(edittxt.getText()).trim() ;
                long endTime = System.currentTimeMillis();
                long elapsedSeconds = (endTime - startTime) / 1000;

                if(kullanicitahmin.equalsIgnoreCase(dogruil)){
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("ilAdi", dogruil);
                    intent.putExtra("sure", elapsedSeconds);
                    startActivity(intent); // en son çağrılmalı ✅

                }
                else {
                    Toast.makeText(getApplicationContext(),"Yanlış Tahmin",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnbasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                txtime.setText("Süre başladı...");
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        long currentTime = System.currentTimeMillis();
                        long elapsedSeconds = (currentTime - startTime) / 1000;
                        txtime.setText("Geçen süre: " + elapsedSeconds + " sn");
                        handler.postDelayed(this, 1000); // her saniye güncelle
                    }
                };
                handler.post(runnable);
            }
        });

    }
}