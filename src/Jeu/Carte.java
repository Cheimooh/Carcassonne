package Jeu;

import java.awt.*;

public class Carte {
    private int identifiant;
    private Point nbPosition;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;

    public Carte(int valId, CoteCarte valNord, CoteCarte valEst, CoteCarte valSud, CoteCarte valOuest){
        this.identifiant = valId;
        this.nord = valNord;
        this.sud = valSud;
        this.est = valEst;
        this.ouest = valOuest;
        this.isAbbaye = false;
        this.nbPosition = new Point(0,0);
    }

    public void placerCarte(int x, int y){
        nbPosition.move(x, y);
    }

    public int getIdentifiant() { return identifiant; }

    public void setIdentifiant(int identifiant) { this.identifiant = identifiant; }

    public Point getNbPosition() { return nbPosition; }

    public void setNbPosition(Point nbPosition) { this.nbPosition = nbPosition; }

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
}
