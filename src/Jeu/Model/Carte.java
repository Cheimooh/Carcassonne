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
        this.nbRotation=0;
        this.positionsCoordonnees=typeCarte.getCoordonneesPartisans();
        this.zonesControlleesParLesPoints=typeCarte.getZonesControlleesParLesPoints();
    }

    public void pivoter() {
        CoteCarte lastNord = nord;
        CoteCarte lastEst = est;
        CoteCarte lastSud = sud;
        CoteCarte lastOuest = ouest;

        this.nord=lastOuest;
        this.est=lastNord;
        this.sud=lastEst;
        this.ouest=lastSud;
        ArrayList<Point> newCoordonnees = new ArrayList<>();
        double x;
        double y;
        for (int i = 0; i < positionsCoordonnees.size(); i++) {
            x = 50-positionsCoordonnees.get(i).getY();
            y = positionsCoordonnees.get(i).getX();
            Point point = new Point((int)x,(int)y);
            newCoordonnees.add(point);
        }
        positionsCoordonnees=newCoordonnees;

        int[][] newZonesControlleesParLesPoints = new int[zonesControlleesParLesPoints.length][];

        for (int i = 0; i < zonesControlleesParLesPoints.length; i++) {
            for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                newZonesControlleesParLesPoints[i][j] = (zonesControlleesParLesPoints[i][j]+3)%12;
                if (newZonesControlleesParLesPoints[i][j]==0) newZonesControlleesParLesPoints[i][j]=12;
                System.out.println("NEW : "+newZonesControlleesParLesPoints[i][j]+ " OLD : "+zonesControlleesParLesPoints[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        zonesControlleesParLesPoints=newZonesControlleesParLesPoints;
    }

    public Point getPosition() { return position; }

    public void setPosition(Point nbPosition) { this.position = nbPosition; }

    public DrawCard getDraw() { return draw; }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getOuest() { return ouest; }

    public int getNbRotation() { return nbRotation; }

    public void setNbRotation(int nbRotation) { this.nbRotation = nbRotation; }

    public boolean isAbbaye() { return isAbbaye; }

    public ArrayList<Point> getPositionsCoordonnees() { return positionsCoordonnees; }

    public void setPositionsCoordonnees(ArrayList<Point> positionsCoordonnees) { this.positionsCoordonnees = positionsCoordonnees; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }
}