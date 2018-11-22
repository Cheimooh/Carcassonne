package Jeu.View;

import javafx.scene.image.Image;

public class DrawCard {
    Image img; // Image non tournée
    Image img90; // Image tournée à 90 degrés
    Image img180; // Image tournée à 180 degrés
    Image img270; // Image tournée à 270 degrés

    public DrawCard(Image newImg, Image img90, Image img180, Image img270){
        this.img = newImg;
        this.img90 = img90;
        this.img180 = img180;
        this.img270 = img270;
    }

    public Image getImg() { return img; }

    public Image getImg90() { return img90; }

    public Image getImg180() { return img180; }

    public Image getImg270() { return img270; }
}