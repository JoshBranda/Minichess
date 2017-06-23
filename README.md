# Minichess
Minichess with Move generator

This program is an artificial intelligence minichess player.  This readme file will detail:

1. How to run the program
2. What is Minichess?
3. Summary of features
4. Features explained
5. Domain analysis/future improvements

## How to run the program
Using a Linux terminal with all of the source code in a single folder, use javac *.java to compile everything.  Next, use Java Main command to run the program.  This will allow you to play against the computer with the comptuer as White and you as Black.

## What is Minichess?
A full explanation of the rules of minichess can be found here:
[Minichess rules](http://wiki.cs.pdx.edu/mc-howto/rules.html)
Here is a quick summary of major differences:

The board is 5 x 6.  There are five pawns and one each of the remaining piece types.  The bishop can attack/move as usual, but can also move one non-diagonal space, if it is free.  Castling is not allowed.  There are a maximum of 40 turns (for each side), resulting in a draw after that.  Kings are allowed to move into check.

## Summary of features
* Negamax Search
* Alpha-beta Pruning
* Search Heuristic
* Iterative Deepening
* Transposition Tables

### Features Explained
#### Negamax search
How does a computer know when it has won in minichess?
