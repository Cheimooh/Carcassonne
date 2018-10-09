package Jeu;

import java.awt.*;

public class Carte {
    private int identifiant;
    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private DrawCard draw;

    public Carte(int valId, TypeCarte typeCarte){
        this.identifiant = valId;
        this.nord = typeCarte.getNord();
        this.sud = typeCarte.getSud();
        this.est = typeCarte.getEst();
        this.ouest = typeCarte.getOuest();
        this.draw = new DrawCard(typeCarte.getImg());
        this.isAbbaye = false;
        this.position = new Point(0,0);
    }

    public void placerCarte(int x, int y){
        position.move(x, y);
    }

    public int getIdentifiant() { return identifiant; }

    public void setIdentifiant(int identifiant) { this.identifiant = identifiant; }

    public Point getPosition() { return position; }

    public void setPosition(Point nbPosition) { this.position = nbPosition; }

    public CoteCarte getNord() { return nord; }

    public void setNord(CoteCarte nord) { this.nord = nord; }

    public CoteCarte getSud() { return sud; }

    public void setSud(CoteCarte sud) { this.sud = sud; }

    public CoteCarte getEst() { return est; }

    public void setEst(CoteCarte est) { this.est = est; }

    public CoteCarte getOuest() { return ouest; }

    public void setOuest(CoteCarte ouest) { this.ouest = ouest; }

    public boolean isAbbaye() { return isAbbaye; }

    public void setAbbaye(boolean abbaye) { isAbbaye = abbaye; }

    public DrawCard getDraw() { return draw; }
}
