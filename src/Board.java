import javax.swing.*;
import java.util.*;
public class Board {

    // Instance variables
    private Piece[][] board; // The array to hold the board


    //TODO:
    // Construct an object of type Board using given arguments.
    public Board() { // Default constructor

        this.board = new Piece[8][8];
    }

    public Board(Piece[][] theBoard) { // A constructor that has an argument is a board
        this.board =theBoard;
    }
    // Accessor Methods

    //TODO:
    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {
        //System.out.println(board[row][col]);
        return board[row][col];
    }

    //TODO:
    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods

    //TODO:
    // Moves a Piece object from one cell in the board to another, provided that
    // this movement is legal. Returns a boolean to signify success or failure.
    // This method calls all necessary helper functions to determine if a move
    // is legal, and to execute the move if it is. Your Game class should not 
    // directly call any other method of this class.
    // Hint: this method should call isMoveLegal() on the starting piece. 
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (verifySourceAndDestination(startRow, startCol, endRow, endCol, getPiece(startRow, startCol).getIsBlack())) {
            // Case 1: if the move is legal according to the rule of chess
            Board myBoard = new Board(this.board);
            if (getPiece(startRow,startCol).isMoveLegal(myBoard, endRow, endCol)) { // if the move is legal for the piece on the start cell
                board[startRow][startCol].setPosition(endRow, endCol); // change the row and column of the piece
                setPiece(endRow, endCol, board[startRow][startCol]);// move the piece on the board
                this.board[startRow][startCol] = null;                     // the last cell become null
                return true;
            }
            return false;       // if the move is illegal for the piece
        }
        else
            // Case 2: if the move is illegal according to the rule of chess
            return false;

    }

    //TODO:
    // Determines whether the game has been ended, i.e., if one player's King
    // has been captured.
    public boolean isGameOver() {
        LinkedList<Character> blackPiece = new LinkedList<>(); // A list that used to store black piece
        LinkedList<Character> whitePiece = new LinkedList<>(); // A list that used to store white piece

        // loop through entire cell of the board
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) { // check the cell contains a piece
                    if (board[i][j].getIsBlack())
                        blackPiece.add(board[i][j].getCharacter()); // add black piece to the list
                    else
                        whitePiece.add(board[i][j].getCharacter()); // add white piece to the list
                }
                else // if the cell doesn't contain a piece
                    continue;
            }
        if (blackPiece.contains('\u265A') && whitePiece.contains('\u2654'))// if both black pieces and white piece still contain their king
            return false;
        else if (blackPiece.contains('\u265A') && !whitePiece.contains('\u2654')) {// if black piece contains king and white piece does not
            System.out.println("Black has won the game!");
            return true;
        }
        System.out.println("White has won the game!");
        return true; // if white piece contains king and black piece does not
    }

    // Constructs a String that represents the Board object's 2D array.
    // Returns the fully constructed String.
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for(int i = 0; i < 8; i++){
            out.append(" ");
            out.append(i);
        }
        out.append('\n');
        for(int i = 0; i < board.length; i++) {
            out.append(i);
            out.append("|");
            for(int j = 0; j < board[0].length; j++) {
                out.append(board[i][j] == null ? "\u2001|" : board[i][j] + "|");
            }
            out.append("\n");
        }
        return out.toString();
    }

    //TODO:
    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        for (int i = 0; i < board.length; i++) // loop through each rows
            for (int j = 0; j < board[i].length; j++) // loop through each columns
                board[i][j] = null;  // change the cell to null
    }

    // Movement helper functions

    //TODO:
    // Ensure that the player's chosen move is even remotely legal.
    // Returns a boolean to signify whether:
    // - 'start' and 'end' fall within the array's bounds.
    // - 'start' contains a Piece object, i.e., not null.
    // - Player's color and color of 'start' Piece match.
    // - 'end' contains either no Piece or a Piece of the opposite color.
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if (startRow > 7 || startCol > 7 || endRow > 7 || endCol > 7)      // check if the start cell and the end cell are in range of the board
            return false;
        else if (startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0) // check if the start cell and the end cell are in range of the board
            return false;
        else if (board[startRow][startCol] == null)                     // check if the start cell contain a piece
            return false;
        else if (board[startRow][startCol].getIsBlack() != isBlack)     // check if the start cell is the same color as the
            return false;
        else if (board[endRow][endCol] != null && board[endRow][endCol].getIsBlack() == isBlack)
            return false;
        return true;
    }

    //TODO:
    // Check whether the 'start' position and 'end' position are adjacent to each other
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow)
            // Case 1: startRow = endRow (The king move to left or right)
            return (endCol == startCol + 1 || endCol == startCol - 1 || endCol == startCol);
        else if (endRow == startRow + 1 || endRow == startRow - 1) {
            // Case 2: end cell is above start cell or start cell is above end cell
            if (endCol == startCol) // move up or down
                return true;
            return (endCol == startCol + 1 || endCol == startCol - 1); // move in the direction of the diagonal
        }
        return false; // the move is not adjacent
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid horizontal move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one row.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        if (startRow != endRow) // if start row and end row are not the same, then these two cells not in horizontal line
            return false;
        int i;
        if (endCol > startCol) {
            // Case 1: start cell on the left and end cell on the right
            for (i = startCol + 1; i < endCol; i++)
                if (board[startRow][i] != null) // check if the cell between end cell and start cell contain a piece
                    return false;
        }
        else {
            // Case 2: start cell on the right and end cell on the left
            for (i = startCol - 1; i > endCol; i--)
                if (board[startRow][i] != null)
                    return false;
        }
        return true; // the move is horizontal if it passes all code above.
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid vertical move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one column.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        if (startCol != endCol)  // start cell and end cell don't line on the vertical line
            return false;
        if (endRow > startRow) {
            // Case 1: End cell is below start cell
            for (int i = startRow + 1; i < endRow; i++) {
                if (board[i][startCol] != null) { // check if the cell between start cell and end cell contain a piece
                    return false;
                }
            }
        }
        else {
            // Case 2: end cell is above start cell
            for (int j = startRow - 1; j > endRow; j--)
                if (board[j][startCol] != null) // check if the cell between start cell and end cell contain a piece
                    return false;
        }
        return true; // the move is vertical if it passes all code above
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        if (Math.abs(startRow - endRow) != Math.abs(endCol - startCol)) // check if the start cell and the end cell line on the same diagonal
            return false;
        if (startRow < endRow & startCol < endCol) {
            for (int i = startRow + 1, j = startCol + 1; i < endRow && j < endCol; i++, j++)
                if (board[i][j] != null)
                    return false;
        }
        else if (startRow < endRow & startCol > endCol) {
            for (int i = startRow + 1, j = startCol - 1; i < endRow & j > endCol; i++, j--)
                if (board[i][j] != null)
                    return false;
        }
        else if (startRow > endRow && startCol < endCol) {
            for (int i = startRow - 1, j = startCol + 1; i > endRow && j < endCol; i--, j++)
                if (board[i][j] != null)
                    return false;
        }
        else if (startRow > endRow && startCol > endCol) {
            for (int i = startRow - 1, j = startCol - 1; i > endRow && j > endCol; i--, j--) {
                if (board[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    // A method used to check if the pawn of either black or white piece has reached the end of the board
    public boolean isPawninLastRow() {
        for (int i = 0; i < 8; i++) { // loop through each cell
            if (board[0][i] != null && board[0][i].getCharacter() == '\u2659') // if the cell in the upper end row contains a piece and it is white pawn
                return true;
            else if (board[7][i] != null && board[7][i].getCharacter() == '\u265F') // if the cell in the bottom end row contains a piece and it is black pawn
                return true;
        }
        return  false;
    }
}
