package com.example.basicgame01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

import static com.example.basicgame01.GView.canvas;

public class GmUpDraw {

    //////////// U  P   D   A   T   E            variables

    public static int score = 1;
    static int penalty = 0;
    public static String gravityLine;
    public static ArrayList<Integer> midlineYarray;
    public static ArrayList<Integer> midlineXarrayInt = new ArrayList<>();
    // private ArrayList<Integer> midlineYarray = new ArrayList<>();
    public static int coinCount;
    public static Coiny[] coins;
    public static int enemyCount;
    public static Enumy[] enumy;
    //todo move all variables to there
    boolean newEnumPos = false;
    public boolean mortality;

    int enumyOldX;
    int enumyNewX;

    public static ArrayList<Rect> enumyRect = new ArrayList<>();// =  enumy[i].getDetectCollision();

    public Rect plrLocRect = new Rect(0, 0, 0, 0);






    //////////// U  P   D   A   T   E

    //too much operation in this thread will cause app to stutter
    public void update(int screenSizeX, int screenSizeY, Plr plr, Rect plrRect, int coinCount, int enemyCount, Coiny[] coins, Enumy[] enumy, int yy) {

        //initializing boom object
        //collision = new Collision(context);

        //Update inherits from GameView
        //score = GView.scoree;


        //todo Null??
        //todo this does not give a value to variables ...
        gravityLine = GView.gravityWhatLine;
        midlineYarray = GView.midlineYarray;
        midlineXarrayInt = GView.midlineXarrayInt;
        //setting boom outside the screen
        //collision.setX(-250);
        //collision.setY(-250);

        //TODO this arrays should be updated only in enemy count hits specific number
        switch (score) {
            case 1:
                gravityLine = "line";
                for (int i = 0; i < screenSizeX; i++) {
                    yy = 360;
                    midlineYarray.add(yy);
                }
                break;
            /*
                case 6666:
                gravityLine = "sinus";

                //TODO catch midline X as int (not necessarily Singen)
                //there we are continuously working within @mptt of type @SinGen
                for (int i = 0; i < screenSizeX; i++) {
                    //for every element inside array @midlineArr call Midline.update with @x parameter
                    SinGen mptt = new SinGen(i, screenSizeX, screenSizeY);
                    //initializa Singen and calculate for this [i]
                    //TODO null
                    mptt.updateMidline(plr.getSpeed());
                    yy = mptt.getYmidline();
                    //midlineYarray.set(i, yy);
                    midlineYarray.add(i, yy);
                    i++;
                }
                break;

             */
        }


        plr.update(midlineYarray);

        //updating coins interactions
        for (int i = 0; i < coinCount; i++) {
            coins[i].update(plr.getSpeed());

            //if collision occurrs with player
            if (Rect.intersects(plr.getDetectCollision(), coins[i].getDetectCollision())) {
                //moving enemy outside the left edge
                Log.i("tag_collision", "collision");
                Random generatorX = new Random();
                coins[i].setX(generatorX.nextInt(screenSizeX - 100));
                coins[i].setY(generatorX.nextInt(screenSizeY - 100));


                //displaying boom at that location
               // collision.setX(coins[i].getX());
               //collision.setY(coins[i].getY());
                //score
                score++;

                //scoreString = " " + score;

            }
        }

        //updating the coin coordinate with respect to player
        for (int i = 0; i < coinCount; i++) {
            coins[i].update(plr.getSpeed());
        }


        int enmXrand = 0;
        int enmYrand = 0;
        Random generatorX = new Random();
        int safezone = 100;

        // catch plr position to avoid spawning enumy on plr
        int plrX = plr.getX();
        int plrY = plr.getY();


        // enumyOldX = enumy[enemyCount - 1].getX();

        if (score % 4 == 0) {// && enumyOldX == enumyNewX) {

            boolean LeftRight = generatorX.nextBoolean();
            boolean UperDown = generatorX.nextBoolean();
            for (int i = 0; i < enemyCount; i++) {
                //enumy[i].update(plr.getSpeed());

                if (LeftRight) {
                    enmXrand = generatorX.nextInt(plrX - safezone);
                    //return;
                } else if (!LeftRight) {
                    enmXrand = plrX + safezone + generatorX.nextInt(screenSizeX - 100);
                }
                if (UperDown) {
                    enmYrand = generatorX.nextInt(plrY - safezone);
                } else if (!UperDown) {
                    enmYrand = plrY + safezone + generatorX.nextInt(screenSizeY - plrY);
                }

                //safety measure to not spawn offscreen
                if (enmXrand > screenSizeX) {
                    enmXrand = screenSizeX - 100;
                }
                if (enmYrand > screenSizeY) {
                    enmYrand = screenSizeY - 100;
                }

                enumy[i].setX(enmXrand);
                enumy[i].setY(enmYrand);
                Log.i("tagEnScreenSize", "Xscreen " + screenSizeX + " - " + "Yscreen" + screenSizeY);
                Log.i("tagEnumPos", "X " + enmXrand + " - " + "Y " + enmYrand);
                //Collision with enum
            }
//todo this is executet only once, why the rest is not?
            score = score + 1;
            //   enumyNewX = enumy[enemyCount - 1].getX();
        }


        mortality = true;
        penalty = 0;

        //todo why doe it think there is no intersection?
        // if ((Rect.intersects(plr.getDetectCollision(), enumy[0].getDetectCollision())) == false && (Rect.intersects(plr.getDetectCollision(), enumy[1].getDetectCollision())) == false && (Rect.intersects(plr.getDetectCollision(), enumy[2].getDetectCollision())) == false) {
        //after offsetting it does not longer intersect, so it does this asignment
        //penalty = 0;
        //plrLocRect.set(plr.getDetectCollision());
        Log.i("tag_rectS ", plrLocRect.left + " " + plrLocRect.right);
        // }


        //todo it does intersect for one @enumy, and it does not intersect for other @enumies ...
        //todo does not make sense to perform check inside  @for loop
        //this we do only when we intersect with enumy's
        for (int i = 0; i < enemyCount; i++) {
            //todo set hitbox (Rect) to empty
            enumy[i].update(plr.getSpeed());
            //  Rect enumyR =  enumy[i].getDetectCollision();
            //  enumyRect.set(i, enumyR);
            //plrLocRect = plrRect;
            //penalty = 0;
            Log.i("tag_rect0 ", plrLocRect.left + " " + plrLocRect.right);
            Log.i("tag_rectEnumi ", enumy[i].getDetectCollision().left + " " + enumy[i].getDetectCollision().right);
            //if (Rect.intersects(plr.getDetectCollision(), enumy[i].getDetectCollision()) && mortality == true) {
            //displaying boom at that location
           // collision.setX(enumy[i].getX());
           // collision.setY(enumy[i].getY());
            //score
            //scoreString = " " + score;
            //reset layout
            // gravityLine = "line";
            //@enumy[i].getDetectCollision() is a Rect (rectangle)
            if (Rect.intersects(plr.getDetectCollision(), enumy[i].getDetectCollision()) && mortality) {
                mortality = false;
                //penalty = 10;
                //plrRect.setEmpty();
                //if I change variables to state in which this is not intersecting, I am jumping to else
            } else {
            }


            boolean rIinters = Rect.intersects(plrLocRect, enumy[i].getDetectCollision());
            if (rIinters) {
                penalty = 10;
                //plrLocRect.offset(2000, 2000);

                Log.i("tag_rectO ", plrLocRect.left + " " + plrLocRect.right);
                //plrLocRect.setEmpty();
                Log.i("tag_rectE ", plrLocRect.left + " " + plrLocRect.right);

                score = score - penalty;//<<<<
            }
            rIinters = Rect.intersects(plrLocRect, enumy[i].getDetectCollision());
            if (!rIinters) {
                plrLocRect.offset(2000, 2000);
                //plrLocRect.offset(2000, 2000);
                penalty = 0;
                Log.i("tag_rectO ", plrLocRect.left + " " + plrLocRect.right);
                //plrLocRect.setEmpty();
                Log.i("tag_rectE ", plrLocRect.left + " " + plrLocRect.right);
                rIinters = false;
                //else{            plrLocRect.set(plr.getDetectCollision());
                // }

            }


            score = score - penalty;


        }
    }

    // }


    /////////// D   R   A   W           variables

    //objects used for drawing
    private Paint paint, paintMidline, scorePaint;


    // He starts 10 pixels from the left
    float bobXPosition = 10;

    // How many frames are there on the sprite sheet?
    private int frameCount = 2;

    //New variables for the sprite sheet animation
    // These next two values can be anything you like
    // As long as the ratio doesn't distort the sprite too much
    private int frameWidth = 105;
    private int frameHeight = 35;

    // A rect that defines an area of the screen
    // on which to draw
    RectF whereToDraw = new RectF(
            bobXPosition, 0,
            bobXPosition + frameWidth,
            frameHeight);

    // A rectangle to define an area of the
    // sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(
            0,
            0,
            frameWidth,
            frameHeight);

    // Start at the first frame - where else?
    private int currentFrame = 0;

    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;

    // How long should each frame last
    private int frameLengthInMilliseconds = 100;


    /////////// D   R   A   W

    public void draw(Context context, SurfaceHolder surfaceHolder, Plr plr, int yy) {


        boolean boosting = plr.getBoosting();
        Log.i("tag_boost", "boosting: " + boosting);

        // Load Bob from his .png file
        Bitmap bitmapBob = BitmapFactory.decodeResource(context.getResources(), R.drawable.plrframes);

        Bitmap bitmapBobWhite = BitmapFactory.decodeResource(context.getResources(), R.drawable.plr20ptwhite);


        paint = new Paint();
        paintMidline = new Paint();


        // Scale the bitmap to the correct size
        // We need to do this because Android automatically
        // scales bitmaps based on screen density
        bitmapBob = Bitmap.createScaledBitmap(bitmapBob,
                frameWidth * frameCount,
                frameHeight,
                false);


        //check if surface is valid
        if (surfaceHolder.getSurface().isValid()) {

            //Canvas canvas = new Canvas(); //GView.canvas;
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();

            //draw @plain@color to make background
            canvas.drawColor(Color.LTGRAY);

            long fps = GView.fpsOfLoop;
            long fpsDisp = GView.fpsDisplayed;
            // Make the text a bit bigger
            paint.setTextSize(20);
            // Display the current fps on the screen
            canvas.drawText("Loop fps: " + fps, 1000, 40, paint);
            canvas.drawText("Disp fps: " + fpsDisp, 1000, 80, paint);

            //drawing @score text
            //Draw inherits from Update
            //int score = GvUpdate.getScore();
            String scoreString = "" + score;
            scorePaint = new Paint();
            scorePaint.setColor(Color.GRAY);
            scorePaint.setTextSize(50);
            canvas.drawText(scoreString, 100, 80, scorePaint);


            //Draw inherits from Update
            // String gravityLine = GvUpdate.getGravityLine();
            //drawing @midline specified for given level
            Log.i("tag_midline", gravityLine + "yy: " + yy);
            paintMidline.setColor(Color.DKGRAY);
            paintMidline.setStrokeWidth(3);
            /*
            // if (gravityLine != null) {
            switch (gravityLine) {
                case "line":
                    for (int mptt : midlineYarray) {
                        yy = 360;
                        canvas.drawPoint(mptt.getXmidline(), yy, paintMidline);
                    }
                    break;
                case "sinus":
                    for (int mptt : midlineYarray) {
                        //for every element inside array @midlineArr call Midline.update with @x parameter
                        //mptt.updateMidline(plr.getSpeed());
                        yy = mptt.getYmidline();
                        canvas.drawPoint(mptt.getXmidline(), yy, paintMidline);
                    }
                    break;

                //    }
 */

        }

/*
            //drawing @plr
            canvas.drawBitmap(
                    plr.getBitmap(),
                    plr.getX(),
                    plr.getY(),
                    paint
            );
 */

        coinCount = GView.coinCount;
        coins = GView.coins;
        //drawing @coins
        for (int i = 0; i < coinCount; i++) {
            canvas.drawBitmap(
                    coins[i].getBitmap(),
                    coins[i].getX(),
                    coins[i].getY(),
                    paint
            );
        }


        //drawing @collision image
/*
        canvas.drawBitmap(
                collision.getBitmap(),
                collision.getX(),
                collision.getY(),
                paint
        );
*/

        // Draw bob at bobXPosition, 200 pixels
        // canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);
        // New drawing code goes here
        // New drawing code goes here
        whereToDraw.set((int) plr.getX(),
                plr.getY(),
                (int) plr.getX() + frameWidth,
                plr.getY() + frameHeight);

//            getCurrentFrame(isMoving);
        getCurrentFrame(boosting);


        if (mortality) {
            canvas.drawBitmap(bitmapBob,
                    frameToDraw,
                    whereToDraw, paint);
        }
        if (!mortality) {
            canvas.drawBitmap(bitmapBobWhite,
                    frameToDraw,
                    whereToDraw, paint);
        }


        enemyCount = GView.enemyCount;
        enumy = GView.enumy;
        //drawing @enm
        for (int i = 0; i < enemyCount; i++) {
            canvas.drawBitmap(
                    enumy[i].getBitmap(),
                    enumy[i].getX(),
                    enumy[i].getY(),
                    paint
            );
        }

        Log.i("tag_colision", "colision" + " score " + score + " mortality: " + mortality);


        //Unlocking the canvas - this prints onto the screen
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    public void getCurrentFrame(boolean boosting) {
        long time = System.currentTimeMillis();
        if (boosting) {// Only animate if bob is moving
            if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {

                    currentFrame = 0;
                }
            }
        } else {
            //frame for sliding while not boosting - might be set to one additional frame, added after last frame for movement
            currentFrame = 0;
        }
        //update the left and right values of the source of
        //the next frame on the spritesheet
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

    }
}
