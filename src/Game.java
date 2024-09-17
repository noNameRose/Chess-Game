
import java.util.*;

public class Game {
    public static void main(String[] args) {
        Board theBoard = new Board(); // Create a board
        String str = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        Fen.load(str, theBoard); // Put the piece to the board
        System.out.println(theBoard); // Print the board for user
        Scanner scanner = new Scanner(System.in); // A scanner object
        System.out.println("You want to start with Black or White ");
        String blackOrwhite = "";
        while (scanner.hasNext()) {
            blackOrwhite = scanner.nextLine(); // Ask user
            if (blackOrwhite.equals("Black") || blackOrwhite.equals("black") || blackOrwhite.equals("White") || blackOrwhite.equals("white")) {
                break;
            }
            System.out.println("Please choose black or white");
        }
        int turn; // a variable that used to check whether it's turn of Black piece player or White piece player
        if (blackOrwhite.equals("black") || blackOrwhite.equals("Black"))
            turn = 1; // Black turn will be counted as odd number
        else
            turn = 0; // White turn will be counted as even number
        System.out.println("It's current " + blackOrwhite + "'s turn to play");
        Scanner theScanner = new Scanner(System.in); // A Scanner Object
        System.out.println("What is your move? format ([Start Row] [Start Column] [End Row] [End Column])");
        while (theScanner.hasNext()) { // Check if next token in the input stream is integer
            int[] input  = new int[4];   // an array used to store user's input
            // ask user for the input
            input[0] = theScanner.nextInt();
            input[1] = theScanner.nextInt();
            input[2] = theScanner.nextInt();
            input[3] = theScanner.nextInt();
            // check if player move correct piece based on their piece's color

            // the try-catch here used to prevent player moving a null cell and choose a cell that is not in the board
            try {
                if (theBoard.getPiece(input[0], input[1]).getIsBlack() && turn % 2 == 0) {
                    // Case 1: it is turn of white piece player but he or she tries to move black piece
                    System.out.println("You can only move the white piece, please try again");
                    continue; // bypass the below code and go back to the beginning of the loop
                } else if (!theBoard.getPiece(input[0], input[1]).getIsBlack() && turn % 2 != 0) {
                    // Case 2: it is turn of black piece player but he or she tries to move white piece
                    System.out.println("You can only move the Black piece, please try again");
                    continue; // bypass the below code and go back to the beginning of the loop
                }
                if (theBoard.movePiece(input[0], input[1], input[2], input[3])) // move the piece
                    System.out.println(theBoard); // print the board
                else {
                    // if the piece can not be excecuted
                    System.out.println("You entered illegal move, please try again");
                    continue;
                }
            }
            catch (NullPointerException exc) {
                System.out.println("You have to choose the cell that contains a piece, please try again");
                continue;
            }
            catch (ArrayIndexOutOfBoundsException exc) {
                System.out.println("You have entered the cell that is not exist on the board, please try again");
                continue;
            }
            catch (InputMismatchException exc) {
                System.out.println("Please enter the following format [start row] [start column] [end row] [end column])");
                continue;
            }
            turn++; // increment the turn so that we can move to opposite player

            // check if the game has over
            if (theBoard.isGameOver())
                break; // if Black pieces player or White piece player has won the game

            // Check if the either upper or bottom end row contains the pawn
            if (theBoard.isPawninLastRow()) { // if it contains a pawn
                System.out.println("Your pawn at cell (" + input[2] + ", " + input[3] + ")" + " has reached the end of the board.");
                System.out.print("What type of Piece you want your piece to be (Bishop, Knight, Rook, Queen): ");
                String in = "";
                while (theScanner.hasNext()) { // ask player until they enter valid choice
                    in = theScanner.nextLine();  // ask user
                    if (in.equals("Bishop") || in.equals("bishop")
                            || in.equals("Knight") || in.equals("knight")
                            || in.equals("Rook") || in.equals("rook")
                            || in.equals("Queen") || in.equals("queen"))
                        break;
                    else
                        System.out.println("Please enter valid choice (Bishop, Knight, Rook, Queen)");
                }
                Piece copy = theBoard.getPiece(input[2], input[3]);
                switch (in) {
                    case "Bishop": // if player want the pawn become the bishop
                    case "bishop":
                        if (input[2] == 0)
                            copy.setChracter('\u2657');
                        else
                            copy.setChracter('\u265D');
                        break;
                    case "Knight":// if player want the pawn become the knight
                    case "knight":
                        if (input[2] == 0)
                            copy.setChracter('\u2658');
                        else
                            copy.setChracter('\u265E');
                        break;
                    case "Rook": // if player want the pawn become the rook
                    case "rook":
                        if (input[2] == 0)
                            copy.setChracter('\u2656');
                        else
                            copy.setChracter('\u265C');
                        break;
                    case "Queen": // if player want the pawn become the Queen
                    case "queen":
                        if (input[2] == 0)
                            copy.setChracter('\u2655');
                        else
                            copy.setChracter('\u265B');
                        break;
                }
                System.out.println(theBoard);
            }

            // check which player's turn based on the turn variable
            if (turn % 2 == 0) // if turn is even number, then this is turn of white piece player
                System.out.println("It currently white's turn to play");
            else // if turn is odd number, then this is turn of black piece player
                System.out.println("It's currently black's turn to play");
            System.out.println("What is your move? format ([Start Row] [Start Column] [End Row] [End Column])");
        }
    }
}