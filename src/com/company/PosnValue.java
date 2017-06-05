package com.company;

import java.io.Serializable;

/**
 * Created by joshuasander on 5/28/17.
 */
public class PosnValue implements Serializable{
    public int myValue;
    public int alpha;
    public int beta;
    public int depth;
    public long position;


    public PosnValue () {
        depth = 0;
    }

    public PosnValue (PosnValue toCopy) {
        myValue = toCopy.myValue;
        alpha = toCopy.alpha;
        beta = toCopy.beta;
        depth = toCopy.depth;
        position = toCopy.position;
    }

    public PosnValue (int toValue, int toAl, int toBe, int toDepth, long toPosition) {

        myValue = toValue;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
        position = toPosition;
    }

    public void setVal (int toValue, int toAl, int toBe, int toDepth, long toPosition) {

        myValue = toValue;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
        position = toPosition;
    }

    public void display () {
        System.out.printf("Value: %d\nAlpha: %d\nBeta: %d\nDepth: %d\n\n", myValue, alpha, beta, depth);
    }

}
