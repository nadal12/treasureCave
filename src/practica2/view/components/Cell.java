package practica2.view.components;

import practica2.model.pieces.base.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Clase que representa las celdas del tablero
 * Las celdas pueden estar bloqueadas
 * Solo una de ellas puede ser la casilla inicial
 * Una vez encontrada una solución, a cada casilla se le asigna un numero de movimiento
 */
public class Cell extends JComponent {

    public final static int IMAGE_MARGIN = 5;
    public final static int TEXT_MARGIN = 10;

    private boolean black = false;
    private boolean blocked = false;
    private boolean init = false;
    private static int currentMove = 1;

    private int moveNumber = 0;

    // Static para que se comparta con todas las instancias de la clase
    private static Piece piece;
    private static Image image;

    /**
     * Constructor
     */
    public Cell() {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (isInit());
                    //resizeImage();

            }
        });
    }

   /* public void resizeImage() {
        int size = getWidth() - Cell.IMAGE_MARGIN * 2;
        Cell.image = piece.getImage().getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        repaint();
    }*/

    /**
     * Cambiar color de la celda en función de sus parámetros
     */
    private void updateColor() {
        if (blocked)
            setBackground(Color.RED);
        else if (black)
            setBackground(Color.GRAY);
        else
            setBackground(Color.WHITE);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());

        //Si es la casilla de inicio, pintar la imagen de la pieza
        if (isCurrentMove()) {
            graphics.drawImage(image, IMAGE_MARGIN, IMAGE_MARGIN, this);
        } else if (moveNumber > 0) {
            //Si tiene movimiento asignado, pintarlo
            String number = String.valueOf(moveNumber - 1);
            Font font = new Font("Verdana", Font.BOLD + Font.ITALIC, getMaxFontSize(moveNumber > 100));

            FontMetrics metrics = graphics.getFontMetrics(font);
            int x = (getWidth() - metrics.stringWidth(number)) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

            // Determine the X coordinate for the text
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);
            graphics.drawString(number, x, y);
        }

    }

    /**
     * Función para obtener el máximo tamaño de fuente en función del tamaño de la celda
     *
     * Código modificado a partir de un snippet de StackOverflow:
     * https://stackoverflow.com/a/2715279/710274
     *
     * @param bigNumber true si el texto a escribir es de 3 cifras
     * @return máximo tamaño de fuente para la celda
     */
    private int getMaxFontSize(boolean bigNumber) {

        Font font = new Font("Verdana", Font.BOLD + Font.ITALIC, 100);
        int stringWidth = getFontMetrics(font).stringWidth(bigNumber ? "100" : "10");
        double widthRatio = getWidth() / (double) stringWidth;

        return (int) (font.getSize() * widthRatio) - (bigNumber ? TEXT_MARGIN / 2 : TEXT_MARGIN);
    }

    /*
     * GETTERS & SETTERS
     */
    public void setBlack(boolean black) {
        this.black = black;

        updateColor();
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {

        //Si la casilla es la inicial, no se puede bloquear
        if (init)
            return;

        this.blocked = blocked;

        updateColor();
    }

    public void setInit(boolean init) {

        //La casilla inicial no puede estar bloqueada
        if (init)
            this.blocked = false;

        moveNumber = init ? 1 : 0;
        this.init = init;

        updateColor();
    }

    public boolean isInit() {
        return init;
    }

    public static void setCurrentMove(int currentMove) {
        Cell.currentMove = currentMove;
    }

    public boolean isCurrentMove() {
        return moveNumber == currentMove;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setPiece(Piece piece) {
        Cell.piece = piece;
        //resizeImage();
    }
}
