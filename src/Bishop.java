class Bishop {
    private int row; // the row
    private int col; // the column
    private boolean isBlack; // the color of the piece (black or while)

    public Bishop(int row, int col, boolean isBlack) { // constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) { // check if the move is legal for the Bishop
        if (board.verifyDiagonal(this.row, this.col, endRow, endCol)) {
            // Case 1: the Bishop can move
            if (board.getPiece(endRow, endCol) == null)
                // Sub-case 1: the end cell is empty
                return true;
            else
                // Sub-case 2: the end cell contains a piece
                return (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack);
        }
        else
            // Case 2: the move is illegal for the Bishop
            return false;
    }
}