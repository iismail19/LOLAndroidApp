package com.comp.lolandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
        lolRequests = new LolGetRequests();
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), summonerName.getText().toString(), Toast.LENGTH_SHORT);
                t.show();
                Log.d("Passed Toast", "works "+ summonerName.getText().toString());
                String url = lolRequests.requestSummonerByName(summonerName.getText().toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            long num = response.getLong("accountId");
                            summonerName.setText(Long.toString(num));
                            Log.d("pass", response.toString());
                        }
                        catch (JSONException e){
                            Log.d("pass", "catch");
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("pass", "no idea why yet-- error");
                    }
                });{
                }

            }
        });
    }



}
