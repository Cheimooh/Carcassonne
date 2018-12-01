package Jeu.Controller;

import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import Jeu.View.FenetreJeu;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControlMousePartisant implements EventHandler<MouseEvent> {
    private FenetreJeu fenetreJeu;
    private Carte carteEnMain;

    public ControlMousePartisant(FenetreJeu fenetreJeu){
        this.fenetreJeu = fenetreJeu;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        verifPlacerPartisan(event);
    }

    /*
     * Si l'on doit placer un partisan sur la fenêtre, on appelle cette fonction
     */
    private void verifPlacerPartisan(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // Si l'on clique pas sur une zone indiquée
        int numZone = getZonePlacementPartisan(x,y);
        if (numZone==-1){
            fenetreJeu.afficheErreur("Votre partisan doit être placé sur une des positions affichées",
                    "Placement de partisan impossible");
        } else {
            if (zonePasEncoreOccupee(numZone)) {
                fenetreJeu.placerPartisan(numZone);
                fenetreJeu.getPopUpPartisant().quitter();
                fenetreJeu.getCarcassonne().joueurSuivant();
                fenetreJeu.getBarreInfos().afficherCarteSuivant();
                fenetreJeu.getCarcassonne().jouer();
            } else {
                fenetreJeu.afficheErreur("Il y a un partisan dans la même zone que celle que vous avez choisie",
                        "Placement de partisan impossible");
                }
            }

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
        for (int i = 0; i < carteEnMain.getPositionsCoordonnees().size() ; i++) {
            double xPartisan = carteEnMain.getPositionsCoordonnees().get(i).getX()*10;
            double yPartisan = carteEnMain.getPositionsCoordonnees().get(i).getY()*10;
            // Si l'on clique dans les cercles
            if(x>xPartisan-10 && x<xPartisan+10 && y>yPartisan-10 && y<yPartisan+10){
                numZone=i;
            }
        }
        return numZone;
    }

    public void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }
}
