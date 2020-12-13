package practica2.model.character;

import javax.swing.*;

public class Character {

    String imageURL;
    ImageIcon image;

    public Character(String imageURL) {
        this.imageURL = imageURL;
        this.image = new ImageIcon(imageURL);
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getImageURL() {
        return imageURL;
    }
}
