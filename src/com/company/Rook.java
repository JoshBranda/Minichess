package com.company;

import java.util.List;

/**
 * Created by joshuasander on 5/13/17.
 */
public class Rook extends Piece {
    public Rook(int toX, int toY, char color) {
        super(toX, toY);
        value = 500;
        this.color = color;
    }

    public char getChar() {
        return color;
    }
    public int checkMoves(char[][] myBoard, Black opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        int value;

        int tempX = 1;
        int tempY = 1;
        char current = '.';

        while (x - tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up
            current = myBoard[x - tempX][y];
            tempX++;
        }

        if (Character.isLowerCase(current)) { //can attack up
            tempX--;

            Piece temp3 = opponent.takenPiece(x - tempX, y);
            value = getValue(myBoard[x-tempX][y]);
            Move temp = new Move(x, y, x - tempX, y, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y + tempY < columns && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack right
            current = myBoard[x][y + tempY];
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack up
            tempY--;

            Piece temp3 = opponent.takenPiece(x, y + tempY);
            value = getValue(myBoard[x][y+tempY]);
            Move temp = new Move(x, y, x, y + tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (x + tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down
            current = myBoard[x + tempX][y];
            tempX++;
        }

        if (Character.isLowerCase(current)) { //can attack up
            tempX--;

            Piece temp3 = opponent.takenPiece(x + tempX, y);
            value = getValue(myBoard[x+tempX][y]);
            Move temp = new Move(x, y, x + tempX, y, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y - tempY >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack left
            current = myBoard[x][y - tempY];
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack up
            tempY--;

            Piece temp3 = opponent.takenPiece(x, y - tempY);
            value = getValue(myBoard[x][y-tempY]);
            Move temp = new Move(x, y, x, y - tempY, this, temp3, value);
            myMoves.add(temp);
        }

        /*
        All of the attacks have been evaluated for the Queen.  Now, we evaluate moves (without attacks) for their value.
         */

        tempX = 1;
        tempY = 1;

        while (x - tempX >= 0) { //check up for move
            current = myBoard[x - tempX][y];

            if (current == '.') {
                Move temp = new Move(x, y, x - tempX, y, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempX++;
        }

        tempX = 1;
        tempY = 1;

        while (y + tempY < columns) { //check right for move
            current = myBoard[x][y + tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x, y + tempY, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempY++;
        }


        tempX = 1;
        tempY = 1;

        while (x + tempX < rows) { //check down for move
            current = myBoard[x + tempX][y];

            if (current == '.') {
                Move temp = new Move(x, y, x + tempX, y, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempX++;
        }

        tempX = 1;
        tempY = 1;

        while (y - tempY >= 0) { //check left for move
            current = myBoard[x][y - tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x, y - tempY, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempY++;
        }

        return 1;
    }


    public int checkMoves(char[][] myBoard, White opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        int value;

        int tempX = 1;
        int tempY = 1;
        char current = '.';

        while (x - tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up
            current = myBoard[x - tempX][y];
            tempX++;
        }

        if (Character.isUpperCase(current)) { //can attack up
            tempX--;

            Piece temp3 = opponent.takenPiece(x - tempX, y);
            value = getValue(myBoard[x-tempX][y]);
            Move temp = new Move(x, y, x - tempX, y, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y + tempY < columns && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack right
            current = myBoard[x][y + tempY];
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack right
            tempY--;

            Piece temp3 = opponent.takenPiece(x, y + tempY);
            value = getValue(myBoard[x][y+tempY]);
            Move temp = new Move(x, y, x, y + tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (x + tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down
            current = myBoard[x + tempX][y];
            tempX++;
        }

        if (Character.isUpperCase(current)) { //can attack down
            tempX--;

            Piece temp3 = opponent.takenPiece(x + tempX, y);
            value = getValue(myBoard[x+tempX][y]);
            Move temp = new Move(x, y, x + tempX, y, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y - tempY >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack left
            current = myBoard[x][y - tempY];
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack up
            tempY--;

            Piece temp3 = opponent.takenPiece(x, y - tempY);
            value = getValue(myBoard[x][y-tempY]);
            Move temp = new Move(x, y, x, y - tempY, this, temp3, value);
            myMoves.add(temp);
        }

        /*
        All of the attacks have been evaluated for the Queen.  Now, we evaluate moves (without attacks) for their value.
         */

        tempX = 1;
        tempY = 1;

        while (x - tempX >= 0) { //check up for move
            current = myBoard[x - tempX][y];

            if (current == '.') {
                Move temp = new Move(x, y, x - tempX, y, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempX++;
        }

        tempX = 1;
        tempY = 1;

        while (y + tempY < columns) { //check right for move
            current = myBoard[x][y + tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x, y + tempY, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempY++;
        }


        tempX = 1;
        tempY = 1;

        while (x + tempX < rows) { //check down for move
            current = myBoard[x + tempX][y];

            if (current == '.') {
                Move temp = new Move(x, y, x + tempX, y, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempX++;
        }

        tempX = 1;
        tempY = 1;

        while (y - tempY >= 0) { //check left for move
            current = myBoard[x][y - tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x, y - tempY, this, null, 0);
                myMoves.add(temp);
            } else
                break;

            tempY++;
        }

        return 1;
    }


}
