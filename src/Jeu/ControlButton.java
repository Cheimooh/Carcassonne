package Jeu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ControlButton implements EventHandler<ActionEvent> {

    private Fenetre fenetre;

    public ControlButton(Fenetre fenetre) {
        this.fenetre = fenetre;
        //fenetre.setControlButton(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        ImageView imageView = fenetre.getImage();
        button.setGraphic(imageView);
        button.setDisable(true);
    }
}
