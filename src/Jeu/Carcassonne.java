package Jeu;

public class Carcassonne {
    private int nbJoueur;
    private int tour;
    private Joueur[] tabJoueur;
    private Pioche p;

    public Carcassonne(){}

    public Carcassonne(int newNbJoueur){
        this.nbJoueur=newNbJoueur;
        tabJoueur = new Joueur[nbJoueur];
        p = new Pioche();
        for (int i = 1; i <= nbJoueur ; i++) {
            tabJoueur[i]= new Joueur(i);
            //ajouter le nom via la vue
        }
        //mise en place du plateau et de la première carte
        tour= (int) Math.random()*nbJoueur;
        while (p.getlCarte().size() > 0){
            for (int i=0 ; i < nbJoueur ; i++){
                if (tabJoueur[i].getIdJoueur()==tour){
                    tabJoueur[i].joue();
                    tour++;
                    tour=tour%nbJoueur;
                }
            }
        }
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public void setNbJoueur(int nbJoueur) {
        this.nbJoueur = nbJoueur;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
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
}
