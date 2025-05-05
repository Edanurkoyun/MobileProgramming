package com.example.wificameraornek;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BluetoothActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    Button btnAc, btnKapat, btnGorunur, btnListele;
    ListView listv;
    private static final int REQUEST_ENABLE_BT = 1;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btnAc = findViewById(R.id.btnac);
        btnKapat = findViewById(R.id.btnkapat);
        btnGorunur = findViewById(R.id.btngorunur);
        btnListele = findViewById(R.id.btnlistele);
        listv = findViewById(R.id.listv);

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth cihazı bulunamadı", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Bluetooth açıldı", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Bluetooth açma reddedildi", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // İzin verildi, Bluetooth'u aç
                        enableBluetooth();
                    } else {
                        Toast.makeText(this, "Bluetooth izni reddedildi", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnAc.setOnClickListener(v -> checkBluetoothPermissions());

    }

    private void checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 ve sonrası için Bluetooth izin kontrolü
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // İzin yok, kullanıcıdan iste
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
            } else {
                // İzin zaten verilmiş, Bluetooth'u aç
                enableBluetooth();
            }
        } else {
            // Android 12'den önce, izin gerekmez, doğrudan Bluetooth açılabilir
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetoothLauncher.launch(enableBtIntent);
        } else {
            Toast.makeText(this, "Bluetooth zaten açık", Toast.LENGTH_SHORT).show();
        }
    }
}
