package practica2.model.figures.base;

import javax.swing.*;

public abstract class Figure {
    /**
     * Fields
     */
    protected ImageIcon image;

    public Figure(String imagePath) {
        image = new ImageIcon(imagePath);
    }

    /**
     * Retorna la imagen de la pieza
     *
     * @return imagen de la pieza
     */
    public ImageIcon getImage() {
        return image;
    }
}
