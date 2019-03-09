package Jeu.MultiJoueur.Model;


import Jeu.Exception.PiocheVideException;


import java.awt.*;
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
    }

    public void joue(){
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
    public void placerPartisan(CartePosee cartePosee, int numZone) {
        if(nombrePartisansRestants>0) {
            nombrePartisansRestants--;
            tabPartisans[8 - nombrePartisansRestants].placerPartisan(cartePosee.getPosition().x, cartePosee.getPosition().y, numZone);
        }
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
}