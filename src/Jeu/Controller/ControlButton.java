package Jeu.Controller;

import Jeu.View.Fenetre;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControlButton implements EventHandler<ActionEvent> {

    private Fenetre fenetre;

    public ControlButton(Fenetre fenetre) {
        this.fenetre = fenetre;
        //fenetre.setControlButton(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        button.setDisable(true);
    }
}
