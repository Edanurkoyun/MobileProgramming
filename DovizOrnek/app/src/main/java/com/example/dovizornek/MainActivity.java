package com.example.dovizornek;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

   private TextView eurText, usdText, jpyText, tryText, cadText;
   private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI bileşenlerini tanımla
        eurText = findViewById(R.id.eurtext);
        usdText = findViewById(R.id.usdtext);
        jpyText = findViewById(R.id.jpytext);
        tryText = findViewById(R.id.trytext);
        cadText = findViewById(R.id.cadtext);
        button = findViewById(R.id.button);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRates();
            }
        });

        Log.d("MainActivity", "Uygulama başlatıldı!");
    }

    public void getRates() {
        Log.d("MainActivity", "getRates çağrıldı!");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://data.fixer.io/api/latest?access_key=4db2486df36ccec9fdd17fac73f47f6e");
        Log.d("MainActivity", "AsyncTask başlatılıyor...");
    }

    public class DownloadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DownloadData", "Error downloading data: " + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject rates = jsonObject.getJSONObject("rates");

                eurText.setText("EUR: 1.0");


                String usd = rates.getString("USD");
                usdText.setText("USD: " + usd);

                String jpy = rates.getString("JPY");
                jpyText.setText("JPY: " + jpy);

                String turkishLira = rates.getString("TRY");
                tryText.setText("TRY: " + turkishLira);

                String cad = rates.getString("CAD");
                cadText.setText("CAD: " + cad);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DownloadData", "Error parsing JSON: " + e.getMessage());
            }
        }
    }
}