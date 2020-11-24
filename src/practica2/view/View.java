package practica2.view;

import com.bulenkov.darcula.DarculaLaf;
import practica2.ErrorLog;
import practica2.EventsListener;
import practica2.MVCEvents;
import practica2.view.components.Board;

import javax.swing.*;
import java.awt.*;

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
    private JButton buttonKnight;
    private JButton buttonKing;
    private JButton buttonQueen;
    private JButton buttonTower;
    private JButton buttonPawn;
    private JButton buttonCustom1;
    private JButton buttonCustom2;

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
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            //Dark GUI - Darcula by Konstantin Bulenkov -  https://github.com/bulenkov/Darcula
            UIManager.setLookAndFeel(new DarculaLaf());
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
        JLabel labelDelay = new JLabel("Delay: 0ms ");
        labelDelay.setPreferredSize(new Dimension(90, 20));

        JSlider sliderDelay = new JSlider(0, 1000, 1);
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

        buttonKnight = new JButton();
        buttonKing = new JButton();
        buttonQueen = new JButton();
        buttonTower = new JButton();
        buttonPawn = new JButton();
        buttonCustom1 = new JButton();
        buttonCustom2 = new JButton();

        //Ajustar el tamaño de las imágenes de los botones
        //Iconos de Flaticon - Leisure pack: https://www.flaticon.com/packs/leisure-14
        ImageIcon iconKnight = new ImageIcon(new ImageIcon("images/knight.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconKing = new ImageIcon(new ImageIcon("images/king.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconQueen = new ImageIcon(new ImageIcon("images/queen.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconTower = new ImageIcon(new ImageIcon("images/tower.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconPawn = new ImageIcon(new ImageIcon("images/pawn.png").getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconCustom1 = new ImageIcon(new ImageIcon("images/custom1.png").getImage().getScaledInstance(CUSTOM_ICON_SIZE, CUSTOM_ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon iconCustom2 = new ImageIcon(new ImageIcon("images/custom2.png").getImage().getScaledInstance(CUSTOM_ICON_SIZE, CUSTOM_ICON_SIZE, Image.SCALE_SMOOTH));

        //Configurar los iconos
        //Iconos de estado - Ultimate Gnome: https://www.iconfinder.com/iconsets/UltimateGnome
        buttonPlay.setIcon(new ImageIcon("images/icon_play.png"));
      /*  buttonKnight.setIcon(iconKnight);
        buttonKing.setIcon(iconKing);
        buttonQueen.setIcon(iconQueen);
        buttonTower.setIcon(iconTower);
        buttonPawn.setIcon(iconPawn);
        buttonCustom1.setIcon(iconCustom1);
        buttonCustom2.setIcon(iconCustom2);*/

        //Configurar los ToolTipText
        buttonPlay.setToolTipText("Play");
       /* buttonKnight.setToolTipText("Knight");
        buttonKing.setToolTipText("King");
        buttonQueen.setToolTipText("Queen");
        buttonTower.setToolTipText("Tower");
        buttonPawn.setToolTipText("Pawn");
        buttonCustom1.setToolTipText("Custom 1");
        buttonCustom2.setToolTipText("Custom 2");

        //Knight seleccionado por defecto
        buttonKnight.setSelected(true);*/

        //Agregar escuchadores de eventos.
        buttonPlay.addActionListener(evt -> {
            switch (status) {
                case STATUS_READY:
                    updateStatus(STATUS_RUNNING);
                    break;
                case STATUS_RUNNING:
                    mvcEvents.getController().notify("Stop");
                    //updateStatus(STATUS_FINISH);
                    break;
                case STATUS_FINISH:
                    updateStatus(STATUS_READY);
                    break;
            }
            buttonPlay.setSelected(false);
        });

       /* buttonKnight.addActionListener(evt -> {
            board.setPiece(new Knight());
            buttonKnight.setSelected(true);
            buttonKing.setSelected(false);
            buttonQueen.setSelected(false);
            buttonTower.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom1.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonKing.addActionListener(evt -> {
            board.setPiece(new King());
            buttonKing.setSelected(true);
            buttonKnight.setSelected(false);
            buttonQueen.setSelected(false);
            buttonTower.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom1.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonQueen.addActionListener(evt -> {
            board.setPiece(new Queen());
            buttonQueen.setSelected(true);
            buttonKnight.setSelected(false);
            buttonKing.setSelected(false);
            buttonTower.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom1.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonTower.addActionListener(evt -> {
            board.setPiece(new Tower());
            buttonTower.setSelected(true);
            buttonKnight.setSelected(false);
            buttonKing.setSelected(false);
            buttonQueen.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom1.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonPawn.addActionListener(evt -> {
            board.setPiece(new Pawn());
            buttonPawn.setSelected(true);
            buttonKnight.setSelected(false);
            buttonKing.setSelected(false);
            buttonQueen.setSelected(false);
            buttonTower.setSelected(false);
            buttonCustom1.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonCustom1.addActionListener(evt -> {
            board.setPiece(customBoard1.getPiece());
            buttonCustom1.setSelected(true);
            buttonKnight.setSelected(false);
            buttonKing.setSelected(false);
            buttonQueen.setSelected(false);
            buttonTower.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom2.setSelected(false);
        });

        buttonCustom2.addActionListener(evt -> {
            board.setPiece(customBoard2.getPiece());
            buttonCustom2.setSelected(true);
            buttonKnight.setSelected(false);
            buttonKing.setSelected(false);
            buttonQueen.setSelected(false);
            buttonTower.setSelected(false);
            buttonPawn.setSelected(false);
            buttonCustom1.setSelected(false);
        });*/

        //Añadir los botones al panel del menú.
        panelPieces.add(buttonKnight);
        panelPieces.add(buttonPlay);
        panelPieces.add(buttonKing);
        panelPieces.add(buttonQueen);
        panelPieces.add(buttonTower);
        panelPieces.add(buttonPawn);
        panelPieces.add(buttonCustom1);
        panelPieces.add(buttonCustom2);

        //Añadir el menú a la izquierda.
        mainPanel.add(panelPieces, BorderLayout.WEST);
    }

    private void setControlsEnabled(boolean enabled) {
        tabbedPane.setSelectedIndex(0);
        tabbedPane.setEnabledAt(1, enabled);
        tabbedPane.setEnabledAt(2, enabled);

        buttonIncreaseBoardSize.setEnabled(enabled);
        buttonDecreaseBoardSize.setEnabled(enabled);

        buttonQueen.setEnabled(enabled);
        buttonKnight.setEnabled(enabled);
        buttonKing.setEnabled(enabled);
        buttonTower.setEnabled(enabled);
        buttonPawn.setEnabled(enabled);
        buttonCustom1.setEnabled(enabled);
        buttonCustom2.setEnabled(enabled);

        board.setCellsEnabled(enabled);
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

    /**
     * Actualizar información de la barra inferior
     *
     * @param status         estado de programa
     * @param performance    rendimiento por segundo
     * @param recursiveCalls llamadas recursivas totales
     * @param running        estado del algoritmo
     */
    public void updateFeedback(String status, String performance, String recursiveCalls, boolean running) {
        if (performance.equals("0"))
            performance = "-";

        if (recursiveCalls.equals("0"))
            recursiveCalls = "-";

        if (!status.equals("-"))
            statusLabel.setText(status);

        performanceLabel.setVisible(this.status != STATUS_READY);

        if (running)
            performanceLabel.setText("   Recursive calls/second: " + performance + "    Total: " + recursiveCalls);

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
            case STATUS_READY:
                updateFeedback("Ready to start", "-", "-", false);
                setControlsEnabled(true);
                buttonPlay.setIcon(new ImageIcon("images/icon_play.png"));
                buttonPlay.setToolTipText("Play");
                board.restartBoard();
                board.restartInitCell();
                break;
            case STATUS_RUNNING:
                updateFeedback("  Working..", "-", "-", true);
                setControlsEnabled(false);
                buttonPlay.setIcon(new ImageIcon("images/icon_stop.png"));
                buttonPlay.setToolTipText("Stop");
                mvcEvents.getController().notify("Start");
                break;
            case STATUS_FINISH:
                updateFeedback("Finished", "-", "-", false);
                setControlsEnabled(false);
                buttonPlay.setIcon(new ImageIcon("images/icon_restart.png"));
                buttonPlay.setToolTipText("Restart");
                mvcEvents.getController().notify("Stop");
                break;
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
            board.restartInitCell();
            JOptionPane.showMessageDialog(this, "Tour stopped by user", "", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/icon_fail.png"));
        }

        //Mensaje de solución no encontrada.
        if (message.startsWith("No solution")) {
            updateStatus(STATUS_FINISH);
            board.restartInitCell();
            JOptionPane.showMessageDialog(this, "No solution found for this configuration", "", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/icon_fail.png"));
        }

        if (message.startsWith("Performance")) {
            String performance = message.split(",")[1];
            String recursiveCalls = message.split(",")[2];
            updateFeedback("-", performance, recursiveCalls, true);
        }
    }
}
