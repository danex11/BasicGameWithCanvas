package com.example.basicgame01;

import java.util.Random;

/**
 * created by Daniel Szablewski
 */
public class SinGen {

    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    private int fx;

    /**
     *
     * @param indeX     -- given x value
     * @param screenX   -- screen resolution x
     * @param screenY   -- screen resolution Y
     */
    public SinGen(int indeX, int screenX, int screenY) {

        minX = 0;
        minY = 0;
        maxY = screenY;
        maxX = screenX;

        x = indeX;
    }


    /**
     *
     * @param xval   -- ??
     *
     */
    public void updateMidline(int xval) {

        //>>x -= 1;

        //function of ship movement on y axis on boosting
        //todo where does this x value come from?
        int fxfspeed = (xval / 3) % 360;
       y = (int) (100 + (int) 300 * (Math.sin(Math.toRadians(fxfspeed))));
    //    y = (int) (100 + (int) modulator * (Math.sin(Math.toRadians(fxfspeed))));


        if (y > maxY) {
            y = maxY - (y - maxY);
        }
        if (y < minY) {
            y = -y;
        }

    }



    public int getXmidline() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getYmidline() {
        return y;
    }
}
