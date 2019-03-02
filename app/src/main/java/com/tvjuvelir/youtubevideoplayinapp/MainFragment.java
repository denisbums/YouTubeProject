package com.tvjuvelir.youtubevideoplayinapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainFragment extends YouTubePlayerSupportFragment {

    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayer activePlayer;
    String URL_VIDEO = "OUKxmHgLe8k";
    String KEY_DEVELOPER = "AIzaSyCpaLDiVr0eUyI8wVa2b2TtteCSF5NbDLc";

    public static MainFragment newInstance() {
        MainFragment playerYouTubeFrag = new MainFragment();
        playerYouTubeFrag.init();
        return playerYouTubeFrag;
    }

    private void init() {
        initialize(KEY_DEVELOPER, new YouTubePlayer.OnInitializedListener() {
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
                    activePlayer.loadVideo(URL_VIDEO, 0);
                }
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}