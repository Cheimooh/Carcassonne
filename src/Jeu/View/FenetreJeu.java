/*
 * Solution 1 :
 * calculer la possibilité de poser des partisans (au debut on peut tous les poser,
 * ensuite il faut verifier le chemin par exemple pour voir si le joueur rouge peut poser son partisan)
 *
 * Solution 2 :
 * Avoir pour chaques zones l'ensemble des couleurs admissibles graces aux couleurs voisines.
 * Principe de contamination de zones
 *
 * -> parcours de graphe
 *
 * chaque coté de carte est divisé apr 3 pour tester des connections
 * chaque carte possède x tableau qui contiennent les coté appartenant aux x zones de la carte*
 * */
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
import java.util.Map;

public class FenetreJeu extends Parent {
    private static PlaceDispo placeDispo;
    private GraphicsContext graphicsContext;
    private ArrayDeque<Image> queueImage;
    private Carcassonne carcassonne;
    private ControlMouse controlMouse;
    private BarreInfos barreInfos;
    private CartePosee derniereCartePosee;

    private PopUpPartisan popUpPartisan;

    /*
     * Classe qui gère l'affichage de la fenêtre de jeu
     */
    public FenetreJeu(Carcassonne newCarcassonne, int width, int height, PopUpPartisan popUpPartisan) {
        carcassonne = newCarcassonne;
        this.popUpPartisan = popUpPartisan;
        popUpPartisan.lierControl(this);
        Canvas canvas = new Canvas(carcassonne.getNB_CASES() * 50, carcassonne.getNB_CASES() * 50);
        controlMouse = new ControlMouse(this);
        canvas.setOnMouseClicked(controlMouse);
        graphicsContext = canvas.getGraphicsContext2D();

        barreInfos = new BarreInfos(width, 100, this);
        drawFond(width, height);

        placeDispo = new PlaceDispo();
        queueImage = new ArrayDeque<>();
        placerCarte(carcassonne.getCarteDeBase());
        barreInfos.afficherCarteSuivante();
        this.getChildren().addAll(canvas, barreInfos.getCanvasInfos());
    }

    private void drawFond(int width, int height) {
        Image image = new Image("Jeu/fond2.jpg");
        graphicsContext.drawImage(image, 0, 100, width, height);
        image = new Image("Jeu/fond.jpg");
        graphicsContext.drawImage(image, 0, 0, width, 100);
    }

    /*
     * Permet de placer une carte sur la fenêtre de jeu
     */
    public void placerCarte(Carte carte) {
        //Ajout de la carte à la liste des cartes déjà posée
        CartePosee cartePosee = new CartePosee(carte);
        derniereCartePosee = cartePosee;
        carcassonne.getListPointOccupe().add(cartePosee.getPosition());
        carcassonne.getPointCarteMap().put(carte.getPosition(), cartePosee);
        //Image de la carte
        Image image = cartePosee.getImageCarte();

        int x = (int) cartePosee.getPosition().getX();
        int y = (int) cartePosee.getPosition().getY();

        //Permet de tester si l'on doit rajouter des emplacements disponibles ou non
        Point p = new Point(x + 1, y);
        testLDispo(p);
        p.setLocation(x - 1, y);
        testLDispo(p);
        p.setLocation(x, y + 1);
        testLDispo(p);
        p.setLocation(x, y - 1);
        testLDispo(p);

        //Supression de l'emplacement de la carte dans la liste des emplacements disponibles
        carcassonne.getListPointDispo().remove(cartePosee.getPosition());
        //Dessine l'image sur la fenêtre de jeu
        graphicsContext.drawImage(image, x * 50, y * 50, 50, 50);
        if (carte != carcassonne.getCarteDeBase()) {
            barreInfos.drawInformationsPartisans();
            carcassonne.contaminationDeLaCarteAvecCouleur(cartePosee);
        }
    }

    /*
     * Test si l'on doit ajouter ou non des emplacements disponibles
     * Permet également de les ajouter
     */
    private void testLDispo(Point p) {
        ArrayList<Point> lDispo = carcassonne.getListPointDispo();
        ArrayList<Point> lOccupee = carcassonne.getListPointOccupe();
        if (!lDispo.contains(p) && !lOccupee.contains(p)) {
            lDispo.add(new Point((int) p.getX(), (int) p.getY()));
            queueImage.addLast(placeDispo.getImagePlus());
            graphicsContext.drawImage(queueImage.getLast(), (int) p.getX() * 50, (int) p.getY() * 50, 50, 50);
        }
    }

    /*
     * Permet d'ajouter un partisan sur la zone où l'on clique et de rajouter la couleur en fonction du joueur
     */
    public void placerPartisan(int numZone) {
        int numJoueur = (carcassonne.getNumJoueur() - 1);
        if (carcassonne.getTabJoueur()[numJoueur].getNombrePartisansRestants() > 0) {
            derniereCartePosee.addZonesOccupees(numZone, carcassonne.getTabJoueur()[carcassonne.getNumJoueur() - 1].getColor());
            Carte carte = carcassonne.getTabJoueur()[numJoueur].getCarteEnMain();
            carcassonne.getTabJoueur()[numJoueur].placerPartisan(derniereCartePosee, numZone);
            int xCarte = (int) carte.getPosition().getX();
            int yCarte = (int) carte.getPosition().getY();

            graphicsContext.drawImage(derniereCartePosee.getImageCarte(), xCarte * 50, yCarte * 50, 50, 50);

            double xPartisan = carte.getPositionsCoordonnees().get(numZone).getX();
            double yPartisan = carte.getPositionsCoordonnees().get(numZone).getY();
            //Dessine le partisan sur la carte que l'on vient de poser
            Color color = carcassonne.getTabJoueur()[numJoueur].getColor();
            graphicsContext.setFill(color);
            graphicsContext.fillOval(xPartisan + (xCarte * 50) - 4, yPartisan + (yCarte * 50) - 4, 8, 8);
            controlMouse.setMode(0);
            carcassonne.contaminationDesAutresCarteAvecCouleur(derniereCartePosee);
        } else {
            afficheErreur(carcassonne.getTabJoueur()[numJoueur].getNom() + " n'a plus de partisans !", "Placement de partisans");
        }
    }

    /*
     * Permet de générer une fenêtre pop-up d'erreur
     */
    public void afficheErreur(String erreur, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);

        alert.setContentText(erreur);
        alert.showAndWait();
    }

    /*
     * Permet de toute les cartes qui sont occuper

    public void actualiserDebuggageContamination(){
        for (int i = 0; i < carcassonne.getListPointOccupe().size(); i++) { // toute carte posse
            CartePosee carteTmp = carcassonne.getPointCarteMap().get(carcassonne.getListPointOccupe().get(i));
            for(Map.Entry<Integer, Color> mapZone : carteTmp.getZonesCouleurPartisan().entrySet()) {
                graphicsContext.setFill(mapZone.getValue());
                colorierZones(mapZone.getKey(), carteTmp);
            }
        }
    }*/

    /*
     * Permet de colorier la bonne zone de la carte
     *
     * Point *50 pour place de la carte puis 50/3 pour chaque zone avec rectangle pour mieux voir


    private void colorierZones(int numZone, CartePosee c){
        Point point = c.getPosition();
        switch(numZone){
            case 1:
                graphicsContext.fillRect(point.getX()*50, point.getY()*50, 16, 8);
                break;
            case 2:
                graphicsContext.fillRect(point.getX()*50+16, point.getY()*50, 16, 8);
                break;
            case 3:
                graphicsContext.fillRect(point.getX()*50+32, point.getY()*50, 16, 8);
                break;
            case 4:
                graphicsContext.fillRect(point.getX()*50+42, point.getY()*50, 8, 16);
                break;
            case 5:
                graphicsContext.fillRect(point.getX()*50+42, point.getY()*50+16, 8, 16);
                break;
            case 6:
                graphicsContext.fillRect(point.getX()*50+42, point.getY()*50+32, 8, 16);//OK
                break;
            case 7:
                graphicsContext.fillRect(point.getX()*50+32, point.getY()*50+42, 16, 8);
                break;
            case 8:
                graphicsContext.fillRect(point.getX()*50+16, point.getY()*50+42, 16, 8);
                break;
            case 9:
                graphicsContext.fillRect(point.getX()*50, point.getY()*50+42, 16, 8);
                break;
            case 10:
                graphicsContext.fillRect(point.getX()*50, point.getY()*50+32, 8, 16);
                break;
            case 11:
                graphicsContext.fillRect(point.getX()*50, point.getY()*50+16, 8, 16);
                break;
            case 12:
                graphicsContext.fillRect(point.getX()*50, point.getY()*50, 8, 16);
                break;
        }
    }*/

    public void afficherCartePourPoserUnPartisan() {
        popUpPartisan.lierCarteEnMain(derniereCartePosee);
        popUpPartisan.afficherCarte(derniereCartePosee);
    }

    ControlMouse getControlMouse() {
        return controlMouse;
    }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public BarreInfos getBarreInfos() {
        return barreInfos;
    }

    public CartePosee getDerniereCartePosee() {
        return derniereCartePosee;
    }

    public PopUpPartisan getPopUpPartisan() {
        return popUpPartisan;
    }
}


