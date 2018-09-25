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


    public Joueur(){}

    public Joueur (int newId){
        this.idJoueur = newId;
    }

    public void joue (){
        carteEnMain = pioche();
    }

    private Carte pioche() {
        //soucis pour l'accès a la pioche
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public String getNom() {
        return this.nom;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public int getPointsPrairie() {
        return pointsPrairie;
    }

    public void setPointsPrairie(int pointsPrairie) {
        this.pointsPrairie = pointsPrairie;
    }

    public int getPointsChemin() {
        return pointsChemin;
    }

    public void setPointsChemin(int pointsChemin) {
        this.pointsChemin = pointsChemin;
    }

    public int getPointsVille() {
        return pointsVille;
    }

    public void setPointsVille(int pointsVille) {
        this.pointsVille = pointsVille;
    }

    public int getPointsAbbaye() {
        return pointsAbbaye;
    }

    public void setPointsAbbaye(int pointsAbbaye) {
        this.pointsAbbaye = pointsAbbaye;
    }
}
