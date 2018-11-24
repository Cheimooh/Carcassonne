package Jeu.View;

import Jeu.Controller.ControlMouse;
import Jeu.Model.Carcassonne;
import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class FenetreJeu extends Parent {

    private static PlaceDispo placeDispo;
    private GraphicsContext graphicsContext;
    private ArrayDeque <Image> queueImage;
    private Carcassonne carcassonne;
    private ControlMouse controlMouse;
    private BarreInfos barreInfos;
    private CartePosee derniereCartePosee;

    /*
     * Classe qui gère l'affichage de la fenêtre de jeu
     */
    public FenetreJeu(Carcassonne newCarcassonne, int width, int height){
        carcassonne = newCarcassonne;
        Canvas canvas = new Canvas(carcassonne.getNB_CASES()*50, carcassonne.getNB_CASES()*50);
        controlMouse = new ControlMouse(this);
        barreInfos = new BarreInfos(width, 100, this);
        canvas.setOnMouseClicked(controlMouse);
        graphicsContext = canvas.getGraphicsContext2D();
        placeDispo = new PlaceDispo();
        queueImage = new ArrayDeque<>();
        placerCarte(carcassonne.getCarteDeBase());
        barreInfos.afficherCarteSuivant();
        this.getChildren().add(canvas);
        this.getChildren().add(barreInfos.getInfos());
    }

    /*
     * Permet de placer une carte sur la fenêtre de jeu
     */
    public void placerCarte(Carte carte){
        //Ajout de la carte à la liste des cartes déjà posée
        CartePosee cartePosee = new CartePosee(carte);
        derniereCartePosee=cartePosee;
        carcassonne.getListPointOccupe().add(cartePosee.getPosition());
        carcassonne.getPointCarteMap().put(carte.getPosition(), cartePosee);
        //Image de la carte
        Image image = cartePosee.getImageCarte();

        int x = (int) cartePosee.getPosition().getX();
        int y = (int) cartePosee.getPosition().getY();

        //Permet de tester si l'on doit rajouter des emplacements disponibles ou non
        Point p = new Point(x+1,y);
        testLDispo(p);
        p.setLocation(x-1,y);
        testLDispo(p);
        p.setLocation(x,y+1);
        testLDispo(p);
        p.setLocation(x,y-1);
        testLDispo(p);

        //Supression de l'emplacement de la carte dans la liste des emplacements disponibles
        carcassonne.getListPointDispo().remove(cartePosee.getPosition());
        //Dessine l'image sur la fenêtre de jeu
        graphicsContext.drawImage(image, x*50,y*50, 50, 50);
        if(carte!=carcassonne.getCarteDeBase()) {
            graphicsContext.setFill(Color.WHITE);
            for (int i = 0; i < carte.getPositionsCoordonnees().size(); i++) {
                double xPartisan = carte.getPositionsCoordonnees().get(i).getX();
                double yPartisan = carte.getPositionsCoordonnees().get(i).getY();
                graphicsContext.strokeOval(xPartisan + (x * 50)-2.5, yPartisan + (y * 50)-2.5, 5, 5);
            }
        }
    }

    /*
     * Test si l'on doit ajouter ou non des emplacements disponibles
     * Permet également de les ajouter
     */
    private void testLDispo(Point p){
        ArrayList<Point> lDispo = carcassonne.getListPointDispo();
        ArrayList<Point> lOccupee = carcassonne.getListPointOccupe();
        if ( !lDispo.contains(p) && !lOccupee.contains(p)) {
            lDispo.add(new Point((int)p.getX(), (int)p.getY()));
            queueImage.addLast(placeDispo.getImagePlus());
            graphicsContext.drawImage(queueImage.getLast(),(int)p.getX()*50, (int)p.getY()*50, 50, 50);
        }
    }

    /*
     * Permet d'ajouter un partisan sur la zone où l'on clique
     */
    public void placerPartisan(int numZone) {
        int numJoueur = (carcassonne.getNumJoueur()-1);
        if (carcassonne.getTabJoueur()[numJoueur].getNombrePartisansRestants()>0) {
            Carte carte = carcassonne.getTabJoueur()[numJoueur].getCarteEnMain();
            int xCarte = (int)carte.getPosition().getX();
            int yCarte = (int)carte.getPosition().getY();

            graphicsContext.drawImage(derniereCartePosee.getImageCarte(), xCarte*50,yCarte*50, 50, 50);

            double xPartisan = carte.getPositionsCoordonnees().get(numZone).getX();
            double yPartisan = carte.getPositionsCoordonnees().get(numZone).getY();
            //Dessine le partisan sur la carte que l'on vient de poser
            Color color = carcassonne.getTabJoueur()[numJoueur].getColor();
            graphicsContext.setFill(color);
            graphicsContext.fillOval(xPartisan+(xCarte*50)-4, yPartisan+(yCarte*50)-4,8,8);
        } else {
            System.out.println(carcassonne.getTabJoueur()[numJoueur].getNom()+" n'a plus de partisans !");
        }
    }

    /*
     * Permet de générer une fenêtre pop-up d'erreur
     */
    public void afficheErreur(String erreur, String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);

        alert.setContentText(erreur);
        alert.showAndWait();
    }

    ControlMouse getControlMouse() { return controlMouse; }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public BarreInfos getBarreInfos() { return barreInfos; }
}
