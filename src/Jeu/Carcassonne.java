package Jeu;

import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carcassonne {

    private final int NB_CARTES = 10;
    private final int NB_CASES = NB_CARTES*2 -1;

    public static Pioche p;
    public static Fenetre fenetre;
    private GridPane lastGridPane;

    private int nbJoueur;
    private int numJoueur;
    private Joueur[] tabJoueur;

    private int[][] tabIndex; //index avec x, y toutes les cases que l'ont peut prendre sur le plateau
    private Map<Point, Carte> pointCarteMap; // Map qui contient pour chaque Point ca Carte
    private ArrayList<Carte> lNodes; //liste qui contient les noeuds (donc les cartes) et qui peut s'etendre
    private ArrayList<Carte[]> lEdges; //liste qui contient un tableau d'arretes représentées par (carte1 et carte2)


    public Carcassonne(Fenetre fenetre){
        lastGridPane = new GridPane();
//        new ControlButton(fenetre, this);
        initTab();
        p = new Pioche(NB_CARTES);
        this.fenetre = fenetre;
        Carte carteDeBase = new Carte(0, TypeCarte.cartePPPP);
        carteDeBase.setPosition(new Point(NB_CASES/2,NB_CASES/2));

        fenetre.placerCarte(carteDeBase);

        // Cases à côté de la case où se trouve la carte de base
        placerTableCote(NB_CASES/2, NB_CASES/2, carteDeBase);

        attribuerJoueur(4, new String[]{"Lucas", "Maeva", "Theo", "Tugdual"});
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


    public void initTab(){
        pointCarteMap = new HashMap<>();
        lEdges = new ArrayList<>();
        lNodes = new ArrayList<>();
        tabIndex = new int[NB_CASES][NB_CASES];
    }

    public void attribuerJoueur(int nbJoueur, String[] nomJoueur){
        tabJoueur = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i+1, p);
            tabJoueur[i].setNom(nomJoueur[i]);
        }
    }

    public void jouer(){
        numJoueur = (int) Math.random()*nbJoueur;
        int i;
        boolean tourFait;
        while (p.getTaille() > 0){
            i=0;
            tourFait = false;
            while(!tourFait ){
                if (tabJoueur[i].getIdJoueur() == numJoueur){
                    tabJoueur[i].joue();
                    numJoueur++;
                    numJoueur = numJoueur %nbJoueur;
                    tourFait = true;
                }
                i++;
            }
        }
    }

    public void placerTableCote(int x, int y, Carte carte){
        pointCarteMap.put(new Point(x,y), carte);
        lNodes.add(carte);
    }

    public void placerEdgesCarte(Carte carte1, Carte carte2){
        lEdges.add(new Carte[]{carte1, carte2});
    }
}
