package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

public class MusicManager {
    private static MusicManager instance; // Singleton örneği için değişken
    private MediaPlayer mediaPlayer;
    private Context context;
    private MediaPlayer sound;
    private boolean isSoundEnabled = true;


    // Constructor, context'i alır ve müzik kaynaklarını başlatır
    private MusicManager(Context context) {
        this.context = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
        mediaPlayer.setLooping(true);// Müziği tekrar çalacak şekilde ayarlıyoruz
        sound=MediaPlayer.create(context,R.raw.water);

    }

    // Singleton desenine göre sadece bir MusicManager örneği yaratmamızı sağlıyor
    public static synchronized MusicManager getInstance(Context context) {
        if (instance == null) {
            instance = new MusicManager(context); // Eğer örnek yoksa oluşturuluyor
        }
        return instance; // Singleton örneği geri dönüyor
    }

    public void startMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Kaynakları serbest bırakma fonksiyonu, çalınan müziklerin ve seslerin bellekten temizlenmesini sağlar
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (sound != null) {
            sound.release();
            sound = null;
        }

    }

    public void playSound() {
        if (sound != null && isSoundEnabled) { // Sadece ses açıksa oynat
            sound.start();
        }
    }

    public void stopSound() {
        if (sound != null && sound.isPlaying()) {
            sound.pause();


        }
    }
    // Sesin açılıp kapanması için ayar fonksiyonu
    public void setSoundEnabled(boolean isEnabled) {
        this.isSoundEnabled = isEnabled;
    }








}

