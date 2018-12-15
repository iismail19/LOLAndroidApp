package com.comp.lolandroidapp;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

public class Summoner {

    public long summonerId;
    public String summonerName;
    public long summonerLevel;
    public long accountId;
    public String winPercentage;
    public LinkedList<Game> recentMatches = new LinkedList<>();
    JSONObject matchlistJson = new JSONObject();
    JSONObject gameJson = new JSONObject();

    // Self initializing summoner
    public static Summoner createSummonerFromJson(JSONObject jsonObject){
        try{
            LolGetRequests lolGetRequests = new LolGetRequests();
            Summoner newSummoner = new Summoner();
            newSummoner.summonerId = jsonObject.getLong("id");
            newSummoner.summonerName = jsonObject.getString("name");
            newSummoner.accountId = jsonObject.getLong("accountId");
            newSummoner.summonerLevel = jsonObject.getLong("summonerLevel");
//            try {
//                newSummoner.recentMatches = newSummoner.requestMatches(Requests.getRequestArray(lolGetRequests.requestMatchHistory(newSummoner.accountId)), newSummoner.accountId);
//                Log.d("LeagueApp", "createSummonerFromJson: success");
//            }catch (IOException e){
//                e.printStackTrace();
//                Log.d("LeagueApp", "createSummonerFromJson: failed");
//            }
            return newSummoner;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    // Games for this summoner
    public void requestMatches() throws JSONException{
        LinkedList<Game> currentMatches = new LinkedList<>();
        //JSON obj is from request that has matchlist
        LolGetRequests lolRequests = new LolGetRequests();
        /*Toast t = Toast.makeText(getApplicationContext(), summonerName.getText().toString(), Toast.LENGTH_SHORT);
        t.show();
        Log.d("Passed Toast", "works "+ summonerName.getText().toString());
        String url = lolRequests.requestSummonerByName(summonerName.getText().toString());*/
        String url = lolRequests.requestMatchHistory(accountId);
        RequestParams params = new RequestParams();
        requestMatchList(params, url);
        int wonGames = 0;
        int numOfGames = 20;

        JSONArray match = matchlistJson.getJSONArray("matches");
        if (match.length()<20)
            numOfGames=match.length();
        //get win percentage
        for (int i=0; i<numOfGames; i++) {
            //System.out.println(match);
            JSONObject individualMatches = match.getJSONObject(i);
            long gameId = individualMatches.getLong("gameId");
            String gameUrl = lolRequests.requestMatchInfo(gameId);
            requestMatch(params, gameUrl);
            Game newGame = getGameInfo(gameJson);
            if (newGame.winStatus)
                wonGames++;
            currentMatches.add(newGame);

            // Request with actual game - send gameId to actual request
            // https://na1.api.riotgames.com/lol/match/v3/matches/2919167105?api_key=RGAPI-86d89a5d-3b70-457b-adc6-610afab42ba7
            // should return actual game which will be added to the linkedlist

            //probably need to make something different for win/loss ratio
        }
        double percentWon = wonGames/numOfGames;
        double goodPercent = percentWon * 100;
        winPercentage = goodPercent + "%";
        
    }

    public Game getGameInfo(JSONObject jsonObject)  throws JSONException{
        JSONArray participantId = jsonObject.getJSONArray("participantIdentities");
        JSONObject indexPlayer;
        int testAccount;
        int playerIdIndex = -1;

        for (int i = 0; i<10; i++) {
            //can probably combine two below
            indexPlayer = participantId.getJSONObject(i);
            testAccount = indexPlayer.getJSONObject("player").getInt("accountId");
            if (accountId==testAccount) {

                playerIdIndex = i;
                break;
            }
        }

        Boolean win = jsonObject.getJSONArray("participants").getJSONObject(playerIdIndex).getJSONObject("stats").getBoolean("win");
        int champion = jsonObject.getJSONArray("participants").getJSONObject(playerIdIndex).getInt("championId");
        Game game = new Game(champion, win);
        return game;
    }

    public void requestMatchList(RequestParams params, String url){
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("pass", url);
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("pass", "OnSuccess: " + response.toString() );
                matchlistJson = response;
            }
//            @Override
//            public void onFailure(){}

        });
    }

    public void requestMatch(RequestParams params, String url){
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("pass", url);
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                gameJson = response;
            }
//            @Override
//            public void onFailure(){}

        });
    }

}
