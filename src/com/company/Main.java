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

        //HashMap<Long, PosnValue> myHash;
        //myHash = new HashMap(1299827, 1);
        //myHash = new HashMap<Long, PosnValue>();
        HashTable myHash;
        //myHash = new HashTable();

        try {
            FileInputStream fileIn = new FileInputStream("CustomTable.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myHash = (HashTable) in.readObject();
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

        /*
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
        */

        if (args.length == 1 && args[0].equals("diagnostic")) {
            int myMax = 2530963;
            int y = 0;
            int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0;
            int eight = 0, nine = 0, ten = 0, eleven = 0, twelve = 0, thirteen = 0, fourteen = 0, fifteen = 0;

            for (int x = 0; x < myMax; x++) {
                if (myHash.get(x) != null) {
                    y++;
                    switch(myHash.get(x).depth) {
                        case(1):
                            one++;
                            break;
                        case(2):
                            two++;
                            break;
                        case(3):
                            three++;
                            break;
                        case(4):
                            four++;
                            break;
                        case(5):
                            five++;
                            break;
                        case(6):
                            six++;
                            break;
                        case(7):
                            seven++;
                            break;
                        case(8):
                            eight++;
                            break;
                        case(9):
                            nine++;
                            break;
                        case(10):
                            ten++;
                            break;
                        case(11):
                            eleven++;
                            break;
                        case(12):
                            twelve++;
                            break;
                        case(13):
                            thirteen++;
                            break;
                        case(14):
                            fourteen++;
                            break;
                        case(15):
                        case(16):
                        case(17):
                        case(18):
                            fifteen++;
                            break;
                        default:
                            break;
                    }
                }
            }

            System.out.printf("Your hash size is: %d\n", y);
            System.out.printf("Depths: 1:%d\n2:%d\n3:%d\n4:%d\n5:%d\n6:%d\n7:%d\n8:%d\n9:%d\n10:%d\n11:%d\n12:%d\n13:%d\n14:%d\n15:%d\n", one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen);

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

                elapsed = System.currentTimeMillis() - start;
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

                    if (x == 0) //Check to see if your move just won the game
                        break;

                    elapsed = System.currentTimeMillis() - start;
                    myWhite.subtractTime(elapsed);

                    row = myClient.getMove();

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

                    if (temp == 'K') { //Check to see if your opponent just won the game
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

                    if (myBlack.getMoves() >= 41)
                        break;

                    if (x == 0)
                        break;

                    elapsed = System.currentTimeMillis() - start;
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

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    if (myWhite.getMoves() >= 41)
                        break;

                    if (x == 0)
                        break;

                    elapsed = System.currentTimeMillis() - start;
                    myWhite.subtractTime(elapsed);

                    row = myClient.getMove();

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

                    String choice = new String(myChoice);

                    myClient.sendMove(choice);

                    if (myBlack.getMoves() >= 41)
                        break;

                    if (x == 0)
                        break;

                    elapsed = System.currentTimeMillis() - start;
                    myBlack.subtractTime(elapsed);
                }
            }
            myClient.close();
        }


        try {
            FileOutputStream fileOut =
                    new FileOutputStream("CustomTable.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myHash);
            out.close();
            fileOut.close();
            System.out.printf("Serialized hash map is saved in CustomTable.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        /*
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
        */
    }
}
