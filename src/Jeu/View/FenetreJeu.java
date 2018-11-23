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
        Carte carte = carcassonne.getTabJoueur()[numJoueur].getCarteEnMain();
        int x = (int)carte.getPosition().getX();
        int y = (int)carte.getPosition().getY();
        Color colorJoueur = carcassonne.getTabJoueur()[numJoueur].getColor();
        switch (numZone){
            case 1:
                x=x*50+(50/6);
                y=y*50+(50/6);
                break;
            case 2:
                x=x*50+(150/6);
                y=y*50+(50/6);
                break;
            case 3:
                x=x*50+(250/6);
                y=y*50+(50/6);
                break;
            case 4:
                x=x*50+(250/6);
                y=y*50+(150/6);
                break;
            case 5:
                x=x*50+(250/6);
                y=y*50+(250/6);
                break;
            case 6:
                x=x*50+(150/6);
                y=y*50+(250/6);
                break;
            case 7:
                x=x*50+(50/6);
                y=y*50+(250/6);
                break;
            case 8:
                x=x*50+(50/6);
                y=y*50+(150/6);
                break;
            case 9:
                x=x*50+(150/6);
                y=y*50+(150/6);
                break;
        }
        if (carcassonne.getTabJoueur()[numJoueur].getNombrePartisansRestants()>0) {
            //Dessine le partisan sur la carte que l'on vient de poser
            graphicsContext.setFill(colorJoueur);
            graphicsContext.fillOval(x - 5, y - 5, 10, 10);
            //Décremente le nombre de partisan du joueur
            carcassonne.getTabJoueur()[numJoueur].placePartisan();
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
