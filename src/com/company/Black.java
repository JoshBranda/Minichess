package com.company;
import java.util.*;

/**
 * Created by joshuasander on 4/28/17.
 */
public class Black extends Player {
    public Black(char [][] board, int startMoves) {
        super(startMoves);

        pieces = new ArrayList();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                switch (board[x][y]) {
                    case 'p':
                        pieces.add(new Pawn(x, y, 'p'));
                        break;
                    case 'q':
                        pieces.add(new Queen(x, y, 'q'));
                        break;
                    case 'k':
                        pieces.add(new King(x, y, 'k'));
                        break;
                    case 'r':
                        pieces.add(new Rook(x, y, 'r'));
                        break;
                    case 'b':
                        pieces.add(new Bishop(x,y, 'b'));
                        break;
                    case 'n':
                        pieces.add(new Knight(x, y, 'n'));
                        break;
                    default:
                        break;
                }
            }
        }

        numPieces = pieces.size();
    }
/*
    public int makeMove(char [][] board, White opponent, char []toCopy) {
        if (moveCount == MAX) {
            System.out.println("DRAW!!!");
            return 0;
        }

        int [] choice = new int[4];

        int negVal = -chooseMove(board, choice, opponent, depth);

        char one = (char)(5 - choice[0] + 49); //Convert the coordinates into readable moves for display
        char two = (char)(choice[1] + 97);
        char three = (char)(5 - choice[2] + 49);
        char four = (char)(choice[3] + 97);

        int oldX = choice[0], oldY = choice[1], newX = choice[2], newY = choice[3];

        char temp = board[newX][newY];

        Move temp2 = new Move(oldX, oldY, newX, newY);

        if (temp2.setBoard(board, board[oldX][oldY])) { //Check for promotion
            this.addQueen(newX, newY, 'q'); //Add queen to list if promotion applies
            this.removePiece(oldX, oldY);
        }
        else
            this.updatePiece(oldX, oldY, newX, newY);
        //board[newX][newY] = board[oldX][oldY];
        //board[oldX][oldY] = '.';

        if (Character.isUpperCase(temp)) {
            opponent.removePiece(newX, newY);
        }

        toCopy[0] = two;
        toCopy[1] = one;
        toCopy[2] = '-';
        toCopy[3] = four;
        toCopy[4] = three;

        incrementMoves();

        return 1;
    }


    /*
    public int checkMovesBlack(char [][] board, int [] indices) {
        int max = -10000; //Lowest value possible to indicate null move
        int index = 0;

        int []values = new int[numPieces];

        for (int x = 0; x < numPieces; x++) {
            values[x] = pieces.get(x).checkMoves(board, this);
        }

        //This algorithm orders the indices of the pieces to try first based on highest attack value to lowest
        for (int x = 0; x < numPieces; x++) {
            for (int y = 0; y < numPieces; y++) {
                if (values[y] > max) {
                    max = values[y];
                    index = y;
                }
            }
            indices[x] = index;
            values[index] = -10000;
            max = -10000;
        }

        return 1;
    }
    */

    public int chooseMove(char [][] board, int [] choice, White opponent, int depth, int alpha, int beta) {
        if (depth == 0 || moveCount == MAX) {
            //System.out.printf("%d Black value\n", this.evalPlayer() - opponent.evalPlayer());
            return -(this.evalPlayer() - opponent.evalPlayer());
        }

        int temp;
        List<Move> myMoves = new ArrayList();

        for (int x = 0; x < numPieces; x++) {
            pieces.get(x).checkMoves(board, opponent, myMoves);
        }
/*
        for (int x = 0; x < myMoves.size(); x++) {
            myMoves.get(x).display();
        }

        if (myMoves.size() == 0) {
            System.out.println("Here it is");
            displayBoard(board);
        }
        */

        int z = myMoves.get(0).makeMove(board);

        if (z == -1)
            return -100000;

        if (z == 1)
            addQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY(), 'q');

        incrementMoves();
        int negaMax = opponent.chooseMove(board, choice, this, depth-1, -beta, -alpha);
       // System.out.printf("%d Black value\n", negaMax);
        decrementMoves();

        if (myMoves.get(0).undoMove(board))
            removeQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY());

        if (depth == this.depth)
            myMoves.get(0).setChoice(choice);

        if (negaMax > beta) {
            return -negaMax;
        }

        if (negaMax > alpha)
            alpha = negaMax;

        int length = myMoves.size();
        /*
        System.out.print("Black: ");
        myMoves.get(0).displayLine();
        System.out.printf(" Value: %d\n", negaMax);
        */

        for (int x = 1; x < length; x++) {

            z = myMoves.get(x).makeMove(board);

            if (z == -1)
                return -100000;

            if (z == 1)
                addQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY(), 'q');

            incrementMoves();
            temp = opponent.chooseMove(board, choice, this, depth-1, -beta, -alpha);
            //System.out.printf("%d Black value\n", temp);
            decrementMoves();

            if (myMoves.get(x).undoMove(board))
                removeQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY());

            if (temp >= beta) {
                return -temp;
            }
        //    System.out.printf("Current Black:%d Negamax:%d\n", temp, negaMax);
            if (temp > negaMax) {
                negaMax = temp;
                if (depth == this.depth)
                    myMoves.get(x).setChoice(choice);
            }

            if (temp > alpha)
                alpha = temp;
/*
            System.out.print("Black: ");
            myMoves.get(x).displayLine();
            System.out.printf(" Value: %d\n", temp);
            */
        }

        /*
        displayBoard(board);
        System.out.println();
        */

        //System.out.printf("%d Highest value black\n", negaMax);
        return -negaMax;
    }

}
