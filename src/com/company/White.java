
package com.company;
/**
 * Created by joshuasander on 4/28/17.
 */
import java.util.*;

public class White extends Player {
    public White(char [][] board, int startMoves) {
        super(startMoves);

        pieces = new ArrayList();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                switch (board[x][y]) {
                    case 'P':
                        pieces.add(new Pawn(x, y, 'P'));
                        break;
                    case 'Q':
                        pieces.add(new Queen(x, y, 'Q'));
                        break;
                    case 'K':
                        pieces.add(new King(x, y, 'K'));
                        break;
                    case 'R':
                        pieces.add(new Rook(x,y, 'R'));
                        break;
                    case 'B':
                        pieces.add(new Bishop(x,y, 'B'));
                        break;
                    case 'N':
                        pieces.add(new Knight(x, y, 'N'));
                        break;
                    default:
                        break;
                }
            }
        }
        numPieces = pieces.size();
    }

    public int makeMove(char [][] board, Black opponent, char[] toCopy) {
        if (moveCount == MAX) {
            System.out.println("DRAW!!!");
            return 0;
        }

        int [] choice = new int[4];

        //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHH");
        int negVal = -chooseMove(board, choice, opponent, depth, -200000, 200000);

        //System.out.printf("%d%d-%d%d\n", choice[0], choice[1], choice[2], choice[3]);
        //displayBoard(board);

        //System.out.printf("%d%d-%d%d\n", choice[0], choice[1], choice[2], choice[3]);

        char one = (char)(5 - choice[0] + 49); //Convert the coordinates into readable moves for display
        char two = (char)(choice[1] + 97);
        char three = (char)(5 - choice[2] + 49);
        char four = (char)(choice[3] + 97);

        int oldX = choice[0], oldY = choice[1], newX = choice[2], newY = choice[3];

        char temp = board[newX][newY];

        /*
        displayBoard(board);
        displayPositions();

        System.out.printf("%d%d\n", oldX, oldY);
        */

        Piece myTemp = takenPiece(oldX, oldY);
       // System.out.println(myTemp.getChar());

        if (Character.isLowerCase(board[newX][newY])) {
            opponent.removePiece(newX, newY);
        }

        int index;

        if (newX == 0 && myTemp.getChar() == 'P') {
                board[newX][newY] = 'Q';
                board[oldX][oldY] = '.';
                index = retIndex(oldX,oldY);
                pieces.remove(index);
                pieces.add(new Queen(newX, newY, 'Q'));
        }

        else {
            board[newX][newY] = myTemp.getChar();
            board[oldX][oldY] = '.';
            myTemp.setXY(newX, newY);
        }

        toCopy[0] = two;
        toCopy[1] = one;
        toCopy[2] = '-';
        toCopy[3] = four;
        toCopy[4] = three;

        incrementMoves();

        this.updatePiece(oldX, oldY, newX, newY);

       // displayPositions();
       // opponent.displayPositions();

        this.displayBoard(board);

        System.out.printf("%c%c-%c%c\n", two, one, four, three);
        /*
        System.out.println();
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        System.out.println("_________________________________________________________");
        */

        if (temp == 'k') {
            System.out.println("White wins!");
            return 0;
        }

        System.out.println();

        return 1;
    }
/*
    public int checkMovesWhite(char [][] board, int [] indices) {
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

    public int chooseMove(char [][] board, int [] choice, Black opponent, int depth, int alpha, int beta) {
        if (depth == 0 || moveCount == MAX) {
            //System.out.printf("%d White value\n", this.evalPlayer() - opponent.evalPlayer());
            return -(this.evalPlayer() - opponent.evalPlayer());
        }

        int temp;
        List<Move> myMoves = new ArrayList();

        for (int x = 0; x < numPieces; x++) {
            pieces.get(x).checkMoves(board, opponent, myMoves);
        }

        Collections.sort(myMoves);

        //displayPositions();
        //System.out.println(depth);

        int z = myMoves.get(0).makeMove(board);

        if (z == -1) {
            if (depth == this.depth) {
                myMoves.get(0).setChoice(choice);
            }
            return -100000;
        }

        if (z == 1)
            addQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY(), 'Q');

        incrementMoves();
        int negaMax = opponent.chooseMove(board, choice, this, depth-1, -beta, -alpha);
        //System.out.printf("%d value\n", negaMax);
        decrementMoves();

        if (depth == this.depth) {
            myMoves.get(0).setChoice(choice);
            //System.out.printf("Setting at depth %d of value %d\n", depth, negaMax);
            //System.out.printf("%d%d %d%d found\n", choice[0], choice[1], choice[2], choice[3]);
        }

        if (myMoves.get(0).undoMove(board))
            removeQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY());

        if (negaMax > beta) {
            return -negaMax;
        }

        if (negaMax > alpha)
            alpha = negaMax;

        /*
        System.out.print("White: ");
        myMoves.get(0).displayLine();
        System.out.printf(" Value: %d\n", negaMax);
        */

        //myMoves.get(0).setChoice(choice);

        int length = myMoves.size();

        for (int x = 1; x < length; x++) {

            z = myMoves.get(x).makeMove(board);

            if (z == -1) {
                if (depth == this.depth) {
                    myMoves.get(x).setChoice(choice);
                }
                return -100000;
            }

            if (z == 1)
                addQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY(), 'Q');

            incrementMoves();
            temp = opponent.chooseMove(board, choice, this, depth-1, -beta, -alpha);
            //System.out.printf("%d value\n", temp);
            decrementMoves();

            if (myMoves.get(x).undoMove(board))
                removeQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY());

            if (temp >= beta) {
                return -temp;
            }

            //System.out.printf("Current White:%d Negamax:%d\n", temp, negaMax);
            if (temp > negaMax) {
                negaMax = temp;
                if (depth == this.depth) {
                    //System.out.printf("Setting at depth %d of value %d\n", depth, negaMax);
                    myMoves.get(x).setChoice(choice);
                   //System.out.printf("%d%d %d%d updated\n", choice[0], choice[1], choice[2], choice[3]);
                }
            }

            if (temp > alpha)
                alpha = temp;
            /*
            System.out.print("White: ");
            myMoves.get(x).displayLine();
            System.out.printf(" Value: %d\n", temp);
            */

            //System.out.printf("New Negamax:%d\n", negaMax);

            //displayBoard(board);
            //displayPositions();
            //System.out.println(depth);
        }
        /*
        if (depth == this.depth)
        System.out.printf("\nChoice is %d%d to %d%d\n",choice[0],choice[1],choice[2],choice[3]);

        displayBoard(board);
        System.out.println();
        */
        //displayPositions();

        //System.out.printf("%d Highest value white\n", negaMax);
        return -negaMax;
    }
}
