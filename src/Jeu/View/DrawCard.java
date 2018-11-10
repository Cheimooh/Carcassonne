package Jeu.View;

import javafx.scene.image.Image;

public class DrawCard {
    Image img;
    Image img90;
    Image img180;
    Image img270;

    public DrawCard(Image newImg, Image img90, Image img180, Image img270){
        this.img = newImg;
        this.img90 = img90;
        this.img180 = img180;
        this.img270 = img270;
    }

}