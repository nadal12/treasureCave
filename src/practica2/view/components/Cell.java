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
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(character.getImage().getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
    }
}
