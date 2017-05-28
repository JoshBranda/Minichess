package com.company;
import java.util.List;
/**
 * Created by joshuasander on 5/13/17.
 */
public class King extends Piece {
    public King (int toX, int toY, char color) {
        super(toX, toY);
        value = 10000;
        this.color = color;
        type = 5;
    }

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

        if (y + 1 < columns && x + 1 < rows && Character.isLowerCase(myBoard[x + 1][y + 1])) { //Can attack down-right
            Piece temp3 = opponent.takenPiece(x+1,y+1);
            value = getValue(myBoard[x+1][y+1]);
            Move temp = new Move(x, y, x+1, y+1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && x + 1 < rows && Character.isLowerCase(myBoard[x + 1][y - 1])) { //Can attack down-left
            Piece temp3 = opponent.takenPiece(x+1,y-1);
            value = getValue(myBoard[x+1][y-1]);
            Move temp = new Move(x, y, x+1, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && Character.isLowerCase(myBoard[x - 1][y])) { //Can attack up
            Piece temp3 = opponent.takenPiece(x-1,y);
            value = getValue(myBoard[x-1][y]);
            Move temp = new Move(x, y, x-1, y, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && Character.isLowerCase(myBoard[x][y + 1])) { //Can attack right
            Piece temp3 = opponent.takenPiece(x, y + 1);
            value = getValue(myBoard[x][y+1]);
            Move temp = new Move(x, y, x, y + 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x + 1 < rows && Character.isLowerCase(myBoard[x + 1][y])) { //Can attack down
            Piece temp3 = opponent.takenPiece(x+1,y);
            value = getValue(myBoard[x+1][y]);
            Move temp = new Move(x, y, x+1, y, this, temp3, value);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && Character.isLowerCase(myBoard[x][y - 1])) { //Can attack left
            Piece temp3 = opponent.takenPiece(x,y-1);
            value = getValue(myBoard[x][y-1]);
            Move temp = new Move(x, y, x, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && myBoard[x - 1][y] == '.') {//Can move up
            Move temp = new Move(x, y, x - 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && y + 1 < columns && myBoard[x - 1][y + 1] == '.') {//Can move up-right
            Move temp = new Move(x, y, x - 1, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (y + 1 < columns && myBoard[x][y + 1] == '.') {//Can move right
            Move temp = new Move(x, y, x, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && y + 1 < columns && myBoard[x + 1][y + 1] == '.') {//Can move down-right
            Move temp = new Move(x, y, x + 1, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && myBoard[x + 1][y] == '.') {//Can move down
            Move temp = new Move(x, y, x + 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && y - 1 >= columns && myBoard[x + 1][y - 1] == '.') {//Can move down-left
            Move temp = new Move(x, y, x + 1, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && myBoard[x][y - 1] == '.') {//Can move left
            Move temp = new Move(x, y, x, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && y - 1 >= 0 && myBoard[x - 1][y - 1] == '.') {//Can move up-left
            Move temp = new Move(x, y, x - 1, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        return 1;
    }

    public int checkMoves (char [][] myBoard, White opponent, List<Move> myMoves) {
        if (taken)
            return 0;


        if (y - 1 >= 0 && x - 1 >= 0 && Character.isUpperCase(myBoard[x - 1][y - 1])) { //Can attack up-left
            Piece temp3 = opponent.takenPiece(x-1,y-1);
            value = getValue(myBoard[x-1][y-1]);
            Move temp = new Move(x, y, x-1, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && x - 1 >= 0 && Character.isUpperCase(myBoard[x - 1][y + 1])) { //Can attack up-right
            Piece temp3 = opponent.takenPiece(x - 1, y + 1);
            value = getValue(myBoard[x-1][y+1]);
            Move temp = new Move(x, y, x - 1, y + 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && x + 1 < rows && Character.isUpperCase(myBoard[x + 1][y + 1])) { //Can attack down-right
            Piece temp3 = opponent.takenPiece(x+1,y+1);
            value = getValue(myBoard[x+1][y+1]);
            Move temp = new Move(x, y, x+1, y+1, this, temp3, value);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && x + 1 < rows && Character.isUpperCase(myBoard[x + 1][y - 1])) { //Can attack down-left
            Piece temp3 = opponent.takenPiece(x+1,y-1);
            value = getValue(myBoard[x+1][y-1]);
            Move temp = new Move(x, y, x+1, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && Character.isUpperCase(myBoard[x - 1][y])) { //Can attack up
            Piece temp3 = opponent.takenPiece(x-1,y);
            value = getValue(myBoard[x-1][y]);
            Move temp = new Move(x, y, x-1, y, this, temp3, value);
            myMoves.add(temp);
        }

        if (y + 1 < columns && Character.isUpperCase(myBoard[x][y + 1])) { //Can attack right
            Piece temp3 = opponent.takenPiece(x, y + 1);
            value = getValue(myBoard[x][y+1]);
            Move temp = new Move(x, y, x, y + 1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x + 1 < rows && Character.isUpperCase(myBoard[x + 1][y])) { //Can attack down
            Piece temp3 = opponent.takenPiece(x+1,y);
            value = getValue(myBoard[x+1][y]);
            Move temp = new Move(x, y, x+1, y, this, temp3, value);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && Character.isUpperCase(myBoard[x][y - 1])) { //Can attack left
            Piece temp3 = opponent.takenPiece(x,y-1);
            value = getValue(myBoard[x][y-1]);
            Move temp = new Move(x, y, x, y-1, this, temp3, value);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && myBoard[x - 1][y] == '.') {//Can move up
            Move temp = new Move(x, y, x - 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && y + 1 < columns && myBoard[x - 1][y + 1] == '.') {//Can move up-right
            Move temp = new Move(x, y, x - 1, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (y + 1 < columns && myBoard[x][y + 1] == '.') {//Can move right
            Move temp = new Move(x, y, x, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && y + 1 < columns && myBoard[x + 1][y + 1] == '.') {//Can move down-right
            Move temp = new Move(x, y, x + 1, y + 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && myBoard[x + 1][y] == '.') {//Can move down
            Move temp = new Move(x, y, x + 1, y, this, null, 0);
            myMoves.add(temp);
        }

        if (x + 1 < rows && y - 1 >= columns && myBoard[x + 1][y - 1] == '.') {//Can move down-left
            Move temp = new Move(x, y, x + 1, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        if (y - 1 >= 0 && myBoard[x][y - 1] == '.') {//Can move left
            Move temp = new Move(x, y, x, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        if (x - 1 >= 0 && y - 1 >= 0 && myBoard[x - 1][y - 1] == '.') {//Can move up-left
            Move temp = new Move(x, y, x - 1, y - 1, this, null, 0);
            myMoves.add(temp);
        }

        return 1;
    }

}
