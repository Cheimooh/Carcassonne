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

    private int width; // largeur de la fenêtre
    private int height; // hauteur de la fenêtre

    private int[] tabDefausseCarte = new int[]{750, 35, 180, 30};
    // {position x, position y, largeur, hauteur}

    public BarreInfos(int width, int height, FenetreJeu f){
        this.width=width;
        this.height=height;
        carcassonne = f.getCarcassonne();
        infos = new Canvas(width, height);
        ControlMouseInfos controlMouseInfos = new ControlMouseInfos(this, f.getControlMouse(), tabDefausseCarte);
        infos.setOnMouseClicked(controlMouseInfos);
        graphicsContextInfos = infos.getGraphicsContext2D();
        graphicsContextInfos.setStroke(Color.color(0.2,0.2,0.2));
    }

    /*
     * Dessine la barre d'infos lorsque le joueur doit poser une carte
     */
    private void drawInformationsCarte(Image prochaineCarte){
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
            String voirPioche = "Pioche";
            String voirDefausse = "Défausse";

            graphicsContextInfos.setFill(Color.color(0.98,0.694, 0.627));

            //Affichage du "bouton" pour voir la pioche
            graphicsContextInfos.fillRect(width/7.,15, 100,30);
            graphicsContextInfos.strokeText(voirPioche, width/7.+20,32);
            //Affichage du "bouton" pour voir la défausse
            graphicsContextInfos.fillRect(width/7.,55,100,30);
            graphicsContextInfos.strokeText(voirDefausse, width/7.+20,72);
            //Affichage du "bouton" pour défausser une carte
            graphicsContextInfos.fillRect(tabDefausseCarte[0],tabDefausseCarte[1],tabDefausseCarte[2],tabDefausseCarte[3]);
            graphicsContextInfos.strokeText(defausse, tabDefausseCarte[0]+20,52);
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
        //VOIR POUR CENTRER LE TEXTE, JE SAIS COMMENT FAIRE FAUT QUE JE REGARDE SUR LE GIT
    }

    /*
     * Dessine la barre d'infos lorsque le joueur doit poser un partisan
     */
    public void drawInformationsPartisans(){
        graphicsContextInfos.clearRect(0,0,width,100);
        graphicsContextInfos.setFill(Color.BLACK);
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

            String voirPioche = "Pioche";
            String voirDefausse = "Défausse";

            graphicsContextInfos.setFill(Color.color(0.98,0.694, 0.627));

            //Affichage du "bouton" pour voir la pioche
            graphicsContextInfos.fillRect(width/7.,15, 100,30);
            graphicsContextInfos.strokeText(voirPioche, width/7.+20,32);
            //Affichage du "bouton" pour voir la défausse
            graphicsContextInfos.fillRect(width/7.,55,100,30);
            graphicsContextInfos.strokeText(voirDefausse, width/7.+20,72);

            if (nbPartisans>0){
                graphicsContextInfos.setFill(color);
                graphicsContextInfos.fillOval(width/2., 25, 50, 50);
                graphicsContextInfos.setFill(Color.BLACK);
                graphicsContextInfos.strokeText("x "+nbPartisans, width/2.+50, 35);
            }
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
        //VOIR POUR CENTRER LE TEXTE, JE SAIS COMMENT FAIRE FAUT QUE JE REGARDE SUR LE GIT
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
        // A MODIFIER
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
}
