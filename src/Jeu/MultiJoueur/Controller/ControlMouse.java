package Jeu.MultiJoueur.Controller;

import Jeu.MultiJoueur.View.MenuReseau;
import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.Model.Point;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ControlMouse implements EventHandler<MouseEvent> {

    private MenuReseau menuReseau;
    private int mode; // 0 placement de carte

    public ControlMouse(MenuReseau menuReseau1){
        menuReseau = menuReseau1;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        if( menuReseau.getNomJoueurCourant().equals(menuReseau.getNomJoueur()) ) placerCarte(event);
        else menuReseau.afficheErreur("C'est pas votre tour de jouer", "Tour de jeu");
    }

    /*
     * Si l'on doit placer une carte sur la fenêtre, on appelle cette fonction
     */
    private void placerCarte(MouseEvent event){
        ObjectOutputStream oo = menuReseau.getSocketJoueur().getOo();
        ObjectInputStream oi = menuReseau.getSocketJoueur().getOi();
        // Position x du clique
        int xCartePlacee = (int) event.getX() / 50;
        // Position y du clique
        int yCartePlacee = (int) event.getY() / 50;
        Point point = new Point(xCartePlacee, yCartePlacee);
        try {
            oo.writeObject("j'envoie");
            oo.writeObject("poserCarte");
            oo.writeObject(point);
            oo.writeObject(menuReseau.getCarteCourante());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}