package com.example.basicgame01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;



public  class GActivity extends AppCompatActivity {



    //declaring gameview
    private GView gView;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //setting the orientation of landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //hide the navigation bar
        if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Init game view object
        gView = new GView(this, size.x, size.y);
        //adding it to contentview
        setContentView(gView);
    }


    @Override
    protected  void onPause(){
        super.onPause();
        //calling pause method in GameView class
        gView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gView.resume();
    }

    //handle Back button (triangle)

    @Override
    public void onBackPressed() {
        //nove application to background on android
        //moveTaskToBack(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Quit Or Not");
        builder.setMessage("Do you want to quit this and lose @int points? ");
        builder.setPositiveButton("Save&Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //saveResult();
                GActivity.super.onBackPressed();
                //is there a point in going back to main menu? or should we just exit app
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               // GActivity.super.onBackPressed();
            }
        });
        builder.show();

    }


}
