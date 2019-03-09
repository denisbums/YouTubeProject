package com.tvjuvelir.youtubevideoplayinapp;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class MainFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayer activePlayer;
    String Url = "";

//    @Override
//    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        //return super.onCreateView(layoutInflater, viewGroup, bundle);
//        View v = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
//
////        activePlayer = (YouTubePlayer) v.findViewById(R.id.youtubeplayerfragment);
//       return v;
//    }

    @Override
    public void onResume() {
        super.onResume();
        if(activePlayer != null)
        activePlayer.loadVideo(Url, 0);
    }

    public MainFragment() {
        initialize(MainActivity.Key, this);
    }

    public static MainFragment newInstance() {
        MainFragment playerYouTubeFrag = new MainFragment();
        //playerYouTubeFrag.init();
        return playerYouTubeFrag;
    }

//    private void init() {
//        initialize(MainActivity.Key, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
//                if (arg1.isUserRecoverableError()) {
//                    arg1.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
//                } else {
//                    String error = String.format(getString(R.string.player_error), arg1.toString());
//                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//                activePlayer = player;
//                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
//                if (!wasRestored) {
//                    LiveData ld = new LiveData();
//                    try {
//                        Url = ld.execute().get();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                       }
//                    activePlayer.loadVideo(Url, 0);
//                }
//            }
//        });
//    }
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        activePlayer = youTubePlayer;
        activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        if (!b) {
            LiveData ld = new LiveData();
            try {
                Url = ld.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            activePlayer.loadVideo(Url, 0);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
    }
}