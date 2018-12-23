package Jeu.Controller;

import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import Jeu.View.BarreInfos;
import Jeu.View.FenetreDefausse;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private BarreInfos barreInfos;
    private Carcassonne carcassonne;
    private ControlMouse controlMouse;
    private int[] tabDefausseCarte;
    private int mode; // 0 placement de carte
                      // 1 placement de partisan

    public ControlMouseInfos(BarreInfos barreInfos, ControlMouse controlMouse, int[] tabDefausseCarte){
        this.barreInfos = barreInfos;
        this.carcassonne = barreInfos.getCarcassonne();
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la barre d'info
     */
    @Override
    public void handle(MouseEvent event) {
        if (barreInfos.getCarcassonne().getPioche().getTaille() >= 0) {
            Carte carteEnMain = carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getCarteEnMain();
            controlMouse.setCarteEnMain(carteEnMain);
            int x = (int) event.getX();
            int y = (int) event.getY();
            //si on clique sur l'endroit sur la barre d'info où il y a la carte
            if (mode==0) {
                if (x > 500 && x < 550 && y > 30 && y < 80) {
                    int nbRotation = controlMouse.getCarteEnMain().getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    controlMouse.getCarteEnMain().setNbRotation(nbRotation);
                    controlMouse.getCarteEnMain().pivoter();
                    barreInfos.rotateCarteSuivante(controlMouse.getCarteEnMain());
                }
                //si on clique sur le "bouton" defausser carte
                if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0] + tabDefausseCarte[2]
                        && y > tabDefausseCarte[1] && y < tabDefausseCarte[1] + tabDefausseCarte[3]) {
                    if(carcassonne.isDefaussable(controlMouse.getCarteEnMain())) {
                        carcassonne.getDefausse().add(controlMouse.getCarteEnMain());
                        carcassonne.jouer();
                        String path = carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getCarteEnMain().getDraw().getPath();
                        barreInfos.drawInformationsCarte(new Image(path));
                    } else {
                        controlMouse.getFenetreJeu().afficheErreur("Vous pouvez poser cette carte à un endroit ","Carte non défaussable");
                    }
                }
            } else if (mode==1){
                if (x > 750 && x < 930 && y > 15 && y < 45) {
                    controlMouse.getFenetreJeu().afficherCartePourPoserUnPartisan();
                } else if (x>750 && x<930 && y>55 && y<85){
                    carcassonne.joueurSuivant();
                    barreInfos.afficherCarteSuivante();
                    carcassonne.jouer();
                    controlMouse.setMode(0);
                }
            }
            if (x>barreInfos.getWidth()/7 && x<barreInfos.getWidth()/7 +100 && y>35 && y<65){
                FenetreDefausse fenetreDefausse = new FenetreDefausse();
                fenetreDefausse.afficherDefausse(carcassonne.getDefausse());
            }
        }
    }

    public void setMode(int mode) { this.mode = mode; }
}
