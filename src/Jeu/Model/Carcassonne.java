package Jeu.Model;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carcassonne {

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
        p = new Pioche();
        // A MODIFIER
        carteDeBase = new Carte(TypeCarte.carteVCPC);

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
        boolean isDefau = true;
        int x;
        int y;
        for (Point pointDispo : listPointDispo) {
            x = pointDispo.x;
            y = pointDispo.y;
            for (int j = 0; j < 4; j++) {
                isDefau = isPlacable(x, y, carteAPosee);
                carteAPosee.pivoter();
            }
            if (isDefau) return false;
        }
        return true;
    }

    /*
     * Permet de savoir si la carte courante peut etre posée où non en fonction de si elle coincide avec les cartes
     * adjacentes ou non
     */
    public boolean isPlacable(int x, int y, Carte carteEnMain) {
        boolean isPlacable = true;
        // creer un point temporaire pour faire les verifications
        Point point = new Point(x-1, y);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getEst() != carteEnMain.getOuest()){
                isPlacable=false;
            }
        }

        point = new Point(x+1, y);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getOuest() != carteEnMain.getEst()){
                isPlacable=false;
            }
        }

        point = new Point(x, y-1);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getSud() != carteEnMain.getNord()){
                isPlacable=false;
            }
        }

        point = new Point(x, y+1);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getNord() != carteEnMain.getSud()){
                isPlacable=false;
            }
        }

        return isPlacable;
    }

    public void contaminationDeLaCarteAvecCouleur(CartePosee carte) {
        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();

        Point point = new Point(x-1, y);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(4)) carte.getZonesCouleurPartisan().put(12, c.getZonesCouleurPartisan().get(4));
            if (c.getZonesCouleurPartisan().containsKey(5)) carte.getZonesCouleurPartisan().put(11, c.getZonesCouleurPartisan().get(5));
            if (c.getZonesCouleurPartisan().containsKey(6)) carte.getZonesCouleurPartisan().put(10, c.getZonesCouleurPartisan().get(6));
            ajoutCouleurMap(c, 4, carte, 12);
            ajoutCouleurMap(c, 5, carte, 11);
            ajoutCouleurMap(c, 6, carte, 10);
        }

        point = new Point(x+1, y);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(12)) carte.getZonesCouleurPartisan().put(4, c.getZonesCouleurPartisan().get(12));
            if (c.getZonesCouleurPartisan().containsKey(11)) carte.getZonesCouleurPartisan().put(5, c.getZonesCouleurPartisan().get(11));
            if (c.getZonesCouleurPartisan().containsKey(10)) carte.getZonesCouleurPartisan().put(6, c.getZonesCouleurPartisan().get(10));
            ajoutCouleurMap(c, 12, carte, 4);
            ajoutCouleurMap(c, 11, carte, 5);
            ajoutCouleurMap(c, 10, carte, 6);
        }

        point = new Point(x, y+1);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(1)) carte.getZonesCouleurPartisan().put(9, c.getZonesCouleurPartisan().get(1));
            if (c.getZonesCouleurPartisan().containsKey(2)) carte.getZonesCouleurPartisan().put(8, c.getZonesCouleurPartisan().get(2));
            if (c.getZonesCouleurPartisan().containsKey(3)) carte.getZonesCouleurPartisan().put(7, c.getZonesCouleurPartisan().get(3));
            ajoutCouleurMap(c, 1, carte, 9);
            ajoutCouleurMap(c, 2, carte, 8);
            ajoutCouleurMap(c, 3, carte, 7);
        }

        point = new Point(x, y-1);
        if(listPointOccupe.contains(point)){
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(9)) carte.getZonesCouleurPartisan().put(1, c.getZonesCouleurPartisan().get(9));
            if (c.getZonesCouleurPartisan().containsKey(8)) carte.getZonesCouleurPartisan().put(2, c.getZonesCouleurPartisan().get(8));
            if (c.getZonesCouleurPartisan().containsKey(7)) carte.getZonesCouleurPartisan().put(3, c.getZonesCouleurPartisan().get(7));
            ajoutCouleurMap(c, 9, carte, 1);
            ajoutCouleurMap(c, 8, carte, 2);
            ajoutCouleurMap(c, 7, carte, 3);
        }
    }

    private void ajoutCouleurMap(CartePosee carteAdjacent, int zoneCarteAdjacente, CartePosee carteCourante, int zoneCarteCourante) {
        for (int i = 0; i < carteCourante.getZonesControlleesParLesPoints().length; i++) {
            for (int j = 0; j < carteCourante.getZonesControlleesParLesPoints()[i].length; j++) {
                if(carteCourante.getZonesControlleesParLesPoints()[i][j] == zoneCarteCourante && carteAdjacent.getZonesCouleurPartisan().containsKey(zoneCarteAdjacente)){
                    for (int k = 0; k < carteCourante.getZonesControlleesParLesPoints()[i].length; k++) {
                        carteCourante.getZonesCouleurPartisan().putIfAbsent(carteCourante.getZonesControlleesParLesPoints()[i][k], carteAdjacent.getZonesCouleurPartisan().get(zoneCarteAdjacente));
                    }
                }
            }
        }
    }

    public void contaminationDesAutresCarteAvecCouleur(CartePosee carteBase){
        ArrayDeque<CartePosee> carteNonVerifiee = new ArrayDeque<>();
        ArrayList<CartePosee> cartesDejaVerifiees = new ArrayList<>();
        carteNonVerifiee.offer(carteBase);
        int x;
        int y;
        while(!carteNonVerifiee.isEmpty()){
            CartePosee carteCourante = carteNonVerifiee.poll();
            cartesDejaVerifiees.add(carteCourante);
            x = (int) carteCourante.getPosition().getX();
            y = (int) carteCourante.getPosition().getY();
            contaminationDeLaCarteAvecCouleur(carteCourante);

            Point point = new Point(x-1, y);
            if(listPointOccupe.contains(point)){
                CartePosee c = pointCarteMap.get(point);
                if(!cartesDejaVerifiees.contains(c)){
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x+1, y);
            if(listPointOccupe.contains(point)){
                CartePosee c = pointCarteMap.get(point);
                if(!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x, y+1);
            if(listPointOccupe.contains(point)){
                CartePosee c = pointCarteMap.get(point);
                if(!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x, y-1);
            if(listPointOccupe.contains(point)){
                CartePosee c = pointCarteMap.get(point);
                if(!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }
        }
    }

    public Carte getCarteDeBase() { return carteDeBase; }

    public int getNB_CASES() {
        int NB_CARTES = 72;
        return NB_CARTES * 2 - 1; }

    public Joueur[] getTabJoueur() { return tabJoueur; }

    public int getNumJoueur() { return numJoueur; }

    public Pioche getP() { return p; }

    public Map<Point, CartePosee> getPointCarteMap() { return pointCarteMap; }

    public ArrayList<Point> getListPointDispo() { return listPointDispo; }

    public ArrayList<Point> getListPointOccupe() { return listPointOccupe; }

    public ArrayList<Carte> getDefausse() { return defausse; }
}
