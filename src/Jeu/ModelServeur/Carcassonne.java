package Jeu.ModelServeur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Carcassonne {

    private List<SocketJoueur> listSocket;
    private ServerSocket serverSocket;

    private List<Joueur> listJoueur; // List de joueurs
    private List<ThreadReceptionClient> listReceptionClient;

    private boolean isPartieCommencer;

    public Carcassonne(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        isPartieCommencer = false;

        // Initialisation des listes et maps
        listSocket = new ArrayList<>();
        listJoueur = new ArrayList<>();
        listReceptionClient = new ArrayList<>();

        // Création du thread:
        new ThreadRejoindrePartie(this, serverSocket);

        while(!isPartieCommencer){
            //clientPret();
        }
        debutPartie();
    }

    private void clientPret() {
        boolean isPret = true;
        int cptJoueur = 0;
        System.out.println(listJoueur.size());
        while(isPret || cptJoueur < listJoueur.size()-1 || listJoueur.size() >= 2){
            Joueur joueurTmp = listJoueur.get(cptJoueur);
            isPret = joueurTmp.isPret();
            cptJoueur++;
        }
        if(isPret){
            isPartieCommencer = true;
        }
    }

    private void debutPartie() {
        System.out.println("Partie commencer");
    }

    private void testList(){
        Joueur joueur = new Joueur("toto", "red");
        joueur.setPret(true);
        ajouterJoueurDansPartie(joueur);
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
        listReceptionClient.get(idList).arreter();
        listReceptionClient.remove(idList);
        socketJoueur.quitter();
        for (int i = idList; i < listJoueur.size()-1; i++) {
            int idClientTmp = listReceptionClient.get(i).getIdList();
            listReceptionClient.get(i).setIdList(idClientTmp);
        }
    }

    public void miseAJourJoueur(){
        Socket sock = null;
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
    }

    public void initJeu(){

    }

    /*
     * Demande au joueur suivant de jouer
     */
    public void jouer(){

    }

    /*
     * Permet de passer au joueur suivant
     */
    public void joueurSuivant(){

    }

    /*
     * Permet de savoir si une carte est adjacente a l'endroit où l'on clique
     * return true si c'est le cas, false sinon
     */
    public boolean isCarteAdjacente(int x, int y){
        return false;
    }

    /*
     * Permet de savoir si la carte donnée en paramètre est défaussable ou non
     */
    public boolean isDefaussable(Carte carteAPosee){
        return true;
    }

    /*
     * Permet de savoir si la carte courante peut etre posée où non en fonction de si elle coincide avec les cartes
     * adjacentes ou non
     */
    public boolean isPlacable(int x, int y, Carte carteEnMain) {
        return true;
    }

    /*
     * Permet de contaminer la carte donnée en paramètre avec les couleurs correspondantes
     */
    public void contaminationDeLaCarteAvecCouleur(CartePosee carte) {

    }

    /*
     * Permet d'ajouter une couleur à la map qui contient la couleur en fonction de la zone contaminée
     */
    private void ajoutCouleurMap(CartePosee carteAdjacent, int zoneCarteAdjacente, CartePosee carteCourante, int zoneCarteCourante) {

    }

    /*
     * Permet de contaminer les cartes déjà posées grâce à la carte donnée en paramètre
     */
    public void contaminationDesAutresCarteAvecCouleur(CartePosee carteBase){

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
}
