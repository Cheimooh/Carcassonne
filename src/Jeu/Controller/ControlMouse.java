package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import Jeu.Model.CoteCarte;
import Jeu.View.Fenetre;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.util.ArrayList;

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
        placerPartisans=true;
    }

    @Override
    public void handle(MouseEvent event) {
        if (placerPartisans) {
            verifPlacerCarte(event);
        } else {
            verifPlacerPartisan(event);
        }
    }

    private void verifPlacerPartisan(MouseEvent event) {
        if (s.equals("fenetreDeJeu")) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if(x/50==xCartePlacee && y/50==yCartePlacee){
                getZonePlacementPartisan(x, y);
                placerPartisans=true;
                fenetre.getCarcassonne().joueurSuivant();
                fenetre.afficherCarteSuivant();
                fenetre.getCarcassonne().jouer();
            }
            else fenetre.afficheErreur("Votre partisan doit être placé sur la carte que vous venez de placer",
                    "Placement de partisans impossible");
        }
    }

    private void getZonePlacementPartisan(int x, int y) {
        x = x-(x /50)*50;
        y = y-(y /50)*50;
        int numZone;
        if (y<50/3){
            if (x<50/3) numZone=1;
            else if (x<(50/3)*2) numZone=2;
            else numZone=3;
        } else if (y<(50/3)*2) {
            if (x<50/3) numZone=8;
            else if (x<(50/3)*2) numZone=9;
            else numZone=4;
        } else {
            if (x<50/3) numZone=7;
            else if (x<(50/3)*2) numZone=6;
            else numZone=5;
        }
        ArrayList<String> listeZones = carteEnMain.getZones();
        fenetre.placerPartisan(numZone);
    }

    private void verifPlacerCarte(MouseEvent event){
        if (s.equals("fenetreDeJeu")) {
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                xCartePlacee = (int) event.getX() / 50;
                yCartePlacee = (int) event.getY() / 50;
                Point point = new Point(xCartePlacee, yCartePlacee);
                if (fenetre.getCarcassonne().getListPointOccupe().contains(point)) {
                    fenetre.afficheErreur("Une carte est déjà placée à cet endroit", "Placement de carte impossible");
                } else if (carteAdjacent(xCartePlacee, yCartePlacee)) {
                    if (isPlacable(xCartePlacee, yCartePlacee)) {
                        placerPartisans=false;
                        carteEnMain.setPosition(new Point(xCartePlacee, yCartePlacee));
                        fenetre.placerCarte(carteEnMain);
                    } else {
                        fenetre.afficheErreur("La carte ne coïncide pas avec la carte adjacente", "Placement de carte impossible");
                    }
                } else {
                    fenetre.afficheErreur("La carte ne peut pas être placée à cet endroit", "Placement de carte impossible");
                }
            }
        } else if (s.equals("barreInfos")) {
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x > 500 && x < 550 && y > 30 && y < 80) {
                    int nbRotation = carteEnMain.getNbRotation();
                    nbRotation++;
                    nbRotation = nbRotation % 4;
                    carteEnMain.setNbRotation(nbRotation);
                    pivoterCoteCarte(carteEnMain);
                    fenetre.rotateCarteSuivante(carteEnMain);
                }
                if (x > 3000/4 && x<3000/4+100 && y>35 && y<65){
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

        carte.getZones().add(0, carte.getZones().get(6));
        carte.getZones().add(1, carte.getZones().get(7));
        carte.getZones().remove(9);
        carte.getZones().remove(8);
    }

}