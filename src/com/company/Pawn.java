package com.company;

import java.util.*;

/**
 * Created by joshuasander on 4/28/17.
 */
public class Pawn extends Piece {
    public Pawn () {};

    public Pawn (int toX, int toY, char color) {
        super(toX, toY);
        value = 100;
        this.color = color;
    };

    public char getChar() {
        return color;
    }

    public int checkMoves (char [][] myBoard, Black opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        int value;

        if (y - 1 >= 0 && x - 1 >= 0 && Character.isLowerCase(myBoard[x - 1][y - 1])) { //Can attack up-left
            Piece temp3 = opponent.takenPiece(x-1,y-1);
            value = getValue(myBoard[x-1][y-1]);
            Move temp = new Move(x, y, x-1, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && x - 1 >= 0 && Character.isLowerCase(myBoard[x - 1][y + 1])) { //Can attack up-right
            Piece temp3 = opponent.takenPiece(x - 1, y + 1);
            value = getValue(myBoard[x-1][y+1]);
            Move temp = new Move(x, y, x - 1, y + 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && myBoard[x - 1][y] == '.') {//Can move forward
            if (x - 1 == 0) {
                Move temp = new Move(x, y, x + 1, y, this, null, 800);
                myMoves.add(temp);
            }
            else {
                Move temp = new Move(x, y, x - 1, y, this, null, 0);
                myMoves.add(temp);
            }
        }

        return 1;
    }

    public int checkMoves (char [][] myBoard, White opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        int value;

        //System.out.println("Im here!");

        if (y - 1 >= 0 && x + 1 < rows && Character.isUpperCase(myBoard[x + 1][y - 1])) { //Can attack down-left
            Piece temp3 = opponent.takenPiece(x + 1, y - 1);
            value = getValue(myBoard[x+1][y-1]);
            Move temp = new Move(x, y, x + 1, y - 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && x + 1 < rows && Character.isUpperCase(myBoard[x + 1][y + 1])) { //Can attack down-right
            Piece temp3 = opponent.takenPiece(x + 1, y + 1);
            value = getValue(myBoard[x+1][y+1]);
            Move temp = new Move(x, y, x + 1, y + 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x + 1 < rows && myBoard[x + 1][y] == '.') {//Can move forward
            if (x+1 == rows - 1) {
                Move temp = new Move(x, y, x + 1, y, this, null, 800);
                myMoves.add(temp);
            }
            else {
                Move temp = new Move(x, y, x + 1, y, this, null, 0);
                myMoves.add(temp);
            }
        }

        return 1;
    }


}
