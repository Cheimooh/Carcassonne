package Jeu;

import java.awt.*;

public class Carte {
    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private DrawCard draw;
    private int nbRotation;

    public Carte(TypeCarte typeCarte){
        this.nord = typeCarte.getNord();
        this.sud = typeCarte.getSud();
        this.est = typeCarte.getEst();
        this.ouest = typeCarte.getOuest();
        this.draw = new DrawCard(typeCarte.getImg());
        this.isAbbaye = false;
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

    public CoteCarte getSud() { return sud; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getOuest() { return ouest; }

    public int getNbRotation() { return nbRotation; }

    public void setNbRotation(int nbRotation) { this.nbRotation = nbRotation; }
}
