package com.homuth.getrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity ->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        int checkVal = this.checkCallingOrSelfPermission(Manifest.permission.INTERNET);

        if(checkVal == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Internet granted");
        }else{
            Log.d(TAG, "No internet");
        }

        final TextView jsonTextView = findViewById(R.id.text);
        //String jsonURL = "https://www.json-generator.com/api/json/get/cqDiNYjwZe?indent=2";
        String jsonURL = "http://10.0.2.2:8080/app/";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(jsonURL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            Log.d(TAG, "Access denied");

            final String error = e.toString();

            Log.d("TAG", error);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jsonTextView.setText(error);
                    }
                });


            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String json = response.body().string();
                createGPSData(json);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jsonTextView.setText(json);
                    }
                });
            }
        });


        CharSequence getIt = jsonTextView.getText();

       Log.d(TAG, getIt.toString());

    }


    public void createGPSData(String data){
        System.out.println("Our Data: " + data);



        //Eventuell ab hier neuen Intent starten und als "Start" festlegen
        //Von hier dann den Waypoint manager mit Daten füllen und starten

    }






}
