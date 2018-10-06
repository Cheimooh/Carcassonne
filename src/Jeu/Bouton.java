package Jeu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Bouton {

    private GridPane gridPane;

    public Bouton(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    Button createBouton() {
        ImageView imageView = new ImageView("Jeu/plus.png");
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Button button = new Button("", imageView);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button1 = (Button) event.getSource();
                ImageView imageView2 = new ImageView("Jeu/prairie.jpg");
                imageView2.setFitHeight(50);
                imageView2.setFitWidth(50);
                button1.setGraphic(imageView2);
            }
        });

        button.setMaxSize(50,50);

        return button;
    }
}