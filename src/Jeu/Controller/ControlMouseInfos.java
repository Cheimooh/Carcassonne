package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CoteCarte;
import Jeu.View.BarreInfos;
import Jeu.View.FenetreDefausse;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

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
                    barreInfos.getCarcassonne().getDefausse().add(controlMouse.getCarteEnMain());
                    barreInfos.getCarcassonne().jouer();
                    barreInfos.drawInformationsCarte(barreInfos.getCarcassonne().getTabJoueur()[barreInfos.getCarcassonne().getNumJoueur()-1].getCarteEnMain().getDraw().getImg());
                }
            } else if (mode==1){
                if (x > 750 && x < 930 && y > 15 && y < 45) {
                    controlMouse.getFenetreJeu().afficherCartePourPoserUnPartisan();
                } else if (x>750 && x<930 && y>55 && y<85){
                    controlMouse.getFenetreJeu().getCarcassonne().joueurSuivant();
                    controlMouse.getFenetreJeu().getBarreInfos().afficherCarteSuivant();
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
        CoteCarte nord = carte.getNord();
        CoteCarte est = carte.getEst();
        CoteCarte sud = carte.getSud();
        CoteCarte ouest = carte.getOuest();

        carte.setNord(ouest);
        carte.setEst(nord);
        carte.setSud(est);
        carte.setOuest(sud);
        ArrayList<Point> newCoordonnees = new ArrayList<>();
        double x;
        double y;
        for (int i = 0; i < carte.getPositionsCoordonnees().size(); i++) {
            x = 50-carte.getPositionsCoordonnees().get(i).getY();
            y = carte.getPositionsCoordonnees().get(i).getX();
            Point point = new Point((int)x,(int)y);
            newCoordonnees.add(point);
        }
        carte.setPositionsCoordonnees(newCoordonnees);

        int[][] zonesControlleesParLesPoints = carte.getZonesControlleesParLesPoints();

        for (int i = 0; i < zonesControlleesParLesPoints.length; i++) {
            for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                zonesControlleesParLesPoints[i][j] = zonesControlleesParLesPoints[i][j]+3;
                if (zonesControlleesParLesPoints[i][j]>12) {
                    zonesControlleesParLesPoints[i][j] = zonesControlleesParLesPoints[i][j] - 12;
                }
            }
        }
        carte.setZonesControlleesParLesPoints(zonesControlleesParLesPoints);
    }

    public void setMode(int mode) { this.mode = mode; }
}
