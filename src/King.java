public class King {
    private int row; // the row
    private int col; // the colum
    private boolean isBlack; // the color of the king

    public King(int row, int col, boolean isBlack) { // constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    // check if the move is legal for the king
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifyAdjacent(this.row, this.col, endRow, endCol)) {
            // Case 1: the move is legal for the king
            if (board.getPiece(endRow, endCol) == null)
                // Sub-case 1: the king move to an empty cell
                return true;
            else
                // Sub-case 2: the king capture the piece in opposite color or not
                return (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack);
        }
        else
            // Case 2: the move is illegal for the king
            return false;
    }
}
