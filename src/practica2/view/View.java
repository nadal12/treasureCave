package practica2.view;

import practica2.MVCEvents;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private MVCEvents mvcEvents;

    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 620;

    public View(String title, MVCEvents mvcEvents) {
        super(title);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.mvcEvents = mvcEvents;

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


    }
}
