package Jeu.View;

import Jeu.Controller.ControlMouseInfos;
import Jeu.Exception.PiocheVideException;
import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import Jeu.Model.Joueur;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BarreInfos {
    private Carcassonne carcassonne;
    private GraphicsContext graphicsContextInfos;
    private Canvas canvasInfos;
    private ControlMouseInfos controlMouseInfos;

    private int width; // largeur de la fenêtre
    private int height; // hauteur de la fenêtre

    private int[] tabDefausseCarte;
    // {position x, position y, largeur, hauteur}

    public BarreInfos(int width, int height, FenetreJeu f) {
        this.width = width;
        this.height = height;
        tabDefausseCarte = new int[]{750, 35, 180, 30};
        carcassonne = f.getCarcassonne();
        canvasInfos = new Canvas(width, height);
        controlMouseInfos = new ControlMouseInfos(this, f.getControlMouse(), tabDefausseCarte);
        canvasInfos.setOnMouseClicked(controlMouseInfos);
        graphicsContextInfos = canvasInfos.getGraphicsContext2D();
        graphicsContextInfos.setStroke(Color.color(0.2, 0.2, 0.2));
    }

    /*
     * Dessine la barre d'canvasInfos lorsque le joueur doit poser une carte
     */
    public void drawInformationsCarte(Image prochaineCarte) {
        controlMouseInfos.setMode(0);
        graphicsContextInfos.clearRect(0, 0, width, 100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();

        String s;

        graphicsContextInfos.drawImage(prochaineCarte, (width / 2.) - 50, 30, 50, 50);

        int numJoueur = carcassonne.getNumJoueur();
        s = "Joueur " + numJoueur;
        s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

        String defausse = "Defausser ma carte";
        String voirDefausse = "Défausse";

        graphicsContextInfos.setFill(Color.color(0.98, 0.694, 0.627));

        //Affichage du "bouton" pour voir la défausse
        drawBouton(voirDefausse, width / 7., 35, 100, 30);
        //Affichage du "bouton" pour défausser une carte
        drawBouton(defausse, tabDefausseCarte[0], tabDefausseCarte[1], tabDefausseCarte[2], tabDefausseCarte[3]);

        graphicsContextInfos.strokeText(s, (width / 2.) - 60, 15);

        int nbPartisans = carcassonne.getTabJoueur()[numJoueur - 1].getNombrePartisansRestants();
        Color color = carcassonne.getTabJoueur()[numJoueur - 1].getColor();
        int nbPoints = carcassonne.getTabJoueur()[numJoueur - 1].getPointsTotal();
        String stringPts = nbPoints+" points";

        if (nbPartisans > 0) {
            graphicsContextInfos.setFill(color);
            graphicsContextInfos.fillOval(width / 2. + 50, 25, 50, 50);
            graphicsContextInfos.setFill(Color.BLACK);
            graphicsContextInfos.strokeText("x " + nbPartisans, width / 2. + 100, 75);
        } else {
            graphicsContextInfos.setFill(Color.BLACK);
            graphicsContextInfos.fillOval(width / 2. + 50, 25, 50, 50);
        }
        graphicsContextInfos.strokeText(stringPts, width/2.+50, 15);
    }

    public void afficherFinDuJeu() {
        graphicsContextInfos.clearRect(0, 0, width, 100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();
        String s = "Fin de partie";

        String voirDefausse = "Défausse";
        graphicsContextInfos.setFill(Color.color(0.98, 0.694, 0.627));
        //Affichage du "bouton" pour voir la défausse
        drawBouton(voirDefausse, width / 7., 35, 100, 30);

        graphicsContextInfos.strokeText(s, (width / 2.), 15);
        controlMouseInfos.setMode(2);

        fenetreFinDuJeu();
    }

    private void fenetreFinDuJeu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Résultats");
        alert.setHeight(300);
        alert.setWidth(300);
        String[] affichageFinal = new String[carcassonne.getTabJoueur().length];

        Joueur[] tabJoueursTriés = new Joueur[carcassonne.getTabJoueur().length];

        for (int i = 0; i < carcassonne.getTabJoueur().length; i++) {
            Joueur tempJ = carcassonne.getTabJoueur()[0];
            for (int j = 0; j < carcassonne.getTabJoueur().length; j++) {
                Joueur joueur = carcassonne.getTabJoueur()[i];
                if (joueur.getPointsTotal() > tempJ.getPointsTotal() && isJoueurNonTrie(joueur, tabJoueursTriés)){
                    tempJ = joueur;
                }
            }
            tabJoueursTriés[i]=tempJ;
        }

        for (int i = 0; i < (tabJoueursTriés.length); i++) {
            Joueur j = tabJoueursTriés[i];
                affichageFinal[i] = i+1 + " : " + j.getNom() + " avec -> " + j.getPointsTotal() + " Points" + "\n";
        }
    }

    //fonction qui regarde si un joueur est déja dans le tableau qui trie les joueur en fonction de leur points
    //return true si le joueur n'est pas encore trié
    private boolean isJoueurNonTrie(Joueur joueur, Joueur[] tabJoueurTrie) {
        for (int i = 0; i < tabJoueurTrie.length ; i++) {
            if (joueur == tabJoueurTrie[i]){
                return false;
            }
        }
        return true;
    }

    private void drawBouton(String texte, double x, int y, int largeur, int hauteur) {
        graphicsContextInfos.fillRoundRect(x, y, largeur, hauteur, 20, 20);
        graphicsContextInfos.strokeText(texte, x + 20, y + 17);
    }

    /*
     * Dessine la barre d'canvasInfos lorsque le joueur doit poser un partisan
     */
    public void drawInformationsPartisans() {
        controlMouseInfos.setMode(1);
        graphicsContextInfos.clearRect(0, 0, width, 100);
        drawLigneSeparatrice();

        String s;

        int numJoueur = carcassonne.getNumJoueur();
        s = "Joueur " + numJoueur;
        s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

        int nbPartisans = carcassonne.getTabJoueur()[numJoueur - 1].getNombrePartisansRestants();
        Color color = carcassonne.getTabJoueur()[numJoueur - 1].getColor();
        int nbPoints = carcassonne.getTabJoueur()[numJoueur - 1].getPointsTotal();
        String stringPts = nbPoints+" points";

        String voirDefausse = "Défausse";
        String poserPartisan = "Poser un partisan";
        String passerTour = "Passer son tour";

        graphicsContextInfos.setFill(Color.color(0.98, 0.694, 0.627));

        //Affichage du "bouton" pour voir la défausse
        drawBouton(voirDefausse, width / 7., 35, 100, 30);
        //Affichage du "bouton" pour poser un partisan
        drawBouton(poserPartisan, 750, 15, 180, 30);
        //Affichage du "bouton" pour passer son tour
        drawBouton(passerTour, 750, 55, 180, 30);

        if (nbPartisans > 0) {
            graphicsContextInfos.setFill(color);
            graphicsContextInfos.fillOval(width / 2. + 50, 25, 50, 50);
            graphicsContextInfos.setFill(Color.BLACK);
            graphicsContextInfos.strokeText("x " + nbPartisans, width / 2. + 100, 75);
        } else {
            graphicsContextInfos.setFill(Color.BLACK);
            graphicsContextInfos.fillOval(width / 2., 25, 50, 50);
        }

        graphicsContextInfos.strokeText(s, (width / 2.) - 65, 35);
        graphicsContextInfos.strokeText(stringPts, (width/2.)-65, 65);
    }

    /*
     * Permet de tourner la carte sur l'affichage dans la barre d'canvasInfos
     */
    public void rotateCarteSuivante(Carte carte) {
        Image image = getImage(carte);
        drawInformationsCarte(image);
    }

    /*
     * Permet de récupérer l'image en fonction du nombre de rotation de la carte
     */
    private Image getImage(Carte carte) {
        int nbRotation = carte.getNbRotation();
        Image image;
        switch (nbRotation) {
            case 0:
                image = new Image(carte.getDraw().getPath());
                break;
            case 1:
                image = new Image(carte.getDraw().getPath90());
                break;
            case 2:
                image = new Image(carte.getDraw().getPath180());
                break;
            case 3:
                image = new Image(carte.getDraw().getPath270());
                break;
            default:
                image = null;
        }
        return image;
    }

    /*
     * Permet d'afficher la carte suivante
     */
    public void afficherCarteSuivante() {
        try {
            drawInformationsCarte(getImage(carcassonne.getPioche().getProchaineCarte()));
        } catch (PiocheVideException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Dessine la ligne séparatrice entre la fenêtre de jeu et la barre d'canvasInfos
     */
    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0, height);
        graphicsContextInfos.lineTo(width, height);
        graphicsContextInfos.stroke();
    }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public Canvas getCanvasInfos() {
        return canvasInfos;
    }

    public int getWidth() {
        return width;
    }
}
