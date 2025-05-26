package com.example.sqliteornek;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button kaydet, goster, sil, guncelle;
    EditText ad, soyad, yas, sehir;
    TextView bilgiler;

    private DatabaseActivity db; // düzeltildi: Database yerine DatabaseActivity

    private String[] sutunlar = {"ad", "soyad", "yas", "sehir"};

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

        db = new DatabaseActivity(this);
        kaydet = findViewById(R.id.btnekle);
        goster = findViewById(R.id.btngoster);
        sil = findViewById(R.id.btnsil);
        guncelle = findViewById(R.id.btnguncelle);
        ad = findViewById(R.id.txtad);
        soyad = findViewById(R.id.txtsoyad);
        yas = findViewById(R.id.txtyas);
        sehir = findViewById(R.id.txtsehir);
        bilgiler = findViewById(R.id.txtbilgi);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Kayıt Ekle Butonuna Basıldı.");
                if (bosAlanVarMi()) return;
                KayitEkle(ad.getText().toString(), soyad.getText().toString(), yas.getText().toString(), sehir.getText().toString());
            }
        });

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Kayıt Göster Butonuna Basıldı.");
                KayitGoster(KayitGetir());
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Kayıt Sil Butonuna Basıldı.");
                if (ad.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Silmek için ad giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                KayitSil(ad.getText().toString());
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Kayıt Güncelle Butonuna Basıldı.");
                if (bosAlanVarMi()) return;
                KayitGuncelle(ad.getText().toString(), soyad.getText().toString(), yas.getText().toString(), sehir.getText().toString());
            }
        });
    }

    private boolean bosAlanVarMi() {
        if (ad.getText().toString().isEmpty() || soyad.getText().toString().isEmpty() ||
                yas.getText().toString().isEmpty() || sehir.getText().toString().isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private Cursor KayitGetir() {
        SQLiteDatabase db1 = db.getWritableDatabase();
        return db1.query("OgrenciBilgi", sutunlar, null, null, null, null, null);
    }

    private void KayitGoster(Cursor goster) {
        StringBuilder builder = new StringBuilder();
        try {
            while (goster.moveToNext()) {
                int columnIndexAd = goster.getColumnIndexOrThrow("ad");
                int columnIndexSoyad = goster.getColumnIndexOrThrow("soyad");
                int columnIndexYas = goster.getColumnIndexOrThrow("yas");
                int columnIndexSehir = goster.getColumnIndexOrThrow("sehir");

                String add = goster.getString(columnIndexAd);
                String soyadd = goster.getString(columnIndexSoyad);
                String yass = goster.getString(columnIndexYas);
                String sehirr = goster.getString(columnIndexSehir);

                builder.append("Ad: ").append(add).append("\n");
                builder.append("Soyad: ").append(soyadd).append("\n");
                builder.append("Yaş: ").append(yass).append("\n");
                builder.append("Şehir: ").append(sehirr).append("\n");
                builder.append("--------------------\n");
            }
            bilgiler.setText(builder.toString());
        } catch (Exception e) {
            Log.e(TAG, "Kayıt Gösterilirken Hata Oluştu! " + e.getMessage());
        } finally {
            if (goster != null && !goster.isClosed()) {
                goster.close();
            }
        }
    }

    private void KayitEkle(String adi, String soyadi, String yasi, String sehri) {
        try {
            SQLiteDatabase db1 = db.getWritableDatabase();
            ContentValues veriler = new ContentValues();
            veriler.put("ad", adi);
            veriler.put("soyad", soyadi);
            veriler.put("yas", yasi);
            veriler.put("sehir", sehri);
            db1.insertOrThrow("OgrenciBilgi", null, veriler);
            db1.close();
        } catch (Exception e) {
            Log.e(TAG, "Kayıt Eklenirken Hata Oluştu! " + e.getMessage());
        }
    }

    private void KayitSil(String adi) {
        try {
            SQLiteDatabase db1 = db.getWritableDatabase();
            int rows = db1.delete("OgrenciBilgi", "ad=?", new String[]{adi});
            db1.close();
            Log.d(TAG, rows + " Kayıt Silindi");
        } catch (Exception e) {
            Log.e(TAG, "Kayıt Silinirken Hata Oluştu! " + e.getMessage());
        }
    }

    private void KayitGuncelle(String adi, String soyadi, String yasi, String sehri) {
        try {
            SQLiteDatabase db1 = db.getWritableDatabase();
            ContentValues cvGuncelle = new ContentValues();
            cvGuncelle.put("soyad", soyadi);
            cvGuncelle.put("yas", yasi);
            cvGuncelle.put("sehir", sehri);
            int rows = db1.update("OgrenciBilgi", cvGuncelle, "ad=?", new String[]{adi});
            db1.close();
            Log.d(TAG, rows + " Kayıt Güncellendi");
        } catch (Exception e) {
            Log.e(TAG, "Kayıt Güncellenirken Hata Oluştu! " + e.getMessage());
        }
    }
}
