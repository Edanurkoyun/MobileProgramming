package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

public class MusicManager {
    private static MusicManager instance;
    private MediaPlayer mediaPlayer;
    private Context context;
    private MediaPlayer sound;
    private boolean isSoundEnabled = true;


    private MusicManager(Context context) {
        this.context = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
        mediaPlayer.setLooping(true);
        sound=MediaPlayer.create(context,R.raw.water);

    }

    public static synchronized MusicManager getInstance(Context context) {
        if (instance == null) {
            instance = new MusicManager(context);
        }
        return instance;
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
    public void setSoundEnabled(boolean isEnabled) {
        this.isSoundEnabled = isEnabled;
    }








}

