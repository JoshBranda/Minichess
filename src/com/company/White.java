
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

        int [] choice = new int[4];
        int [] choice2 = new int[4];
        long currentTime = System.currentTimeMillis();


            cap = totalTime / (MAX - moveCount);
            System.out.printf("Your cap is: %d, totalTime: %d, moveCount: %d\n", cap, totalTime, moveCount);
            //cap = 2500;
            opponent.setCap(this.cap);

            for (int x = 1; x < (MAX - moveCount + 1); x++) {
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

            System.out.printf("Depth: %d\n", depth);


        /*
        depth = 10;
        cap = 300000;
        opponent.setCap(this.cap);
        int negVal = -chooseMove(board, choice, opponent, depth, -200000, 200000, currentTime);
        */

        char one = (char)(5 - choice[0] + 49); //Convert the coordinates into readable moves for display
        char two = (char)(choice[1] + 97);
        char three = (char)(5 - choice[2] + 49);
        char four = (char)(choice[3] + 97);

        int oldX = choice[0], oldY = choice[1], newX = choice[2], newY = choice[3];

        char temp = board[newX][newY];


        Piece myTemp = takenPiece(oldX, oldY);

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
            return -(this.evalPlayer() - opponent.evalPlayer());
        }

        int temp, negaMax;
        List<Move> myMoves = new ArrayList();

        for (int x = 0; x < numPieces; x++) {
            pieces.get(x).checkMoves(board, opponent, myMoves);
        }

        Collections.sort(myMoves);

        int z = myMoves.get(0).makeMove(board);

        if (z == -1) {
            if (depth == this.depth) {
                myMoves.get(0).setChoice(choice);
            }
            return -100000;
        }

        if (depth == this.depth) {
            myMoves.get(0).setChoice(choice);
        }

        if (z == 1)
            addQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY(), 'Q');

        int indx = myZobrist.getZobristHash(this, opponent, true);
        long tempPosition = myZobrist.getPosition();
        PosnValue tempPos = myTable.get(indx);

        if (tempPos != null && tempPos.position == tempPosition  && ( (tempPos.alpha < tempPos.myValue && tempPos.myValue < tempPos.beta) || (tempPos.alpha <= alpha && beta <= tempPos.beta)) && tempPos.depth >= depth )
            negaMax = tempPos.myValue;

        else {
            incrementMoves();
            negaMax = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
            //System.out.printf("%d value\n", negaMax);
            decrementMoves();

            if (tempPos != null && tempPos.depth <= depth) {
                //myTable.remove(indx);
                myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));
            }

            //else if (tempPos == null && myTable.size() <= MAX_HASH)
            else if (tempPos == null)
                myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));

        }

        if (myMoves.get(0).undoMove(board))
            removeQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY());

        if (System.currentTimeMillis() > oldTime + cap) {
            choice[0] = -1;
            return 1;
        }

        if (negaMax > beta) {
            return -negaMax;
        }

        if (negaMax > alpha)
            alpha = negaMax;

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

            /*
            incrementMoves();
            temp = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
            //System.out.printf("%d value\n", temp);
            decrementMoves();
            */

            indx = myZobrist.getZobristHash(this, opponent, true);
            tempPosition = myZobrist.getPosition();
            tempPos = myTable.get(indx);

            if (tempPos != null && tempPos.position == tempPosition && ( (tempPos.alpha < tempPos.myValue && tempPos.myValue < tempPos.beta) || (tempPos.alpha <= alpha && beta <= tempPos.beta)) && tempPos.depth >= depth )
                temp = tempPos.myValue;

            else {
                incrementMoves();
                temp = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
                //System.out.printf("%d value\n", temp);
                decrementMoves();

                if (tempPos != null && tempPos.depth <= depth) {
                    //myTable.remove(indx);
                    myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));
                }

                else if (tempPos == null)
                    myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));
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

            if (temp > negaMax) {
                negaMax = temp;
                if (depth == this.depth) {
                    myMoves.get(x).setChoice(choice);
                }
            }

            if (temp > alpha)
                alpha = temp;
        }
        return -negaMax;
    }

}
