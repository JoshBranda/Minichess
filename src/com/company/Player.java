package com.company;
/**
 * Created by joshuasander on 4/28/17.
 */
import java.util.List;

abstract class Player {
    protected static final int rows = 6;
    protected static final int columns = 5;
    //protected static final long cap = 2500;
    protected static int depth;
    protected static final int MAX = 41;

    protected long cap;
    protected long totalTime;
    protected int numPieces;
    protected int moveCount;
    protected List<Piece> pieces;

    protected boolean checkMate;

    public Player() {};

    public Player(int startMoves) {
        moveCount = startMoves;
        checkMate = false;
        totalTime = 300000;
    };

    public void setCap(long toSet) {cap = toSet;}
    public void subtractTime(long toSubtract) {totalTime-= toSubtract;}
    public void incrementMoves() {moveCount++;}
    public void decrementMoves() {moveCount--;}

    public int evalPlayer() {
        int total = 0;

        for (Piece piece: pieces) {
            total += piece.getVal();
        }
        return total;
    }

    //Find the piece that was taken among this players list and set it to taken
    public Piece takenPiece(int toX, int toY) {
        for (Piece piece: pieces) {
            if (piece.checkXY(toX, toY)) {
//                piece.setTaken();

                return piece;
            }
        }
        return null;
    }

    public void updatePiece(int oldX, int oldY, int newX, int newY) {
        for (Piece piece: pieces) {
            if (piece.checkXY(oldX, oldY)) {
                piece.setXY(newX, newY);
                break;
            }
        }
    }

    public int retIndex(int toX, int toY) {
        for (int x = numPieces - 1; x >= 0; x--) {
            if (pieces.get(x).checkXY(toX, toY))
                return x;
        }
        return -1;
    }

    public int printPiece(int toX, int toY) {
        int z = retIndex(toX, toY);
        pieces.get(z).printPiece();
        return 0;
    }

    public void removePiece(int toX, int toY) {
        int index = retIndex(toX, toY);
        pieces.remove(index);
        numPieces--;
    }

    public void addQueen(int toX, int toY, char toColor) {
        pieces.add(new Queen(toX, toY, toColor));
        numPieces++;
    }

    public void removeQueen(int toX, int toY) { //optomize by removing
        int index = retIndex(toX, toY);
        pieces.remove(index);
        numPieces--;
    }

    public void displayBoard(char[][] toDisplay) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                System.out.print(toDisplay[x][y]);
            }
            System.out.println();
        }
    }

    public void dislpayPieces() {
        for (Piece piece: pieces)
            System.out.print(piece.getChar());
        System.out.println();
    }

    public void displayPositions() {
        for (Piece piece: pieces) {
            System.out.printf("%c %d%d\n", piece.getChar(), piece.getX(), piece.getY());
        }
        System.out.println();
    }

    void setCheck(boolean toSet) {checkMate = toSet;};
    void setPromote(boolean toSet) {checkMate = toSet;};
}

