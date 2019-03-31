package Jeu.MultiJoueur.Model;

import Jeu.Exception.PiocheVideException;

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

    private List<SocketJoueur> listSocket;
    private ServerSocket serverSocket;

    private List<Joueur> listJoueur; // List de joueurs
    private List<ThreadReceptionClient> listReceptionClient;

    private boolean isPartieCommencer;

    private boolean isEnvoyer;

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
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("startPartie");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initJeu();
    }

    public void initJeu(){
        numJoueurCourant = (int) (Math.random()*(nbJoueur-1));
        carteCourante = carteDeBase;
        placerCarte(new Point(8,8));
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                int tailleMap = pointCarteMap.size();
                oo.writeInt(tailleMap);
                for (HashMap.Entry<Point, CartePosee> entry : pointCarteMap.entrySet())
                {
                    oo.writeObject(entry.getKey());
                    oo.writeObject(entry.getValue());
                }

                int tailleListDispo = listPointDispo.size();
                oo.writeInt(tailleListDispo);
                for (int j = 0; j < tailleListDispo; j++) {
                    oo.writeObject(listPointDispo.get(j));
                }

                int tailleListOccuper = listPointOccupe.size();
                oo.writeInt(tailleListOccuper);
                for (int j = 0; j < tailleListOccuper; j++) {
                    oo.writeObject(listPointOccupe.get(j));
                }

                int tailleDefausse = defausse.size();
                oo.writeInt(tailleDefausse);
                for (int j = 0; j < tailleDefausse; j++) {
                    oo.writeObject(defausse.get(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        envoieClientsTourSuivant();
    }

    public void placerCarte(Point positionCarte){
        carteCourante.setPosition(positionCarte);
        CartePosee cartePosee = new CartePosee(carteCourante);
        listPointOccupe.add(positionCarte);
        pointCarteMap.put(positionCarte, cartePosee);

        int x = positionCarte.getX();
        int y = positionCarte.getY();

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

        System.out.println("point: " + positionCarte.getX() + ", " + positionCarte.getY() + " dans pointCarte: " + pointCarteMap.containsKey(new Point(positionCarte.getX(), positionCarte.getY())));

        //Dessine l'image sur la fenêtre de jeu
        if(carteCourante!=carteDeBase) {
            //contaminationDeLaCarteAvecCouleur(cartePosee);
        }
    }

    public void envoieCartePlacer(){
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("actualise");
                oo.writeObject("poserCarte");

                int tailleMap = pointCarteMap.size();
                oo.writeInt(tailleMap);
                for (HashMap.Entry<Point, CartePosee> entry : pointCarteMap.entrySet())
                {
                    oo.writeObject(entry.getKey().clone());
                    oo.writeObject(entry.getValue().clone());
                }

                int tailleListDispo = listPointDispo.size();
                oo.writeInt(tailleListDispo);
                for (int j = 0; j < tailleListDispo; j++) {
                    oo.writeObject(listPointDispo.get(j).clone());
                }

                int tailleListOccuper = listPointOccupe.size();
                oo.writeInt(tailleListOccuper);
                for (int j = 0; j < tailleListOccuper; j++) {
                    oo.writeObject(listPointOccupe.get(j).clone());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void testLDispo(Point p){
        if ( !listPointDispo.contains(p) && !listPointOccupe.contains(p)) {
            listPointDispo.add(new Point(p.getX(), p.getY()));
        }
    }

    /*
     * Permet de passer au joueur suivant
     */
    public void joueurSuivant(){
        numJoueurCourant++;
        numJoueurCourant = numJoueurCourant %nbJoueur;
        System.out.println("numJoueur: " + numJoueurCourant );
        envoieClientsTourSuivant();
    }

    public void piocher() {
        try {
            carteCourante =  pioche.piocher();
        } catch (PiocheVideException e) {
            e.printStackTrace();
        }
    }

    private void envoieClientsTourSuivant(){
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
            x = pointDispo.getX();
            y = pointDispo.getY();
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
        for (HashMap.Entry<Point, CartePosee> entry : pointCarteMap.entrySet())
        {
            Point pointCourant = entry.getKey();
            // creer un point temporaire pour faire les verifications
            Point point = new Point(x-1, y);
            if(pointCourant.equals(point)){
                CartePosee c = entry.getValue();
                System.out.println("getOuest carteCourant: " + carteCourante.getOuest());
                System.out.println("getEst c: " + c.getEst());
                if (c.getEst() != carteCourante.getOuest()){
                    isPlacable=false;
                }
            }

            point = new Point(x+1, y);
            if(pointCourant.equals(point)){
                CartePosee c = entry.getValue();
                System.out.println("getEst carteCourant: " + carteCourante.getEst());
                System.out.println("getOuest c: " + c.getOuest());
                if (c.getOuest() != carteCourante.getEst()){
                    isPlacable=false;
                }
            }

            point = new Point(x, y-1);
            if(pointCourant.equals(point)){
                CartePosee c = entry.getValue();
                System.out.println(c);
                System.out.println("getNord carteCourant: " + carteCourante.getNord());
                System.out.println("getSud c: " + c.getSud());
                if (c.getSud() != carteCourante.getNord()){
                    isPlacable=false;
                }
            }

            point = new Point(x, y+1);
            if(pointCourant.equals(point)){
                CartePosee c = entry.getValue();
                System.out.println(c);
                System.out.println("getSud carteCourant: " + carteCourante.getSud());
                System.out.println("getNord c: " + c.getNord());
                if (c.getNord() != carteCourante.getSud()){
                    isPlacable=false;
                }
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

    public void verificationZoneFermee(CartePosee carteEnMain) {

    }

    public int getNB_CASES() {
        return 143;
    }

    public List<Joueur> getListJoueur() { return listJoueur; }

    public List<SocketJoueur> getTabSocket() { return listSocket; }

    public void setTabSocket(ArrayList<SocketJoueur> listSocket) { this.listSocket = listSocket; }

    public List<ThreadReceptionClient> getListReceptionClient() { return listReceptionClient; }

    public Map<Point, CartePosee> getPointCarteMap() { return pointCarteMap; }

    public ArrayList<Point> getListPointDispo() { return listPointDispo; }

    public ArrayList<Point> getListPointOccupe() { return listPointOccupe; }

    public ArrayList<Carte> getDefausse() { return defausse; }

    public Carte getCarteCourante() { return carteCourante; }

    public void setCarteCourante(Carte carteCourante) { this.carteCourante = carteCourante; }
}
