package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int result;
        char playerStart;
        char[][] toCopy;
        List<String> board = new ArrayList<String>();
        String row;
        int rows, columns;
        long elapsed, start;
        boolean diagnostic = true;

        Scanner input = new Scanner(System.in);

        rows = 6;
        columns = 5;
        toCopy = new char[rows][columns];

        for (int x = 2; x <= 3; x++) {
            for (int y = 0; y < columns; y++) {
                toCopy[x][y] = '.';
            }
        }

        for (int y = 0; y < columns; y++) {
            toCopy[1][y] = 'p';
        }

        for (int y = 0; y < columns; y++) {
            toCopy[4][y] = 'P';
        }

        toCopy[0][0] = 'k';
        toCopy[0][1] = 'q';
        toCopy[0][2] = 'b';
        toCopy[0][3] = 'n';
        toCopy[0][4] = 'r';
        toCopy[5][0] = 'R';
        toCopy[5][1] = 'N';
        toCopy[5][2] = 'B';
        toCopy[5][3] = 'Q';
        toCopy[5][4] = 'K';

        HashMap<Long, PosnValue> myHash;
        //myHash = new HashMap(1299827, 1);
        //myHash = new HashMap<Long, PosnValue>();

        try {
            FileInputStream fileIn = new FileInputStream("TranspositionTable.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myHash = (HashMap) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("TTable not found");
            c.printStackTrace();
            return;
        }

        if (args.length < 3 && args[0].equals("diagnostic")) {
            System.out.printf("Your hash size is: %d\n", myHash.size());

            int w = Integer.parseInt(args[1]);
            int v = 0;

            for (long key : myHash.keySet()) {
                System.out.println("------------------------------------------------");
                myHash.get(key).display();
                //System.out.println("key: " + key + " value: " + loans.get(key));
                if (v > w)
                    break;
                v++;
            }

            return;
        }

        //Zobrist myZob = new Zobrist();
        Zobrist myZob = null;

        try {
            FileInputStream fileIn = new FileInputStream("test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myZob = (Zobrist) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Zobrist not found");
            c.printStackTrace();
            return;
        }

        //System.out.println(myZob.zArray[1][1][1]);
        //System.out.println();

        //This should only be used once when creating the zobrist file
        /*
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("test.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myZob);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in test.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
        */

        White myWhite = new White(toCopy, 1, myHash, myZob);
        Black myBlack = new Black(toCopy, 1, myHash, myZob);

        if (args.length < 1) {

            //Play against me

            char[] myChoice = new char[5];
            char[] myArray = new char[5];

            start = System.currentTimeMillis();

            while (true) {
                int x = myWhite.makeMove(toCopy, myBlack, myChoice);

                if (x == 0)
                    break;

                elapsed = start - System.currentTimeMillis();
                myWhite.subtractTime(elapsed);

                System.out.println("Your move");

                row = input.nextLine();

                if (row == null)
                    break;

                start = System.currentTimeMillis();

                for (int z = 0; z < 5; z++) {
                    myArray[z] = row.charAt(z);
                }

                int oldY = Character.getNumericValue(myArray[0]) - 10;
                int oldX = 6 - Character.getNumericValue(myArray[1]);
                int newY = Character.getNumericValue(myArray[3]) - 10;
                int newX = 6 - Character.getNumericValue(myArray[4]);

                char temp = toCopy[newX][newY];

                System.out.println(temp);

                Piece myTemp = myBlack.takenPiece(oldX, oldY);

                if (Character.isUpperCase(toCopy[newX][newY])) {
                    myWhite.removePiece(newX, newY);
                }

                int index;

                if (newX == 5 && myTemp.getChar() == 'p') {
                    toCopy[newX][newY] = 'q';
                    toCopy[oldX][oldY] = '.';
                    index = myBlack.retIndex(oldX, oldY);
                    myBlack.pieces.remove(index);
                    myBlack.pieces.add(new Queen(newX, newY, 'q'));
                } else {
                    toCopy[newX][newY] = myTemp.getChar();
                    toCopy[oldX][oldY] = '.';
                    myTemp.setXY(newX, newY);
                }

                // myWhite.displayPositions();
                // myBlack.displayPositions();
                myWhite.displayBoard(toCopy);
                System.out.println();

                //System.out.println("Test2");
                if (temp == 'K') {
                    System.out.println("Black wins!");
                    break;
                }

                myBlack.incrementMoves();

            }

        } else if (args.length < 3 && args[0].equals("offer")) {
            char response;
            char[] myArray = new char[5];

            Client myClient = new Client("imcs.svcs.cs.pdx.edu", "3589", "lizardSpock", "minichess1");

            response = args[1].charAt(0);

            if (response == 'W')
                myClient.offer('W');

            if (response == 'B')
                myClient.offer('B');

            if (args[1].equals("?")) {
                response = myClient.offer('?');
            }


            //Computer begins playing game as white

            if (response == 'W') {

                start = System.currentTimeMillis();

                while (true) {
                    char[] myChoice = new char[5];

                    int x = myWhite.makeMove(toCopy, myBlack, myChoice);

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    if (myWhite.getMoves() >= 41)
                        break;

                    if (x == 0)
                        break;

                    elapsed = start - System.currentTimeMillis();
                    myWhite.subtractTime(elapsed);

                    //start = System.currentTimeMillis();

                    row = myClient.getMove();

                    if (row == null)
                        break;

                    //myWhite.correctTime(System.currentTimeMillis() - start);
                    start = System.currentTimeMillis();

                    for (int z = 0; z < 5; z++) {
                        myArray[z] = row.charAt(z);
                    }

                    int oldY = Character.getNumericValue(myArray[0]) - 10;
                    int oldX = 6 - Character.getNumericValue(myArray[1]);
                    int newY = Character.getNumericValue(myArray[3]) - 10;
                    int newX = 6 - Character.getNumericValue(myArray[4]);

                    char temp = toCopy[newX][newY];

                    Piece myTemp = myBlack.takenPiece(oldX, oldY);

                    if (Character.isUpperCase(toCopy[newX][newY])) {
                        myWhite.removePiece(newX, newY);
                    }

                    int index;

                    if (newX == 5 && myTemp.getChar() == 'p') {
                        toCopy[newX][newY] = 'q';
                        toCopy[oldX][oldY] = '.';
                        index = myBlack.retIndex(oldX, oldY);
                        myBlack.pieces.remove(index);
                        myBlack.pieces.add(new Queen(newX, newY, 'q'));
                    } else {
                        toCopy[newX][newY] = myTemp.getChar();
                        toCopy[oldX][oldY] = '.';
                        myTemp.setXY(newX, newY);
                    }

                    if (temp == 'K') {
                        System.out.println("Black wins!");
                        break;
                    }
                    myBlack.incrementMoves();
                }
            }

            if (response == 'B') {

                while (true) {
                    row = myClient.getMove();

                    if (row == null)
                        break;

                    start = System.currentTimeMillis();

                    myWhite.incrementMoves();

                    for (int z = 0; z < 5; z++) {
                        myArray[z] = row.charAt(z);
                    }

                    int oldY = Character.getNumericValue(myArray[0]) - 10;
                    int oldX = 6 - Character.getNumericValue(myArray[1]);
                    int newY = Character.getNumericValue(myArray[3]) - 10;
                    int newX = 6 - Character.getNumericValue(myArray[4]);

                    char temp = toCopy[newX][newY];

                    Piece myTemp = myWhite.takenPiece(oldX, oldY);

                    if (Character.isLowerCase(toCopy[newX][newY])) {
                        myBlack.removePiece(newX, newY);
                    }

                    int index;

                    if (newX == 0 && myTemp.getChar() == 'P') {
                        toCopy[newX][newY] = 'Q';
                        toCopy[oldX][oldY] = '.';
                        index = myWhite.retIndex(oldX, oldY);
                        myWhite.pieces.remove(index);
                        myWhite.pieces.add(new Queen(newX, newY, 'Q'));
                    } else {
                        toCopy[newX][newY] = myTemp.getChar();
                        toCopy[oldX][oldY] = '.';
                        myTemp.setXY(newX, newY);
                    }

                    if (temp == 'k') {
                        System.out.println("White wins!");
                        break;
                    }

                    char[] myChoice = new char[5];

                    int x = myBlack.makeMove(toCopy, myWhite, myChoice);

                    if (x == 0)
                        break;

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    elapsed = start - System.currentTimeMillis();
                    myBlack.subtractTime(elapsed);

                    //start = System.currentTimeMillis();


                    //myWhite.correctTime(System.currentTimeMillis() - start);
                }

            }
            myClient.close();
        } else if (args.length < 3 && args[0].equals("accept")) {
            char response;
            char[] myArray = new char[5];

            Client myClient = new Client("imcs.svcs.cs.pdx.edu", "3589", "lizardSpock", "minichess1");

            response = myClient.accept(args[1], '?');


            //Computer begins playing game as white

            if (response == 'W') {

                start = System.currentTimeMillis();

                while (true) {
                    char[] myChoice = new char[5];

                    int x = myWhite.makeMove(toCopy, myBlack, myChoice);

                    if (x == 0)
                        break;

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    elapsed = start - System.currentTimeMillis();
                    myWhite.subtractTime(elapsed);

                    //start = System.currentTimeMillis();

                    row = myClient.getMove();

                    if (row == null)
                        break;

                    //myWhite.correctTime(System.currentTimeMillis() - start);
                    start = System.currentTimeMillis();

                    for (int z = 0; z < 5; z++) {
                        myArray[z] = row.charAt(z);
                    }

                    int oldY = Character.getNumericValue(myArray[0]) - 10;
                    int oldX = 6 - Character.getNumericValue(myArray[1]);
                    int newY = Character.getNumericValue(myArray[3]) - 10;
                    int newX = 6 - Character.getNumericValue(myArray[4]);

                    char temp = toCopy[newX][newY];

                    Piece myTemp = myBlack.takenPiece(oldX, oldY);

                    if (Character.isUpperCase(toCopy[newX][newY])) {
                        myWhite.removePiece(newX, newY);
                    }

                    int index;

                    if (newX == 5 && myTemp.getChar() == 'p') {
                        toCopy[newX][newY] = 'q';
                        toCopy[oldX][oldY] = '.';
                        index = myBlack.retIndex(oldX, oldY);
                        myBlack.pieces.remove(index);
                        myBlack.pieces.add(new Queen(newX, newY, 'q'));
                    } else {
                        toCopy[newX][newY] = myTemp.getChar();
                        toCopy[oldX][oldY] = '.';
                        myTemp.setXY(newX, newY);
                    }

                    if (temp == 'K') {
                        System.out.println("Black wins!");
                        break;
                    }

                    myBlack.incrementMoves();
                }
            }

            if (response == 'B') {

                while (true) {
                    row = myClient.getMove();

                    if (row == null)
                        break;

                    start = System.currentTimeMillis();

                    myWhite.incrementMoves();

                    for (int z = 0; z < 5; z++) {
                        myArray[z] = row.charAt(z);
                    }

                    int oldY = Character.getNumericValue(myArray[0]) - 10;
                    int oldX = 6 - Character.getNumericValue(myArray[1]);
                    int newY = Character.getNumericValue(myArray[3]) - 10;
                    int newX = 6 - Character.getNumericValue(myArray[4]);

                    char temp = toCopy[newX][newY];

                    Piece myTemp = myWhite.takenPiece(oldX, oldY);

                    if (Character.isLowerCase(toCopy[newX][newY])) {
                        myBlack.removePiece(newX, newY);
                    }

                    int index;

                    if (newX == 0 && myTemp.getChar() == 'P') {
                        toCopy[newX][newY] = 'Q';
                        toCopy[oldX][oldY] = '.';
                        index = myWhite.retIndex(oldX, oldY);
                        myWhite.pieces.remove(index);
                        myWhite.pieces.add(new Queen(newX, newY, 'Q'));
                    } else {
                        toCopy[newX][newY] = myTemp.getChar();
                        toCopy[oldX][oldY] = '.';
                        myTemp.setXY(newX, newY);
                    }

                    if (temp == 'k') {
                        System.out.println("White wins!");
                        break;
                    }

                    char[] myChoice = new char[5];

                    int x = myBlack.makeMove(toCopy, myWhite, myChoice);

                    if (x == 0)
                        break;

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    elapsed = start - System.currentTimeMillis();
                    myBlack.subtractTime(elapsed);
                }
            }
            myClient.close();
        }


        try {
            FileOutputStream fileOut =
                    new FileOutputStream("TranspositionTable.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myHash);
            out.close();
            fileOut.close();
            System.out.printf("Serialized hash map is saved in TranspositionTable.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
