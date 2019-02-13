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

    private List<Joueur> tabJoueur; // List de joueurs

    public Carcassonne(ServerSocket serverSocket){
        this.serverSocket = serverSocket;

        // Initialisation des listes et maps
        listSocket = new ArrayList<>();
        tabJoueur = new ArrayList<>();

        testList();
        // Création du thread:
        new ThreadRejoindrePartie(this, serverSocket);
    }

    private void testList(){
        ajouterJoueurDansPartie(new Joueur("lucas", "red"));
        ajouterJoueurDansPartie(new Joueur("Maeva", "blue"));
    }

    /*
     * Fonction qui permet d'initialiser les joueurs
     */
    public void ajouterJoueurDansPartie(Joueur joueur){
        tabJoueur.add(joueur);
    }

    public void miseAJourJoueur(){
        Socket sock = null;
        try {
            for (int i = 0; i < listSocket.size(); i++) {
                ObjectInputStream oi = listSocket.get(i).getOi();
                ObjectOutputStream oo = listSocket.get(i).getOo();

                oo.writeObject("j'envoie");

                int nbJoueur = getTabJoueur().size();
                oo.writeInt(nbJoueur);

                for (int j = 0; j < nbJoueur; j++) {
                    System.out.println("N°" + j);
                    Joueur joueurTmp = getTabJoueur().get(j);
                    oo.writeObject(joueurTmp);
                }
            }
        } catch (IOException e) {
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

    public List<Joueur> getTabJoueur() { return tabJoueur; }

    public List<SocketJoueur> getTabSocket() { return listSocket; }

    public void setTabSocket(ArrayList<SocketJoueur> listSocket) { this.listSocket = listSocket; }
}
