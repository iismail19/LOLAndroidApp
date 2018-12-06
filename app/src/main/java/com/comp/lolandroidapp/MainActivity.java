package com.comp.lolandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    // Member Variables (Global)
    EditText summonerName;
    Button submit;
    Requests theRequest;
    LolGetRequests lolRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summonerName = findViewById(R.id.SummonerNameeditText);
        submit = findViewById(R.id.submitButton);
        theRequest = new Requests();
        lolRequests = new LolGetRequests();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), summonerName.getText().toString(), Toast.LENGTH_SHORT);
                t.show();
                Log.d("Passed Toast", "works ");
                try {
                    JSONObject x = theRequest.getRequest(lolRequests.requestSummonerByName(summonerName.getText().toString()));
                    //String results = x.get();
                    Log.d("Json", "This is the json: " + x);

                    //phil - start creating the view components in this xml; if not you'll have to create ten diff table cells
                    // with their text --> would have to create unique id's for each cells
                    // uses lolRequest variables w/ class methods for calling diff endpoints on the api and returns json
                    // THIS METHOD DOESN'T WORK.
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
