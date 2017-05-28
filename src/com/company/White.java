
package com.company;
/**
 * Created by joshuasander on 4/28/17.
 */
import java.util.*;

public class White extends Player {
    public White(char [][] board, int startMoves, HashTable toHash, Zobrist toZob) {
        super(startMoves, toHash, toZob);

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
        color = 0;
    }

    public int makeMove(char [][] board, Black opponent, char[] toCopy) {
        if (moveCount == MAX) {
            System.out.println("DRAW!!!");
            return 0;
        }

        //depth = 6;
        int [] choice = new int[4];
        int [] choice2 = new int[4];
        long currentTime = System.currentTimeMillis();

        if (moveCount > 3) {

            cap = totalTime / (MAX - moveCount);
            //cap = 2500;
            opponent.setCap(this.cap);

            for (int x = 1; x < MAX - moveCount; x++) {
                depth = x;
                int negVal = -chooseMove(board, choice2, opponent, depth, -200000, 200000, currentTime);
                if (negVal == -100000)
                    break;

                if (choice2[0] == -1)
                    break;
                for (int z = 0; z < 4; z++) {
                    choice[z] = choice2[z];
                }
                if (negVal == 100000)
                    break;
            }
        }

        else {
            depth = moveCount * 2;
            cap = 300000;
            opponent.setCap(this.cap);
            int negVal = -chooseMove(board, choice, opponent, depth, -200000, 200000, currentTime);
        }

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

        if (temp == 'k') {
            System.out.println("White wins!");
            return 0;
        }

        System.out.println();

        return 1;
    }

    public int chooseMove(char [][] board, int [] choice, Black opponent, int depth, int alpha, int beta, long oldTime) {
        if (depth == 0 || moveCount == MAX) {
            //System.out.printf("%d White value\n", this.evalPlayer() - opponent.evalPlayer());
            return -(this.evalPlayer() - opponent.evalPlayer());
        }

        int temp, negaMax;
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

        int indx = myZobrist.getZobristHash(this, opponent, true);

        if (myTable.myTable[indx] == null || myTable.myTable[indx].depth < depth || myTable.myTable[indx].player != color) {
            incrementMoves();
            negaMax = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
            //System.out.printf("%d value\n", negaMax);
            decrementMoves();

            if (myTable.myTable[indx] == null)
                myTable.myTable[indx] = new PosnValue(negaMax, myMoves.get(0).oldX, myMoves.get(0).oldY, myMoves.get(0).newX, myMoves.get(0).newY, alpha, beta, depth, color);
            else
                myTable.myTable[indx].setVal(negaMax, myMoves.get(0).oldX, myMoves.get(0).oldY, myMoves.get(0).newX, myMoves.get(0).newY, alpha, beta, depth, color);
        }
        else {
            negaMax = myTable.myTable[indx].myValue;
        }

        if (myMoves.get(0).undoMove(board))
            removeQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY());

        if (System.currentTimeMillis() > oldTime + cap) {
            choice[0] = -1;
            return 1;
        }

        if (depth == this.depth) {
            myMoves.get(0).setChoice(choice);
            //System.out.printf("Setting at depth %d of value %d\n", depth, negaMax);
            //System.out.printf("%d%d %d%d found\n", choice[0], choice[1], choice[2], choice[3]);
        }

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

            indx = myZobrist.getZobristHash(this, opponent, true);

            if (myTable.myTable[indx] == null || myTable.myTable[indx].depth < depth || myTable.myTable[indx].player != color) {
                incrementMoves();
                temp = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
                //System.out.printf("%d value\n", temp);
                decrementMoves();

                if (myTable.myTable[indx] == null)
                    myTable.myTable[indx] = new PosnValue(negaMax, myMoves.get(x).oldX, myMoves.get(x).oldY, myMoves.get(x).newX, myMoves.get(x).newY, alpha, beta, depth, color);
                else
                    myTable.myTable[indx].setVal(negaMax, myMoves.get(x).oldX, myMoves.get(x).oldY, myMoves.get(x).newX, myMoves.get(x).newY, alpha, beta, depth, color);
            }

            else {
                temp = myTable.myTable[indx].myValue;
            }

            if (myMoves.get(x).undoMove(board))
                removeQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY());

            if (System.currentTimeMillis() > oldTime + cap) {
                choice[0] = -1;
                return 1;
            }

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
