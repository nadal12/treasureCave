package practica2.view.components.base;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que extiende JPanel, con el propósito de mantener siempre el ancho y el alto proporcionales
 * <p>
 * Código hecho a partir de esta respuesta StackOverflow:
 * https://stackoverflow.com/a/16075907/710274
 */
public class JSquarePanel extends JPanel {

    @Override
    public Dimension getPreferredSize() {
        if (getParent() != null) {
            int l = (Math.min((int) getParent().getSize().getWidth(), (int) getParent().getSize().getHeight()));
            return new Dimension(l, l);
        } else {
            return new Dimension(100, 100);
        }
    }
}