package com.company;

import java.util.List;

/**
 * Created by joshuasander on 5/13/17.
 */
public class Bishop extends Piece {
    public Bishop(int toX, int toY, char color) {
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

        int value;

        int tempX = 1;
        int tempY = 1;
        char current = '.';

        while (y-tempY >= 0 && x-tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up-left
            current = myBoard[x - tempX][y - tempY];
            tempX++;
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack up-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x-tempX,y-tempY);
            value = getValue(myBoard[x-tempX][y-tempY]);
            Move temp = new Move(x, y, x-tempX, y-tempY, this, temp3, value);
            myMoves.add(temp);
        }


        tempX = 1;
        tempY = 1;
        current = '.';

        while (y+tempY < columns && x-tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up-right
            current = myBoard[x - tempX][y + tempY];
            tempX++;
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack up-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x-tempX,y+tempY);
            value = getValue(myBoard[x-tempX][y+tempY]);
            Move temp = new Move(x, y, x-tempX, y+tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y+tempY < columns && x+tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down-right
            current = myBoard[x + tempX][y + tempY];
            tempX++;
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack down-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x+tempX,y+tempY);
            value = getValue(myBoard[x+tempX][y+tempY]);
            Move temp = new Move(x, y, x+tempX, y+tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y-tempY >= 0 && x+tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down-left
            current = myBoard[x + tempX][y - tempY];
            tempX++;
            tempY++;
        }

        if (Character.isLowerCase(current)) { //can attack down-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x+tempX,y-tempY);
            value = getValue(myBoard[x+tempX][y-tempY]);
            Move temp = new Move(x, y, x+tempX, y-tempY, this, temp3, value);
            myMoves.add(temp);
        }

        /*
        All of the attacks have been evaluated for the Queen.  Now, we evaluate moves (without attacks) for their value.
         */

        if (x - 1 >= 0 && myBoard[x - 1][y] == '.') {//Can move up
            Move temp = new Move(x, y, x - 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (y + 1 < columns && myBoard[x][y+1] == '.') {//Can move right
            Move temp = new Move(x, y, x, y+1, this, null, 0);
            myMoves.add(temp);
        }

        if (x +1 < rows && myBoard[x + 1][y] == '.') {//Can move down
            Move temp = new Move(x, y, x + 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (y - 1 >= columns && myBoard[x][y-1] == '.') {//Can move left
            Move temp = new Move(x, y, x, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;

        while (x-tempX >= 0 && y-tempY >= 0) { //check up-left for move
            current = myBoard[x - tempX][y - tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x-tempX, y-tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }


        tempX = 1;
        tempY = 1;

        while (x-tempX >= 0 && y+tempY < columns) { //check up-right for move
            current = myBoard[x - tempX][y+tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x-tempX, y+tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        tempX = 1;
        tempY = 1;

        while (x+tempX < rows && y+tempY < columns) { //check down-right for move
            current = myBoard[x + tempX][y+tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x+tempX, y+tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        tempX = 1;
        tempY = 1;

        while (x+tempX < rows && y-tempY >= 0) { //check down-left for move
            current = myBoard[x + tempX][y-tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x+tempX, y-tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        return 1;
    }


    public int checkMoves (char [][] myBoard, White opponent, List<Move> myMoves) {
        if (taken)
            return 0;

        int value;

        int tempX = 1;
        int tempY = 1;
        char current = '.';

        while (y-tempY >= 0 && x-tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up-left
            current = myBoard[x - tempX][y - tempY];
            tempX++;
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack up-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x-tempX,y-tempY);
            value = getValue(myBoard[x-tempX][y-tempY]);
            Move temp = new Move(x, y, x-tempX, y-tempY, this, temp3, value);
            myMoves.add(temp);
        }


        tempX = 1;
        tempY = 1;
        current = '.';

        while (y+tempY < columns && x-tempX >= 0 && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack up-right
            current = myBoard[x - tempX][y + tempY];
            tempX++;
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack up-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x-tempX,y+tempY);
            value = getValue(myBoard[x-tempX][y+tempY]);
            Move temp = new Move(x, y, x-tempX, y+tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y+tempY < columns && x+tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down-right
            current = myBoard[x + tempX][y + tempY];
            tempX++;
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack down-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x+tempX,y+tempY);
            value = getValue(myBoard[x+tempX][y+tempY]);
            Move temp = new Move(x, y, x+tempX, y+tempY, this, temp3, value);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;
        current = '.';

        while (y-tempY >= 0 && x+tempX < rows && Character.isLowerCase(current) == false && Character.isUpperCase(current) == false) { //check attack down-left
            current = myBoard[x + tempX][y - tempY];
            tempX++;
            tempY++;
        }

        if (Character.isUpperCase(current)) { //can attack down-left
            tempX--;
            tempY--;

            Piece temp3 = opponent.takenPiece(x+tempX,y-tempY);
            value = getValue(myBoard[x+tempX][y-tempY]);
            Move temp = new Move(x, y, x+tempX, y-tempY, this, temp3, value);
            myMoves.add(temp);
        }

        /*
        All of the attacks have been evaluated for the Queen.  Now, we evaluate moves (without attacks) for their value.
         */

        if (x - 1 >= 0 && myBoard[x - 1][y] == '.') {//Can move up
            Move temp = new Move(x, y, x - 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (y + 1 < columns && myBoard[x][y+1] == '.') {//Can move right
            Move temp = new Move(x, y, x, y+1, this, null, 0);
            myMoves.add(temp);
        }

        if (x +1 < rows && myBoard[x + 1][y] == '.') {//Can move down
            Move temp = new Move(x, y, x + 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (y - 1 >= columns && myBoard[x][y-1] == '.') {//Can move left
            Move temp = new Move(x, y, x, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        tempX = 1;
        tempY = 1;

        while (x-tempX >= 0 && y-tempY >= 0) { //check up-left for move
            current = myBoard[x - tempX][y - tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x-tempX, y-tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }


        tempX = 1;
        tempY = 1;

        while (x-tempX >= 0 && y+tempY < columns) { //check up-right for move
            current = myBoard[x - tempX][y+tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x-tempX, y+tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        tempX = 1;
        tempY = 1;

        while (x+tempX < rows && y+tempY < columns) { //check down-right for move
            current = myBoard[x + tempX][y+tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x+tempX, y+tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        tempX = 1;
        tempY = 1;

        while (x+tempX < rows && y-tempY >= 0) { //check down-left for move
            current = myBoard[x + tempX][y-tempY];

            if (current == '.') {
                Move temp = new Move(x, y, x+tempX, y-tempY, this, null, 0);
                myMoves.add(temp);
            }

            else
                break;

            tempX++;
            tempY++;
        }

        return 1;
    }

}
