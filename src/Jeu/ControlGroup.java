package Jeu;

import javafx.application.Application;
import javafx.stage.Stage;

public class ControlGroup {
    private Carcassonne carcassonne;
    private Fenetre fenetre;
    private ControlButton controlButton;

    public ControlGroup(Carcassonne carcassonne, Appli fenetre) {
            this.controlButton = new ControlButton(carcassonne,fenetre);
            this.carcassonne = carcassonne;
    }
}
