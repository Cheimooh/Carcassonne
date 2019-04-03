package Jeu.MultiJoueur.Model;

import java.awt.*;
import java.io.Serializable;

public class Partisan implements Serializable, Cloneable {
    private boolean isPlacer;
    private int numZone; // -1 si non Placer
    private Point pointPlacementCarte;
    private Joueur joueur;

    public Partisan(Joueur joueur) {
        this.joueur = joueur;
        pointPlacementCarte = new Point();
        isPlacer = false;
        numZone = -1;
    }

    public Partisan placerPartisan(int x, int y, int numZone){
        this.numZone = numZone;
        pointPlacementCarte.setLocation(x, y);
        isPlacer = true;
        return this;
    }

    public void retirerPartisan(){
        numZone = -1;
        isPlacer = false;
        pointPlacementCarte = new Point();
    }

    @Override
    public Partisan clone() throws CloneNotSupportedException{ return (Partisan)super.clone(); }

    public Point getPointPlacementCarte() { return pointPlacementCarte; }

    public boolean isPlacer() { return isPlacer; }

    public int getNumZone() { return numZone; }

    public Joueur getJoueur() { return joueur; }
}
