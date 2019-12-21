package com.homuth.getrequest;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadPictures extends AppCompatActivity {

    private Button btn_send;
    String encodedImage ="SGVsbG8=";
    String responseFromServer ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pictures);
        initUI();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadPhotoHandler().execute();
            }
        });
    }

    public void initUI(){
        btn_send = findViewById(R.id.btn_sendPhoto);
    }


    public class UploadPhotoHandler extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String plusServiceURL ="http://10.0.2.2:8080/droneapp/api/saveImage.php?data="+encodedImage;
            try {
                URL url = new URL(plusServiceURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();


                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                responseFromServer = bf.readLine();


            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            Toast.makeText(UploadPictures.this, responseFromServer, Toast.LENGTH_LONG).show();
        }


    }


}