package practica2.view.components;

import practica2.MVCEvents;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import practica2.model.character.Character;

public class Board extends JSquarePanel {

    private Cell[][] cells;
    private int boardSize;
    private MVCEvents mvcEvents;

    public Board(int boardSize, MVCEvents mvcEvents) {
        this.boardSize = boardSize;
        this.mvcEvents = mvcEvents;
        cells = new Cell[boardSize][boardSize];
        createBoard();
    }

    private void createBoard() {
        setLayout(new GridLayout(boardSize, boardSize));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();

                int finalRow = i;
                int finalCol = j;

                cells[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent mouseEvent) {
                        setCharacter(finalRow, finalCol);
                    }
                });

                this.add(cells[i][j]);
            }
        }
    }

    private void setCharacter(int row, int col) {
        Character selectedCharacter = mvcEvents.getView().getSelectedCharacter();
        cells[row][col].setCharacter(selectedCharacter);
    }
}
