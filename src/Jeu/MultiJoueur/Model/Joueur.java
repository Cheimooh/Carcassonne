package Jeu.MultiJoueur.Model;


import Jeu.Exception.PiocheVideException;

import java.io.Serializable;

public class Joueur implements Serializable, Cloneable {
    private String nom;
    private String couleur;
    private int nombrePartisansRestants;
    private Partisan[] tabPartisans;
    private boolean isPret;

    public Joueur(String nom, String couleur){
        nombrePartisansRestants=8;
        tabPartisans = new Partisan[nombrePartisansRestants];
        this.nom = nom;
        this.couleur = couleur;
        isPret = false;
        for (int i = 0; i < nombrePartisansRestants; i++) {
            tabPartisans[i] = new Partisan(this);
        }
    }

    /*
     * Permet de piocher une carte dans la pioche
     */
    private Carte piocherCarte() throws PiocheVideException {
        //return p.piocher();
        return null;
    }

    /*
     * Décrémente le nombre de partisans "non posés" du joueur
     */
    public Partisan placerPartisan(CartePosee cartePosee, int numZone) {
        if (nombrePartisansRestants > 0) {
            System.out.println("i: " + (8-nombrePartisansRestants));
            Partisan p = tabPartisans[8 - nombrePartisansRestants].placerPartisan(cartePosee.getPosition().getX(), cartePosee.getPosition().getY(), numZone);
            nombrePartisansRestants--;
            return p;
        } else return null;
    }

    public void retirerPartisan(Point p){
        if (nombrePartisansRestants<8) {
            for (int i = 0; i < tabPartisans.length; i++) {
                if (tabPartisans[i].getPointPlacementCarte().equals(p)) tabPartisans[i].retirerPartisan();
                nombrePartisansRestants++;
            }
        }
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

    public Partisan[] getTabPartisans() {return tabPartisans;}

    public void setTabPartisans(Partisan[] tabPartisans) {this.tabPartisans = tabPartisans; }
}