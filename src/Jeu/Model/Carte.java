package Jeu.Model;

import Jeu.View.DrawCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Carte {
    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private DrawCard draw;
    private int nbRotation;
    private ArrayList<String> zones;
    private String zoneCentrale;

    public Carte(TypeCarte typeCarte){
        this.nord = typeCarte.getNord();
        this.sud = typeCarte.getSud();
        this.est = typeCarte.getEst();
        this.ouest = typeCarte.getOuest();
        this.draw = new DrawCard(typeCarte.getImg(), typeCarte.getImg90(), typeCarte.getImg180(), typeCarte.getImg270());
        this.zones = new ArrayList<>();
        this.zones = typeCarte.getZones();
        this.zoneCentrale = typeCarte.getZoneCentrale();
        this.isAbbaye = typeCarte.getZoneCentrale().equals("abbaye");
        this.position = new Point();
        nbRotation=0;
    }

    public void placerCarte(int x, int y){
        position.move(x, y);
    }

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

    public ArrayList<String> getZones() { return zones; }

    public String getZoneCentrale() { return zoneCentrale; }
}
