package Jeu.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carcassonne {

    private final int NB_CARTES = 72;

    private static Pioche p;

    private int nbJoueur; // Nombre de joueur
    private int numJoueur; // Numéro du joueur courant
    private Joueur[] tabJoueur; // Tableau de joueurs

    private Map<Point, CartePosee> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo; // Liste de point où l'on peut ajouter une carte
    private ArrayList<Point> listPointOccupe; // Liste de point où il y a déjà une carte posée
    private ArrayList<Carte> defausse; // Liste de carte où il y a la défausse

    private Carte carteDeBase; // Carte posée au départ (à modifier)

    public Carcassonne(){
        // Initialisation des listes et maps

        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<>();
        listPointOccupe = new ArrayList<>();
        defausse = new ArrayList<>();
        p = new Pioche(NB_CARTES);
        // A MODIFIER
        carteDeBase = new Carte(TypeCarte.carteVVVVBlason);

        //carteDeBase.setPosition(new Point(NB_CASES/2,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2+1,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2-1,NB_CASES/2));
        //listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2+1));
        //listPointDispo.add(new Point(NB_CASES/2,NB_CASES/2-1));
        //pointCarteMap.put(new Point(NB_CASES/2,NB_CASES/2), carteDeBase);

        carteDeBase.setPosition(new Point(8,8));

        // Attributs à modifier en fonction du menu permettant de sélectionner le nombre de joueur et leur nom
    }

    /*
     * Fonction qui permet d'initialiser les joueurs
     */
    public void initialisationJoueurs(String[] nomJoueur, Color[] couleursJoueurs){
        this.nbJoueur=nomJoueur.length;
        tabJoueur = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i+1, p, couleursJoueurs[i]);
            tabJoueur[i].setNom(nomJoueur[i]);
        }
        numJoueur = (int) (Math.random()*(nbJoueur-1))+1;
    }

    /*
     * Demande au joueur suivant de jouer
     */
    public void jouer(){
        for (Joueur aTabJoueur : tabJoueur) {
            if (aTabJoueur.getIdJoueur() == numJoueur) {
                aTabJoueur.joue();
            }
        }
    }

    /*
     * Permet de passer au joueur suivant
     */
    public void joueurSuivant(){
        // C'est très mal fait
        numJoueur++;
        numJoueur = numJoueur %(nbJoueur+1);
        if(numJoueur == 0){
            numJoueur++;
        }
    }

    public boolean isDefaussable(Carte carteAPosee){
        boolean isDefau;
        int x;
        int y;
        for (int i = 0; i < listPointDispo.size(); i++) {
            x = listPointDispo.get(i).x;
            y = listPointDispo.get(i).y;
            for (int j = 0; j <4; j++) {
                isDefau = isPlacable(x, y, carteAPosee);
                if(isDefau) return false;
                carteAPosee.pivoter();
            }
        }
        return true;
    }

    /*
     * Permet de savoir si la carte courante peut etre posée où non en fonction de si elle coincide avec les cartes
     * adjacentes ou non
     */
    private boolean isPlacable(int x, int y, Carte carteEnMain) {
        boolean isPlacable = true;
        // creer un point temporaire pour faire les verifications
        Point point = new Point(x-1, y);
        if(getListPointOccupe().contains(point)){
            CartePosee c = getPointCarteMap().get(point);
            if (c.getEst() != carteEnMain.getOuest()){
                isPlacable=false;
            }
        }

        point = new Point(x+1, y);
        if(getListPointOccupe().contains(point)){
            CartePosee c = getPointCarteMap().get(point);
            if (c.getOuest() != carteEnMain.getEst()){
                isPlacable=false;
            }
        }

        point = new Point(x, y-1);
        if(getListPointOccupe().contains(point)){
            CartePosee c = getPointCarteMap().get(point);
            if (c.getSud() != carteEnMain.getNord()){
                isPlacable=false;
            }
        }

        point = new Point(x, y+1);
        if(getListPointOccupe().contains(point)){
            CartePosee c = getPointCarteMap().get(point);
            if (c.getNord() != carteEnMain.getSud()){
                isPlacable=false;
            }
        }

        return isPlacable;
    }


    public Carte getCarteDeBase() { return carteDeBase; }

    public int getNB_CASES() { return NB_CARTES * 2 - 1; }

    public Joueur[] getTabJoueur() { return tabJoueur; }

    public int getNumJoueur() { return numJoueur; }

    public Pioche getP() { return p; }

    public Map<Point, CartePosee> getPointCarteMap() { return pointCarteMap; }

    public ArrayList<Point> getListPointDispo() { return listPointDispo; }

    public ArrayList<Point> getListPointOccupe() { return listPointOccupe; }

    public ArrayList<Carte> getDefausse() { return defausse; }
}
