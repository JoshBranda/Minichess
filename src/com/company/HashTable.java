package com.company;

import java.io.Serializable;

/**
 * Created by joshuasander on 6/4/17.
 */
public class HashTable implements Serializable {
    private PosnValue [] myArray;

    public HashTable() {
        myArray = new PosnValue[2530963];
    }

    public HashTable(HashTable toCopy) {
        myArray = new PosnValue[2530963];

        for (int x = 0; x < 2530963; x++) {
            myArray[x] = new PosnValue(toCopy.myArray[x]);
        }
    }

    public void put(int index, PosnValue toSet) {
        myArray[index] = toSet;
    }

    public PosnValue get(int index) {
        return myArray[index];
    }
}
