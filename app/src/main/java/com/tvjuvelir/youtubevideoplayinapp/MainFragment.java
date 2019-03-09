package com.tvjuvelir.youtubevideoplayinapp;


import android.os.Bundle;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.concurrent.ExecutionException;

public class MainFragment extends YouTubePlayerSupportFragment {


    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayer activePlayer;

    public static MainFragment newInstance() {
        MainFragment playerYouTubeFrag = new MainFragment();
        playerYouTubeFrag.init();
        return playerYouTubeFrag;
    }

    private void init() {
        initialize(MainActivity.Key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                if (arg1.isUserRecoverableError()) {
                    arg1.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
                } else {
                    String error = String.format(getString(R.string.player_error), arg1.toString());
                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                if (!wasRestored) {
                    LiveData ld = new LiveData();
                    String Url = "";
                    try {
                        Url = ld.execute().get();
                        } catch (Exception e) {
                            e.printStackTrace();
                       }
                    activePlayer.loadVideo(Url, 0);
                }
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}