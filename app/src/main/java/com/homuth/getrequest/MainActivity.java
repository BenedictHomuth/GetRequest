package com.homuth.getrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "Main Activity ->";
    private TextView tv_error;
    private Button btn_getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        //Check permissions
        int checkVal = this.checkCallingOrSelfPermission(Manifest.permission.INTERNET);
        if(checkVal == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Internet granted");
            tv_error.setText(R.string.InternetGranted);

            btn_getData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataFromDB();
                }
            });
        }else{
            Log.d(TAG, "No internet");
            tv_error.setText(R.string.InternetNotGranted);
        }
    }

    public void initUI(){
        tv_error = findViewById(R.id.ifError);
        btn_getData = findViewById(R.id.getData);
    }


    public void getDataFromDB(){
        final TextView tv_errorMsg = findViewById(R.id.ifError);
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
                        tv_errorMsg.setText(error);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String dbData = response.body().string();
                createGPSData(dbData);
            }
        });


    }


    public void createGPSData(String data){
        System.out.println("Our Data: " + data);



        //Eventuell ab hier neuen Intent starten und als "Start" festlegen
        //Von hier dann den Waypoint manager mit Daten füllen und starten
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("intent_data", data);
        startActivity(intent);

    }






}
