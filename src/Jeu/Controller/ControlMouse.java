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
            getZonePlacementPartisan(x, y);
            placerCarte=true;
            fenetreJeu.getCarcassonne().joueurSuivant();
            fenetreJeu.getBarreInfos().afficherCarteSuivant();
            fenetreJeu.getCarcassonne().jouer();
        }
        // Erreur si l'on clique à un autre endroit que l'endroit où se trouve la carte précédemment placée
        else fenetreJeu.afficheErreur("Votre partisan doit être placé sur la carte que vous venez de placer",
                "Placement de partisans impossible");
    }

    /*
     * Récupère la zone où l'on clique
     */
    private void getZonePlacementPartisan(int x, int y) {
        x = x-(x /50)*50;
        y = y-(y /50)*50;
        int numZone;
        if (y<50/3){
            if (x<50/3) numZone=1; // Zone en haut à gauche
            else if (x<(50/3)*2) numZone=2; // Zone en haut au centre
            else numZone=3; // Zone en haut à droite
        } else if (y<(50/3)*2) {
            if (x<50/3) numZone=8; // Zone au milieu à gauche
            else if (x<(50/3)*2) numZone=9; // Zone au centre de la carte
            else numZone=4; // Zone au milieu à droite
        } else {
            if (x<50/3) numZone=7; // Zone en bas à gauche
            else if (x<(50/3)*2) numZone=6; // Zone en bas au centre
            else numZone=5; // Zone en bas à droite
        }
        ArrayList<String> listeZones = carteEnMain.getZones();
        fenetreJeu.placerPartisan(numZone);
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
        if(fenetreJeu.getCarcassonne().getListPointOccupe().contains(point)) return true;
        return false;
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