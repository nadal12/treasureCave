package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class King extends Piece {

    private final static int[][] VALID_MOVES = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {-1, -1}
    };

    /**
     * Constructor
     */
    public King() {
        super("images/king.png");
        loadMoves(VALID_MOVES);
    }
}
