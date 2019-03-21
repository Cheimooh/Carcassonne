package Jeu.Model;

import javafx.scene.paint.Color;

import java.awt.*;

public class Partisan {
    private boolean isPlacer;
    private int numZone; // -1 si non placé
    private Point pointPlacementCarte;
    private Joueur joueur;

    public Partisan(Joueur joueur) {
        this.joueur = joueur;
        pointPlacementCarte = new Point();
        isPlacer = false;
        numZone = -1;
    }

    public void placerPartisan(int x, int y, int numZone) {
        this.numZone = numZone;
        pointPlacementCarte.setLocation(x, y);
        isPlacer = true;
    }

    public void retirerPartisan() {
        numZone = -1;
        isPlacer = false;
        pointPlacementCarte = new Point();
    }

    public Point getPointPlacementCarte() {
        return pointPlacementCarte;
    }

    public boolean isPlacer() {
        return isPlacer;
    }

    public int getNumZone() {
        return numZone;
    }

    public Joueur getJoueur() {
        return joueur;
    }
}
