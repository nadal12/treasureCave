package practica2.model.pieces.base;

public abstract class CustomPiece extends Piece {

    public CustomPiece(String imagePath) {
        super(imagePath);
    }

    /**
     * Añadir movimiento válido a la pieza custom
     * @param move nuevo movimiento válido
     */
    public void addValidMove(Movement move) {
        if (!validMoves.contains(move))
            validMoves.add(move);
    }

    /**
     * Eliminar moviiento válido de la pieza custom
     * @param move movimiento a eliminar
     */
    public void removeValidMove(Movement move) {
        validMoves.remove(move);
    }
}
