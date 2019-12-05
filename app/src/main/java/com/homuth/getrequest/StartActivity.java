package com.homuth.getrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();

        String getIT = (String)intent.getStringExtra("intent_data") + "<- got Dammit";

        TextView tv = findViewById(R.id.int_Data);

        tv.setText(getIT);
    }
}
