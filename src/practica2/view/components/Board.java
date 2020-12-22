package practica2.view.components;

import practica2.model.figures.*;
import practica2.model.figures.base.Figure;
import practica2.view.components.base.JSquarePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JSquarePanel {

    /**
     * Constant fields
     */
    private static final int MIN_BOARD_SIZE = 5;
    private static final int MAX_BOARD_SIZE = 16;

    private static final int DEFAULT_BOARD_SIZE = 10;

    /**
     * Fields
     */
    private int boardSize;
    private Figure piece;

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
    public void setPiece(Figure piece) {
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
                            if (cells[finalRow][finalCol].isEnabled()) {
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
                        setPerimeters(finalRow, finalCol, true);
                    } else if (cells[finalRow][finalCol].isMonster()) {
                        cells[finalRow][finalCol].setMonster(false);
                        removePerimeters(finalRow, finalCol, true);
                    }
                }
                case "hole.png" -> {
                    if (cells[finalRow][finalCol].isEmpty()) {
                        cells[finalRow][finalCol].setHole(true);
                        cells[finalRow][finalCol].setPiece(new Hole());
                        setPerimeters(finalRow, finalCol, false);
                    } else if (cells[finalRow][finalCol].isHole()) {
                        cells[finalRow][finalCol].setHole(false);
                        removePerimeters(finalRow, finalCol, false);
                    }
                }
            }
        }
        printBoard();
        System.out.println();
    }

    private void removePerimeters(int finalRow, int finalCol, boolean monster) {
        //Arriba
        if ((finalRow - 1) >= 0) {
            if (!cells[finalRow - 1][finalCol].isTreasure() && !cells[finalRow - 1][finalCol].isHole() && !cells[finalRow - 1][finalCol].isMonster() && !cells[finalRow - 1][finalCol].isAgent()) {
                if (!monster) {
                    cells[finalRow - 1][finalCol].setBreeze(false);
                } else {
                    cells[finalRow - 1][finalCol].setStench(false);
                }
            }
        }

        //Abajo
        if ((finalRow + 1) < boardSize) {
            if (!cells[finalRow + 1][finalCol].isTreasure() && !cells[finalRow + 1][finalCol].isHole() && !cells[finalRow + 1][finalCol].isMonster() && !cells[finalRow + 1][finalCol].isAgent()) {
                if (!monster) {
                    cells[finalRow + 1][finalCol].setBreeze(false);
                } else {
                    cells[finalRow + 1][finalCol].setStench(false);
                }
            }
        }

        //Derecha
        if ((finalCol + 1) < boardSize) {
            if (!cells[finalRow][finalCol + 1].isTreasure() && !cells[finalRow][finalCol + 1].isHole() && !cells[finalRow][finalCol + 1].isMonster() && !cells[finalRow][finalCol + 1].isAgent()) {
                if (!monster) {
                    cells[finalRow][finalCol + 1].setBreeze(false);
                } else {
                    cells[finalRow][finalCol + 1].setStench(false);
                }
            }
        }

        //Izquierda
        if ((finalCol - 1) >= 0) {
            if (!cells[finalRow][finalCol - 1].isTreasure() && !cells[finalRow][finalCol - 1].isHole() && !cells[finalRow][finalCol - 1].isMonster() && !cells[finalRow][finalCol - 1].isAgent()) {
                if (!monster) {
                    cells[finalRow][finalCol - 1].setBreeze(false);
                } else {
                    cells[finalRow][finalCol - 1].setStench(false);
                }
            }
        }
    }

    private void setPerimeters(int finalRow, int finalCol, boolean monster) {

        //Arriba
        if ((finalRow - 1) >= 0) {
            if (cells[finalRow - 1][finalCol].isEmpty()) {
                if (!monster) {
                    cells[finalRow - 1][finalCol].setPiece(new Breeze());
                    cells[finalRow - 1][finalCol].setBreeze(true);
                } else {
                    cells[finalRow - 1][finalCol].setPiece(new Stench());
                    cells[finalRow - 1][finalCol].setStench(true);
                }
            }
        }

        //Abajo
        if ((finalRow + 1) < boardSize) {
            if (cells[finalRow + 1][finalCol].isEmpty()) {
                if (!monster) {
                    cells[finalRow + 1][finalCol].setPiece(new Breeze());
                    cells[finalRow + 1][finalCol].setBreeze(true);
                } else {
                    cells[finalRow + 1][finalCol].setPiece(new Stench());
                    cells[finalRow + 1][finalCol].setStench(true);
                }
            }
        }

        //Derecha
        if ((finalCol + 1) < boardSize) {
            if (cells[finalRow][finalCol + 1].isEmpty()) {
                if (!monster) {
                    cells[finalRow][finalCol + 1].setPiece(new Breeze());
                    cells[finalRow][finalCol + 1].setBreeze(true);
                } else {
                    cells[finalRow][finalCol + 1].setPiece(new Stench());
                    cells[finalRow][finalCol + 1].setStench(true);
                }
            }
        }

        //Izquierda
        if ((finalCol - 1) >= 0) {
            if (cells[finalRow][finalCol - 1].isEmpty()) {
                if (!monster) {
                    cells[finalRow][finalCol - 1].setPiece(new Breeze());
                    cells[finalRow][finalCol - 1].setBreeze(true);
                } else {
                    cells[finalRow][finalCol - 1].setPiece(new Stench());
                    cells[finalRow][finalCol - 1].setStench(true);
                }
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
    public Figure getPiece() {
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
        int[] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (col + 1 < boardSize) {
            cells[row][col].setAgent(false);
            restorePreviousImage(row, col);
            cells[row][col + 1].setPiece(new Agent());
            cells[row][col + 1].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveWest() {
        int[] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (col - 1 >= 0) {
            cells[row][col].setAgent(false);
            restorePreviousImage(row, col);
            cells[row][col - 1].setPiece(new Agent());
            cells[row][col - 1].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveNorth() {
        int[] coordinates = getAgentCell();

        int row = coordinates[0];
        int col = coordinates[1];

        if (row - 1 >= 0) {
            cells[row][col].setAgent(false);
            restorePreviousImage(row, col);
            cells[row - 1][col].setPiece(new Agent());
            cells[row - 1][col].setAgent(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth() {
        int[] coordinates = getAgentCell();

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

    public void restorePreviousImage(int row, int col) {
        if (cells[row][col].isBreeze()) {
            cells[row][col].setPiece(new Breeze());
        } else if (cells[row][col].isStench()) {
            cells[row][col].setPiece(new Stench());
        }
    }

    public void restartBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j].reset();
            }
        }
        //Colocar agente
        cells[0][0].setAgent(true);
        cells[0][0].setPiece(new Agent());
    }
}