package Jeu.Model;


import javafx.scene.paint.Color;

import java.awt.*;

public class Joueur {
    private String nom;
    private int idJoueur;
    private int pointsTotal;
    private int pointsPrairie;
    private int pointsChemin;
    private int pointsVille;
    private int pointsAbbaye;
    private Carte carteEnMain; // Dernière carte qui a été dans la "main" du joueur
    private static Pioche p;
    private Color color;
    private int nombrePartisansRestants;
    private Partisan[] tabPartisans;

    public Joueur (int newId, Pioche newP, Color newColor){
        nombrePartisansRestants=8;
        tabPartisans = new Partisan[nombrePartisansRestants];
        this.idJoueur = newId;
        p = newP;
        this.color = newColor;
        for (int i = 0; i < nombrePartisansRestants; i++) {
            tabPartisans[i] = new Partisan(color);
        }
    }

    public void joue(){
        carteEnMain = piocherCarte();
    }

    /*
     * Permet de piocher une carte dans la pioche
     */
    private Carte piocherCarte(){ return p.piocher(); }

    /*
     * Décrémente le nombre de partisans "non posés" du joueur
     */
    public void placePartisan(CartePosee cartePosee, int numZone) {
        if(nombrePartisansRestants>0) nombrePartisansRestants--;
        tabPartisans[nombrePartisansRestants-8].setPosition(cartePosee.getPosition().x, cartePosee.getPosition().y, numZone);
    }

    public void retirerPartisan(Point p){
        for (int i = 0; i < tabPartisans.length; i++) {
            if(tabPartisans[i].getPointPlacementCarte().equals(p)) tabPartisans[i].retirer();
        }
    }

    public void setNom(String nom) { this.nom=nom; }

    public int getIdJoueur() { return idJoueur; }

    public Carte getCarteEnMain() { return carteEnMain; }

    public String getNom() { return nom; }

    public int getNombrePartisansRestants(){ return nombrePartisansRestants; }

    public Color getColor(){return color;}
}