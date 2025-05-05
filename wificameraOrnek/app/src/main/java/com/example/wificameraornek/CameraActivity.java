package com.example.wificameraornek;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    Button btnfoto, btnvideo;
    ImageView imageView;
    Uri photoUri, videoUri;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnfoto=findViewById(R.id.btnfoto);
        btnvideo=findViewById(R.id.btnvideo);
        imageView = findViewById(R.id.imageView);
        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Dosya oluşturuluyor
            File photoFile = new File(getExternalFilesDir(null), "photo.jpg");
            photoUri = Uri.fromFile(photoFile);

            // Intent'e URI ekleniyor
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            // Dosya oluşturuluyor
            File videoFile = new File(getExternalFilesDir(null), "video.mp4");
            videoUri = Uri.fromFile(videoFile);

            // Intent'e URI ekleniyor
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);  // Video süresi 30 saniye ile sınırlı
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Fotoğraf başarıyla çekildi
                imageView.setImageURI(photoUri);
                Toast.makeText(this, "Fotoğraf başarıyla çekildi!", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
                // Video başarıyla kaydedildi
                Toast.makeText(this, "Video başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "İşlem başarısız oldu", Toast.LENGTH_SHORT).show();
        }
    }
}