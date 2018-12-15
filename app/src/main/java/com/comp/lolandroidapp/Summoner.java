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
                newSummoner.recentMatches = newSummoner.requestMatches(Requests.getRequestArray(lolGetRequests.requestMatchHistory(newSummoner.accountId)), newSummoner.accountId);
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
    public LinkedList<Game> requestMatches(JSONObject jsonObject, long accountId) throws JSONException{
        LinkedList<Game> currentMatches = new LinkedList<>();
        //JSON obj is from request that has matchlist
        int wonGames = 0;
        int numOfGames = 20;

        JSONArray match = jsonObject.getJSONArray("matches");
        if (match.length()<20)
            numOfGames=match.length();
        //get win percentage
        for (int i=0; i<numOfGames; i++) {
            //System.out.println(match);
            JSONObject individualMatches = match.getJSONObject(i);
            long gameId = individualMatches.getLong("gameId");

            // Request with actual game - send gameId to actual request
            // https://na1.api.riotgames.com/lol/match/v3/matches/2919167105?api_key=RGAPI-86d89a5d-3b70-457b-adc6-610afab42ba7
            // should return actual game which will be added to the linkedlist

            //probably need to make something different for win/loss ratio
        }
        
        return currentMatches;
    }

    public Game getGameInfo(JSONObject jsonObject, long accountId)  throws JSONException{
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

}
