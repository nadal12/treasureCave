package practica2.model.pieces;

import practica2.model.pieces.base.CustomPiece;

public class CustomPiece1 extends CustomPiece {

    //Por defecto movimientos del rey
    private final static int[][] VALID_MOVES = {
            {2, 0}, {-2, 0}, {0, 2}, {0, -2},
            {3, 0}, {-3, 0}, {0, 3}, {0, -3}
    };

    /**
     * Constructor
     */
    public CustomPiece1() {
        super("images/custom1.png");
        loadMoves(VALID_MOVES);
    }
}
