package practica2.view;

import practica2.MVCEvents;
import practica2.model.Treasure;
import practica2.model.character.Character;
import practica2.view.components.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private final MVCEvents mvcEvents;

    private static final int DEFAULT_WIDTH = 720;
    private static final int DEFAULT_HEIGHT = 620;
    private static final int ICON_SIZE = 50;

    private int boardSize;
    private JPanel menu;

    private JButton monsterButton;
    private JButton treasureButton;
    private JButton holeButton;
    private JButton startButton;

    private Character selectedCharacter = null;

    public View(String title, MVCEvents mvcEvents) {
        super(title);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.mvcEvents = mvcEvents;

        setLayout(new BorderLayout());
        configureUI();
    }

    /**
     * Configurar la interfaz gráfica de usuario con Java Swing
     */
    private void configureUI() {
        setBoardSize();
        configureMenu();
        configureBoard();
    }

    private void configureBoard() {
        add(new Board(boardSize, mvcEvents));
    }

    private void configureMenu() {
        menu = new JPanel(new GridLayout(2, 2));

        //Iconos de Flaticon:
        // https://www.flaticon.com/free-icon/hole_595608?term=hole&page=1&position=2&related_item_id=595608
        // https://www.flaticon.com/free-icon/monster_1236413?term=monster&page=1&position=10&related_item_id=1236413
        // https://www.flaticon.com/free-icon/treasure_2851781?term=treasure&page=1&position=37&related_item_id=2851781
        ImageIcon treasure = new ImageIcon(new ImageIcon("images/treasure.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon monster = new ImageIcon(new ImageIcon("images/monster.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon hole = new ImageIcon(new ImageIcon("images/hole.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon start = new ImageIcon(new ImageIcon("images/icon_play.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        monsterButton = new JButton(monster);
        treasureButton = new JButton(treasure);
        holeButton = new JButton(hole);
        startButton = new JButton(start);

        treasureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCharacter = new Treasure();
            }
        });

        menu.add(startButton);
        menu.add(treasureButton);
        menu.add(holeButton);
        menu.add(monsterButton);

        add(menu, BorderLayout.WEST);
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    private void setBoardSize() {
        String[] options = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10"};

        // Cuadro de diálogo inicial.
        String answer = (String) JOptionPane.showInputDialog(null,
                "Introduce el tamaño del tablero", "Selecciona el tamaño del tablero",
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[2]);

        // Si se aprieta el botón cancelar.
        if (answer == null) {
            System.exit(0);
        }
        boardSize = Integer.parseInt(answer.split("x")[0]);
        System.out.println(boardSize);
    }

    /**
     * Mostrar interfaz gráfica
     */
    public void start() {
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizable(false);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        revalidate();
        repaint();
    }
}
