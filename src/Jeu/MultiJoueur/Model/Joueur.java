package Jeu.MultiJoueur.Model;


import java.io.Serializable;

public class Joueur implements Serializable, Cloneable {
    private String nom;
    private String couleur;
    private int nombrePartisansRestants;
    private boolean isPret;
    private int idJoueur;
    private int pointsTotal;
    private int pointsPrairie;
    private int pointsChemin;
    private int pointsVille;
    private int pointsAbbaye;

    public Joueur(int idJoueur, String nom, String couleur){
        nombrePartisansRestants=8;
        this.idJoueur = idJoueur;
        this.nom = nom;
        this.couleur = couleur;
        isPret = false;
        pointsTotal = 0;
        pointsPrairie = 0;
        pointsChemin = 0;
        pointsVille = 0;
        pointsAbbaye = 0;
    }

    /*
     * Décrémente le nombre de partisans "non posés" du joueur
     */
    public Partisan placerPartisan(CartePosee cartePosee, int numZone) {
        if (nombrePartisansRestants > 0) {
            System.out.println("i: " + (8-nombrePartisansRestants));
            Partisan p = new Partisan(this);
            p.placerPartisan((int) cartePosee.getPosition().getX(), (int) cartePosee.getPosition().getY(), numZone);
            nombrePartisansRestants--;
            return p;
        } else return null;
    }

    /*public void retirerPartisan(Point p){
        if (nombrePartisansRestants<8) {
            for (int i = 0; i < tabPartisans.length; i++) {
                if (tabPartisans[i].getPointPlacementCarte().equals(p)) tabPartisans[i].retirerPartisan();
                nombrePartisansRestants++;
            }
        }
    }*/

    public void addPointsChemin(int nbPoints) {
        pointsChemin += nbPoints;
        pointsTotal += nbPoints;
    }

    @Override
    public Joueur clone() throws CloneNotSupportedException{
        return (Joueur)super.clone();
    }

    public String getNom() { return nom; }

    public String getCouleur() { return couleur; }

    public boolean isPret() { return isPret; }

    public void setPret(boolean pret) { isPret = pret; }

    public int getNombrePartisansRestants() { return nombrePartisansRestants; }

    public int getIdJoueur(){ return idJoueur; }

    public int getPointsTotal() { return pointsTotal; }
}