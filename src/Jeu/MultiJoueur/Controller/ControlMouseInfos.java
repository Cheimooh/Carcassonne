package Jeu.MultiJoueur.Controller;

import Jeu.MenuReseau;
import Jeu.MultiJoueur.Controller.ControlMouse;
import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.View.BarreInfos;
import Jeu.MultiJoueur.View.FenetreDefausse;
import Jeu.MultiJoueur.View.FenetreJeu;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private BarreInfos barreInfos;
    private MenuReseau menuReseau;
    private ControlMouse controlMouse;
    private FenetreJeu fenetreJeu;
    private int[] tabDefausseCarte;
    private int mode; // 0 placement de carte
                      // 1 placement de partisan

    public ControlMouseInfos(ControlMouse controlMouse, int[] tabDefausseCarte){
        this.barreInfos = barreInfos;
        //this.menuReseau = barreInfos.;
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la barre d'info
     */
    @Override
    public void handle(MouseEvent event) {
        /*if (menuReseau.getPioche().getTaille() >= 0) {
            Carte carteCourante = carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getCarteEnMain();
            controlMouse.setCarteEnMain(carteCourante);
            int x = (int) event.getX();
            int y = (int) event.getY();
            //si on clique sur l'endroit sur la barre d'info où il y a la carte
            if (mode==0) {
                if (x > 500 && x < 550 && y > 30 && y < 80) {
                    int nbRotation = controlMouse.getCarteEnMain().getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    carteCourante.setNbRotation(nbRotation);
                    carteCourante.pivoter();
                    barreInfos.rotateCarteSuivante(carteCourante);
                }
                //si on clique sur le "bouton" defausser carte
                if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0] + tabDefausseCarte[2]
                        && y > tabDefausseCarte[1] && y < tabDefausseCarte[1] + tabDefausseCarte[3]) {
                    if(carcassonne.isDefaussable(carteCourante)) {
                        carcassonne.getDefausse().add(carteCourante);
                        carcassonne.jouer();
                        carteCourante = carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getCarteEnMain(); // Le joueur a repioché
                        String path = carteCourante.getDraw().getPath();
                        barreInfos.drawInformationsCarte(new Image(path));
                    } else {
                        fenetreJeu.afficheErreur("Vous pouvez poser cette carte à un endroit ","Carte non défaussable");
                    }
                }
            } else if (mode==1){
                if (x > 750 && x < 930 && y > 15 && y < 45) {
                    fenetreJeu.afficherCartePourPoserUnPartisan();
                } else if (x>750 && x<930 && y>55 && y<85){
                    carcassonne.verificationZoneFermee(fenetreJeu.getDerniereCartePosee());
                    carcassonne.joueurSuivant();
                    barreInfos.afficherCarteSuivante();
                    carcassonne.jouer();
                    controlMouse.setMode(0);
                }
            }
            //si on clique sur lebouton defausse
            if (x>barreInfos.getWidth()/7 && x<barreInfos.getWidth()/7 +100 && y>35 && y<65){
                FenetreDefausse fenetreDefausse = new FenetreDefausse();
                fenetreDefausse.afficherDefausse(carcassonne.getDefausse());
            }
        }*/
    }

    public void setMode(int mode) { this.mode = mode; }
}
