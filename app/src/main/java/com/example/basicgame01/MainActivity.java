package com.example.basicgame01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

//https://www.simplifiedcoding.net/android-game-development-tutorial-1/

//shared preferences for highscoe array
//https://www.simplifiedcoding.net/android-game-development-tutorial-2/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation of landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //hide the navigation bar
        if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

        //gettin the button
        buttonPlay= (ImageButton)findViewById(R.id.buttonPlay);

        //addin a click listener
        buttonPlay.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){

        //starting game activity
        startActivity(new Intent(this, GActivity.class));

    }

    //handle Back button (triangle)

    @Override
    public void onBackPressed() {
        finish();
    }


}
