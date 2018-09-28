package Jeu;

import java.awt.*;

public class Carte {
    private int identifiant;
    private Point nbPosition;
    private int nord, sud, est, ouest;
    private boolean isAbbaye;

    public Carte(int valId, int valNord, int valEst, int valSud, int valOuest){
        this.identifiant = valId;
        this.nord = valNord;
        this.sud = valSud;
        this.est = valEst;
        this.ouest = valOuest;
        this.isAbbaye = false;
        this.nbPosition = new Point(0,0);
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getNord() {
        return nord;
    }

    public void setNord(int nord) {
        this.nord = nord;
    }

    public int getSud() {
        return sud;
    }

    public void setSud(int sud) {
        this.sud = sud;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public int getOuest() {
        return ouest;
    }

    public void setOuest(int ouest) {
        this.ouest = ouest;
    }

    public boolean isAbbaye() {
        return isAbbaye;
    }

    public void setAbbaye(boolean abbaye) {
        isAbbaye = abbaye;
    }

    public void placerCarte(int x, int y){
        nbPosition.move(x, y);
    }
}
