package com.comp.lolandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class summoner_returned extends AppCompatActivity {

    TextView name;
    TextView winP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_returned);
        name = findViewById(R.id.summonerName);
        winP = findViewById(R.id.summonerStats);
        Intent myIntent = getIntent();
        String summonerName = myIntent.getStringExtra("summonerName");
        String winPer = myIntent.getStringExtra("winP");

        name.setText(summonerName);
        winP.setText(winPer);

    }
}
