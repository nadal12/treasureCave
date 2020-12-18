package practica2.view.components;

import practica2.model.pieces.Hole;
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

    private boolean treasure = false;
    private boolean monster = false;
    private boolean hole = false;
    private boolean agent = false;

    private static int currentMove = 1;

    private int moveNumber = 0;

    // Static para que se comparta con todas las instancias de la clase
    private Piece piece;
    private Image image;

    /**
     * Constructor
     */
    public Cell() {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeImage();
            }
        });
    }

    public void resizeImage() {
        if (piece != null) {
            int size = getWidth() - Cell.IMAGE_MARGIN * 2;
            image = piece.getImage().getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            repaint();
        }
    }

    /**
     * Cambiar color de la celda en función de sus parámetros
     */
    private void updateColor() {
        if (!monster && !hole && !treasure) {
            setBackground(Color.WHITE);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());

        //Dibujar imagen correspondiente.
        graphics.drawImage(image, IMAGE_MARGIN, IMAGE_MARGIN, this);
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

    /*public void setInit(boolean init) {

        //La casilla inicial no puede estar bloqueada
        if (init)
            this.blocked = false;

        moveNumber = init ? 1 : 0;
        this.init = init;

        updateColor();
    }*/

    public void setTreasure(boolean treasure) {
        this.treasure = treasure;

        if (!treasure) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setMonster(boolean monster) {
        this.monster = monster;

        if (!monster) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setHole(boolean hole) {
        this.hole = hole;

        if (!hole) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setAgent(boolean agent) {
        this.agent = agent;

        if (!agent) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public boolean isTreasure() {
        return treasure;
    }

    public boolean isMonster() {
        return monster;
    }

    public boolean isHole() {
        return hole;
    }

    public boolean isAgent() {
        return agent;
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
        this.piece = piece;
        resizeImage();
    }

    public boolean isEmpty() {
        return !monster && !treasure && !hole && !agent;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "treasure=" + treasure +
                ", monster=" + monster +
                ", hole=" + hole +
                '}';
    }
}
