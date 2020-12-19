package practica2.view.components;

import practica2.model.pieces.*;
import practica2.model.pieces.base.Piece;
import practica2.view.components.base.JSquarePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board extends JSquarePanel {

    /**
     * Constant fields
     */
    private static final int MIN_BOARD_SIZE = 5;
    private static final int MAX_BOARD_SIZE = 16;

    private static final int DEFAULT_BOARD_SIZE = 8;

    /**
     * Fields
     */
    private int boardSize;
    private Piece piece;

    /**
     * Array de celdas que representan el tablero
     */
    private Cell[][] cells;

    /**
     * Constructor
     */
    public Board() {
        boardSize = DEFAULT_BOARD_SIZE;
        configureBoard();
    }

    /**
     * Cambia la pieza del tablero
     *
     * @param piece nueva pieza
     */
    public void setPiece(Piece piece) {
        this.piece = piece;

        //Cambiar la pieza en la celda inicial
     /*   for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                if (cells[row][col] != null) {//&& cells[row][col].isInit())
            cells[row][col].setPiece(piece);
        }*/
    }

    /**
     * Inicializar el array de celdas
     * Se tiene que ejecutar cada vez que se cambia el tamaño del tablero
     */
    private void configureBoard() {
        setLayout(new GridLayout(0, boardSize));

        //Inicializar el array de Celdas que representa el tablero
        cells = new Cell[boardSize][boardSize];

        //Rellenar el GridLayout con las celdas
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {

                cells[row][col] = new Cell();

                int finalRow = row;
                int finalCol = col;

                cells[finalRow][finalCol].addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent mouseEvent) {
                        if (SwingUtilities.isLeftMouseButton(mouseEvent))
                            if ( cells[finalRow][finalCol].isEnabled()) {
                                setFigure(finalRow, finalCol);
                            }
                    }
                });

                if (finalRow == 0 && finalCol == 0) {
                    cells[finalRow][finalCol].setAgent(true);
                    cells[finalRow][finalCol].setPiece(new Agent());
                }

                add(cells[row][col]);
            }
        }


    }

    private void setFigure(int finalRow, int finalCol) {

        if (piece != null) {
            String figure = piece.getImage().toString().split("images/")[1];

            switch (figure) {
                case "treasure.png" -> {
                    if (cells[finalRow][finalCol].isEmpty()) {
                        removeTreasure();
                        cells[finalRow][finalCol].setTreasure(true);
                        cells[finalRow][finalCol].setPiece(new Treasure());
                    } else if (cells[finalRow][finalCol].isTreasure()) {
                        cells[finalRow][finalCol].setTreasure(false);
                    }
                }
                case "monster.png" -> {
                    if (cells[finalRow][finalCol].isEmpty()) {
                        cells[finalRow][finalCol].setMonster(true);
                        cells[finalRow][finalCol].setPiece(new Monster());
                    } else if (cells[finalRow][finalCol].isMonster()) {
                        cells[finalRow][finalCol].setMonster(false);
                    }
                }
                case "hole.png" -> {
                    if (cells[finalRow][finalCol].isEmpty()) {
                        cells[finalRow][finalCol].setHole(true);
                        cells[finalRow][finalCol].setPiece(new Hole());
                        setBreezes(finalRow, finalCol);
                    } else if (cells[finalRow][finalCol].isHole()) {
                        cells[finalRow][finalCol].setHole(false);
                        removeBreezes(finalRow, finalCol);
                    }
                }
            }
        }
        printBoard();
        System.out.println();
    }

    private void removeBreezes(int finalRow, int finalCol) {
        //Arriba
        if ((finalRow - 1) >= 0) {
            if (!cells[finalRow - 1][finalCol].isTreasure() && !cells[finalRow - 1][finalCol].isHole() && !cells[finalRow - 1][finalCol].isMonster() && !cells[finalRow - 1][finalCol].isAgent()) {
                cells[finalRow - 1][finalCol].setBreeze(false);
            }
        }

        //Abajo
        if ((finalRow + 1) < boardSize) {
            if (!cells[finalRow + 1][finalCol].isTreasure() && !cells[finalRow + 1][finalCol].isHole() && !cells[finalRow + 1][finalCol].isMonster() && !cells[finalRow + 1][finalCol].isAgent()) {
                cells[finalRow + 1][finalCol].setBreeze(false);
            }
        }

        //Derecha
        if ((finalCol + 1) < boardSize) {
            if (!cells[finalRow][finalCol + 1].isTreasure() && !cells[finalRow][finalCol + 1].isHole() && !cells[finalRow][finalCol + 1].isMonster() && !cells[finalRow][finalCol + 1].isAgent()) {
                cells[finalRow][finalCol + 1].setBreeze(false);
            }
        }

        //Izquierda
        if ((finalCol - 1) >= 0) {
            if (!cells[finalRow][finalCol - 1].isTreasure() && !cells[finalRow][finalCol - 1].isHole() && !cells[finalRow][finalCol - 1].isMonster() && !cells[finalRow][finalCol - 1].isAgent()) {
                cells[finalRow][finalCol - 1].setBreeze(false);
            }
        }
    }

    private void setBreezes(int finalRow, int finalCol) {

        //Arriba
        if ((finalRow - 1) >= 0) {
            if (cells[finalRow - 1][finalCol].isEmpty()) {
                cells[finalRow - 1][finalCol].setPiece(new Breeze());
                cells[finalRow - 1][finalCol].setBreeze(true);
            }
        }

        //Abajo
        if ((finalRow + 1) < boardSize) {
            if (cells[finalRow + 1][finalCol].isEmpty()) {
                cells[finalRow + 1][finalCol].setPiece(new Breeze());
                cells[finalRow + 1][finalCol].setBreeze(true);
            }
        }

        //Derecha
        if ((finalCol + 1) < boardSize) {
            if (cells[finalRow][finalCol + 1].isEmpty()) {
                cells[finalRow][finalCol + 1].setPiece(new Breeze());
                cells[finalRow][finalCol + 1].setBreeze(true);
            }
        }

        //Izquierda
        if ((finalCol - 1) >= 0) {
            if (cells[finalRow][finalCol - 1].isEmpty()) {
                cells[finalRow][finalCol - 1].setPiece(new Breeze());
                cells[finalRow][finalCol - 1].setBreeze(true);
            }
        }

    }

    private void removeTreasure() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (cells[i][j].isTreasure()) {
                    cells[i][j].setTreasure(false);
                }
            }
        }
    }

    /**
     * Cambiar casilla inicial
     *
     * @param initRow fila de la casilla inicial
     * @param initCol columna de la casilla inicial
     */
   /* private void setInitCell(int initRow, int initCol) {
        //Resetear la anterior casilla inicial
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                cells[row][col].setInit(false);

        cells[initRow][initCol].setInit(true);
    }*/

    /**
     * Aumentar el tamaño del tablero
     */
    public void increaseSize() {
        if (boardSize >= MAX_BOARD_SIZE)
            return;

        boardSize++;
        removeAll();
        configureBoard();
        repaint();
    }

    /**
     * Reducir el tamaño del tablero
     */
    public void decreaseSize() {
        if (boardSize <= MIN_BOARD_SIZE)
            return;

        boardSize--;
        removeAll();
        configureBoard();
        repaint();
    }

    /**
     * Activar o desactivar las celdas
     *
     * @param enabled estado de las celdas
     */
    public void setCellsEnabled(boolean enabled) {
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                cells[row][col].setEnabled(enabled);
    }

    /**
     * Reiniciar todas las casillas del tablero
     */
 /*   public void restartBoard() {
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                cells[row][col].setMoveNumber(0);

        repaint();
    }*/

    /**
     * Reiniciar todas las casillas del tablero
     */
  /*  public void restartInitCell() {
        Cell.setCurrentMove(1);

        findInitCell:
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (cells[row][col].isInit()) {
                    cells[row][col].setMoveNumber(1);
                    cells[row][col].repaint();
                    break findInitCell;
                }
            }
        }
    }*/

    /**
     * Obtener tamaño del tablero
     *
     * @return tamaño del tablero
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Obtener array de celdas del tablero
     *
     * @return array de celdas
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Obtener pieza del tablero
     *
     * @return pieza del tablero
     */
    public Piece getPiece() {
        return piece;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(" | " + cells[i][j].isTreasure());
            }
            System.out.println();
        }
    }

    public int[] getAgentCell() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (cells[i][j].isAgent()) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean moveEast() {
        int [] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (col + 1 < boardSize) {
            cells[row][col].setAgent(false);
            cells[row][col + 1].setPiece(new Agent());
            cells[row][col + 1].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveWest() {
        int [] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (col - 1 >= 0) {
            cells[row][col].setAgent(false);
            cells[row][col - 1].setPiece(new Agent());
            cells[row][col - 1].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveNorth() {
        int [] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (row - 1 >= 0) {
            cells[row][col].setAgent(false);
            cells[row - 1][col].setPiece(new Agent());
            cells[row - 1][col].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        int [] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (row + 1 < boardSize) {
            cells[row][col].setAgent(false);
            restorePreviousImage(row, col);
            cells[row + 1][col].setPiece(new Agent());
            cells[row + 1][col].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    private void restorePreviousImage(int row, int col) {
        if (cells[row][col].isBreeze()) {
            cells[row][col].setPiece(new Breeze());
        }
    }
}