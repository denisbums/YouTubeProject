package com.tvjuvelir.youtubevideoplayinapp;

import android.os.AsyncTask;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

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
        String returnValue = "";
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
                    new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest hr) {
                }
            }).setApplicationName("Ювелирочка").build();

            YouTube.Search.List searchListByKeywordRequest = youtube.search().list("snippet");
            searchListByKeywordRequest.setMaxResults(Long.parseLong("5"));
            searchListByKeywordRequest.setQ("Ювелирочка ТВ");
            searchListByKeywordRequest.setKey(MainActivity.Key);
            SearchListResponse response = searchListByKeywordRequest.execute();
            for (SearchResult result : response.getItems()) {
                if(result.getId().getKind().equals("youtube#video") && result.getSnippet().getLiveBroadcastContent().equals("live")) {
                    returnValue = result.getId().getVideoId();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
