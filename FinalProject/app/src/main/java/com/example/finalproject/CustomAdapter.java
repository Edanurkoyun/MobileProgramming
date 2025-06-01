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
    // Bağlamı (Context) ve oyuncu listesini tutan değişkenler
    Context context;
    List<String> players;

    // CustomAdapter sınıfının kurucusu, context ve oyuncu listesi alır
    public CustomAdapter(Context context, List<String> players) {
        super(context, 0, players); // ArrayAdapter sınıfının constructor'ını çağırır
        this.context = context;     // Context'i sınıf değişkenine atar
        this.players = players;     // Oyuncu listesini sınıf değişkenine atar
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Eğer daha önce oluşturulmuş bir görünüm yoksa yeni bir tane oluştur
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // list_item.xml dosyasındaki TextView ve ImageView öğelerini bul
        TextView playerName = convertView.findViewById(R.id.playerName);
        ImageView trophyIcon = convertView.findViewById(R.id.trophyIcon);

        // Mevcut konumdaki oyuncu adını al
        String player = players.get(position);
        playerName.setText(player); // Oyuncu adını TextView'e yerleştir

        // Oyuncu adı boş değilse, pozisyona göre kupa simgesini göster
        if (player != null && !player.trim().isEmpty()) {
            switch (position) {
                case 0:
                    trophyIcon.setImageResource(R.drawable.gold); // 1. için altın kupa
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    trophyIcon.setImageResource(R.drawable.silver); // 2. için gümüş kupa
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    trophyIcon.setImageResource(R.drawable.bronz); // 3. için bronz kupa
                    trophyIcon.setVisibility(View.VISIBLE);
                    break;
                default:
                    trophyIcon.setVisibility(View.GONE); // Diğerleri için kupa gösterilmez
                    break;
            }
        } else {
            trophyIcon.setVisibility(View.GONE); // Oyuncu adı boşsa kupa gizlenir
        }

        return convertView; // Oluşturulan/güncellenen görünüm döndürülür
    }
}