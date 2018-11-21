package com.comp.lolandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView summonerName = findViewById(R.id.summonerNameText);
        Button submit = findViewById(R.id.submitButton);
        String sName = summonerName.getText().toString();

    }
}
