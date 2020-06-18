package com.example.basicgame01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class GView extends SurfaceView implements Runnable {


    //VARIABLES
    //true if game is playing
    //'volatile' is used to modify the value of a variable by different thread
    // There is only one copy in the main memory.
    // The value of a volatile variable will never be stored in the cache.
    // All read and write will be done from and to the main memory.
    //If you are not using the volatile keyword, different reader thread may see different values
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;


    //get this from cache memory
    //public String lvlNo;// = "line";

    // Bob starts off not moving
    boolean isMoving = false;


    //adding the player to this class
    private Plr plr;

    private int screenSizeX, screenSizeY;

    //Adding 3 coin you may increase the size
    static public int coinCount = 3;

    //Adding 3 enemies you may increase the size
    static public int enemyCount = 3;

    //this is wrong
    //  private Canvas canvas;
    //this is right
    public static Canvas canvas;

    private SurfaceHolder surfaceHolder;

    static int scoree;
    int yy = 0;
    static String gravityWhatLine;

    static Coiny[] coins;
    static Enumy[] enumy;
    static Collision collision;

    public static ArrayList<Integer> midlineXarrayInt;
    public static ArrayList<Integer> midlineYarray = new ArrayList<>();

    Context context = getContext();

    //bolean to track if ship is boosting or not
    public boolean boosting;

    GmUpDraw Gupdraw = new GmUpDraw();

    // public boolean mortality=true;
    Rect plrRect;


    // This variable tracks the game frame rate
    static long fpsOfLoop;
    static long fpsDisplayed;


    //CLASS CONSTRUCTORS
    public GView(Context context, int screenX, int screenY) {
        super(context);

        //THIS IS WHERE WE INITIALIZE VARIABLES VALUES

        gravityWhatLine = "line";

        screenSizeX = screenX;
        screenSizeY = screenY;

        //initialize player object
        //also passing screen size to player constructor
        plr = new Plr(context, screenX, screenY, boosting);
        plrRect = plr.getDetectCollision();

        //initiate drawing objects
        surfaceHolder = getHolder();


        //initializing coin object array
        coins = new Coiny[coinCount];
        for (int i = 0; i < coinCount; i++) {
            coins[i] = new Coiny(context, screenX, screenY);
        }

        //initializing enemy object array
        enumy = new Enumy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enumy[i] = new Enumy(context, screenX, screenY, 0, 0);
        }

        //Generate
        /*
        for (int i = 0; i < screenSizeX; i++) {
            SinGen mpoint = new SinGen(i, screenSizeX, screenSizeY);
            mpoint.updateMidline(i);
            Log.i("tag_mpointY", " MESSAGE" + mpoint.getYmidline());
            midlineYarray.add(mpoint.getYmidline());
         ///////////   midlineXarrayInt.add(i);
        }

         */



    }


    //METHODS


    //LOOOOP
    //THIS runs in the loop constantly
    @Override
    public void run() {
        //With attempt to keep the refresh rate at 60fps
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        //todo what does this @targetFPS matter?
        int targetFPS = 25;
        long targetTime = 1000 / targetFPS;
        long averageFPS;

        while (playing) {
            //startTime = System.nanoTime();
            startTime = System.currentTimeMillis();
            ///canvas = null;

            try {

                synchronized (surfaceHolder) {


                    Gupdraw.update(screenSizeX, screenSizeY,
                            plr, plrRect, coinCount, enemyCount, coins, enumy, yy);

                    //Gupdraw.newEnumPos(plr, screenSizeX,screenSizeY);

                    Gupdraw.draw(context, surfaceHolder, plr, yy);

                    // https://stackoverflow.com/questions/19666652/changing-the-value-of-superclass-instance-variables-from-a-subclass
                    // https://stackoverflow.com/questions/10978788/how-to-share-variables-between-classes-in-java

                    //todo waittime to slow dow
                    control();

                }
            } catch (Exception e) {
                Log.e("error", "failed", e);
                e.printStackTrace();
            }
            //timeMillis = (System.nanoTime() - startTime) / 1000000;
            timeMillis = (System.currentTimeMillis() - startTime);
            waitTime = targetTime - timeMillis;
            Log.i("tag_waittime", "waitTime: " + waitTime + "timeMilis: " + timeMillis);
            if (timeMillis > 0) {
                fpsOfLoop = 1000 / timeMillis;
            }


            if (timeMillis < targetTime) {
                fpsDisplayed = 1000 / (timeMillis + waitTime);
            } else {
                fpsDisplayed = fpsOfLoop;
            }


            try {
                gameThread.sleep(waitTime);

                Log.i("tag_waittime_waiting", "waitTime: " + waitTime);
            } catch (Exception e) {

                Log.e("tag_waittime_error", "failed", e);
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetFPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //  System.out.println(averageFPS);
                Log.i("tag_waittime_loop2", "averageFPS: " + averageFPS);
            }

        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //when screen released
                Log.i("tag", "action_up");
                plr.stopBoosting();
                // Set isMoving so Bob is moved in the update method
                isMoving = false;
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i("tag", "action_down");
                //when screen pressed
                plr.setBoosting();
                // Set isMoving so Bob is moved in the update method
                isMoving = true;
                break;
        }
        return true;
    }


    private void control() {
        try {
            gameThread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void pause() {
        // when paused set values to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void resume() {
        //when game is resumed, start the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}