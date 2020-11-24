package practica2.model.pieces;

import practica2.model.pieces.base.Piece;

public class Pawn extends Piece {

    private final static int[][] VALID_MOVES = {
            {0, 1}
    };

    /**
     * Constructor
     */
    public Pawn() {
        super("images/pawn.png");
        loadMoves(VALID_MOVES);
    }
}
