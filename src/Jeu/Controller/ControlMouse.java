package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import Jeu.View.FenetreJeu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.util.ArrayList;

public class ControlMouse implements EventHandler<MouseEvent> {

    private FenetreJeu fenetreJeu;
    private Carte carteEnMain;
    private boolean placerPartisans;

    private int xCartePlacee;
    private int yCartePlacee;

    public ControlMouse(FenetreJeu fenetreJeu){
        this.fenetreJeu = fenetreJeu;
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
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(x/50==xCartePlacee && y/50==yCartePlacee){
            getZonePlacementPartisan(x, y);
            placerPartisans=true;
            fenetreJeu.getCarcassonne().joueurSuivant();
            fenetreJeu.getBarreInfos().afficherCarteSuivant();
            fenetreJeu.getCarcassonne().jouer();
        }
        else fenetreJeu.afficheErreur("Votre partisan doit être placé sur la carte que vous venez de placer",
                "Placement de partisans impossible");
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
        fenetreJeu.placerPartisan(numZone);
    }

    private void verifPlacerCarte(MouseEvent event){
        if (fenetreJeu.getCarcassonne().getP().getTaille() >= 0) {
            setCarteEnMain(fenetreJeu.getCarcassonne().getTabJoueur()[fenetreJeu.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
            xCartePlacee = (int) event.getX() / 50;
            yCartePlacee = (int) event.getY() / 50;
            Point point = new Point(xCartePlacee, yCartePlacee);
            if (fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) {
                fenetreJeu.afficheErreur("Une carte est déjà placée à cet endroit", "Placement de carte impossible");
            } else if (carteAdjacent(xCartePlacee, yCartePlacee)) {
                if (isPlacable(xCartePlacee, yCartePlacee)) {
                    placerPartisans=false;
                    carteEnMain.setPosition(new Point(xCartePlacee, yCartePlacee));
                    fenetreJeu.placerCarte(carteEnMain);
                } else {
                    fenetreJeu.afficheErreur("La carte ne coïncide pas avec la carte adjacente", "Placement de carte impossible");
                }
            } else {
                fenetreJeu.afficheErreur("La carte ne peut pas être placée à cet endroit", "Placement de carte impossible");
            }
        }
    }

    private boolean carteAdjacent(int x, int y){
        Point point = new Point(x+1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x-1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y+1);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y-1);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;
        return false;
    }

    private boolean isPlacable(int x, int y) {
        boolean isPlacable = true;
        Point point = new Point(x-1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetreJeu.getCarcassonne().getPointCarteMap().get(point);
            if (c.getEst() != carteEnMain.getOuest()){
                isPlacable=false;
            }
        }

        point = new Point(x+1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetreJeu.getCarcassonne().getPointCarteMap().get(point);
            if (c.getOuest() != carteEnMain.getEst()){
                isPlacable=false;
            }
        }

        point = new Point(x, y-1);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetreJeu.getCarcassonne().getPointCarteMap().get(point);
            if (c.getSud() != carteEnMain.getNord()){
                isPlacable=false;
            }
        }

        point = new Point(x, y+1);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)){
            CartePosee c = fenetreJeu.getCarcassonne().getPointCarteMap().get(point);
            if (c.getNord() != carteEnMain.getSud()){
                isPlacable=false;
            }
        }

        return isPlacable;
    }

    protected void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }

    public Carte getCarteEnMain() { return carteEnMain; }
}