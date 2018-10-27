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
        this.draw = new DrawCard(typeCarte.getImg(), typeCarte.getImg90(), typeCarte.getImg180(), typeCarte.getImg270());
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

    public void setNord(CoteCarte nord) { this.nord = nord; }

    public CoteCarte getSud() { return sud; }

    public void setSud(CoteCarte sud) { this.sud = sud; }

    public CoteCarte getEst() { return est; }

    public void setEst(CoteCarte est) { this.est = est; }

    public CoteCarte getOuest() { return ouest; }

    public void setOuest(CoteCarte ouest) { this.ouest = ouest; }

    public int getNbRotation() { return nbRotation; }

    public void setNbRotation(int nbRotation) { this.nbRotation = nbRotation; }
}
