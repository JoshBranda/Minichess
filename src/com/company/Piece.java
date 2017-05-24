package com.company;
import java.util.*;
/**
 * Created by joshuasander on 4/28/17.
 */
abstract class Piece {
    protected static final int depth = 3;

    protected int x;
    protected int y;
    protected int value; //Value of this piece
    protected int reward; //Highest attack reward available
    protected boolean taken;
    protected boolean cantMove;
    protected boolean win;
    protected char color;

    public static final int rows = 6;
    public static final int columns = 5;


    public Piece () {};

    public Piece (int toX, int toY) {
        taken = false;
        x = toX;
        y = toY;
        win = false;
    };

    abstract int checkMoves(char [][] myBoard, White player, List<Move> myMoves);
    abstract int checkMoves(char [][] myBoard, Black player, List<Move> myMoves);
    abstract public char getChar();

    public int getX(){return x;}
    public int getY(){return y;}

    public int getVal() {
        if (taken)
            return 0;
        return value;
    }

    public void printBoard(char [][]myBoard) {
        for (int z = 0; z < rows; z++) {
            for (int w = 0; w < columns; w++) {
                System.out.print(myBoard[z][w]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkXY(int toX, int toY) {
        if (x == toX && y == toY && taken == false)
            return true;
        return false;
    }

    public void printPiece() {
        System.out.printf("%d%d %d\n", x, y, value);
    }

    public void setTaken() {
        taken = true;
    }

    public void undoTaken() {
        taken = false;
    }

    public void setXY(int toX, int toY) {
        x = toX;
        y = toY;
    }

    public int getValue(char toCheck) { //Optimize by having getValueWhite / getValueBlack?
        int value = 0;

        switch(toCheck) {
            case 'K':
            case 'k':
                value = 10000;
                break;
            case 'Q':
            case 'q':
                value = 900;
                break;
            case 'R':
            case 'r':
                value = 500;
                break;
            case 'B':
            case 'b':
                value = 300;
                break;
            case 'N':
            case 'n':
                value = 300;
                break;
            case 'P':
            case 'p':
                value = 100;
                break;
            default:
                break;
        }

        return value;
        }
}
