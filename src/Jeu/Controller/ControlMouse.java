package Jeu.Controller;

import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import Jeu.View.FenetreJeu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.awt.*;

public class ControlMouse implements EventHandler<MouseEvent> {

    private FenetreJeu fenetreJeu;
    private Carcassonne carcassonne;
    private Carte carteEnMain;
    private int mode; // 0 placement de carte

    public ControlMouse(FenetreJeu fenetreJeu){
        this.fenetreJeu = fenetreJeu;
        carcassonne = fenetreJeu.getCarcassonne();
        this.mode=0;
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
        // Si on attend du joueur qu'il pose une carte
        if (mode==0) {
            // Si la pioche n'est pas vide
            if (carcassonne.getP().getTaille() >= 0) {
                carteEnMain = carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getCarteEnMain();
                // Position x de la dernière carte placée
                int xCartePlacee = (int) event.getX() / 50;
                // Position y de la dernière carte placée
                int yCartePlacee = (int) event.getY() / 50;
                Point point = new Point(xCartePlacee, yCartePlacee);
                // Erreur s'il y a déjà une carte où l'on a cliqué
                if (carcassonne.getListPointOccupe().contains(point)) {
                    fenetreJeu.afficheErreur("Une carte est déjà placée à cet endroit", "Placement de carte impossible");
                } else if (carteAdjacent(xCartePlacee, yCartePlacee)) {
                    if (carcassonne.isPlacable(xCartePlacee, yCartePlacee, carteEnMain)) {
                        carteEnMain.setPosition(new Point(xCartePlacee, yCartePlacee));
                        fenetreJeu.placerCarte(carteEnMain);
                        fenetreJeu.getBarreInfos().drawInformationsPartisans();
                        fenetreJeu.actualiserDebuggageContamination();
                        mode = 1;
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
    }

    /*
     * Permet de savoir si une carte est adjacente a l'endroit où l'on clique
     * return true si c'est le cas, false sinon
     */
    private boolean carteAdjacent(int x, int y){
        Point point = new Point(x+1, y);
        if(carcassonne.getListPointOccupe().contains(point)) return true;

        point = new Point(x-1, y);
        if(carcassonne.getListPointOccupe().contains(point)) return true;

        point = new Point(x, y+1);
        if(carcassonne.getListPointOccupe().contains(point)) return true;

        point = new Point(x, y-1);
        return carcassonne.getListPointOccupe().contains(point);
    }

    protected void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }

    public Carte getCarteEnMain() { return carteEnMain; }

    public void setMode(int mode) { this.mode = mode; }

    public FenetreJeu getFenetreJeu() { return fenetreJeu; }
}