package com.company;
import java.util.*;

/**
 * Created by joshuasander on 4/28/17.
 */
public class Black extends Player {
    public Black(char [][] board, int startMoves, HashTable toHash, Zobrist toZob) {
        super(startMoves, toHash, toZob);

        pieces = new ArrayList();

        //Scan the board (2-d char array) and add all pieces belonging to this player to their pieces list.
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
        color = 1;
    }

    public int makeMove(char [][] board, White opponent, char[] toCopy) {
        if (moveCount == MAX) {
            System.out.println("DRAW!!!");
            return 0;
        }

        int[] choice = new int[4]; //Master choice
        int[] choice2 = new int[4]; //Choice for each iterative deepening step, ignored of deepening terminated
        long currentTime = System.currentTimeMillis();

            cap = totalTime / (MAX - moveCount);  //Calculate average move time remaining
            //cap = 2500;
            opponent.setCap(this.cap); //Concord other player with the same move time

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

        /*
        depth = 8;
        cap = 300000;
        opponent.setCap(this.cap);
        int negVal = -chooseMove(board, choice, opponent, depth, -200000, 200000, currentTime);
        */

        char one = (char) (5 - choice[0] + 49); //Convert the coordinates into readable moves for display
        char two = (char) (choice[1] + 97);
        char three = (char) (5 - choice[2] + 49);
        char four = (char) (choice[3] + 97);

        //Store the values in variables which are easier to reference
        int oldX = choice[0], oldY = choice[1], newX = choice[2], newY = choice[3];

        //Value of the spot to be replaced
        char temp = board[newX][newY];

        //Get the piece that is to be moved
        Piece myTemp = takenPiece(oldX, oldY);

        //Check for attack
        if (Character.isUpperCase(board[newX][newY])) {
            opponent.removePiece(newX, newY);
        }

        int index;

        //Check for promotion
        if (newX == 0 && myTemp.getChar() == 'p') {
            board[newX][newY] = 'q';
            board[oldX][oldY] = '.';
            index = retIndex(oldX, oldY);
            pieces.remove(index);
            pieces.add(new Queen(newX, newY, 'q'));
        } else {
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

        //Check for game over
        if (temp == 'K') {
            System.out.println("Black wins!");
            return 0;
        }

        System.out.println();

        return 1;
    }

    public int chooseMove(char [][] board, int [] choice, White opponent, int depth, int alpha, int beta, long oldTime) {
        if (depth == 0 || moveCount == MAX) {
            return -(this.evalPlayer() - opponent.evalPlayer());
        }

        int temp, negaMax;
        List<Move> myMoves = new ArrayList();

        for (int x = 0; x < numPieces; x++) {
            pieces.get(x).checkMoves(board, opponent, myMoves);
        }

        //Sort the moves by those scoring the hightest points first
        Collections.sort(myMoves);

        //Make the first move
        int z = myMoves.get(0).makeMove(board);

        //Found a win
        if (z == -1) {
            if (depth == this.depth) {
                myMoves.get(0).setChoice(choice);
            }
            return -100000;
        }

        //Set the choice to the first move so that it is not null if all choices are of equal value
        if (depth == this.depth) {
            myMoves.get(0).setChoice(choice);
        }

        //Check for promotion
        if (z == 1)
            addQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY(), 'q');

        //Get the index in the hashtable for this position
        int indx = myZobrist.getZobristHash(opponent, this, false);
        //Get the identifier for this position.  There is a chance of collision with another long identifier, but small
        long tempPosition = myZobrist.getPosition();
        PosnValue tempPos = myTable.get(indx);

        //If the hashtable stores useful information, we can avoid the longer move evaluation
        if (tempPos != null && tempPos.position == tempPosition && ( (tempPos.alpha < tempPos.myValue && tempPos.myValue < tempPos.beta) || (tempPos.alpha <= alpha && beta <= tempPos.beta)) && tempPos.depth >= depth )
            negaMax = tempPos.myValue;

        //The hashtable did not store useful information
        else {
            incrementMoves();
            negaMax = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
            decrementMoves();

            if (tempPos != null && tempPos.depth <= depth) {
                myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));
            }

            else if (tempPos == null)
                myTable.put(indx,  new PosnValue(negaMax, alpha, beta, depth, tempPosition));

        }

        if (myMoves.get(0).undoMove(board))
            removeQueen(myMoves.get(0).getNewX(), myMoves.get(0).getNewY());

        //Check to make sure time hasn't expired.  Nullify search if it has.
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
                addQueen(myMoves.get(x).getNewX(), myMoves.get(x).getNewY(), 'q');

            indx = myZobrist.getZobristHash(opponent, this, false);
            tempPosition = myZobrist.getPosition();
            tempPos = myTable.get(indx);

            if (tempPos != null && tempPos.position == tempPosition && ( (tempPos.alpha < tempPos.myValue && tempPos.myValue < tempPos.beta) || (tempPos.alpha <= alpha && beta <= tempPos.beta)) && tempPos.depth >= depth )
                temp = tempPos.myValue;

            else {
                incrementMoves();
                temp = opponent.chooseMove(board, choice, this, depth - 1, -beta, -alpha, oldTime);
                decrementMoves();

                if (tempPos != null && tempPos.depth <= depth) {
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
                if (depth == this.depth)
                    myMoves.get(x).setChoice(choice);
            }

            if (temp > alpha)
                alpha = temp;
        }

        return -negaMax;
    }

}
