package practica2.view;

import practica2.ErrorLog;
import practica2.EventsListener;
import practica2.MVCEvents;
import practica2.model.pieces.Hole;
import practica2.model.pieces.Monster;
import practica2.model.pieces.Treasure;
import practica2.view.components.Board;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author nadalLlabres
 */
public class View extends JFrame implements EventsListener {

    /**
     * Constant fields
     */
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 620;

    private static final int ICON_SIZE = 50;
    private static final int CUSTOM_ICON_SIZE = 40;

    private static final int STATUS_READY = 1;
    private static final int STATUS_RUNNING = 2;
    private static final int STATUS_FINISH = 3;

    /**
     * Componentes de la interfaz
     */
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JLabel statusLabel;
    private JLabel performanceLabel;
    private JLabel loadingAnimation;
    private JTabbedPane tabbedPane;

    private Board board;

    private JButton buttonPlay;

    /**
     * Componentes del menú
     */

    private MVCEvents mvcEvents;

    /**
     * Variables de estado del programa
     */
    private int status = STATUS_READY;
    private JButton buttonTreasure;
    private JButton buttonMonster;
    private JButton buttonHole;

    private JButton buttonIncreaseBoardSize;
    private JButton buttonDecreaseBoardSize;

    /**
     * Constructor
     *
     * @param title     titulo de la ventana
     * @param mvcEvents referencia a clase principal
     */
    public View(String title, MVCEvents mvcEvents) {
        super(title);

        //Estilo de los elementos gráficos propios del sistema operativo.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ErrorLog.logException(e);
        }

        this.mvcEvents = mvcEvents;

        //Inicializar tableros
        board = new Board();

        configureUI();
    }

    /**
     * Inicializar datos y vista
     */
    public void start() {
        showView();
    }

    /**
     * Mostrar interfaz gráfica
     */
    public void showView() {
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * Configurar la interfaz gráfica de usuario con Java Swing
     */
    private void configureUI() {

        configureTabPanel();
        configureMenuPanel();
        configurePiecesPanel();
        configureFeedbackPanel();

        setContentPane(mainPanel);
    }

    /**
     * Gestión de pestañas obtenida de la web de Oracle:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TabbedPaneDemoProject/src/components/TabbedPaneDemo.java
     */
    private void configureTabPanel() {
        //Panel con pestañas
        tabbedPane = new JTabbedPane();

        //Panel de tablero
        JPanel panelBoard = new JPanel();
        panelBoard.setLayout(new GridBagLayout());
        panelBoard.add(board);

        tabbedPane.addTab("    Board    ", panelBoard);

        //Border Layout para poner el menú en la parte de arriba
        mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.add(tabbedPane);
    }

    /**
     * Inicializar menu superior
     */
    private void configureMenuPanel() {

        JToolBar menu = new JToolBar();
        menu.setFloatable(false);

        //Configurar botones para redimensionar el tablero
        buttonIncreaseBoardSize = new JButton("+");
        buttonDecreaseBoardSize = new JButton("-");

        JLabel labelBoardSizeTitle = new JLabel("Board size: ");
        JLabel labelBoardSize = new JLabel(" " + board.getBoardSize() + "x" + board.getBoardSize() + " ");

        //Configurar slider de delay
        JLabel labelDelay = new JLabel("Delay: 300ms ");
        labelDelay.setPreferredSize(new Dimension(90, 20));

        JSlider sliderDelay = new JSlider(0, 1000, 300);
        sliderDelay.setMajorTickSpacing(100);
        sliderDelay.setMinorTickSpacing(25);

        menu.add(labelBoardSizeTitle);

        menu.add(buttonDecreaseBoardSize);
        menu.add(labelBoardSize);
        menu.add(buttonIncreaseBoardSize);

        menu.addSeparator(new Dimension(20, 0));

        menu.add(labelDelay);
        menu.add(sliderDelay);

        buttonIncreaseBoardSize.addActionListener(e -> {
            board.increaseSize();
            labelBoardSize.setText(" " + board.getBoardSize() + "x" + board.getBoardSize() + " ");
        });

        buttonDecreaseBoardSize.addActionListener(e -> {
            board.decreaseSize();
            labelBoardSize.setText(" " + board.getBoardSize() + "x" + board.getBoardSize() + " ");
        });

        sliderDelay.addChangeListener(e -> {
            int newDelay = sliderDelay.getValue();

            if (newDelay == 1000)
                labelDelay.setText("Delay: 1s ");
            else
                labelDelay.setText("Delay: " + newDelay + "ms ");

            mvcEvents.getController().notify("Delay," + newDelay);
        });


        mainPanel.add(menu, BorderLayout.PAGE_START);
    }

    /**
     * Inicializar panel con botones de piezas
     */
    public void configurePiecesPanel() {

        JPanel panelPieces = new JPanel(new GridLayout(0, 2));

        //Inicialización de los botones del menu lateral
        buttonPlay = new JButton();
        buttonPlay.setRequestFocusEnabled(false);

        buttonTreasure = new JButton();
        buttonMonster = new JButton();
        buttonHole = new JButton();

        //Ajustar el tamaño de las imágenes de los botones
        //Iconos de Flaticon:
        // https://www.flaticon.com/free-icon/hole_595608?term=hole&page=1&position=2&related_item_id=595608
        // https://www.flaticon.com/free-icon/monster_1236413?term=monster&page=1&position=10&related_item_id=1236413
        // https://www.flaticon.com/free-icon/treasure_2851781?term=treasure&page=1&position=37&related_item_id=2851781
        ImageIcon treasure = new ImageIcon(new ImageIcon("images/treasure.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon monster = new ImageIcon(new ImageIcon("images/monster.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon hole = new ImageIcon(new ImageIcon("images/hole.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        //Configurar los iconos
        //Iconos de estado - Ultimate Gnome: https://www.iconfinder.com/iconsets/UltimateGnome
        buttonPlay.setIcon(new ImageIcon("images/icon_play.png"));
        buttonTreasure.setIcon(treasure);
        buttonMonster.setIcon(monster);
        buttonHole.setIcon(hole);

        //Configurar los ToolTipText
        buttonPlay.setToolTipText("Play");
        buttonTreasure.setToolTipText("Treasure");
        buttonMonster.setToolTipText("Monster");
        buttonHole.setToolTipText("Hole");

        //Agregar escuchadores de eventos.
        buttonPlay.addActionListener(evt -> {
            switch (status) {
                case STATUS_READY -> updateStatus(STATUS_RUNNING);
                case STATUS_RUNNING -> {
                    mvcEvents.getController().notify("Stop");
                    updateStatus(STATUS_FINISH);
                }
                case STATUS_FINISH -> updateStatus(STATUS_READY);
            }
            buttonPlay.setSelected(false);
        });

        buttonTreasure.addActionListener(evt -> {
            board.setPiece(new Treasure());
            setObjectsSelected(false);
            buttonTreasure.setSelected(true);
        });

        buttonMonster.addActionListener(evt -> {
            board.setPiece(new Monster());
            setObjectsSelected(false);
            buttonMonster.setSelected(true);
        });

        buttonHole.addActionListener(evt -> {
            board.setPiece(new Hole());
            setObjectsSelected(false);
            buttonHole.setSelected(true);
        });

        //Añadir los botones al panel del menú.
        panelPieces.add(buttonPlay);
        panelPieces.add(buttonTreasure);
        panelPieces.add(buttonMonster);
        panelPieces.add(buttonHole);

        //Añadir el menú a la izquierda.
        mainPanel.add(panelPieces, BorderLayout.WEST);
    }

    private void setControlsEnabled(boolean enabled) {
        tabbedPane.setSelectedIndex(0);

        buttonIncreaseBoardSize.setEnabled(enabled);
        buttonDecreaseBoardSize.setEnabled(enabled);

        buttonHole.setEnabled(enabled);
        buttonTreasure.setEnabled(enabled);
        buttonMonster.setEnabled(enabled);

        board.setCellsEnabled(enabled);
    }

    private void setObjectsSelected(boolean enabled) {
        buttonHole.setSelected(enabled);
        buttonTreasure.setSelected(enabled);
        buttonMonster.setSelected(enabled);
    }

    /**
     * Inicializar componentes de la barra inferior
     */
    public void configureFeedbackPanel() {
        infoPanel = new JPanel();

        loadingAnimation = new JLabel(new ImageIcon("images/loading.gif"));
        statusLabel = new JLabel();
        performanceLabel = new JLabel();

        infoPanel.add(loadingAnimation);
        infoPanel.add(statusLabel);
        infoPanel.add(performanceLabel);

        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        updateFeedback("Ready to start", "-", "-", false);
    }

    public void updateFeedback(String status, String performance, String recursiveCalls, boolean running) {
       if (!status.equals("-"))
            statusLabel.setText(status);

        performanceLabel.setVisible(this.status != STATUS_READY);

        loadingAnimation.setVisible(running);

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    /**
     * Actualizar estado de programa
     */
    public void updateStatus(int status) {
        this.status = status;

        switch (status) {
            case STATUS_READY -> {
                updateFeedback("Ready to start", "-", "-", false);
                setControlsEnabled(true);
                board.restartBoard();
                buttonPlay.setIcon(new ImageIcon("images/icon_play.png"));
                buttonPlay.setToolTipText("Play");
            }
            case STATUS_RUNNING -> {
                updateFeedback("  Working..", "-", "-", true);
                setControlsEnabled(false);
                buttonPlay.setIcon(new ImageIcon("images/icon_stop.png"));
                buttonPlay.setToolTipText("Stop");
                mvcEvents.getController().notify("Start");
            }
            case STATUS_FINISH -> {
                updateFeedback("Finished", "-", "-", false);
                setControlsEnabled(false);
                buttonPlay.setIcon(new ImageIcon("images/icon_restart.png"));
                buttonPlay.setToolTipText("Restart");
                mvcEvents.getController().notify("Stop");
            }
        }

    }

    /**
     * Retornar tablero principal
     *
     * @return tablero
     */
    public Board getBoard() {
        return board;
    }


    @Override
    public void notify(String message) {

        System.out.println("Mensaje en Vista: " + message);

        // Actualizar barra de progreso
        if (message.startsWith("End")) {
            boolean stoppedManually = Boolean.parseBoolean(message.split(",")[1]);
            updateStatus(STATUS_FINISH);

            if (!stoppedManually)
                JOptionPane.showMessageDialog(this, "Solution found!", "", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/icon_check.png"));
        }

        //Mensaje parada forzada por el usuario
        if (message.startsWith("Stopped")) {
            updateStatus(STATUS_FINISH);
            //board.restartInitCell();
            JOptionPane.showMessageDialog(this, "Tour stopped by user", "", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/icon_fail.png"));
        }

        if (message.startsWith("Performance")) {
            String performance = message.split(",")[1];
            String recursiveCalls = message.split(",")[2];
            updateFeedback("-", performance, recursiveCalls, true);
        }

        if (message.startsWith("found")) {
            updateStatus(STATUS_FINISH);
            //board.restartInitCell();
            JOptionPane.showMessageDialog(this, "Treasure found", "", JOptionPane.PLAIN_MESSAGE, new ImageIcon(new ImageIcon("images/treasure.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        }
    }
}
