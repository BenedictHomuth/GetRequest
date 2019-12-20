package com.homuth.getrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "Start Activity ->";
    private Button btn_sendData;
    private TextView tv_serverResonse;
    private TextView tv_messageFromServer;
    private TextView tv_sendData;
    private TextView tv_OnResponseServerQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

        String getIT = intent.getStringExtra("intent_data");
        initUI();
        tv_messageFromServer.setText(getIT);

        btn_sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDataToDB();
            }
        });
    }

    public void initUI(){
        btn_sendData = findViewById(R.id.btn_sendData);
        tv_serverResonse = findViewById(R.id.tv_responseFromServer);
        tv_messageFromServer = findViewById(R.id.tv_messageFromServer);
        tv_sendData = findViewById(R.id.tv_sendDataToDB);
        tv_OnResponseServerQuery = findViewById(R.id.tv_OnResponseDataSend);
    }

    public void writeDataToDB(){

        String httpURL = "http://10.0.2.2/droneapp/api/insertData.php";

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("name", "Mayer")
                .add("vorname", "Hans")
                .add("telefon", "5555")
                .build();
        final Request request = new Request.Builder()
                .url(httpURL)
                .post(formBody)
                .build();


        try {
            //TODO
            /*
            There is an NetworkOnMainThreadException because of obviously it runs on the main thread.
            Network stuff should never run on the main thread!
            Try again to implement Asynchronous task or find a synchronous approch to send data to a
            server.

             */

            final Response response = client.newCall(request).execute();

            Log.d(TAG,"I'm here!");

           StartActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   tv_OnResponseServerQuery.setText(response.body().toString());
               }
           });
        } catch (IOException e) {

            tv_OnResponseServerQuery.setText("That didn't work");
            //Thread.sleep(5000);
            e.printStackTrace();
        }
    }
}
