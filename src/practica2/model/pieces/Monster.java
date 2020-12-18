package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Monster extends Piece {

    private final static int[][] VALID_MOVES = {
            {2, 1}, {2, -1},
            {-2, 1}, {-2, -1},
            {1, 2}, {1, -2},
            {-1, 2}, {-1, -2}};

    /**
     * Constructor
     */
    public Monster() {
        super("images/monster.png");
        loadMoves(VALID_MOVES);
    }
}
