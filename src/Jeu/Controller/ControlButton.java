package Jeu.Controller;

import Jeu.View.FenetreJeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControlButton implements EventHandler<ActionEvent> {

    private FenetreJeu fenetreJeu;

    public ControlButton(FenetreJeu fenetreJeu) {
        this.fenetreJeu = fenetreJeu;
        //fenetreJeu.setControlButton(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button) event.getSource();
        button.setDisable(true);
    }
}
