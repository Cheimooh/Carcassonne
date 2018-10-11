package Jeu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Bouton {

    private GridPane gridPane;

    public Bouton(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    ImageView createBouton() {
        ImageView imageView = new ImageView("Jeu/plus.png");
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        return imageView;
    }
}