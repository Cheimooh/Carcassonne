package Jeu.MultiJoueur.Controller;

import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.View.MenuReseau;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private MenuReseau menuReseau;
    private ControlMouse controlMouse;
    private int[] tabDefausseCarte;

    public ControlMouseInfos(ControlMouse controlMouse, int[] tabDefausseCarte, MenuReseau menuReseau){
        this.menuReseau= menuReseau;
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
    }

    /*
     * S'active si l'on clique sur un endroit de la barre d'info
     */
    @Override
    public void handle(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Carte carteCourante = menuReseau.getCarteCourante();
        if (menuReseau.getMode() == 0) {

            if (x > 450 && x < 500 && y > 30 && y < 80) {
                if( menuReseau.getJoueurCourant().getNom().equals(menuReseau.getNomJoueur())){
                    int nbRotation = carteCourante.getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    carteCourante.setNbRotation(nbRotation);
                    carteCourante.pivoter();
                    menuReseau.actualiserBarreInfo();
                }
            }

            //si on clique sur le "bouton" defausser carte

            if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0] + tabDefausseCarte[2] && y > tabDefausseCarte[1] && y < tabDefausseCarte[1] + tabDefausseCarte[3]) {
                if( menuReseau.getJoueurCourant().getNom().equals(menuReseau.getNomJoueur())) {
                    try {
                        ObjectOutputStream oo = menuReseau.getSocketJoueur().getOo();
                        oo.writeObject("j'envoie");
                        oo.writeObject("defausse");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else menuReseau.afficheErreur("C'est pas votre tour de jouer", "Tour de jeu");
            }

        } else if (menuReseau.getMode() == 1) {
            if (x > 750 && x < 930 && y > 15 && y < 45){
                if (menuReseau.getJoueurCourant().getNom().equals(menuReseau.getNomJoueur())) menuReseau.afficherCartePourPoserUnPartisan();
                else menuReseau.afficheErreur("C'est pas votre tour de jouer", "Tour de jeu");
            }

            if (x > 750 && x < 930 && y > 55 && y < 85) {
                if (menuReseau.getJoueurCourant().getNom().equals(menuReseau.getNomJoueur())) {
                    try {
                        ObjectOutputStream oo = menuReseau.getSocketJoueur().getOo();
                        oo.writeObject("j'envoie");
                        oo.writeObject("tourSuivant");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else menuReseau.afficheErreur("C'est pas votre tour de jouer", "Tour de jeu");
            }
        }if (x > menuReseau.getWidth() / 7 && x < menuReseau.getWidth() / 7 + 100 && y > 35 && y < 65) {
            menuReseau.afficherDefausse();
        }
    }
}
