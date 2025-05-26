package com.example.firebaseornek;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.app.WallpaperManager;
import android.widget.Toast;
import com.squareup.picasso.Picasso;



import java.io.IOException;

import androidx.annotation.NonNull;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    private Context context;
    private List<String> wallpaperUrls;

    public WallpaperAdapter(Context context, List<String> wallpaperUrls) {
        this.context = context;
        this.wallpaperUrls = wallpaperUrls;
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViewHolder holder, int position) {
        String url = wallpaperUrls.get(position);
        Picasso.get().load(url).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            try {
                WallpaperManager manager = WallpaperManager.getInstance(context);
                Bitmap bitmap = ((BitmapDrawable) ((ImageView) v).getDrawable()).getBitmap();
                manager.setBitmap(bitmap);
                Toast.makeText(context, "Duvar kağıdı değiştirildi", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, "Hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperUrls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewWallpaper);
        }
    }
}

