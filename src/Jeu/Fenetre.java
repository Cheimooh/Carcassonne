package Jeu;

//detection de la posisition de la souris.
//souris recupère une ccordonnée dans la zone visible, mais il faut recuperer les coordonnée de la zone sur la map
//penser a linvisible : translation
//

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

    public Fenetre(Carcassonne newCarcassonne){
        carcassonne = newCarcassonne;
        Canvas canvas = new Canvas(carcassonne.getNB_CASES()*50, carcassonne.getNB_CASES()*50);
        Canvas infos = new Canvas(1000, 100);
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
        Image image = carte.getDraw().img;
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
        Image prochaineCarte = carcassonne.getP().getProchaineCarte().getDraw().img;
        drawInformations(prochaineCarte);
    }

    public void testLDispo(Point p){
        if ( !lDispo.contains(p) && !lOccupee.contains(p)) {
            lDispo.add(new Point((int)p.getX(), (int)p.getY()));
            queueImage.addLast(placeDispo.getImagePlus());
            graphicsContext.drawImage(queueImage.getLast(),(int)p.getX()*50, (int)p.getY()*50, 50, 50);
        }
    }

    private void drawInformations(Image prochaineCarte){
        graphicsContextInfos.clearRect(0,0,1000,100);
        drawLigneSeparatrice();

        String s;

        if(carcassonne.getP().getTaille()<=0){
            s = "Fin de partie";
        }
        else {
            graphicsContextInfos.drawImage(prochaineCarte, 500, 20, 50, 50);

            int numJoueur = carcassonne.getNumJoueur();
            s = "Joueur " + numJoueur;
            s += " : " + carcassonne.getTabJoueur()[numJoueur - 1].getNom();
        }
        graphicsContextInfos.strokeText(s, 250, 50);
    }

    private void drawLigneSeparatrice() {
        graphicsContextInfos.moveTo(0,100);
        graphicsContextInfos.lineTo(1000,100);
        graphicsContextInfos.stroke();
    }

    public void rotateCarteSuivante(int nbRotation){
        System.out.println("clic " + nbRotation);
    }
}
