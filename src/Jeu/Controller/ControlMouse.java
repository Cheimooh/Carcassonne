package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import Jeu.View.FenetreJeu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.awt.*;

public class ControlMouse implements EventHandler<MouseEvent> {

    private FenetreJeu fenetreJeu;
    private Carte carteEnMain;

    private int xCartePlacee; // Position x de la dernière carte placée
    private int yCartePlacee; // Position y de la dernière carte placée

    public ControlMouse(FenetreJeu fenetreJeu){
        this.fenetreJeu = fenetreJeu;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        verifPlacerCarte(event);
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