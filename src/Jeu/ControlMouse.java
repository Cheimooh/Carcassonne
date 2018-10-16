package Jeu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private Carte carteEnMain;

    public ControlMouse(Fenetre fenetre){
        this.fenetre = fenetre;
    }

    @Override
    public void handle(MouseEvent event) {
//        System.out.println("x: " + event.getX()); // De gauche a droite
//        System.out.println("y: " + event.getY()); // De haut en bas
        int x = (int)event.getX()/50;
        int y = (int)event.getY()/50;
        if(carteColler(x, y)) {
            carteEnMain.setPosition(new Point(x, y));
            fenetre.placerCarte(carteEnMain);
        }
        else{
            System.out.println("ERREUR: CLIQUEZ SUR UNE CROIX !!!!!!!!!!!!!!!");
        }
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }

    private boolean carteColler(int x, int y){
        Point point = new Point(x+1, y);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x-1, y);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x, y+1);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x, y-1);
        if(fenetre.getlOccupee().contains(point)) return true;
        return false;
    }
}
