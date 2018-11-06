package Jeu;


import javafx.scene.image.Image;
import java.awt.*;

public class Joueur {
    private String nom;
    private int idJoueur;
    private int pointsTotal;
    private int pointsPrairie;
    private int pointsChemin;
    private int pointsVille;
    private int pointsAbbaye;
    private Carte carteEnMain;
    public static Pioche p;
    private Color color;
    private Partisan[] partisans = new Partisan[8];
    private int nombrePartisansRestants;

    public Joueur (int newId, Pioche newP, Color newColor){
        this.idJoueur = newId;
        this.p = newP;
        this.color = newColor;
        initPartisans();
    }

    private void initPartisans() {
        for (int i=0 ; i<partisans.length ; i++){
            if (color==Color.green) partisans[i] = Partisan.partisanVert;
            if (color==Color.yellow) partisans[i] = Partisan.partisanJaune;
            if (color==Color.blue) partisans[i] = Partisan.partisanBleu;
            if (color==Color.red) partisans[i] = Partisan.partisanRouge;
        }
        nombrePartisansRestants=8;
    }

    public void joue(){
        carteEnMain = piocherCarte();
        //poserCarte();
        //poserPartisant();
    }

    private Carte piocherCarte(){
        Carte cartePiocher = p.piocher();
        return cartePiocher;
    }

    private void poserCarte(int x, int y) {
        carteEnMain.placerCarte(x, y);
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    private void poserPartisant() { }

    public Carte getCarteEnMain() {
        return carteEnMain;
    }

    public String getNom() { return nom; }

    public Image getImagePartisan(){
        for (int i=0 ; i<partisans.length ; i++){
            if (partisans[i]!=null){
                return partisans[i].getImage();
            }
        }
        return null;
    }

    public int getNombrePartisansRestants(){ return nombrePartisansRestants; }
}