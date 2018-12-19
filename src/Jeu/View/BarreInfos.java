package Jeu.View;

import Jeu.Controller.ControlMouseInfos;
import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BarreInfos {
    private Carcassonne carcassonne;
    private GraphicsContext graphicsContextInfos;
    private Canvas infos;
    private ControlMouseInfos controlMouseInfos;

    private int width; // largeur de la fenêtre
    private int height; // hauteur de la fenêtre

    private int[] tabDefausseCarte;
    // {position x, position y, largeur, hauteur}

    public BarreInfos(int width, int height, FenetreJeu f){
        this.width=width;
        this.height=height;
        tabDefausseCarte = new int[]{750, 35, 180, 30};
        carcassonne = f.getCarcassonne();
        infos = new Canvas(width, height);
        controlMouseInfos = new ControlMouseInfos(this, f.getControlMouse(), tabDefausseCarte);
        infos.setOnMouseClicked(controlMouseInfos);
        graphicsContextInfos = infos.getGraphicsContext2D();
        graphicsContextInfos.setStroke(Color.color(0.2,0.2,0.2));
    }

    /*
     * Dessine la barre d'infos lorsque le joueur doit poser une carte
     */
    private void drawInformationsCarte(Image prochaineCarte){
        controlMouseInfos.setMode(0);
        graphicsContextInfos.clearRect(0,0,width,100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();

        String s;

        //Pioche vide
        if(carcassonne.getP().getTaille()<=0){
            s = "Fin de partie";
        }
        else {
            graphicsContextInfos.drawImage(prochaineCarte, (width/2.), 30, 50, 50);

            int numJoueur = carcassonne.getNumJoueur();
            s = "Joueur " + numJoueur;
            s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

            String defausse = "Defausser ma carte";
            String voirDefausse = "Défausse";

            graphicsContextInfos.setFill(Color.color(0.98,0.694, 0.627));

            //Affichage du "bouton" pour voir la défausse
            drawBouton(voirDefausse, width/7., 35, 100, 30);
            //Affichage du "bouton" pour défausser une carte
            drawBouton(defausse, tabDefausseCarte[0], tabDefausseCarte[1], tabDefausseCarte[2], tabDefausseCarte[3]);
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
    }

    private void drawBouton(String texte, double x, int y, int largeur, int hauteur) {
        graphicsContextInfos.fillRoundRect(x,y, largeur,hauteur,20,20);
        graphicsContextInfos.strokeText(texte, x+20,y+17);
    }

    /*
     * Dessine la barre d'infos lorsque le joueur doit poser un partisan
     */
    public void drawInformationsPartisans(){
        controlMouseInfos.setMode(1);
        graphicsContextInfos.clearRect(0,0,width,100);
        drawLigneSeparatrice();

        String s;

        if(carcassonne.getP().getTaille()<=0){
            s = "Fin de partie";
        }
        else {
            int numJoueur = carcassonne.getNumJoueur();
            s = "Joueur " + numJoueur;
            s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

            int nbPartisans = carcassonne.getTabJoueur()[numJoueur-1].getNombrePartisansRestants();
            Color color = carcassonne.getTabJoueur()[numJoueur-1].getColor();

            String voirDefausse = "Défausse";
            String poserPartisan = "Poser un partisan";
            String passerTour = "Passer son tour";

            graphicsContextInfos.setFill(Color.color(0.98,0.694, 0.627));

            //Affichage du "bouton" pour voir la défausse
            drawBouton(voirDefausse, width/7., 35, 100, 30);
            //Affichage du "bouton" pour poser un partisan
            drawBouton(poserPartisan, 750, 15, 180, 30);
            //Affichage du "bouton" pour passer son tour
            drawBouton(passerTour, 750, 55, 180, 30);

            if (nbPartisans>0){
                graphicsContextInfos.setFill(color);
                graphicsContextInfos.fillOval(width/2., 25, 50, 50);
                graphicsContextInfos.setFill(Color.BLACK);
                graphicsContextInfos.strokeText("x "+nbPartisans, width/2.+50, 35);
            }
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
    }

    /*
     * Permet de tourner la carte sur l'affichage dans la barre d'infos
     */
    public void rotateCarteSuivante(Carte carte){
        Image image = getImage(carte);
        drawInformationsCarte(image);
    }

    /*
     * Permet de récupérer l'image en fonction du nombre de rotation de la carte
     */
    private Image getImage(Carte carte){
        int nbRotation=carte.getNbRotation();
        Image image;
        switch (nbRotation){
            case 0:
                image= carte.getDraw().img;
                break;
            case 1:
                image = carte.getDraw().img90;
                break;
            case 2:
                image = carte.getDraw().img180;
                break;
            case 3:
                image = carte.getDraw().img270;
                break;
            default:
                image=null;
        }
        return image;
    }

    /*
     * Supprime la carte courante de l'affichage
     * Ajoute la carte "supprimé" à la défausse
     */
    public void defausserCarte(Carte carte) {
        carcassonne.getDefausse().add(carte);
        carcassonne.jouer();
        drawInformationsCarte(carcassonne.getTabJoueur()[carcassonne.getNumJoueur()-1].getCarteEnMain().getDraw().getImg());
    }

    /*
     * Permet d'afficher la carte suivante
     */
    public void afficherCarteSuivant() {
        drawInformationsCarte(getImage(carcassonne.getP().getProchaineCarte()));
    }

    /*
     * Dessine la ligne séparatrice entre la fenêtre de jeu et la barre d'infos
     */
    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0,height);
        graphicsContextInfos.lineTo(width,height);
        graphicsContextInfos.stroke();
    }

    public Carcassonne getCarcassonne() { return carcassonne; }

    public Canvas getInfos() { return infos; }

    public int getWidth() { return width; }
}
