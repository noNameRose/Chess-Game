class Knight {
    private int row; // the row
    private int col; // the column
    private boolean isBlack; // the color of the cell

    public Knight(int row, int col, boolean isBlack) { // constructor
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endrow, int endcol) {
        if (endrow == this.row - 2 | endrow == this.row + 2) {
            // Case 1: the Knight move up or down
            if (board.getPiece(endrow, endcol) == null) {
                // sub-case 1: the Knight move to empty cell
                return (endcol == this.col + 1 | endcol == this.col - 1);
            }
            else {
                // sub- case 2: the end cell contain a piece
                if (endcol == this.col + 1 | endcol == this.col - 1)
                    return (board.getPiece(endrow, endcol).getIsBlack() != this.isBlack);
            }
        }
        else if (endrow == this.row + 1 | endrow == this.row - 1) {
            if (endcol == this.col + 2 | endcol == this.col - 2) {
                if (board.getPiece(endrow, endcol) == null)
                  return true;
                else
                  return (board.getPiece(endrow, endcol).getIsBlack() != this.isBlack);
            }
       }
        // Case 3: the move is illegal
        return false;
    }
}