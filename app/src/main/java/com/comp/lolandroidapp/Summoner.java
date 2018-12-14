package com.comp.lolandroidapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

public class Summoner {

    private long summonerId;
    private String summonerName;
    private long summonerLevel;
    private long accountId;
    private LinkedList<Game> recentMatches = new LinkedList<>();

    // Self initializing summoner
    public static Summoner createSummonerFromJson(JSONObject jsonObject){
        try{
            LolGetRequests lolGetRequests = new LolGetRequests();
            Summoner newSummoner = new Summoner();
            newSummoner.summonerId = jsonObject.getLong("id");
            newSummoner.summonerName = jsonObject.getString("name");
            newSummoner.accountId = jsonObject.getLong("accountId");
            newSummoner.summonerLevel = jsonObject.getLong("summonerLevel");
            try {
                newSummoner.recentMatches = newSummoner.requestMatches(Requests.getRequestArray(lolGetRequests.requestMatchHistory(newSummoner.accountId)));
                Log.d("LeagueApp", "createSummonerFromJson: success");
            }catch (IOException e){
                e.printStackTrace();
                Log.d("LeagueApp", "createSummonerFromJson: failed");
            }
            return newSummoner;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    // Games for this summoner
    public LinkedList<Game> requestMatches(JSONObject jsonObject, long accountId){
        LinkedList<Game> currentMatches = new LinkedList<>();
        //okay this is the return match part
        JSONArray participantId = jsonObject.getJSONArray("participantIdentities");
        JSONObject indexPlayer;
        int testAccount;
        int playerIndex = -1;

        for (int i = 0; i<10; i++) {
            //can probably combine two below
            indexPlayer = participantId.getJSONObject(i);
            testAccount = indexPlayer.getJSONObject("player").getInt("accountId");
            if (accountId==testAccount) {
                playerIndex = i;
                break;
            }
        }
        Boolean win2 = jsonObject.getJSONArray("participants").getJSONObject(playerIndex).getJSONObject("stats").getBoolean("win");
        return currentMatches;
    }
}
