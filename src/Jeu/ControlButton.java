package Jeu;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControlButton implements EventHandler {

    private Carcassonne carcassonne;
    private Appli fenetre;

    public ControlButton(Carcassonne carcassonne, Appli fenetre) {
        this.carcassonne = carcassonne;
        this.fenetre = fenetre;
        fenetre.setControlButton(this);
    }

    @Override
    public void handle(Event event) {
        Button boutonSource = (Button) event.getSource();
        if(boutonSource == fenetre.btn){
            fenetre.changerScene(1);
        }
        if(boutonSource == fenetre.btn1){
            fenetre.changerScene(2);
        }
        if(boutonSource == fenetre.btn2){
            fenetre.changerScene(0);
        }
    }
}
