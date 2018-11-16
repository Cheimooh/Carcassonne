package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import Jeu.Model.CoteCarte;
import Jeu.View.Fenetre;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;

//

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private Carte carteEnMain;
    private String s;
    private boolean placerPartisans;

    private int xCartePlacee;
    private int yCartePlacee;

    public ControlMouse(Fenetre fenetre, String s){
        this.fenetre = fenetre;
        this.s=s;
        placerPartisans=false;
    }

    @Override
    public void handle(MouseEvent event) {
        if (!placerPartisans) {
            verifPlacerCarte(event);
        } else {
            verifPlacerPartisan(event);
        }
    }

    private void verifPlacerPartisan(MouseEvent event) {
        if (s.equals("fenetreDeJeu")) {
            int x = (int) event.getX() / 50;
            int y = (int) event.getY() / 50;
            if(x==xCartePlacee && y==yCartePlacee){
                //fenetre.placerPartisan(x,y);
                placerPartisans=false;
                System.out.println(x);
                System.out.println(y);
                fenetre.getCarcassonne().joueurSuivant();
                fenetre.afficherCarteSuivant();
                fenetre.getCarcassonne().jouer();
            }
            else fenetre.afficheErreur("Placer partisant sur la carte placée");
        }
    }

    private void verifPlacerCarte(MouseEvent event){
        if (s.equals("fenetreDeJeu")) {
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                xCartePlacee = (int) event.getX() / 50;
                yCartePlacee = (int) event.getY() / 50;
                Point point = new Point(xCartePlacee, yCartePlacee);
                if (fenetre.getCarcassonne().getListPointOccupe().contains(point)) {
                    fenetre.afficheErreur("Une carte est déjà placée à cet endroit");
                } else if (carteAdjacent(xCartePlacee, yCartePlacee)) {
                    if (isPlacable(xCartePlacee, yCartePlacee)) {
                        placerPartisans=true;
                        carteEnMain.setPosition(new Point(xCartePlacee, yCartePlacee));
                        fenetre.placerCarte(carteEnMain);
                    } else {
                        fenetre.afficheErreur("La carte ne peut pas être placée à cet endroit");
                    }
                } else {
                    fenetre.afficheErreur("La carte ne peut pas être placée à cet endroit");
                }
            }
        } else if (s.equals("barreInfos")) {
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x > 500 && x < 550 && y > 20 && y < 70) {
                    int nbRotation = carteEnMain.getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    carteEnMain.setNbRotation(nbRotation);
                    pivoterCoteCarte(carteEnMain);
                    fenetre.rotateCarteSuivante(carteEnMain);
                }
                if (x > 50 && x<150 && y>35 && y<65){
                    fenetre.defausserCarte(carteEnMain);
                }
            }
        }
    }

    private void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }

    private boolean carteAdjacent(int x, int y){
        Point point = new Point(x+1, y);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x-1, y);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y+1);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y-1);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)) return true;
        return false;
    }

    private boolean isPlacable(int x, int y) {
        boolean isPlacable = true;
        Point point = new Point(x-1, y);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getEst() != carteEnMain.getOuest()){
                isPlacable=false;
            }
        }

        point = new Point(x+1, y);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getOuest() != carteEnMain.getEst()){
                isPlacable=false;
            }
        }

        point = new Point(x, y-1);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getSud() != carteEnMain.getNord()){
                isPlacable=false;
            }
        }

        point = new Point(x, y+1);
        if(fenetre.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getNord() != carteEnMain.getSud()){
                isPlacable=false;
            }
        }

        return isPlacable;
    }

    private void pivoterCoteCarte(Carte carte){
        CoteCarte nord = carte.getNord();
        CoteCarte est = carte.getEst();
        CoteCarte sud = carte.getSud();
        CoteCarte ouest = carte.getOuest();

        carte.setNord(ouest);
        carte.setEst(nord);
        carte.setSud(est);
        carte.setOuest(sud);

        for (int i = 0; i < 7 ; i++) {
            carte.getZones().add(carte.getZones().get(i));
        }
        for (int j = 0; j < 7 ; j++) {
            carte.getZones().remove(0);
        }
    }

}