package Jeu;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carcassonne {

    private final int NB_CARTES = 10;
    private final int NB_CASES = NB_CARTES*2 -1;

    public static Pioche p;

    private int nbJoueur; // nombre de joueur
    private int numJoueur;
    private Joueur[] tabJoueur;

    private Map<Point, Carte> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo;

    private Carte carteDeBase;

    private boolean aJouer = false;

    public Carcassonne(){
        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<>();
        p = new Pioche(NB_CARTES);
        carteDeBase = new Carte(TypeCarte.cartePPPP);
        carteDeBase.setPosition(new Point(NB_CASES/2,NB_CASES/2));
        listPointDispo.add(new Point(NB_CASES/2+1,NB_CASES/2));
        listPointDispo.add(new Point(NB_CASES/2-1,NB_CASES/2));
        listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2+1));
        listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2-1));

        // Cases à côté de la case où se trouve la carte de base
        pointCarteMap.put(new Point(NB_CASES/2,NB_CASES/2), carteDeBase);

        attribuerJoueur(4, new String[]{"Lucas", "Maeva", "Theo", "Tugdual"});
    }

    public void attribuerJoueur(int newNbJoueur, String[] nomJoueur){
        nbJoueur=newNbJoueur;
        tabJoueur = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i+1, p);
            tabJoueur[i].setNom(nomJoueur[i]);
        }
    }

    public void jouer(){
        numJoueur = (int) (Math.random()*(nbJoueur-1))+1;
        int i;
        boolean tourFait;
        while (p.getTaille() > 0){
            i=0;
            tourFait = false;
            while(!tourFait || i<tabJoueur.length){
                if (tabJoueur[i].getIdJoueur() == numJoueur){
                    tabJoueur[i].joue();
                    while(!aJouer){
                        System.out.println("test dans while Jouer");
                        continue;
                    }
                    aJouer = false;
                    numJoueur++;
                    numJoueur = numJoueur %nbJoueur;
                    tourFait = true;
                }
                i++;
            }
        }
    }

    public Carte getCarteDeBase() { return carteDeBase; }

    public int getNB_CASES() { return NB_CASES; }

    public void setaJouer(boolean aJouer) {
        this.aJouer = aJouer;
    }
}
