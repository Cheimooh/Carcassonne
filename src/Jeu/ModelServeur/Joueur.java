package Jeu.ModelServeur;


import Jeu.Exception.PiocheVideException;


import java.awt.*;
import java.io.Serializable;

public class Joueur implements Serializable {
    private String nom;
    private int nombrePartisansRestants;
    private Partisan[] tabPartisans;

    public Joueur(String nom){
        nombrePartisansRestants=8;
        tabPartisans = new Partisan[nombrePartisansRestants];
        this.nom = nom;
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

    public void setNom(String nom) { this.nom=nom; }

    public String getNom() { return nom; }

    public int getNombrePartisansRestants(){ return nombrePartisansRestants; }

   // public Color getColor(){return color;}
}