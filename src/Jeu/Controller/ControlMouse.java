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
    private boolean placerCarte; // Booléen qui indique si c'est le moment de placer une carte ou non
    // Si non, il faut placer un partisan

    private int xCartePlacee; // Position x de la dernière carte placée
    private int yCartePlacee; // Position y de la dernière carte placée

    public ControlMouse(FenetreJeu fenetreJeu){
        this.fenetreJeu = fenetreJeu;
        placerCarte=true;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        if (placerCarte) {
            verifPlacerCarte(event);
        } else {
            verifPlacerPartisan(event);
        }
    }

    /*
     * Si l'on doit placer un partisan sur la fenêtre, on appelle cette fonction
     */
    private void verifPlacerPartisan(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // Si l'on clique sur la carte placée précédemment
        if(x/50==xCartePlacee && y/50==yCartePlacee){
            // Si l'on clique pas sur une zone indiquée
            int numZone = getZonePlacementPartisan(x,y);
            if (numZone==-1){
                fenetreJeu.afficheErreur("Votre partisan doit être placé sur une des positions affichées",
                        "Placement de partisan impossible");
            } else {
                if (zonePasEncoreOccupee(numZone)) {
                    fenetreJeu.placerPartisan(numZone);
                    placerCarte = true;
                    fenetreJeu.getCarcassonne().joueurSuivant();
                    fenetreJeu.getBarreInfos().afficherCarteSuivant();
                    fenetreJeu.getCarcassonne().jouer();
                } else {
                    fenetreJeu.afficheErreur("Il y a un partisan dans la même zone que celle que vous avez choisie",
                            "Placement de partisan impossible");
                }
            }
        }
        // Erreur si l'on clique à un autre endroit que l'endroit où se trouve la carte précédemment placée
        else fenetreJeu.afficheErreur("Votre partisan doit être placé sur la carte que vous venez de placer",
                "Placement de partisans impossible");
    }

    private boolean zonePasEncoreOccupee(int numZone) {
        CartePosee c = fenetreJeu.getDerniereCartePosee();
        for (int i = 0; i < c.getZonesControlleesParLesPoints().length ; i++) {
            if (i==numZone){
                for (int j = 0; j < c.getZonesControlleesParLesPoints()[i].length ; j++) {
                    if (c.getZonesOccupees().contains(c.getZonesControlleesParLesPoints()[i][j])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /*
     * Récupère la zone où l'on clique
     */
    private int getZonePlacementPartisan(int x, int y) {
        int numZone=-1;
        for (int i = 0; i <carteEnMain.getPositionsCoordonnees().size() ; i++) {
            double xPartisan = carteEnMain.getPositionsCoordonnees().get(i).getX();
            double yPartisan = carteEnMain.getPositionsCoordonnees().get(i).getY();
            xPartisan+=carteEnMain.getPosition().getX()*50;
            yPartisan+=carteEnMain.getPosition().getY()*50;
            // Si l'on clique 5 pixels autour d'un des cercles
            if(x>xPartisan-3 && x<xPartisan+8 && y>yPartisan-3 && y<yPartisan+8){
                numZone=i;
            }
        }
        return numZone;
    }

    /*
     * Si l'on doit placer une carte sur la fenêtre, on appelle cette fonction
     */
    private void verifPlacerCarte(MouseEvent event){
        // Si la pioche n'est pas vide
        if (fenetreJeu.getCarcassonne().getP().getTaille() >= 0) {
            setCarteEnMain(fenetreJeu.getCarcassonne().getTabJoueur()[fenetreJeu.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
            xCartePlacee = (int) event.getX() / 50;
            yCartePlacee = (int) event.getY() / 50;
            Point point = new Point(xCartePlacee, yCartePlacee);
            // Erreur s'il y a déjà une carte où l'on a cliqué
            if (fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) {
                fenetreJeu.afficheErreur("Une carte est déjà placée à cet endroit", "Placement de carte impossible");
            }
            else if (carteAdjacent(xCartePlacee, yCartePlacee)) {
                if (isPlacable(xCartePlacee, yCartePlacee)) {
                    placerCarte=false;
                    carteEnMain.setPosition(new Point(xCartePlacee, yCartePlacee));
                    fenetreJeu.placerCarte(carteEnMain);
                    fenetreJeu.getBarreInfos().drawInformationsPartisans();
                }
                // Erreur si les cartes à côté ne coïncident pas
                else {
                    fenetreJeu.afficheErreur("La carte ne coïncide pas avec la carte adjacente", "Placement de carte impossible");
                }
            }
            // Erreur s'il n'y a pas de carte à côté de l'endroit où l'on clique
            else {
                fenetreJeu.afficheErreur("La carte ne peut pas être placée à cet endroit", "Placement de carte impossible");
            }
        }
    }

    /*
     * Permet de savoir si une carte est adjacente a l'endroit où l'on clique
     * return true si c'est le cas, false sinon
     */
    private boolean carteAdjacent(int x, int y){
        Point point = new Point(x+1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x-1, y);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y+1);
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;

        point = new Point(x, y-1);
        return fenetreJeu.getCarcassonne().getListPointOccupe().contains(point);
    }

    /*
     * Permet de savoir si la carte courante peut etre posée où non en fonction de si elle coincide avec les cartes
     * adjacentes ou non
     */
    private boolean isPlacable(int x, int y) {
        boolean isPlacable = true;
        // creer un point temporaire pour faire les verifications
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