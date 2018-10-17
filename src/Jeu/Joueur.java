package Jeu;

public class Joueur {
    private String nom;
    private int idJoueur;
    private int pointsTotal;
    private int pointsPrairie;
    private int pointsChemin;
    private int pointsVille;
    private int pointsAbbaye;
    private Carte carteEnMain;
    public static Pioche p;
    //couleur

    public Joueur (int newId, Pioche newP){
        this.idJoueur = newId;
        this.p = newP;
    }

    public void joue(){
        carteEnMain = piocherCarte();
        //poserCarte();
        //poserPartisant();
    }

    private Carte piocherCarte(){
        Carte cartePiocher = p.piocher();
        return cartePiocher;
    }

    private void poserCarte(int x, int y) {
        carteEnMain.placerCarte(x, y);
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    private void poserPartisant() { }

    public Carte getCarteEnMain() {
        return carteEnMain;
    }
}