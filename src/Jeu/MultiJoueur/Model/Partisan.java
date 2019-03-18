package Jeu.MultiJoueur.Model;

import javafx.scene.paint.Color;

import java.awt.*;

public class Partisan {
    private boolean isPlacer;
    private int numZone; // -1 si non Placer
    private Point pointPlacementCarte;
    private String colorPartisan;

    public Partisan(String colorPartisan) {
        this.colorPartisan = colorPartisan;
        pointPlacementCarte = new Point();
        isPlacer = false;
        numZone = -1;
    }

    public void placerPartisan(int x, int y, int numZone){
        this.numZone = numZone;
        pointPlacementCarte.setLocation(x, y);
        isPlacer = true;
    }

    public void retirerPartisan(){
        numZone = -1;
        isPlacer = false;
    }

    public Point getPointPlacementCarte() { return pointPlacementCarte; }

    public boolean isPlacer() { return isPlacer; }

    public int getNumZone() { return numZone; }
}