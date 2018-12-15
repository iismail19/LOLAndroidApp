package com.comp.lolandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class summoner_returned extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_returned);
        name = findViewById(R.id.summonerName);
        Intent myIntent = getIntent();
        String summonerName = myIntent.getStringExtra("summonerName");
        name.setText(summonerName);

    }
}
