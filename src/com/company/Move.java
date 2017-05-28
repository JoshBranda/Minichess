package com.company;
/**
 * Created by joshuasander on 4/29/17.
 */
public class Move implements Comparable<Move>{
    public int oldX, oldY, newX, newY;
    private char takenPiece, attackPiece;
    private boolean promotion;
    private Piece myPiece;
    private Piece opponentPiece;
    private int value;

    public Move () {};

    public Move(int prevX, int prevY, int curX, int curY, Piece toPiece, Piece toOpponent, int toValue) {
        oldX = prevX;
        oldY = prevY;
        newX = curX;
        newY = curY;
        promotion = false;
        myPiece = toPiece;
        opponentPiece = toOpponent;
        value = toValue;
    }

    public int getValue(){return value;}

    @Override
    public int compareTo(Move comparestu) {
        int compareage=((Move)comparestu).getValue();
            return compareage-this.value;
    }

    public int getNewX(){return newX;}
    public int getNewY(){return newY;}

    public int makeMove(char [][] board) {
        if (board[newX][newY] == 'k' || board[newX][newY] == 'K')
           return -1;

        else if (newX == 0 && myPiece.getChar() == 'P') {
                board[newX][newY] = 'Q';
                board[oldX][oldY] = '.';
                promotion = true;
                myPiece.setTaken();
        }

        else if (newX == 5 && myPiece.getChar() == 'p') {
                board[newX][newY] = 'q';
                board[oldX][oldY] = '.';
                promotion = true;
                myPiece.setTaken();
        }

        else {
            board[newX][newY] = myPiece.getChar();
            board[oldX][oldY] = '.';
            myPiece.setXY(newX,newY);
        }

        if (opponentPiece != null) {
            opponentPiece.setTaken();
        }

        if (promotion)
            return 1;

        return 0;

    }

    public boolean undoMove(char [][] board) {
        if (opponentPiece != null) {
            opponentPiece.undoTaken();
            board[newX][newY] = opponentPiece.getChar();
        }
        else
            board[newX][newY] = '.';

        if (promotion) {
            myPiece.undoTaken();
            board[oldX][oldY] = myPiece.getChar();
        }

        else {
            board[oldX][oldY] = myPiece.getChar();
            myPiece.setXY(oldX, oldY);
        }

        return promotion;
    }

    public boolean setBoard(char [][] board, char toSet) {
        attackPiece = toSet;
        takenPiece = board[newX][newY];

        if (newX == 0) {
            if (attackPiece == 'P') {
                board[newX][newY] = 'Q';
                board[oldX][oldY] = '.';
                promotion = true;
                return promotion;
            }
        }

        else if (newX == 5) {
            if (attackPiece == 'p') {
                board[newX][newY] = 'q';
                board[oldX][oldY] = '.';
                promotion = true;
                return promotion;
            }
        }

        board[newX][newY] = attackPiece;
        board[oldX][oldY] = '.';

        return promotion;
    }

    public void setPiece(Piece toSet) {
        if (promotion == false) {
            toSet.setXY(newX, newY);
        }
        else
            toSet.setTaken();
    }

    public boolean restorePiece(Piece toSet) {
        if (promotion == false) {
            toSet.setXY(oldX, oldY);
            return false;
        }

        toSet.undoTaken();
        return true;
    }

    public void setChoice(int [] toSet) {

        toSet[0] = oldX;
        toSet[1] = oldY;
        toSet[2] = newX;
        toSet[3] = newY;
    }

    public void restoreBoard(char [][] board) {
       // if (promotion == false) {
            board[newX][newY] = takenPiece;
            board[oldX][oldY] = attackPiece;
       /* }
        else {
            if (attackPiece == 'Q') {
                board[newX][newY] = takenPiece;
                board[oldX][oldY] = 'P';
                promotion = false;
            }
            else if (attackPiece == 'q') {
                board[newX][newY] = takenPiece;
                board[oldX][oldY] = 'p';
                promotion = false;
            }
        }
        */
    }

    public void display() {
        System.out.printf("%d%d to %d%d\n", oldX, oldY, newX, newY);
    }
    public void displayLine() {
        System.out.printf("%d%d to %d%d ", oldX, oldY, newX, newY);
    }
}
