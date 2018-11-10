package Jeu.View;

//detection de la posisition de la souris.
//souris recupère une ccordonnée dans la zone visible, mais il faut recuperer les coordonnée de la zone sur la map
//penser a linvisible : translation
//

import Jeu.Controller.ControlButton;
import Jeu.Controller.ControlMouse;
import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Fenetre extends Parent {

    public static PlaceDispo placeDispo;
    private GraphicsContext graphicsContext;
    private GraphicsContext graphicsContextInfos;
    private ArrayDeque <Image> queueImage;
    private ControlButton controlButton;
    private ArrayList <Point> lDispo;
    private ArrayList <Point> lOccupee;
    private ControlMouse controlMouse;
    private ControlMouse controlMouseInfos;
    private Carcassonne carcassonne;
    private int width;
    private int height;
    private Image prochaineCarte;

    public Fenetre(Carcassonne newCarcassonne, int width, int height){
        this.width=width;
        this.height=height;
        carcassonne = newCarcassonne;
        Canvas canvas = new Canvas(carcassonne.getNB_CASES()*50, carcassonne.getNB_CASES()*50);
        Canvas infos = new Canvas(width, 100);
        controlMouse = new ControlMouse(this, "fenetreDeJeu");
        controlMouseInfos = new ControlMouse(this, "barreInfos");
        canvas.setOnMouseClicked(controlMouse);
        infos.setOnMouseClicked(controlMouseInfos);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContextInfos = infos.getGraphicsContext2D();
        placeDispo = new PlaceDispo();
        lDispo = new ArrayList<>();
        lOccupee = new ArrayList<>();
        queueImage = new ArrayDeque<>();
        placerCarte(carcassonne.getCarteDeBase());
        this.getChildren().add(canvas);
        this.getChildren().add(infos);
        carcassonne.jouer();
    }

    public ArrayList<Point> getlOccupee() {
        return lOccupee;
    }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public void placerCarte(Carte carte){
        lOccupee.add(carte.getPosition());
        carcassonne.getPointCarteMap().put(carte.getPosition(), carte);
        Image image = getImage(carte);

        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();

        //bloc de test pour tester les listes
        Point p = new Point(x+1,y);
        testLDispo(p);

        p.setLocation(x-1,y);
        testLDispo(p);

        p.setLocation(x,y+1);
        testLDispo(p);

        p.setLocation(x,y-1);
        testLDispo(p);

        if (lDispo.contains(carte.getPosition())){ lDispo.remove(carte.getPosition()); }

        graphicsContext.drawImage(image, x*50,y*50, 50, 50);
        prochaineCarte = carcassonne.getP().getProchaineCarte().getDraw().img;
        drawInformations(prochaineCarte);
    }

    private void testLDispo(Point p){
        if ( !lDispo.contains(p) && !lOccupee.contains(p)) {
            lDispo.add(new Point((int)p.getX(), (int)p.getY()));
            queueImage.addLast(placeDispo.getImagePlus());
            graphicsContext.drawImage(queueImage.getLast(),(int)p.getX()*50, (int)p.getY()*50, 50, 50);
        }
    }

    private void drawInformations(Image prochaineCarte){
        graphicsContextInfos.clearRect(0,0,width,100);
        drawLigneSeparatrice();

        String s;

        if(carcassonne.getP().getTaille()<=0){
            s = "Fin de partie";
        }
        else {
            graphicsContextInfos.drawImage(prochaineCarte, (width/2.), 20, 50, 50);

            int numJoueur = carcassonne.getNumJoueur();
            s = "Joueur " + numJoueur;
            s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();

            int nbPartisans = carcassonne.getTabJoueur()[numJoueur-1].getNombrePartisansRestants();
            Color color = carcassonne.getTabJoueur()[numJoueur-1].getColor();

            if (nbPartisans>0){
                graphicsContextInfos.setFill(color);
                graphicsContextInfos.fillOval((width/4.)*3, 25, 50, 50);
                graphicsContextInfos.setFill(Color.BLACK);
                graphicsContextInfos.strokeText("x "+nbPartisans, (width/4)*3+50, 35);
            }

        }
        graphicsContextInfos.strokeText(s, (width/4.), 50);
    }

    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0,100);
        graphicsContextInfos.lineTo(width,100);
        graphicsContextInfos.stroke();
    }

    public void rotateCarteSuivante(Carte carte){
        Image image = getImage(carte);
        drawInformations(image);
    }

    public Image getImage(Carte carte){
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

    public void placerPartisan(int x, int y) {
        int numJoueur = (carcassonne.getNumJoueur()-2)%4;
        String nom = carcassonne.getTabJoueur()[numJoueur].getNom();
        System.out.println(nom+" place un partisan en "+x+" "+y);
        carcassonne.getTabJoueur()[numJoueur].placePartisan();
    }

    public void afficheErreur(String erreur){
        drawInformations(prochaineCarte);
        graphicsContextInfos.strokeText(erreur, (width/2.)-70, 80);
    }
}
