package Jeu.Model;

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
    private Partisan[] zonesControlleesParLesPartisans;

    public Carte(TypeCarte typeCarte) {
        this.type = typeCarte.getTypeCarte();
        this.nord = typeCarte.getNord();
        this.sud = typeCarte.getSud();
        this.est = typeCarte.getEst();
        this.ouest = typeCarte.getOuest();
        this.draw = new DrawCard(typeCarte.getPath(), typeCarte.getPath90(), typeCarte.getPath180(), typeCarte.getPath270());
        this.position = new Point();
        this.nbRotation = 0;
        this.positionsCoordonnees = typeCarte.getCoordonneesPartisans();
        this.zonesControlleesParLesPoints = typeCarte.getZonesControlleesParLesPoints();
        this.zonesControlleesParLesPartisans = typeCarte.getZonesControlleesParLesPartisans();
    }

    /*
     * permet de pivoter la carte dans la barre d'info
     * pivote egalement les attributs de variable afin de stocker sa position pour la suite
     */
    public void pivoter() {
        CoteCarte lastNord = nord;
        CoteCarte lastEst = est;
        CoteCarte lastSud = sud;

        this.nord = ouest;
        this.est = lastNord;
        this.sud = lastEst;
        this.ouest = lastSud;
        ArrayList<Point> newCoordonnees = new ArrayList<>();
        double x;
        double y;
        for (Point positionsCoordonnee : positionsCoordonnees) {
            x = 50 - positionsCoordonnee.getY();
            y = positionsCoordonnee.getX();
            Point point = new Point((int) x, (int) y);
            newCoordonnees.add(point);
        }
        positionsCoordonnees = newCoordonnees;

        int[][] newZonesControlleesParLesPoints = new int[zonesControlleesParLesPoints.length][];

        for (int i = 0; i < zonesControlleesParLesPoints.length; i++) {
            newZonesControlleesParLesPoints[i] = new int[zonesControlleesParLesPoints[i].length];
        }

        for (int i = 0; i < zonesControlleesParLesPoints.length; i++) {
            for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                newZonesControlleesParLesPoints[i][j] = (zonesControlleesParLesPoints[i][j] + 3) % 12;
                if (newZonesControlleesParLesPoints[i][j] == 0) newZonesControlleesParLesPoints[i][j] = 12;
            }
        }
        zonesControlleesParLesPoints = newZonesControlleesParLesPoints;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point nbPosition) {
        this.position = nbPosition;
    }

    public DrawCard getDraw() {
        return draw;
    }

    public CoteCarte getNord() {
        return nord;
    }

    public CoteCarte getSud() {
        return sud;
    }

    public CoteCarte getEst() {
        return est;
    }

    public CoteCarte getOuest() {
        return ouest;
    }

    public int getNbRotation() {
        return nbRotation;
    }

    public void setNbRotation(int nbRotation) {
        this.nbRotation = nbRotation;
    }

    public boolean isAbbaye() {
        return isAbbaye;
    }

    public ArrayList<Point> getPositionsCoordonnees() {
        return positionsCoordonnees;
    }

    public void setPositionsCoordonnees(ArrayList<Point> positionsCoordonnees) {
        this.positionsCoordonnees = positionsCoordonnees;
    }

    public int[][] getZonesControlleesParLesPoints() {
        return zonesControlleesParLesPoints;
    }

    public String getType() {
        return type;
    }

    public Partisan[] getZonesControlleesParLesPartisans() {
        return zonesControlleesParLesPartisans;
    }
}