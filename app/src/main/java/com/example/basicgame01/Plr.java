package com.example.basicgame01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Plr {

    //bitmap to get character from image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    //motion speed for sprite
    private int speedY;


    //Gravity Value to add gravity effect to the ship
    //private final int GRAVITY = -5;
    //>>>private int GRAVITY = -9;
    //todo gravity -9?
    private int GRAVITY = -9;

    //Y boundaries to keep sprite onscreen
    private int maxY;
    private int minY;

    private int maxX;
    private int minX;

    private int screeNx;
    private int screeNy;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 30;

    private volatile boolean upmark = false;
    private volatile boolean downmark = false;

    private Rect detectCollision;

    int xStart = 300, yStart = 300;
    private boolean boosting;

    //CONSTRUCTOR
    public Plr(Context context, int screenX, int screenY, boolean boosting) {
        this.boosting = boosting;
        x = xStart;
        y = yStart;
        speedY = 3;

        //get bitmap from resource and set
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plr20pt);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();
        //top edge's y point is 0 so min y will always be zero
        minY = 0;

        maxX = screenX - bitmap.getWidth();
        minX = 15;

        //boosting value to false
        //boosting = false;

        screeNy = screenY;
        screeNx = screenX;

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        /*
        Midline midpoint = null; 
        
        for (int i = 0; i < screeNx; i++) {
            midpoint =  new Midline(i, screeNx, screeNy);
            midlineArr.add(midpoint);
        }
            midlineValue = midpoint.getYmidline();
*/
    }

    //public ArrayList<Midline> midlineArr = new ArrayList<>();

    //boosting value to true
    public void setBoosting() {
        boosting = true;
    }

    //boosting value to false
    public void stopBoosting() {
        boosting = false;
    }

    ///SIMULATE PUSH WITH monkeyrunner IN DEBUG MODE
    //method to update coordinate of character

    public void update(ArrayList midlineArray) {


        //midlineArray = 260;
        //int midlinevalue = midlineArray;


        //if the ship is boosting
        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        //but controlling it also so that it won't go off the screen
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
        if (boosting) {
            //speeding up the ship
            speedY += 5;
            //speedY = 10;
            //x move function
            x += 20;
        } else {
            //slowing down if not boosting
            //speedY -= 5;
            speedY = 3;
            //x move function
            x -= 10;
        }
        //controlling the top speed
        if (speedY > MAX_SPEED) {
            speedY = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speedY < MIN_SPEED) {
            speedY = MIN_SPEED;
        }


        int midpointForNow = (int) midlineArray.get(x);


        //this borders need to be at least the size of speed
        // to ensure that ship WILL appear in this zone
        if (y >= (midpointForNow - speedY) && y < midpointForNow) {
            boosting = false;
            //+10 so we exit this dead zone
            // it is -size of the zone at the opposite side
            //y = y + speedY + 18;
            y = y + speedY + speedY;
        } else if (y >= midpointForNow && y < (midpointForNow + speedY)) {
            boosting = false;
            //-10 so we exit this dead zone
            // it is -size of the zone at the opposite side
            //y = y - speedY - 18;
            y = y - speedY - speedY;
        }


        if (y < midpointForNow - speedY) {
            Log.i("tag_upperscreen", y + "<" + screeNy / 2);
            Log.i("tag_spdY_p_GRAV", "speedY:" + speedY + "GRAV:" + GRAVITY);
            //moving the ship up
            y = y + speedY + GRAVITY;
            //speedY = -speedY;

        } else if (y >= midpointForNow + speedY) {
            Log.i("tag_downrscreen>", y + ">" + screeNy / 2);
            //GRAVITY is minus value (upward) so wee need to reverse it - and - = +
            y = y - speedY - GRAVITY;
            //GRAVITY = - GRAVITY;
            //speedY = -speedY;


        }


        Log.i("tag_y", "y" + y);
        Log.i("tag_midlineVal", " " + midpointForNow);

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();


    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    /*
     * These are getters you can generate it automatically
     * right click on editor -> generate -> getters
     * */
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getxStart() {
        return xStart;
    }

    public int getyStart() {
        return yStart;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speedY;
    }

    public boolean getBoosting() { return boosting; }


}
