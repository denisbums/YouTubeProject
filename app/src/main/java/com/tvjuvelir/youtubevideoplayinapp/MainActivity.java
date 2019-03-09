package com.tvjuvelir.youtubevideoplayinapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String Key = "AIzaSyCpaLDiVr0eUyI8wVa2b2TtteCSF5NbDLc";

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
    }

    protected Fragment createMainFragment()
    {
        return MainFragment.newInstance();
    }
}
