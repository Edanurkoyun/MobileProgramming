package com.example.mapsornek;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class SetLocationActivity extends  AppCompatActivity{
    private MapView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_set_location);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


        GeoPoint ev = new GeoPoint(41.044480, 28.929054);
        map.getController().setZoom(12.0);
        map.getController().setCenter(ev);

        Marker marker = new Marker(map);
        marker.setPosition(ev);
        marker.setTitle("Ev");
        map.getOverlays().add(marker);
    }

}
