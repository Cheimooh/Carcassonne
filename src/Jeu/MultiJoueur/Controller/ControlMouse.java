package Jeu.MultiJoueur.Controller;

import Jeu.MultiJoueur.View.MenuReseau;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;
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
        if( menuReseau.getJoueurCourant().getNom().equals(menuReseau.getNomJoueur()) ){
            if (menuReseau.getMode() == 0) placerCarte(event);
            else menuReseau.afficheErreur("Jouer un partisant ou passer votre tour", "Deja poser une carte");
        }
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
        System.out.println("x: " + xCartePlacee + ", y: " + yCartePlacee);
        try {
            oo.writeObject("j'envoie");
            oo.writeObject("poserCarte");
            oo.writeObject(point);
            oo.writeObject(menuReseau.getCarteCourante().clone());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}