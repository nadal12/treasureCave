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

    private boolean treasure = false;
    private boolean monster = false;
    private boolean hole = false;
    private boolean agent = false;
    private boolean breeze = false;
    private boolean stench = false;

    private boolean visited = false;

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
        if (!visited && !agent) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.RED);
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

    public void setTreasure(boolean treasure) {
        this.treasure = treasure;

        if (!treasure && !visited) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setBreeze(boolean breeze) {
        this.breeze = breeze;

        if (!breeze) {
           // setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setMonster(boolean monster) {
        this.monster = monster;

        if (!monster && !visited) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setHole(boolean hole) {
        this.hole = hole;

        if (!hole && !visited) {
            setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
        if (!agent) {
            image = null;
        }
        updateColor();
    }

    public void setVisited(boolean visited) {
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
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

    public boolean isBreeze() {
        return breeze;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        resizeImage();
    }

    public boolean isEmpty() {
        return !monster && !treasure && !hole && !agent && !breeze && !stench;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "treasure=" + treasure +
                ", monster=" + monster +
                ", hole=" + hole +
                '}';
    }

    public boolean isStench() {
        return false;
    }

    public void reset() {
        treasure = false;
        monster = false;
        hole = false;
        agent = false;
        breeze = false;
        stench = false;

        visited = false;
        image = null;
        setBackground(Color.WHITE);
        repaint();
    }

    public void setStench(boolean stench) {
        this.stench = stench;

        if (!stench) {
            // setBackground(Color.WHITE);
            image = null;
        }
        updateColor();
    }
}
