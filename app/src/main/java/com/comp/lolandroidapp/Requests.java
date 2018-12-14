package com.comp.lolandroidapp;

import android.util.Log;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests{

    public JSONObject getRequest(String requestURL) throws IOException, JSONException {
        URL link = new URL(requestURL);
        HttpURLConnection connect = (HttpURLConnection) link.openConnection();
        // response code: tells us if the request url is successful
        int responeCode = connect.getResponseCode();
        Log.d("Pass", "Status code is 200, app should get data");
        // returns the response from the link
        BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null){
            //line = line.replace("[", "").replace("]", "");
            response.append(line);
        } in.close();

        connect.disconnect();
        JSONObject responseData = new JSONObject(response.toString());
        return  responseData;

    }

    // if JSON is an array/list use this
    public static JSONArray getRequestArray(String requestURL) throws IOException, JSONException{
        URL link = new URL(requestURL);
        HttpURLConnection connect = (HttpURLConnection) link.openConnection();
        // response code: tells us if the request url is successful
        int responeCode = connect.getResponseCode();
        // returns the response from the link
        BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null){
            response.append(line);
        } in.close();
        connect.disconnect();

        JSONArray responseData = new JSONArray(response.toString());
        return  responseData;
    }

    // if single line don't need a JSON formatted object
    public static String getRequestLine(String requestURL) throws IOException, JSONException {
        URL link = new URL(requestURL);
        HttpURLConnection connect = (HttpURLConnection) link.openConnection();
        // response code: tells us if the request url is successful
        int responeCode = connect.getResponseCode();
        // returns the response from the link
        BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null){
            //line = line.replace("[", "").replace("]", "");
            response.append(line);
        } in.close();
        connect.disconnect();
        return  line = response.toString();
    }

}
