# Minichess
Minichess with Move generator

This program is an artificial intelligence minichess player.  This readme file will detail:

1. How to run the program
2. What is Minichess?
3. Summary of features
4. Features explained
5. Domain analysis/future improvements

## How to run the program
Using a Linux terminal with all of the source code in a single folder, use javac on all of the .java files to compile everything.  Next, use Java Main command to run the program.  This will allow you to play against the computer with the computer as White and you as Black.

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

## Features Explained
### Negamax search
How does a computer know when it has won in minichess?
How does a computer know when it has lost or reached a draw?

These questions are answered by the mechanics of a negamax search.  I will illustrate a naive negamax search with the following example:

Imagine you are the computer and you are playing as White.  You are looking for the "best" move to make in your current position.  Without any search heuristics, you are not able to estimate which move is better than another.  So you "guess" each possible move/attack you can make.  After making a move, you check to see if you have caputered a king.  If not, you pass the board on to your opponent.  Next, you assume the role of your opponent, and follow the same process.  Once you have reached your limit of evaluations (dicussed later), you tally the total score of the board from your perspective.

Each of the pieces have a value assigned to them, with pawns being the lowest and kings the highest.  Assume the A.I. is playing the role of black in the current position and has reached its depth limit of evaluation.  It evaluates its point value for all of its (black's) pieces added together, and then subtracts all of white's pieces from that.  Then, it sends the negation (hence negamax) of that score up to its recursive caller, which would be white in this case.  That way each player knows the value of the board after a particular move is made.

Obviously, the highest value yieled from any of the moves in your current position is a good move to choose.  Without more complicated heuristics, the A.I. will choose this move and make it.  This negamax search assumes the opponent is as "smart" as the computer.  If the opponent is less sophisticated, the A.I. should win.  If the opponent is more sophisticated (i.e. searches at a greater depth), the A.I. will likely lose.

### Alpha-beta Pruning

With negamax search, you will already have a basic, functioning player.  However, alpha-beta pruning gives the player a serious boost.  It uses the "princess bride" logic of: "I know that you know that I know..."

Imagine that the computer moves a rook forward one space to see how well that works.  It immediately finds that you, its opponent, can use your queen to capture its king if the rook is moved.  However, a naive search would still evaluate every single move you could make, including you moving your pawn instead of attacking with the queen.  This is nonsensical.  Since you know you can capture the king, you will clearly take that choice and ignore all others.  Therefore, the A.I. can save significant time if it prunes out all of the moves, such as initially moving its rook in this case, that do not need a full evaluation since they are not optimal.

This example used a checkmate.  However, this covers more nuanced cases.  The A.I. must first fully evaluate (to the lowest branch) a single choice.  Having evaluated that choice and received a negamax score for that move, it can terminate any additional evaluation if it allows the opponent a single change to score a better move.

### Search Heuristics

The next step is to try and organize moves so that the best value is found first, and all the rest are "pruned."  It is highly unlikely to find a heuristic which always finds the best move first, but the closer the best moves get to the front of the evaluation, the better.  I used a simple search heuristic which ordered the moves by attacks/promotions before moves, and ordered those attacks/promotions by the highest to lowest values (from the initial attack, not the full evaluation).

### Iterative Deepening

Now we come to an important topic not yet discussed: time.  This program was written for a timed chess game where each side had 5 minutes to play.  Although the time limit can change, boundaries are still required.  Chess is an NP-Hard problem.  Although Minichess is smaller, it retains much of the complexity of chess and therefore it is not practical for an A.I. to evaluate each move until it finds the "perfect" solution, as that would take too much time.

Instead, each negamax search is performed to a certain depth.  Without iterative deepening, this program could be set to depth 6, for example, and it would be able to evaluate most positions in a few seconds.  However, to get a deeper evaluation, I used an iterative deepening algorithm.  This algoritm begins by evaluating depth 1, then increments to depth 2, etc.  If its alloted time runs out during a depth evaluation, that depth's information is discarded (since it is partial, and therefore inaccurate).  The deepest completed search's value is used instead.

### Transposition Tables

Transposition tables were the final and most complex piece of the A.I.  As the previous discussions explained, deep evaluations of a given move are what take the most computing time.  However, in chess and minichess, it is quite likely that the same position will be encountered more than once.  Therefore, if the A.I. could save the value of a given position, once its initial evaluation is complete, and store it for future use, it could save a lot of time by referencing that value instead of re-doing the evaluation.

Transposition tables are essentially hashtables that store information about a position.  I used a Zobrist Hash method, i.e. using random long values to represent unique board positions, to store positions in a hash table.  When the A.I. is about to evaluate a position, it first checks to see if it is stored in the hashtable.  If so, it uses that value and avoids a lengthy evaluation.

## Domain Analysis / Future Improvements

Building a minichess player was incredibly fun and challenging.  Though I enjoy chess, I was not a chess wizard prior to taking on this project.  As software engineers, we may encounter any number of unique domains which require our services, such as legal, medical, financial, etc.  Though there are many aspects of chess which are quite complex and even daunting, a good software engineer can use a good programming language, logic, reason, and math to establish a good product for any domain.  If you can go from a lay person's knowledge of chess to having an A.I. player that can beat advanced chess players, you can be confident that you can adapt to and learn what is needed from any given domain to excel in it.

Time permitting, I hope to introduce incremental evaluation at a later date.  After achieving that, I will develop a simple opening book for the player.
