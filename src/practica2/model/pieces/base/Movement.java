package practica2.model.pieces.base;

/**
 * Clase que representa un movimiento desde la casilla actual
 */
public class Movement implements Comparable<Movement> {

    private int x;
    private int y;

    //Movimientos validos desde la casilla una vez realizado este movimiento
    private int onwardMoves = 0;

    /**
     * Constructor
     * @param x numero de casillas en horizontal
     * @param y numero de casillas en vertical
     */
    public Movement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param o Object con el que comparar
     * @return true si son objetos iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Movement))
            return false;

        Movement move = (Movement) o;

        return x == move.x && y == move.y;
    }

    /**
     * @return String que representa este movimiento
     */
    @Override
    public String toString() {
        return "Movimiento {" + "x=" + x + ", y=" + y + ", alcance=" + onwardMoves + '}';
    }

    /**
     * @param move Movimiento con el que comparar
     * @return diferencia de alcance
     */
    @Override
    public int compareTo(Movement move) {
        return this.onwardMoves - move.getOnwardMoves();
    }


    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y new Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return alcance del movimiento
     */
    public int getOnwardMoves() {
        return onwardMoves;
    }

    /**
     * @param onwardMoves new alcance
     */
    public void setOnwardMoves(int onwardMoves) {
        this.onwardMoves = onwardMoves;
    }
}
