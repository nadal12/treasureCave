package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Hole extends Piece {

    private final static int[][] VALID_MOVES = {
            {0, 1}
    };

    /**
     * Constructor
     */
    public Hole() {
        super("images/hole.png");
        loadMoves(VALID_MOVES);
    }
}
