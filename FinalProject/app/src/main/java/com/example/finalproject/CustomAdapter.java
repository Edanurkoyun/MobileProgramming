package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> players;

    public CustomAdapter(Context context, List<String> players) {
        super(context, 0, players);
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        TextView playerName = convertView.findViewById(R.id.playerName);
        ImageView trophyIcon = convertView.findViewById(R.id.trophyIcon);

        String player = players.get(position);
        playerName.setText(player);

        // 🏆 Sıralamaya göre kupa görsellerini ayarla
        if (player != null && !player.trim().isEmpty()) {
            switch (position) {
                case 0:
                    trophyIcon.setImageResource(R.drawable.gold);
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    trophyIcon.setImageResource(R.drawable.silver);
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    trophyIcon.setImageResource(R.drawable.bronz);
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                default:
                    trophyIcon.setVisibility(View.GONE);
                    break;
            }
        } else {
            trophyIcon.setVisibility(View.GONE);
        }

        return convertView;
    }
}
