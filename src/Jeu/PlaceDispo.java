package Jeu;

import javafx.scene.image.ImageView;

public class PlaceDispo {

    ImageView createPlaceDispo() {
        ImageView imageView = new ImageView("Jeu/plus.png");
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        return imageView;
    }
}