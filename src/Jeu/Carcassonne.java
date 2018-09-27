package Jeu;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Carcassonne {
    private int nbJoueur;
    private int numJoueur;
    private Joueur[] tabJoueur;
    public static Pioche p;
    private Point[][] tabIndex; //index toutes les cases que l'ont peut prendre sur le plateau
    private int[][] tabDispo; //determine si une case est occupee 2 , peut être occupee 1 ou désactivee 0
    private int NB_CASES;
    private ArrayList<Carte> lNodes; //liste qui contient les noeuds (donc les cartes) et qui peut s'etendre
    private ArrayList<Carte[]> lEdges; //liste qui contient un tableau d'arretes représentées par (carte1 et carte2)

    public Carcassonne(){}

    public Carcassonne(int newNbJoueur){
        this.nbJoueur=newNbJoueur;
        p = new Pioche();
        NB_CASES = p.getTaille()*2 -1;
        initTab();
        tabJoueur = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i, p);
            //ajouter le nom via la vue
        }
        //mise en place du plateau et de la première carte
        numJoueur = (int) Math.random()*nbJoueur;
        while (p.getTaille() > 0){
            for (int i=0 ; i < nbJoueur ; i++){
                if (tabJoueur[i].getIdJoueur() == numJoueur){
                    tabJoueur[i].joue();
                    numJoueur++;
                    numJoueur = numJoueur %nbJoueur;
                }
            }
        }
    }

    public void initTab(){
        lEdges = new ArrayList<>();
        lNodes = new ArrayList<>();
        tabDispo = new int[NB_CASES][NB_CASES];
        tabIndex = new Point[NB_CASES][NB_CASES];
        for (int i = 0; i < NB_CASES; i++) {
            for (int j = 0; j < NB_CASES ; j++) {
                if(i == 72 && j==72){
                    tabDispo[i][j]=2;
                }
                else tabDispo[i][j]=0;
            }
        }
        for (int i = 0 ; i < NB_CASES ; i++) {
            for (int j = 0; j < NB_CASES ; j++) {
                tabIndex[i][j]=new Point(i-NB_CASES, j-NB_CASES);
            }
        }
    }

    public Carte pioche(){
        return null;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public void setNbJoueur(int nbJoueur) {
        this.nbJoueur = nbJoueur;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

    public Joueur[] getTabJoueur() {
        return tabJoueur;
    }

    public void setTabJoueur(Joueur[] tabJoueur) {
        this.tabJoueur = tabJoueur;
    }

    public Pioche getP() {
        return p;
    }

    public void setP(Pioche p) {
        this.p = p;
    }
}
