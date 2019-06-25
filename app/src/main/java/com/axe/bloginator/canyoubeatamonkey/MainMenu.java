package com.axe.bloginator.canyoubeatamonkey;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class MainMenu extends ActionBarActivity {
    private static ImageView mStartGame;
    private static ImageView mOptions;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mediaPlayer = MediaPlayer.create(MainMenu.this, R.raw.possibly);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //mStartGame = (ImageView) this.findViewById(R.id.imgStartGame);
        /*mStartGame.setClickable(true);
        mStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });*/
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onStop(){
        super.onStop();

        mediaPlayer.stop();
    }

    @Override
    public void onRestart(){
        super.onRestart();

        mediaPlayer = MediaPlayer.create(MainMenu.this, R.raw.possibly);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }

        public static class AdFragment extends Fragment {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_ad, container, false);
            }

            @Override
            public void onActivityCreated(Bundle bundle) {
                super.onActivityCreated(bundle);
                AdView mAdView = (AdView) getView().findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

            rootView.findViewById(R.id.imgStartGame);
            mStartGame = (ImageView) rootView.findViewById(R.id.imgStartGame);
            mStartGame.setClickable(true);
            mStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }

            });

            mOptions = (ImageView) rootView.findViewById(R.id.imageView2);
            mOptions.setClickable(true);
            mOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), MonkeyOptions.class);
                    startActivity(i);
                }

            });

            return rootView;
        }
    }
}
