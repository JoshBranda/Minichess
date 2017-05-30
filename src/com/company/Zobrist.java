package com.company;

/**
 * Created by joshuasander on 5/28/17.
 * Code based on program from https://www.youtube.com/watch?v=gyLCFfrLGIM
 */
//import java.security.*;
public class Zobrist {
    public long zArray[][][] = new long[2][6][64];
    private long zBlackMove;
    public long zobrist;
    private int hashVal;

    /*
    public long random64() {
        SecureRandom random = new SecureRandom();
        return random.nextLong();
    }
    */
    public static long random64() {
        return (long)(Math.random()*1000000000000000000L);
    }

    public Zobrist() {
        for (int color = 0; color < 2; color++)
        {
            for (int pieceType = 0; pieceType < 6; pieceType++)
            {
                for (int square = 0; square < 30; square++)
                {
                    zArray[color][pieceType][square] = random64();
                }
            }
        }
        zBlackMove = random64();
        hashVal = 1299827;
    }

    public int getZobristHash(White white, Black black, boolean whiteOnMove) {
        zobrist = 0;

        white.calculateZobrist(this);
        black.calculateZobrist(this);

        if (!whiteOnMove)
            zobrist ^= zBlackMove;

        zobrist = zobrist % hashVal;

        //System.out.println(zobrist);

        return (int)zobrist;

    }
}
