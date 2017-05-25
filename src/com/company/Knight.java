package com.company;
import java.util.*;
/**
 * Created by joshuasander on 5/13/17.
 */
public class Knight extends Piece {
    public Knight(int toX, int toY, char color) {
        super(toX, toY);
        value = 300;
        this.color = color;
    }

    public char getChar() {
        return color;
    }

    public int checkMoves (char [][] myBoard, Black opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        if (x-2 >= 0 && y - 1 >= 0) {
            if (Character.isLowerCase(myBoard[x - 2][y - 1])) { //Can attack up-left
                Piece temp3 = opponent.takenPiece(x-2,y-1);
                Move temp = new Move(x, y, x-2, y-1, this, temp3, value);
                myMoves.add(temp);
            }
            else if (myBoard[x - 2][y - 1] == '.') {
                Move temp = new Move(x, y, x - 2, y - 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x-2 >= 0 && y + 1 < columns) {
            if (Character.isLowerCase(myBoard[x - 2][y + 1])) { //Can attack up-right
                Piece temp3 = opponent.takenPiece(x-2,y+1);
                Move temp = new Move(x, y, x-2, y+1, this, temp3, value);
                myMoves.add(temp);
            }
            else if (myBoard[x - 2][y + 1] == '.') {
                Move temp = new Move(x, y, x - 2, y + 1, this, null, 0);
                myMoves.add(temp);
            }
        }


        if (x-1 >= 0 && y - 2 >= 0) {
            if (Character.isLowerCase(myBoard[x - 1][y - 2])) { //Can attack left-up
                Piece temp3 = opponent.takenPiece(x-1,y-2);
                Move temp = new Move(x, y, x-1, y-2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x - 1][y - 2] == '.') {
                Move temp = new Move(x, y, x - 1, y - 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+1 < rows && y - 2 >= 0) {
            if (Character.isLowerCase(myBoard[x + 1][y - 2])) { //Can attack left-down
                Piece temp3 = opponent.takenPiece(x+1,y-2);
                Move temp = new Move(x, y, x+1, y-2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 1][y - 2] == '.') {
                Move temp = new Move(x, y, x + 1, y - 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+2 < rows && y - 1 >= 0) {
            if (Character.isLowerCase(myBoard[x + 2][y - 1])) { //Can attack down-left
                Piece temp3 = opponent.takenPiece(x+2,y-1);
                Move temp = new Move(x, y, x+2, y-1, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 2][y - 1] == '.') {
                Move temp = new Move(x, y, x + 2, y - 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+2 < rows && y + 1 < columns) {
            if (Character.isLowerCase(myBoard[x + 2][y + 1])) { //Can attack down-right
                Piece temp3 = opponent.takenPiece(x+2,y+1);
                Move temp = new Move(x, y, x+2, y+1, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 2][y + 1] == '.') {
                Move temp = new Move(x, y, x + 2, y + 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+1 < rows && y + 2 < columns) {
            if (Character.isLowerCase(myBoard[x +1][y + 2])) { //Can attack right-down
                Piece temp3 = opponent.takenPiece(x+1,y+2);
                Move temp = new Move(x, y, x+1, y+2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 1][y + 2] == '.') {
                Move temp = new Move(x, y, x + 1, y + 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x-1 >= 0 && y + 2 < columns) {
            if (Character.isLowerCase(myBoard[x -1][y + 2])) { //Can attack right-up
                Piece temp3 = opponent.takenPiece(x-1,y+2);
                Move temp = new Move(x, y, x-1, y+2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x - 1][y + 2] == '.') {
                Move temp = new Move(x, y, x - 1, y + 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        return 1;
    }

    public int checkMoves (char [][] myBoard, White opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        if (x-2 >= 0 && y - 1 >= 0) {
            if (Character.isUpperCase(myBoard[x - 2][y - 1])) { //Can attack up-left
                Piece temp3 = opponent.takenPiece(x-2,y-1);
                Move temp = new Move(x, y, x-2, y-1, this, temp3, value);
                myMoves.add(temp);
            }
            else if (myBoard[x - 2][y - 1] == '.') {
                Move temp = new Move(x, y, x - 2, y - 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x-2 >= 0 && y + 1 < columns) {
            if (Character.isUpperCase(myBoard[x - 2][y + 1])) { //Can attack up-right
                Piece temp3 = opponent.takenPiece(x-2,y+1);
                Move temp = new Move(x, y, x-2, y+1, this, temp3, value);
                myMoves.add(temp);
            }
            else if (myBoard[x - 2][y + 1] == '.') {
                Move temp = new Move(x, y, x - 2, y + 1, this, null, 0);
                myMoves.add(temp);
            }
        }


        if (x-1 >= 0 && y - 2 >= 0) {
            if (Character.isUpperCase(myBoard[x - 1][y - 2])) { //Can attack left-up
                Piece temp3 = opponent.takenPiece(x-1,y-2);
                Move temp = new Move(x, y, x-1, y-2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x - 1][y - 2] == '.') {
                Move temp = new Move(x, y, x - 1, y - 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+1 < rows && y - 2 >= 0) {
            if (Character.isUpperCase(myBoard[x + 1][y - 2])) { //Can attack left-down
                Piece temp3 = opponent.takenPiece(x+1,y-2);
                Move temp = new Move(x, y, x+1, y-2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 1][y - 2] == '.') {
                Move temp = new Move(x, y, x + 1, y - 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+2 < rows && y - 1 >= 0) {
            if (Character.isUpperCase(myBoard[x + 2][y - 1])) { //Can attack down-left
                Piece temp3 = opponent.takenPiece(x+2,y-1);
                Move temp = new Move(x, y, x+2, y-1, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 2][y - 1] == '.') {
                Move temp = new Move(x, y, x + 2, y - 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+2 < rows && y + 1 < columns) {
            if (Character.isUpperCase(myBoard[x + 2][y + 1])) { //Can attack down-right
                Piece temp3 = opponent.takenPiece(x+2,y+1);
                Move temp = new Move(x, y, x+2, y+1, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 2][y + 1] == '.') {
                Move temp = new Move(x, y, x + 2, y + 1, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x+1 < rows && y + 2 < columns) {
            if (Character.isUpperCase(myBoard[x +1][y + 2])) { //Can attack right-down
                Piece temp3 = opponent.takenPiece(x+1,y+2);
                Move temp = new Move(x, y, x+1, y+2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x + 1][y + 2] == '.') {
                Move temp = new Move(x, y, x + 1, y + 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        if (x-1 >= 0 && y + 2 < columns) {
            if (Character.isUpperCase(myBoard[x -1][y + 2])) { //Can attack right-up
                Piece temp3 = opponent.takenPiece(x-1,y+2);
                Move temp = new Move(x, y, x-1, y+2, this, temp3, value);
                myMoves.add(temp);
            }

            else if (myBoard[x - 1][y + 2] == '.') {
                Move temp = new Move(x, y, x - 1, y + 2, this, null, 0);
                myMoves.add(temp);
            }
        }

        return 1;
    }

}
