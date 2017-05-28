package com.company;

/**
 * Created by joshuasander on 5/28/17.
 */
public class PosnValue {
    public int myValue;
    public int [] myMove;
    public int alpha;
    public int beta;
    public int depth;
    public int player;


    public PosnValue () {
        myMove = null;
        depth = 0;
        player = -1;
    }

    public PosnValue (int toValue, int prevX, int prevY, int newX, int newY, int toAl, int toBe, int toDepth, int toPlayer) {

        myValue = toValue;
        myMove = new int[4];
        myMove[0] = prevX;
        myMove[1] = prevY;
        myMove[2] = newX;
        myMove[3] = newY;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
        player = toPlayer;
    }

    public void setVal (int toValue, int prevX, int prevY, int newX, int newY, int toAl, int toBe, int toDepth, int toPlayer) {

        myValue = toValue;

        if (myMove == null)
            myMove = new int[4];

        myMove[0] = prevX;
        myMove[1] = prevY;
        myMove[2] = newX;
        myMove[3] = newY;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
        player = toPlayer;
    }

}
