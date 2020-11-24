package practica2.model.pieces;

import practica2.model.pieces.base.CustomPiece;

public class CustomPiece2 extends CustomPiece {

    //Por defecto hacia delante, atrás y los lados
    private final static int[][] VALID_MOVES = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    /**
     * Constructor
     */
    public CustomPiece2() {
        super("images/custom2.png");
        loadMoves(VALID_MOVES);
    }
}
