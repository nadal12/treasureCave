package practica2.model.pieces.base;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Piece {
    /**
     * Fields
     */
    protected ArrayList<Movement> validMoves = new ArrayList<>();
    protected ImageIcon image;

    public ArrayList<Movement> getValidMoves() {
        return validMoves;
    }

    public Piece(String imagePath) {
        image = new ImageIcon(imagePath);
    }

    /**
     * Retorna la imagen de la pieza
     * @return imagen de la pieza
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * Cargar movimientos de la pieza en el arraylist de movimientos
     * @param moves array de movimientos de la pieza
     */
    protected void loadMoves(int[][] moves) {
        for (int[] move : moves)
            validMoves.add(new Movement(move[0], move[1]));
    }
}
