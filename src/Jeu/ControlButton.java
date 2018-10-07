package Jeu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ControlButton implements EventHandler<ActionEvent> {

    private Fenetre fenetre;
//    private Carcassonne carcassonne;

    public ControlButton(Fenetre fenetre) {
        this.fenetre = fenetre;
//        this.carcassonne = carcassonne;
        fenetre.setControlButton(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        ImageView imageView = new ImageView("Jeu/prairie.jpg");
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        button.setGraphic(imageView);
        button.setDisable(true);
    }
}
