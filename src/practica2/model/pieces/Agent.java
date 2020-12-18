package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Agent extends Piece {

    private final static int[][] VALID_MOVES = {
            {1, 1}, {2, 2}, {3, 3},
            {1, -1}, {2, -2}, {3, -3},
            {-1, -1}, {-2, -2}, {-3, -3},
            {-1, 1}, {-2, 2}, {-3, 3},
            {0, 1}, {0, 2}, {0, 3},
            {0, -1}, {0, -2}, {0, -3},
            {1, 0}, {2, 0}, {3, 0},
            {-1, 0}, {-2, 0}, {-3, 0}
    };

    /**
     * Constructor
     */
    public Agent() {
        super("images/agent.png");
        loadMoves(VALID_MOVES);
    }
}
