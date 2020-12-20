package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Stone extends Piece {

    private final static int[][] VALID_MOVES = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {-1, -1}
    };

    /**
     * Constructor
     */
    public Stone() {
        super("images/stone.png");
        loadMoves(VALID_MOVES);
    }
}
