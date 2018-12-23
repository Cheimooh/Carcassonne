package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.View.BarreInfos;
import Jeu.View.FenetreDefausse;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private BarreInfos barreInfos;
    private ControlMouse controlMouse;
    private int[] tabDefausseCarte;
    private int mode; // 0 placement de carte
                      // 1 placement de partisan

    public ControlMouseInfos(BarreInfos barreInfos, ControlMouse controlMouse, int[] tabDefausseCarte){
        this.barreInfos = barreInfos;
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la barre d'info
     */
    @Override
    public void handle(MouseEvent event) {
        if (barreInfos.getCarcassonne().getP().getTaille() >= 0) {
            controlMouse.setCarteEnMain(barreInfos.getCarcassonne().getTabJoueur()[barreInfos.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
            int x = (int) event.getX();
            int y = (int) event.getY();
            //si on clique sur l'endroit sur la barre d'info où il y a la carte
            if (mode==0) {
                if (x > 500 && x < 550 && y > 30 && y < 80) {
                    int nbRotation = controlMouse.getCarteEnMain().getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    controlMouse.getCarteEnMain().setNbRotation(nbRotation);
                    pivoterCoteCarte(controlMouse.getCarteEnMain());
                    barreInfos.rotateCarteSuivante(controlMouse.getCarteEnMain());
                }
                //si on clique sur le "bouton" defausser carte
                if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0] + tabDefausseCarte[2]
                        && y > tabDefausseCarte[1] && y < tabDefausseCarte[1] + tabDefausseCarte[3]) {
                    if(controlMouse.getFenetreJeu().getCarcassonne().isDefaussable(controlMouse.getCarteEnMain())) {
                        barreInfos.getCarcassonne().getDefausse().add(controlMouse.getCarteEnMain());
                        barreInfos.getCarcassonne().jouer();
                        barreInfos.drawInformationsCarte(new Image(barreInfos.getCarcassonne().getTabJoueur()[barreInfos.getCarcassonne().getNumJoueur() - 1].getCarteEnMain().getDraw().getPath()));
                    } else {
                        controlMouse.getFenetreJeu().afficheErreur("Vous pouvez poser cette carte à un endroit ","Carte non défaussable");
                    }
                }
            } else if (mode==1){
                if (x > 750 && x < 930 && y > 15 && y < 45) {
                    controlMouse.getFenetreJeu().afficherCartePourPoserUnPartisan();
                } else if (x>750 && x<930 && y>55 && y<85){
                    controlMouse.getFenetreJeu().getCarcassonne().joueurSuivant();
                    controlMouse.getFenetreJeu().getBarreInfos().afficherCarteSuivante();
                    controlMouse.getFenetreJeu().getCarcassonne().jouer();
                    controlMouse.setMode(0);
                }
            }
            if (x>barreInfos.getWidth()/7 && x<barreInfos.getWidth()/7 +100 && y>35 && y<65){
                FenetreDefausse fenetreDefausse = new FenetreDefausse();
                fenetreDefausse.afficherDefausse(controlMouse.getFenetreJeu().getCarcassonne().getDefausse());
            }
        }
    }

    /*
     * permet de pivoter la carte dans la barre d'info
     * pivote egalement les attributs de variable afin de stocker sa position pour la suite
     */
    private void pivoterCoteCarte(Carte carte){
        carte.pivoter();

    }

    public void setMode(int mode) { this.mode = mode; }
}
