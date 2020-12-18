package practica2.view.components;

import practica2.model.pieces.Agent;
import practica2.model.pieces.Hole;
import practica2.model.pieces.Monster;
import practica2.model.pieces.Treasure;
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
                    } else if (cells[finalRow][finalCol].isHole()) {
                        cells[finalRow][finalCol].setHole(false);
                    }
                }
            }
        }
        printBoard();
        System.out.println();
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
    public void restartBoard() {
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                cells[row][col].setMoveNumber(0);

        repaint();
    }

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
}