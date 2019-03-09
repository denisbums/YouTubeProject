package com.tvjuvelir.youtubevideoplayinapp;

import android.os.AsyncTask;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.util.HashMap;

public class LiveData extends AsyncTask<Void, Void, String> {
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
                    new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest hr) {
                }
            }).setApplicationName("Ювелирочка").build();
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults", "50");
            parameters.put("q", "Ювелирочка ТВ");
            parameters.put("type", "");

            YouTube.Search.List searchListByKeywordRequest = youtube.search().list(parameters.get("part"));
            if (parameters.containsKey("maxResults")) {
                searchListByKeywordRequest.setMaxResults(Long.parseLong(parameters.get("maxResults")));
            }
            if (parameters.containsKey("q") && parameters.get("q") != "") {
                searchListByKeywordRequest.setQ(parameters.get("q"));
            }
            if (parameters.containsKey("type") && parameters.get("type") != "") {
                searchListByKeywordRequest.setType(parameters.get("type"));
            }
            searchListByKeywordRequest.setKey(MainActivity.Key);
            SearchListResponse response = searchListByKeywordRequest.execute();
            //System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "RLuYziUgP8I";
    }
}
