package com.homuth.getrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class StartActivity extends AppCompatActivity {
    private static final String TAG = "Start Activity ->";
    private Button btn_sendData;
    private Button btn_photo;
    private TextView tv_serverResonse;
    private TextView tv_messageFromServer;
    private TextView tv_sendData;
    private TextView tv_OnResponseServerQuery;

    String serverResponse = "";
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
                new WriteToDBTask().execute();
            }
        });

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, UploadPictures.class);
                startActivity(i);
            }
        });
    }

    public void initUI(){
        btn_sendData = findViewById(R.id.btn_sendData);
        tv_serverResonse = findViewById(R.id.tv_responseFromServer);
        tv_messageFromServer = findViewById(R.id.tv_messageFromServer);
        tv_sendData = findViewById(R.id.tv_sendDataToDB);
        tv_OnResponseServerQuery = findViewById(R.id.tv_OnResponseDataSend);
        btn_photo = findViewById(R.id.btn_photo);
    }

    public class WriteToDBTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
           // super.onPostExecute(s);
            Toast.makeText(StartActivity.this, serverResponse, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String plusServiceURL ="http://10.0.2.2:8080/droneapp/api/pushDataToDB.php?lastName=HANS&firstName=HASN&email=HASN";
            try {
                URL url = new URL(plusServiceURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();


                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                serverResponse= bf.readLine();


            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}



