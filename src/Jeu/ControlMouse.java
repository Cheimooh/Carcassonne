package Jeu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private Carte carteEnMain;

    public ControlMouse(Fenetre fenetre){
        this.fenetre = fenetre;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("x: " + event.getX()); // De gauche a droite
        System.out.println("y: " + event.getY()); // De haut en bas
        fenetre.placerCarte(carteEnMain);
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }
}
