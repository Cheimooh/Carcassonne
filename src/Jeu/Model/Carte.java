package Jeu.Model;

import Jeu.View.DrawCard;
import java.awt.*;
import java.util.ArrayList;

public class Carte {
    private Point position; // Position de la carte
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private DrawCard draw;
    private int nbRotation;
    private ArrayList<Point> positionsCoordonnees;
    private int[][] zonesControlleesParLesPoints;
    private String type;

    public Carte(TypeCarte typeCarte){
        this.type = typeCarte.getTypeCarte();
        this.nord = typeCarte.getNord();
        this.sud = typeCarte.getSud();
        this.est = typeCarte.getEst();
        this.ouest = typeCarte.getOuest();
        this.draw = new DrawCard(typeCarte.getImg(), typeCarte.getImg90(), typeCarte.getImg180(), typeCarte.getImg270());
        this.position = new Point();
        nbRotation=0;
        positionsCoordonnees=typeCarte.getCoordonneesPartisans();
        zonesControlleesParLesPoints=typeCarte.getZonesControlleesParLesPoints();
    }

    /*
     * Initialise la position de la carte
     */
    public void placerCarte(int x, int y){ position.move(x, y); }

    public Point getPosition() { return position; }

    public void setPosition(Point nbPosition) { this.position = nbPosition; }

    public DrawCard getDraw() { return draw; }

    public CoteCarte getNord() { return nord; }

    public void setNord(CoteCarte nord) { this.nord = nord; }

    public CoteCarte getSud() { return sud; }

    public void setSud(CoteCarte sud) { this.sud = sud; }

    public CoteCarte getEst() { return est; }

    public void setEst(CoteCarte est) { this.est = est; }

    public CoteCarte getOuest() { return ouest; }

    public void setOuest(CoteCarte ouest) { this.ouest = ouest; }

    public int getNbRotation() { return nbRotation; }

    public void setNbRotation(int nbRotation) { this.nbRotation = nbRotation; }

    public boolean isAbbaye() { return isAbbaye; }

    public ArrayList<Point> getPositionsCoordonnees() { return positionsCoordonnees; }

    public void setPositionsCoordonnees(ArrayList<Point> positionsCoordonnees) { this.positionsCoordonnees = positionsCoordonnees; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }

    public void setZonesControlleesParLesPoints(int[][] zonesControlleesParLesPoints) {
        this.zonesControlleesParLesPoints = zonesControlleesParLesPoints;
    }

    public String getType() { return type; }
}