class Queen {
    private int row; // the row of the piece
    private int col; // the column of the piece
    private boolean isBlack; // the color of the Queen (black or white)

    public Queen(int row, int col, boolean isBlack) { // constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifyVertical(this.row, this.col, endRow, endCol)
                || board.verifyHorizontal(this.row, this.col, endRow, endCol)
                || board.verifyDiagonal(this.row, this.col, endRow, endCol)) {
            // Case 1: if one of the legal move of the Queen can be executed
            if (board.getPiece(endRow, endCol) == null)
                // Sub-case 1: if the end cell is empty
                return true;
            else {
                // Sub-case 2: if the end cell contains a piece
                if (board.getPiece(endRow, endCol).getIsBlack() == this.isBlack)
                    // if the piece in the end cell has the same color
                    return false;
                    // if the piece in the end cell has different color, then it will be captured
                return true;
            }
        }
        else    // Case 2: the move is illegal for the queen.
            return false;

    }



}