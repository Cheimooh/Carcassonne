package Jeu;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bouton {
    public Bouton() {
    }

    Button createBouton() {
        ImageView imageView = new ImageView(new Image("Jeu/plus.png"));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Button button = new Button("", imageView);

        button.setMaxSize(50,50);

        return button;
    }
}