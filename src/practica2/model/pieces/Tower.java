package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Tower extends Piece {

    private final static int[][] VALID_MOVES = {
            {0, 1}, {0, 2}, {0, 3},
            {0, -1}, {0, -2}, {0, -3},
            {1, 0}, {2, 0}, {3, 0},
            {-1, 0}, {-2, 0}, {-3, 0}
    };

    /**
     * Constructor
     */
    public Tower() {
        super("images/tower.png");
        loadMoves(VALID_MOVES);
    }
}
