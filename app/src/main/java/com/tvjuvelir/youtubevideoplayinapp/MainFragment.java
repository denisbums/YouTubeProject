package com.tvjuvelir.youtubevideoplayinapp;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class MainFragment extends Fragment  implements YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayer activePlayer;
    YouTubePlayerSupportFragment playerFragment;
    String Url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View MainFragment =inflater.inflate(R.layout.main_fragment, container, false);
        playerFragment = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        playerFragment.initialize(MainActivity.Key, this);
        return MainFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(activePlayer != null && playerFragment != null){
            activePlayer.release();
            playerFragment.initialize(MainActivity.Key, this);
        }
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        activePlayer = youTubePlayer;
        activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        if (!b && Url.equals("")) {
            LiveData ld = new LiveData();
            try {
                Url = ld.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(Url.equals("")) {
                Url = "Mi_HIpmMelU";
                Toast.makeText(getContext(),"Quotas!",Toast.LENGTH_SHORT).show();
            }
        }
        activePlayer.loadVideo(Url, 0);
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