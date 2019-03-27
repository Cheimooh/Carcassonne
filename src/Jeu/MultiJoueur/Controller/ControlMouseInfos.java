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
    private int mode; // 0 placement de carte
                      // 1 placement de partisan

    public ControlMouseInfos(ControlMouse controlMouse, int[] tabDefausseCarte, MenuReseau menuReseau){
        this.menuReseau= menuReseau;
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la barre d'info
     */
    @Override
    public void handle(MouseEvent event) {
            Carte carteCourante = menuReseau.getCarteCourante();
            int x = (int) event.getX();
            int y = (int) event.getY();
            //si on clique sur l'endroit sur la barre d'info où il y a la carte
            if (mode==0) {
                if (x > 500 && x < 550 && y > 30 && y < 80) {
                    int nbRotation = carteCourante.getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    carteCourante.setNbRotation(nbRotation);
                    carteCourante.pivoter();
                    menuReseau.actualiserBarreInfo();
                }
               //si on clique sur le "bouton" defausser carte
                if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0] + tabDefausseCarte[2] && y > tabDefausseCarte[1] && y < tabDefausseCarte[1] + tabDefausseCarte[3]) {
                    try {
                        ObjectOutputStream oo = menuReseau.getSocketJoueur().getOo();
                        oo.writeObject("j'envoie");
                        oo.writeObject("defausse");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //fenetreJeu.afficheErreur("Vous pouvez poser cette carte à un endroit ","Carte non défaussable");
                }/*
            } else if (mode==1){
                if (x > 750 && x < 930 && y > 15 && y < 45) {
                    fenetreJeu.afficherCartePourPoserUnPartisan();
                } else if (x>750 && x<930 && y>55 && y<85){
                    carcassonne.verificationZoneFermee(fenetreJeu.getDerniereCartePosee());
                    carcassonne.joueurSuivant();
                    barreInfos.afficherCarteSuivante();
                    carcassonne.jouer();
                    controlMouse.setMode(0);
                }*/
            }
            //si on clique sur lebouton defausse
            if (x>menuReseau.getWidth()/7 && x<menuReseau.getWidth()/7 +100 && y>35 && y<65){
                menuReseau.afficherDefausse();
            }

    }

    public void setMode(int mode) { this.mode = mode; }
}
