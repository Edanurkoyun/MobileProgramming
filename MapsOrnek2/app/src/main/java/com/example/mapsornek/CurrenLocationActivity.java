package com.example.mapsornek;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

public class CurrenLocationActivity extends AppCompatActivity {

    private MapView map;
    private FusedLocationProviderClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_current_location);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


        locationClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Konumu al
        locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    showLocationOnMap(location);
                } else {
                    Log.d("LocationError", "Location is null");
                }
            }
        });


        showLocation();
    }


    private void showLocation() {

        GeoPoint marmaraLibrary = new GeoPoint(40.9982, 29.0124);
        map.getController().setZoom(16.0);
        map.getController().setCenter(marmaraLibrary);


        Marker marker = new Marker(map);
        marker.setPosition(marmaraLibrary);
        map.getOverlays().add(marker);
    }


    private void showLocationOnMap(Location location) {
        GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
        map.getController().setZoom(20.0);
        map.getController().setCenter(point);

        Marker marker = new Marker(map);
        marker.setPosition(point);
        marker.setTitle("Şu Anki Konum");
        map.getOverlays().add(marker);
    }
}
