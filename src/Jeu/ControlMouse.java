package Jeu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;

//

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private Carte carteEnMain;
    private String s;

    public ControlMouse(Fenetre fenetre, String s){
        this.fenetre = fenetre;
        this.s=s;
    }

    @Override
    public void handle(MouseEvent event) {
        if (s=="fenetreDeJeu") {
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                int x = (int) event.getX() / 50;
                int y = (int) event.getY() / 50;
                Point point = new Point(x, y);
                if (fenetre.getlOccupee().contains(point)) {
                    System.out.println("ERREUR: Une carte est déjà placée à cet endroit");
                } else if (carteAdjacent(x, y)) {
                    if (isPlacable(x, y)) {
                        carteEnMain.setPosition(new Point(x, y));
                        fenetre.getCarcassonne().joueurSuivant();
                        fenetre.placerCarte(carteEnMain);
                        fenetre.getCarcassonne().jouer();
                    } else System.out.println("ERREUR : La carte ne peut pas être placée à cet endroit");
                } else {
                    System.out.println("ERREUR: CLIQUEZ SUR UNE CROIX !!!!!!!!!!!!!!!");
                }
            } else
                System.out.println("Plus de carte");
        }
        else if (s=="barreInfos"){
            if (fenetre.getCarcassonne().getP().getTaille() >= 0) {
                setCarteEnMain(fenetre.getCarcassonne().getTabJoueur()[fenetre.getCarcassonne().getNumJoueur() - 1].getCarteEnMain());
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (x>500 && x<550 && y>20 && y<70){
                    int nbRotation = carteEnMain.getNbRotation();
                    nbRotation++;
                    nbRotation=nbRotation%4;
                    carteEnMain.setNbRotation(nbRotation);
                    fenetre.rotateCarteSuivante(carteEnMain.getNbRotation());
                }
            } else {
                System.out.println("Plus de carte");
            }
        }
    }

    public void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }

    private boolean carteAdjacent(int x, int y){
        Point point = new Point(x+1, y);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x-1, y);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x, y+1);
        if(fenetre.getlOccupee().contains(point)) return true;

        point = new Point(x, y-1);
        if(fenetre.getlOccupee().contains(point)) return true;
        return false;
    }

    private boolean isPlacable(int x, int y) {
        boolean isPlacable = true;
        Point point = new Point(x-1, y);
        if(fenetre.getlOccupee().contains(point)){
            Carte c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getEst() != carteEnMain.getOuest()){
                isPlacable=false;
            }
        }

        point = new Point(x+1, y);
        if(fenetre.getlOccupee().contains(point)){
            Carte c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getOuest() != carteEnMain.getEst()){
                isPlacable=false;
            }
        }

        point = new Point(x, y-1);
        if(fenetre.getlOccupee().contains(point)){
            Carte c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getSud() != carteEnMain.getNord()){
                isPlacable=false;
            }
        }

        point = new Point(x, y+1);
        if(fenetre.getlOccupee().contains(point)){
            Carte c = fenetre.getCarcassonne().getPointCarteMap().get(point);
            if (c.getNord() != carteEnMain.getSud()){
                isPlacable=false;
            }
        }

        return isPlacable;
    }

}