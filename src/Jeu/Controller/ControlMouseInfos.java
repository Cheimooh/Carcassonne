package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CoteCarte;
import Jeu.View.BarreInfos;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControlMouseInfos implements EventHandler<MouseEvent> {
    private BarreInfos barreInfos;
    private ControlMouse controlMouse;
    private int[] tabDefausseCarte;

    public ControlMouseInfos(BarreInfos barreInfos, ControlMouse controlMouse, int[] tabDefausseCarte){
        this.barreInfos = barreInfos;
        this.controlMouse = controlMouse;
        this.tabDefausseCarte=tabDefausseCarte;
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
            if (x > tabDefausseCarte[0] && x < tabDefausseCarte[0]+tabDefausseCarte[2]
                    && y > tabDefausseCarte[1] && y < tabDefausseCarte[1]+tabDefausseCarte[3]) {
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

        for (int i = 0; i < carte.getPositionsCoordonnees().size(); i++) {
            double x = carte.getPositionsCoordonnees().get(i).getX();
            double y = carte.getPositionsCoordonnees().get(i).getY();
            carte.getPositionsCoordonnees().get(i).setLocation(50-y,x);
        }
    }
}
