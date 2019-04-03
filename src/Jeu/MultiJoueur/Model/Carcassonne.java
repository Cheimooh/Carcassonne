package Jeu.MultiJoueur.Model;

import Jeu.Exception.PiocheVideException;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class Carcassonne {

    private static Pioche pioche;

    private Map<Point, CartePosee> pointCarteMap;
    private ArrayList<Point> listPointDispo;
    private ArrayList<Point> listPointOccupe;
    private ArrayList<Carte> defausse;

    private Carte carteDeBase;
    private int nbJoueur;
    private int numJoueurCourant;
    private Carte carteCourante;
    private CartePosee carteCourantePosee;

    private List<SocketJoueur> listSocket;
    private ServerSocket serverSocket;

    private List<Joueur> listJoueur; // List de joueurs
    private List<ThreadReceptionClient> listReceptionClient;

    private boolean isPartieCommencer;

    private boolean isEnvoyer;

    private List<Point> etendueDuChemin;
    private List<Integer> passageChemin;
    private int[] nbPartisansSurLeChemin;

    public Carcassonne(ServerSocket serverSocket){
        //Instanciation pour le model du jeu
        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<>();
        listPointOccupe = new ArrayList<>();
        defausse = new ArrayList<>();

        pioche = new Pioche();
        carteDeBase = new Carte(TypeCarte.carteVCPC);

        isEnvoyer = true;

        //Instantiation pour le serveur
        this.serverSocket = serverSocket;
        isPartieCommencer = false;

        // Initialisation des listes et maps
        listSocket = new ArrayList<>();
        listJoueur = new ArrayList<>();
        listReceptionClient = new ArrayList<>();

        etendueDuChemin = new ArrayList<>();
        passageChemin = new ArrayList<>();

        // Création du thread:
        ThreadRejoindrePartie rejoindrePartie =  new ThreadRejoindrePartie(this, serverSocket);

        while(!isPartieCommencer){
            clientPret();
        }
        rejoindrePartie.arreter();
        debutPartie();
    }

    private void clientPret() {
        boolean isPret = true;
        int cptJoueur = 0;
        System.out.println(listJoueur.size());
        while(isPret && cptJoueur < listJoueur.size() && listJoueur.size() > 0  ){
            Joueur joueurTmp = listJoueur.get(cptJoueur);
            isPret = joueurTmp.isPret();
            cptJoueur++;
        }
        if(isPret && listJoueur.size() >= 2 && listJoueur.size() <= 5 ){
            isPartieCommencer = true;
            System.out.println("startPartie");
        }
    }

    /*
     * Fonction qui permet d'initialiser les joueurs
     */
    public void ajouterJoueurDansPartie(Joueur joueur){
        listJoueur.add(joueur);
    }

    public void quitterClient(int idList) {
        listJoueur.remove(idList);
        SocketJoueur socketJoueur = listSocket.remove(idList);
        try {
            socketJoueur.getOo().writeObject("quitter");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listReceptionClient.get(idList).arreter();
        listReceptionClient.remove(idList);
        socketJoueur.quitter();
        for (int i = idList; i < listJoueur.size(); i++) {
            int idClientTmp = listReceptionClient.get(i).getIdList();
            listReceptionClient.get(i).setIdList(idClientTmp-1);
        }
    }

    public void miseAJourJoueur(){
        Socket sock = null;
        isEnvoyer = false;
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("j'envoie");

                int nbJoueur = getListJoueur().size();
                oo.writeInt(nbJoueur);

                for (int j = 0; j < nbJoueur; j++) {
                    Joueur joueurTmp = getListJoueur().get(j);
                    oo.writeObject(joueurTmp.clone());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        isEnvoyer = true;
    }

    private void debutPartie() {
        isPartieCommencer = true;
        nbJoueur = listJoueur.size();
        try {
            while(!isEnvoyer){
                continue;
            }
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("startPartie");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initJeu();
    }

    public void initJeu(){
        nbPartisansSurLeChemin = new int[nbJoueur];
        numJoueurCourant = (int) (Math.random()*(nbJoueur-1))+1;
        carteCourante = carteDeBase;
        placerCarte(new Point(8,8));
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();

                int tailleDefausse = defausse.size();
                oo.writeInt(tailleDefausse);
                for (int j = 0; j < tailleDefausse; j++) {
                    oo.writeObject(defausse.get(j).clone());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        envoieClientsTourSuivant();
    }

    public void placerCarte(Point positionCarte){
        carteCourante.setPosition(positionCarte);
        carteCourantePosee = new CartePosee(carteCourante);
        listPointOccupe.add(positionCarte);
        pointCarteMap.put(positionCarte, carteCourantePosee);

        int x = (int) positionCarte.getX();
        int y = (int) positionCarte.getY();

        //Permet de tester si l'on doit rajouter des emplacements disponibles ou non
        Point p = new Point(x+1,y);
        testLDispo(p);
        p.setLocation(x-1,y);
        testLDispo(p);
        p.setLocation(x,y+1);
        testLDispo(p);
        p.setLocation(x,y-1);
        testLDispo(p);

        //Supression de l'emplacement de la carte dans la liste des emplacements disponibles
        listPointDispo.remove(positionCarte);
        verificationCheminFerme(carteCourantePosee);

        //Dessine l'image sur la fenêtre de jeu
        if(carteCourante!=carteDeBase) {
            contaminationDeLaCarteAvecCouleur(carteCourantePosee);
        }
        envoieCartePlacer(positionCarte, carteCourantePosee.getImageCarte());
    }

    public void envoieCartePlacer(Point pointCarte, String image){
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("actualise");
                oo.writeObject("poserCarte");

                oo.writeObject(image);
                oo.writeObject(pointCarte);

                int tailleListDispo = listPointDispo.size();
                oo.writeInt(tailleListDispo);
                for (int j = 0; j < tailleListDispo; j++) {
                    oo.writeObject(listPointDispo.get(j).clone());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testLDispo(Point p){
        if ( !listPointDispo.contains(p) && !listPointOccupe.contains(p)) {
            listPointDispo.add(new Point((int) p.getX(), (int) p.getY()));
        }
    }

    /*
     * Permet de passer au joueur suivant
     */
    public void joueurSuivant(){
        numJoueurCourant++;
        numJoueurCourant = numJoueurCourant %nbJoueur;
        envoieClientsTourSuivant();
    }

    public void piocher() {
        try {
            carteCourante =  pioche.piocher();
        } catch (PiocheVideException e) {
            e.printStackTrace();
        }
    }

    public void envoiePartisantAjouter(String couleur, Point pointCarte, Point pointPartisant){
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();
                oo.writeObject("actualise");
                oo.writeObject("partisan");
                oo.writeObject(couleur);
                oo.writeObject(pointCarte.clone());
                oo.writeObject(pointPartisant.clone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void envoieClientsTourSuivant(){
        if (pioche.getTaille()>0){

            piocher();
            Joueur joueurCourant = listJoueur.get(numJoueurCourant);
            try {
                for (int i = 0; i < listSocket.size(); i++) {
                    ObjectOutputStream oo = listSocket.get(i).getOo();
                    oo.writeObject("actualise");
                    oo.writeObject("tourSuivant");
                    oo.writeObject(joueurCourant.clone());
                    oo.writeObject(carteCourante.clone());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }else {
            finDePartie();
        }
    }

    private void finDePartie() {
        List<Integer> tabPoints = new ArrayList<>();
        for (int i = 0; i < listJoueur.size(); i++) {
            tabPoints.add(listJoueur.get(i).getPointsTotal());
        }

        Collections.sort(tabPoints);
        Collections.reverse(tabPoints);

        List<Joueur> joueurs = listJoueur;
        List<Joueur> tabJoueursTries = new ArrayList<>();
        for (Integer point : tabPoints) {
            for (Joueur joueur : joueurs) {
                if (joueur.getPointsTotal() == point && !tabJoueursTries.contains(joueur)) {
                    tabJoueursTries.add(joueur);
                }
            }
        }
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();
                oo.writeObject("finPartie");
                oo.writeInt(listJoueur.size());
                for (int j = 0; j < listJoueur.size(); j++) {
                    System.out.println(tabJoueursTries.get(j).getNom() + " => " + tabJoueursTries.get(j).getPointsTotal());
                    oo.writeObject(tabJoueursTries.get(j).clone());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listSocket.size(); i++) {
            listSocket.get(i).quitter();
            listReceptionClient.get(i).arreter();
        }
        Appli.lancer();
    }

    /*
     * Permet de savoir si une carte est adjacente a l'endroit où l'on clique
     * return true si c'est le cas, false sinon
     */
    public boolean isCarteAdjacente(int x, int y){
        Point point = new Point(x+1, y);
        if(listPointOccupe.contains(point)) return true;

        point = new Point(x-1, y);
        if(listPointOccupe.contains(point)) return true;

        point = new Point(x, y+1);
        if(listPointOccupe.contains(point)) return true;

        point = new Point(x, y-1);
        return listPointOccupe.contains(point);
    }

    /*
     * Permet de savoir si la carte donnée en paramètre est défaussable ou non
     */
    public boolean isDefaussable(){
        boolean isDefau = true;
        int x;
        int y;
        for (Point pointDispo : listPointDispo) {
            x = (int) pointDispo.getX();
            y = (int) pointDispo.getY();
            for (int j = 0; j < 4; j++) {
                isDefau = isPlacable(x, y);
                carteCourante.pivoter();
            }
            if (isDefau) return false;
        }
        return true;
    }

    public void defausser(){
        defausse.add(carteCourante);
        piocher();
        envoieDefausse();
    }

    public void envoieDefausse() {
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("actualise");
                oo.writeObject("defausse");

                int tailleDefausse = defausse.size();
                oo.writeInt(tailleDefausse);
                for (int j = 0; j < tailleDefausse; j++) {
                    oo.writeObject(defausse.get(j).clone());
                }

                oo.writeObject(carteCourante.clone());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Permet de savoir si la carte courante peut etre posée où non en fonction de si elle coincide avec les cartes
     * adjacentes ou non
     */
    public boolean isPlacable(int x, int y) {
        boolean isPlacable = true;
        // creer un point temporaire pour faire les verifications
        Point point = new Point(x - 1, y);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getEst() != carteCourante.getOuest()) {
                isPlacable = false;
            }
        }

        point = new Point(x + 1, y);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getOuest() != carteCourante.getEst()) {
                isPlacable = false;
            }
        }

        point = new Point(x, y - 1);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getSud() != carteCourante.getNord()) {
                isPlacable = false;
            }
        }

        point = new Point(x, y + 1);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getNord() != carteCourante.getSud()) {
                isPlacable = false;
            }
        }

        return isPlacable;
    }

    /*
     * Permet de contaminer la carte donnée en paramètre avec les couleurs correspondantes
     */
    public void contaminationDeLaCarteAvecCouleur(CartePosee carte) {
        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();

        Point point = new Point(x - 1, y);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(4))
                carte.getZonesCouleurPartisan().put(12, c.getZonesCouleurPartisan().get(4));
            if (c.getZonesCouleurPartisan().containsKey(5))
                carte.getZonesCouleurPartisan().put(11, c.getZonesCouleurPartisan().get(5));
            if (c.getZonesCouleurPartisan().containsKey(6))
                carte.getZonesCouleurPartisan().put(10, c.getZonesCouleurPartisan().get(6));
            ajoutCouleurMap(c, 4, carte, 12);
            ajoutCouleurMap(c, 5, carte, 11);
            ajoutCouleurMap(c, 6, carte, 10);
        }

        point = new Point(x + 1, y);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(12))
                carte.getZonesCouleurPartisan().put(4, c.getZonesCouleurPartisan().get(12));
            if (c.getZonesCouleurPartisan().containsKey(11))
                carte.getZonesCouleurPartisan().put(5, c.getZonesCouleurPartisan().get(11));
            if (c.getZonesCouleurPartisan().containsKey(10))
                carte.getZonesCouleurPartisan().put(6, c.getZonesCouleurPartisan().get(10));
            ajoutCouleurMap(c, 12, carte, 4);
            ajoutCouleurMap(c, 11, carte, 5);
            ajoutCouleurMap(c, 10, carte, 6);
        }

        point = new Point(x, y + 1);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(1))
                carte.getZonesCouleurPartisan().put(9, c.getZonesCouleurPartisan().get(1));
            if (c.getZonesCouleurPartisan().containsKey(2))
                carte.getZonesCouleurPartisan().put(8, c.getZonesCouleurPartisan().get(2));
            if (c.getZonesCouleurPartisan().containsKey(3))
                carte.getZonesCouleurPartisan().put(7, c.getZonesCouleurPartisan().get(3));
            ajoutCouleurMap(c, 1, carte, 9);
            ajoutCouleurMap(c, 2, carte, 8);
            ajoutCouleurMap(c, 3, carte, 7);
        }

        point = new Point(x, y - 1);
        if (listPointOccupe.contains(point)) {
            CartePosee c = pointCarteMap.get(point);
            if (c.getZonesCouleurPartisan().containsKey(9))
                carte.getZonesCouleurPartisan().put(1, c.getZonesCouleurPartisan().get(9));
            if (c.getZonesCouleurPartisan().containsKey(8))
                carte.getZonesCouleurPartisan().put(2, c.getZonesCouleurPartisan().get(8));
            if (c.getZonesCouleurPartisan().containsKey(7))
                carte.getZonesCouleurPartisan().put(3, c.getZonesCouleurPartisan().get(7));
            ajoutCouleurMap(c, 9, carte, 1);
            ajoutCouleurMap(c, 8, carte, 2);
            ajoutCouleurMap(c, 7, carte, 3);
        }
    }

    /*
     * Permet d'ajouter une couleur à la map qui contient la couleur en fonction de la zone contaminée
     */
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

    /*
     * Permet de contaminer les cartes déjà posées grâce à la carte donnée en paramètre
     */
    public void contaminationDesAutresCarteAvecCouleur(){
        ArrayDeque<CartePosee> carteNonVerifiee = new ArrayDeque<>();
        ArrayList<CartePosee> cartesDejaVerifiees = new ArrayList<>();
        carteNonVerifiee.offer(new CartePosee(carteDeBase));
        int x;
        int y;
        while (!carteNonVerifiee.isEmpty()) {
            CartePosee carteCourante = carteNonVerifiee.poll();
            cartesDejaVerifiees.add(carteCourante);
            x = (int) carteCourante.getPosition().getX();
            y = (int) carteCourante.getPosition().getY();
            contaminationDeLaCarteAvecCouleur(carteCourante);

            Point point = new Point(x - 1, y);
            if (listPointOccupe.contains(point)) {
                CartePosee c = pointCarteMap.get(point);
                if (!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x + 1, y);
            if (listPointOccupe.contains(point)) {
                CartePosee c = pointCarteMap.get(point);
                if (!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x, y + 1);
            if (listPointOccupe.contains(point)) {
                CartePosee c = pointCarteMap.get(point);
                if (!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }

            point = new Point(x, y - 1);
            if (listPointOccupe.contains(point)) {
                CartePosee c = pointCarteMap.get(point);
                if (!cartesDejaVerifiees.contains(c)) {
                    carteNonVerifiee.addFirst(c);
                }
            }
        }
    }

    /*
     * Permet d'ajouter un partisan sur la zone où l'on clique et de rajouter la couleur en fonction du joueur
     */
    public void placerPartisan(int numZone) {
        if (getJoueurCourant().getNombrePartisansRestants()>0) {
            carteCourantePosee.addZonesOccupees(numZone, getJoueurCourant().getCouleur());
            Partisan p = getJoueurCourant().placerPartisan(carteCourantePosee ,numZone);
            carteCourantePosee.attributionPartisan(p, numZone);
            contaminationDesAutresCarteAvecCouleur();
            verificationCheminFerme(carteCourantePosee);
            Point pointCarte = carteCourantePosee.getPosition();
            Point pointPartisan = carteCourantePosee.getPositionsCoordonnees().get(numZone);
            envoiePartisantAjouter(p.getJoueur().getCouleur(), pointCarte, pointPartisan);
            joueurSuivant();
        } else {
            envoieErreur(getJoueurCourant().getNom()+" n'a plus de partisans !","Placement de partisans");
        }
    }

    private void envoieErreur(String erreur, String titre) {
        ObjectOutputStream oo = listSocket.get(numJoueurCourant).getOo();
        try{
            oo.writeObject("erreur");
            oo.writeObject(titre);
            oo.writeObject(erreur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCheminFerme(int numCote, CartePosee carte) {
        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();
        int numListe = getNombreChemin(carte, numCote)[0];
        int longueurListe = getNombreChemin(carte, numCote)[1];
        etendueDuChemin.add(carte.getPosition());
        if (longueurListe == 2) {
            passageChemin.add(numCote);
            int newI = 0;
            for (int j = 0; j < longueurListe; j++) {
                if (carte.getZonesControlleesParLesPoints()[numListe][j] != numCote) {
                    newI = carte.getZonesControlleesParLesPoints()[numListe][j];
                }
            }
            if (newI == 2) {
                Point p = new Point(x, y - 1);
                if (listPointOccupe.contains(p)) {
                    return isCheminFerme(8, pointCarteMap.get(p));
                } else return false;
            } else if (newI == 5) {
                Point p = new Point(x + 1, y);
                if (listPointOccupe.contains(p)) {
                    return isCheminFerme(11, pointCarteMap.get(p));
                } else return false;
            } else if (newI == 8) {
                Point p = new Point(x, y + 1);
                if (listPointOccupe.contains(p)) {
                    return isCheminFerme(2, pointCarteMap.get(p));
                } else return false;
            } else if (newI == 11) {
                Point p = new Point(x - 1, y);
                if (listPointOccupe.contains(p)) {
                    return isCheminFerme(5, pointCarteMap.get(p));
                } else return false;
            }
        }
        return true;
    }

    /*
     * Verficication d'un chemin fermé a partir de la carte qu'on vient de poser
     */
    public void verificationCheminFerme(CartePosee carteEnMain) {
        etendueDuChemin.clear();
        passageChemin.clear();
        etendueDuChemin.add(carteEnMain.getPosition());

        int x = (int) carteEnMain.getPosition().getX();
        int y = (int) carteEnMain.getPosition().getY();


        Point p = new Point(x, y - 1);
        if (listPointOccupe.contains(p)) {
            if (carteEnMain.getNord().equals(CoteCarte.chemin)) {
                for (int i = 0; i < nbPartisansSurLeChemin.length; i++) {
                    nbPartisansSurLeChemin[i] = 0;
                }
                boolean cheminFermeSurLaCarteCourante = false;
                int longueurListe = getNombreChemin(carteEnMain, 2)[1];
                if (longueurListe == 1) cheminFermeSurLaCarteCourante = true;
                if (isCheminFerme(8, pointCarteMap.get(p))) {
                    passageChemin.add(8);
                    if (cheminFermeSurLaCarteCourante) {
                        attributionPointsChemin();
                    }
                }
            }
        }
        p = new Point(x, y + 1);
        if (listPointOccupe.contains(p)) {
            if (carteEnMain.getSud().equals(CoteCarte.chemin)) {
                for (int i = 0; i < nbPartisansSurLeChemin.length; i++) {
                    nbPartisansSurLeChemin[i] = 0;
                }
                boolean cheminFermeSurLaCarteCourante = false;
                int longueurListe = getNombreChemin(carteEnMain, 8)[1];
                if (longueurListe == 1) cheminFermeSurLaCarteCourante = true;
                if (isCheminFerme(2, pointCarteMap.get(p))) {
                    passageChemin.add(2);
                    if (cheminFermeSurLaCarteCourante) {
                        attributionPointsChemin();
                    }
                }
            }
        }
        p = new Point(x - 1, y);
        if (listPointOccupe.contains(p)) {
            if (carteEnMain.getOuest().equals(CoteCarte.chemin)) {
                for (int i = 0; i < nbPartisansSurLeChemin.length; i++) {
                    nbPartisansSurLeChemin[i] = 0;
                }
                boolean cheminFermeSurLaCarteCourante = false;
                int longueurListe = getNombreChemin(carteEnMain, 11)[1];
                if (longueurListe == 1) cheminFermeSurLaCarteCourante = true;
                if (isCheminFerme(5, pointCarteMap.get(p))) {
                    passageChemin.add(5);
                    if (cheminFermeSurLaCarteCourante) {
                        attributionPointsChemin();
                    }
                }
            }
        }
        p = new Point(x + 1, y);
        if (listPointOccupe.contains(p)) {
            if (carteEnMain.getEst().equals(CoteCarte.chemin)) {
                for (int i = 0; i < nbPartisansSurLeChemin.length; i++) {
                    nbPartisansSurLeChemin[i] = 0;
                }
                boolean cheminFermeSurLaCarteCourante = false;
                int longueurListe = getNombreChemin(carteEnMain, 5)[1];
                if (longueurListe == 1) cheminFermeSurLaCarteCourante = true;
                if (isCheminFerme(11, pointCarteMap.get(p))) {
                    passageChemin.add(11);
                    if (cheminFermeSurLaCarteCourante) {
                        attributionPointsChemin();
                    }
                }
            }
        }
    }

    private void attributionPointsChemin() {
        int[] tabNbPartisans = new int[nbJoueur];
        List<Partisan> tabPartisansSurLeChemin = new ArrayList<>();
        for (int i = 0; i < etendueDuChemin.size(); i++) {
            CartePosee carte = pointCarteMap.get(etendueDuChemin.get(i));
            Partisan p = null;
            for (int j = 0; j < carte.getZonesControlleesParLesPartisans().length; j++) {
                if (carte.getZonesControlleesParLesPartisans()[j] != null) {
                    p = carte.getZonesControlleesParLesPartisans()[j];
                }
            }
            if (p != null) {
                boolean cheminDejaPritEnCompte = false;
                if(p.getNumZone() != -1) {
                    int[] tabZones = carte.getZonesControlleesParLesPoints()[p.getNumZone()];
                    if (carte.getNord() == CoteCarte.chemin) {
                        for (int j = 0; j < tabZones.length; j++) {
                            if (tabZones[j] == 2) {
                                if (tabZones.length == 1) {
                                    Point point = new Point((int) carte.getPosition().getX(), (int) carte.getPosition().getY() - 1);
                                    if (pointCarteMap.containsKey(point)) {
                                        if (etendueDuChemin.contains(point)) {
                                            tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                            tabPartisansSurLeChemin.add(p);
                                        }
                                    }
                                } else {
                                    cheminDejaPritEnCompte = true;
                                    tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                    tabPartisansSurLeChemin.add(p);
                                }
                            }
                        }
                    }
                    if (carte.getEst() == CoteCarte.chemin) {
                        for (int j = 0; j < tabZones.length; j++) {
                            if (tabZones[j] == 5) {
                                if (tabZones.length == 1) {
                                    Point point = new Point((int) carte.getPosition().getX() + 1, (int) carte.getPosition().getY());
                                    if (pointCarteMap.containsKey(point)) {
                                        if (etendueDuChemin.contains(point)) {
                                            tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                            tabPartisansSurLeChemin.add(p);
                                        }
                                    }
                                } else {
                                    if (!cheminDejaPritEnCompte) {
                                        tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                        cheminDejaPritEnCompte = true;
                                        tabPartisansSurLeChemin.add(p);
                                    }
                                }
                            }
                        }
                    }
                    if (carte.getSud() == CoteCarte.chemin) {
                        for (int j = 0; j < tabZones.length; j++) {
                            if (tabZones[j] == 8) {
                                if (tabZones.length == 1) {
                                    Point point = new Point((int) carte.getPosition().getX(), (int) carte.getPosition().getY() + 1);
                                    if (pointCarteMap.containsKey(point)) {
                                        if (etendueDuChemin.contains(point)) {
                                            tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                            tabPartisansSurLeChemin.add(p);
                                        }
                                    }
                                } else {
                                    if (!cheminDejaPritEnCompte) {
                                        tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                        cheminDejaPritEnCompte = true;
                                        tabPartisansSurLeChemin.add(p);
                                    }
                                }
                            }
                        }
                    }
                    if (carte.getOuest() == CoteCarte.chemin) {
                        for (int j = 0; j < tabZones.length; j++) {
                            if (tabZones[j] == 11) {
                                if (tabZones.length == 1) {
                                    Point point = new Point((int) carte.getPosition().getX() - 1, (int) carte.getPosition().getY());
                                    if (pointCarteMap.containsKey(point)) {
                                        if (etendueDuChemin.contains(point)) {
                                            tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                            tabPartisansSurLeChemin.add(p);
                                        }
                                    }
                                } else {
                                    if (!cheminDejaPritEnCompte) {
                                        tabNbPartisans[p.getJoueur().getIdJoueur()]++;
                                        cheminDejaPritEnCompte = true;
                                        tabPartisansSurLeChemin.add(p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Integer> joueursGagnants = new ArrayList<>();
        int maxPartisan = -1;
        for (int i = 0; i < tabNbPartisans.length; i++) {
            if (tabNbPartisans[i] > maxPartisan) {
                joueursGagnants.clear();
                joueursGagnants.add(i);
                maxPartisan = tabNbPartisans[i];
            } else if (tabNbPartisans[i] == maxPartisan) {
                joueursGagnants.add(i);
            }
        }
        if (maxPartisan > 0) {
            for (Integer idJoueur : joueursGagnants) {
                listJoueur.get(idJoueur).addPointsChemin(etendueDuChemin.size());
                System.out.println("Score: " + listJoueur.get(idJoueur).getPointsTotal());
            }
        }
        List<Partisan> partisans = new ArrayList<>();
        for (int i = 0; i < tabPartisansSurLeChemin.size(); i++) {
            if (!partisans.contains(tabPartisansSurLeChemin.get(i))){
                partisans.add(tabPartisansSurLeChemin.get(i));
            }
        }

        for (int i = 0; i < partisans.size(); i++) {
            CartePosee cartePosee = pointCarteMap.get(partisans.get(i).getPointPlacementCarte());
            Joueur j = partisans.get(i).getJoueur();
            j.addPartisanRestant();
            partisans.get(i).retirerPartisan(cartePosee);
            redrawCarte(cartePosee, cartePosee.getPosition(), j.getCouleur(), j.getPointsTotal());
        }
    }

    private void redrawCarte(CartePosee cartePosee, Point position, String color, int score) {
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("actualise");
                oo.writeObject("reDraw");

                oo.writeObject(cartePosee.clone());
                oo.writeObject(position.clone());
                oo.writeObject(color);
                oo.writeInt(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Permet de récupérer le numéro et la longueur de la listeControlleesParLesPoints de la carte contenant numCote
     */
    private int[] getNombreChemin(CartePosee carte, int numCote) {
        int numListe = 0;
        for (int j = 0; j < carte.getZonesControlleesParLesPoints().length; j++) {
            for (int k = 0; k < carte.getZonesControlleesParLesPoints()[j].length; k++) {
                if (carte.getZonesControlleesParLesPoints()[j][k] == numCote) {
                    numListe = j;
                }
            }
        }
        int longueurListe = carte.getZonesControlleesParLesPoints()[numListe].length;
        return new int[]{numListe, longueurListe};
    }

    public List<Joueur> getListJoueur() { return listJoueur; }

    public List<SocketJoueur> getTabSocket() { return listSocket; }

    public List<ThreadReceptionClient> getListReceptionClient() { return listReceptionClient; }

    public ArrayList<Point> getListPointOccupe() { return listPointOccupe; }

    public Carte getCarteCourante() { return carteCourante; }

    public void setCarteCourante(Carte carteCourante) { this.carteCourante = carteCourante; }

    public CartePosee getCarteCourantePosee() { return carteCourantePosee; }

    public Joueur getJoueurCourant() { return listJoueur.get(numJoueurCourant); }
}
