package practica2.view.components;

import java.awt.*;

public class Board extends JSquarePanel {

    private Cell[][] cells;
    private int boardSize;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        cells = new Cell[boardSize][boardSize];
        createBoard();
    }

    private void createBoard() {
        setLayout(new GridLayout(boardSize, boardSize));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();
                this.add(cells[i][j]);
            }
        }
    }
}
