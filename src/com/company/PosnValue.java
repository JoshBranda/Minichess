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


    public PosnValue () {
        depth = 0;
    }

    public PosnValue (PosnValue toCopy) {
        myValue = toCopy.myValue;
        alpha = toCopy.alpha;
        beta = toCopy.beta;
        depth = toCopy.depth;

    }

    public PosnValue (int toValue, int toAl, int toBe, int toDepth) {

        myValue = toValue;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
    }

    public void setVal (int toValue, int toAl, int toBe, int toDepth) {

        myValue = toValue;

        alpha = toAl;
        beta = toBe;
        depth = toDepth;
    }

    public void display () {
        System.out.printf("Value: %d\nAlpha: %d\nBeta: %d\nDepth: %d\n\n", myValue, alpha, beta, depth);
    }

}
