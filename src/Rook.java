class Rook {
    private int row; // the row
    private int col; // the column
    private boolean isBlack; // the color of the rook

    public Rook(int row, int col, boolean isBlack) { // the constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifyVertical(this.row, this.col, endRow, endCol)
                || board.verifyHorizontal(this.row, this.col, endRow, endCol)) {
            // Case 1: the Rook can move
            if (board.getPiece(endRow, endCol) == null)
                // Sub-case 1: the Rook move to an empty cell
                return true;
            else {
                // Sub-case 2: if the end cell contains a piece
                return (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack);
            }
        }
        // Case 2: the move is illegal
        return false;
    }
}