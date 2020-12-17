package practica2.view.components;

import practica2.model.character.Character;

import javax.swing.*;
import java.awt.*;

public class Cell extends JComponent {

    private Character character;

    public Cell() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setCharacter(Character character) {
        this.character = character;
        System.out.println(character.getImageURL());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (character != null) {
            System.out.println("pinta");
            graphics.drawImage(character.getImage().getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH), 0, 0, this);
        }
    }
}
