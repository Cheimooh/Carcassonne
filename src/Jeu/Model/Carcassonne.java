package Jeu.Model;


import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//

public class Carcassonne {

    private final int NB_CARTES = 72;
    private final int NB_CASES = NB_CARTES*2 -1;

    private static Pioche p;

    private int nbJoueur; // nombre de joueur
    private int numJoueur;
    private Joueur[] tabJoueur;

    private Map<Point, CartePosee> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo;
    private ArrayList<Point> listPointOccupe;
    private ArrayList<Carte> defausse;

    private Carte carteDeBase;

    private boolean aJouer = false;

    public Carcassonne(){
        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<>();
        listPointOccupe = new ArrayList<>();
        defausse = new ArrayList<>();
        p = new Pioche(NB_CARTES);
        carteDeBase = new Carte(TypeCarte.cartePPPP);
        //carteDeBase.setPosition(new Point(NB_CASES/2,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2+1,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2-1,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2+1));
        //listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2-1));
        //pointCarteMap.put(new Point(NB_CASES/2,NB_CASES/2), carteDeBase);

        carteDeBase.setPosition(new Point(10,10));
        listPointDispo.add(carteDeBase.getPosition());

        attribuerJoueur(4, new String[]{"Lucas", "Maeva", "Theo", "Tugdual"});
    }

    private void attribuerJoueur(int nbJoueur, String[] nomJoueur){
        this.nbJoueur=nbJoueur;
        tabJoueur = new Joueur[nbJoueur];
        Color[] tabCouleur = new Color[4];
        tabCouleur[0] = Color.GREEN;
        tabCouleur[1] = Color.RED;
        tabCouleur[2] = Color.BLUE;
        tabCouleur[3] = Color.YELLOW;
        for (int i = 0; i < nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i+1, p, tabCouleur[i]);
            tabJoueur[i].setNom(nomJoueur[i]);
        }
        numJoueur = (int) (Math.random()*(nbJoueur-1))+1;
    }

    public void jouer(){
        for (Joueur aTabJoueur : tabJoueur) {
            if (aTabJoueur.getIdJoueur() == numJoueur) {
                aTabJoueur.joue();
            }
        }
    }

    public void joueurSuivant(){
        numJoueur++;
        numJoueur = numJoueur %(nbJoueur+1);
        if(numJoueur == 0){
            numJoueur++;
        }
    }

    public Carte getCarteDeBase() { return carteDeBase; }

    public int getNB_CASES() { return NB_CASES; }

    public Joueur[] getTabJoueur() {
        return tabJoueur;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public Pioche getP() {
        return p;
    }

    public Map<Point, CartePosee> getPointCarteMap() { return pointCarteMap; }

    public ArrayList<Point> getListPointDispo() { return listPointDispo; }

    public ArrayList<Point> getListPointOccupe() { return listPointOccupe; }

    public ArrayList<Carte> getDefausse() { return defausse; }
}
