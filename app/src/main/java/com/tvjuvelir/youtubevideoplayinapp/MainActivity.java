package com.tvjuvelir.youtubevideoplayinapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createMainFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
        //getKey();
    }

    protected Fragment createMainFragment()
    {
        return MainFragment.newInstance();
        //getSupportFragmentManager().beginTransaction().replace(R.id.video_container, myFragment).commit();
    }

    public void getKey()
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
                            new JacksonFactory(), new HttpRequestInitializer() {
                        @Override
                        public void initialize(HttpRequest hr) {}
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
                    SearchListResponse response = searchListByKeywordRequest.execute();
                    System.out.println(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
