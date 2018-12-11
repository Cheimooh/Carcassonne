package Jeu.Model;


import javafx.scene.paint.Color;

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

    public Joueur (int newId, Pioche newP, Color newColor){
        this.idJoueur = newId;
        p = newP;
        this.color = newColor;
        nombrePartisansRestants=8;
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
    public void placePartisan() { if (nombrePartisansRestants>0) nombrePartisansRestants--; }

    public void setNom(String nom) { this.nom=nom; }

    public int getIdJoueur() { return idJoueur; }

    public Carte getCarteEnMain() { return carteEnMain; }

    public String getNom() { return nom; }

    public int getNombrePartisansRestants(){ return nombrePartisansRestants; }

    public Color getColor(){return color;}
}