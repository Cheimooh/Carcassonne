package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CoteCarte;
import Jeu.View.BarreInfos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private BarreInfos barreInfos;
    private ControlMouse controlMouse;

    public ControlMouseInfos(BarreInfos barreInfos, ControlMouse controlMouse){
        this.barreInfos = barreInfos;
        this.controlMouse = controlMouse;
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
            if (x > 500 && x < 550 && y > 30 && y < 80) {
                int nbRotation = controlMouse.getCarteEnMain().getNbRotation();
                nbRotation++;
                nbRotation = nbRotation % 4;
                controlMouse.getCarteEnMain().setNbRotation(nbRotation);
                pivoterCoteCarte(controlMouse.getCarteEnMain());
                barreInfos.rotateCarteSuivante(controlMouse.getCarteEnMain());
            }
            //si on clique sur le "bouton" defausser carte
            if (x > 3000 / 4 && x < 3000 / 4 + 100 && y > 35 && y < 65) {
                barreInfos.defausserCarte(controlMouse.getCarteEnMain());
            }
        }
    }

    /*
     * permet de pivoter la carte dans la barre d'info
     * pivote egalement les attributs de variable afin de stocker sa position pour la suite
     */
    private void pivoterCoteCarte(Carte carte){
        CoteCarte nord = carte.getNord();
        CoteCarte est = carte.getEst();
        CoteCarte sud = carte.getSud();
        CoteCarte ouest = carte.getOuest();

        carte.setNord(ouest);
        carte.setEst(nord);
        carte.setSud(est);
        carte.setOuest(sud);

        carte.getZones().add(0, carte.getZones().get(6));
        carte.getZones().add(1, carte.getZones().get(7));
        carte.getZones().remove(9);
        carte.getZones().remove(8);
    }
}
