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

    private Image imageAffichee;

    private int width;
    private int height;

    public BarreInfos(int width, int height, FenetreJeu f){
        this.width=width;
        this.height=height;
        carcassonne = f.getCarcassonne();
        infos = new Canvas(width, height);
        ControlMouseInfos controlMouseInfos = new ControlMouseInfos(this, f.getControlMouse());
        infos.setOnMouseClicked(controlMouseInfos);
        graphicsContextInfos = infos.getGraphicsContext2D();
    }

    private void drawInformationsCarte(Image prochaineCarte){
        graphicsContextInfos.clearRect(0,0,width,100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();

        String s;

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

            graphicsContextInfos.strokeRect(width/7,15, 100,30);
            graphicsContextInfos.strokeText(voirPioche, width/7+20,32);

            graphicsContextInfos.strokeRect(width/7,55,100,30);
            graphicsContextInfos.strokeText(voirDefausse, width/7+20,72);

            graphicsContextInfos.strokeRect(width*3/4,35,140,30);
            graphicsContextInfos.strokeText(defausse, width*3/4+20,52);
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
        //VOIR POUR CENTRER LE TEXTE, JE SAIS COMMENT FAIRE FAUT QUE JE REGARDE SUR LE GIT
        this.imageAffichee=prochaineCarte;
    }

    private void drawInformationsPartisans(Image prochaineCarte){
        graphicsContextInfos.clearRect(0,0,width,100);
        graphicsContextInfos.setFill(Color.BLACK);
        drawLigneSeparatrice();

        String s;

        if(carcassonne.getP().getTaille()<=0){
            s = "Fin de partie";
        }
        else {
            graphicsContextInfos.drawImage(prochaineCarte, (width/2.), 30, 50, 50);

            int numJoueur = carcassonne.getNumJoueur();
            s = "Joueur " + numJoueur;
            s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

            int nbPartisans = carcassonne.getTabJoueur()[numJoueur-1].getNombrePartisansRestants();
            Color color = carcassonne.getTabJoueur()[numJoueur-1].getColor();

            String defausse = "Defausse";

            graphicsContextInfos.strokeRect(width*3/4,35,100,30);
            graphicsContextInfos.strokeText(defausse, width*3/4+20,52);

            if (nbPartisans>0){
                // A DEPLACER OUAIS

                //graphicsContextInfos.setFill(color);
                //graphicsContextInfos.fillOval((width/4.)*3, 25, 50, 50);
                //graphicsContextInfos.setFill(Color.BLACK);
                //graphicsContextInfos.strokeText("x "+nbPartisans, (width/4.)*3+50, 35);
            }
        }
        graphicsContextInfos.strokeText(s, (width/2.), 15);
        //VOIR POUR CENTRER LE TEXTE, JE SAIS COMMENT FAIRE FAUT QUE JE REGARDE SUR LE GIT
        this.imageAffichee=prochaineCarte;
    }

    public void rotateCarteSuivante(Carte carte){
        Image image = getImage(carte);
        drawInformationsCarte(image);
    }

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

    public void defausserCarte(Carte carte) {
        // A MODIFIER
        carcassonne.getDefausse().add(carte);
        carcassonne.jouer();
        drawInformationsCarte(carcassonne.getTabJoueur()[carcassonne.getNumJoueur()-1].getCarteEnMain().getDraw().getImg());
    }

    public void afficherCarteSuivant() {
        drawInformationsCarte(getImage(carcassonne.getP().getProchaineCarte()));
    }

    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0,height);
        graphicsContextInfos.lineTo(width,height);
        graphicsContextInfos.stroke();
    }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public Canvas getInfos() { return infos; }
}
