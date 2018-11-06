package Jeu;

import javafx.scene.image.Image;

import java.awt.*;

public enum Partisan {
    partisanVert(Color.green, new Image("Jeu/imgPartisans/PartisanVert.png")),
    partisanJaune(Color.yellow, new Image("Jeu/imgPartisans/PartisanJaune.png")),
    partisanBleu(Color.blue, new Image("Jeu/imgPartisans/PartisanBleu.png")),
    partisanRouge(Color.red, new Image("Jeu/imgPartisans/PartisanRouge.png"));

    private Color color;
    private Image image;

    Partisan(Color color, Image image){
        this.color=color;
        this.image=image;
    }

    public Image getImage(){return image;}
}
