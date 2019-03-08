package Jeu;

import Jeu.MultiJoueur.*;
import Jeu.MultiJoueur.Controller.ControlMouse;
import Jeu.MultiJoueur.View.FenetreJeu;
import Jeu.MultiJoueur.View.PopUpPartisan;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VueMultiJoueurs extends Parent {
    private Map<Point, CartePosee> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo; // Liste de point où l'on peut ajouter une carte
    private ArrayList<Point> listPointOccupe; // Liste de point où il y a déjà une carte posée
    private ArrayList<Carte> defausse; // Liste de carte où il y a la défausse
    private Carte carteCourante;
    private Stage primaryStage;
    private String nomJoueur;
    private String nomJoueurCourant;
    private SocketJoueur socketJoueur;


    public VueMultiJoueurs(Stage primaryStage, SocketJoueur socketJoueur, String nom) {
        this.primaryStage = primaryStage;
        nomJoueur = nom;
        pointCarteMap = new HashMap<>();
        listPointDispo = new ArrayList<Point>();
        listPointOccupe = new ArrayList<Point>();
        defausse = new ArrayList<Carte>();
        this.socketJoueur = socketJoueur;
        ControlMouse controlMouse = new ControlMouse(this);
    }

    public void initialiser(){
        ObjectInputStream oi = socketJoueur.getOi();
        try {
            /*On récupère la map point carte*/
            int tailleMap = oi.readInt();
            for (int i = 0; i < tailleMap; i++) {
                Point point = (Point) oi.readObject();
                CartePosee cartePosee = (CartePosee) oi.readObject();
                pointCarteMap.put(point,cartePosee);
            }

            /*On récupère la liste des points disponible*/
            int tailleListePointDispo = oi.readInt();
            for (int i = 0; i < tailleListePointDispo; i++) {
                listPointDispo.add((Point)oi.readObject());
            }

            /*On récupère la liste des points occuper*/
            int tailleListePointOccupe = oi.readInt();
            for (int i = 0; i < tailleListePointOccupe; i++) {
                listPointOccupe.add((Point)oi.readObject());
            }

            /*On récupère la defausse*/
            int tailleDefausse = oi.readInt();
            for (int i = 0; i < tailleDefausse; i++) {
                defausse.add((Carte)oi.readObject());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        afficherFenetreJeu();
    }

    private void afficherFenetreJeu() {
        Group root = new Group();
        int WIDTH = 1000;
        int HEIGHT = 700;
        PopUpPartisan popUpPartisan = new PopUpPartisan(primaryStage);
        FenetreJeu fenetreJeu = new FenetreJeu(this, WIDTH, HEIGHT, popUpPartisan);
        root.getChildren().add(fenetreJeu);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
    }

    public void actualiserTourSuivant() {
    }

    public void actualiserPoserCarte() {
    }

    public void actualiserDefausse() {
    }

    public void actualiserPoserPartisant() {
    }

    public SocketJoueur getSocketJoueur() {
        return socketJoueur;
    }

    public void setPointCarteMap(Map<Point, CartePosee> pointCarteMap) {
        this.pointCarteMap = pointCarteMap;
    }

    public void setListPointDispo(ArrayList<Point> listPointDispo) {
        this.listPointDispo = listPointDispo;
    }

    public void setListPointOccupe(ArrayList<Point> listPointOccupe) {
        this.listPointOccupe = listPointOccupe;
    }

    public void setDefausse(ArrayList<Carte> defausse) {
        this.defausse = defausse;
    }

    public Map<Point, CartePosee> getPointCarteMap() {
        return pointCarteMap;
    }

    public ArrayList<Point> getListPointDispo() {
        return listPointDispo;
    }

    public ArrayList<Point> getListPointOccupe() {
        return listPointOccupe;
    }

    public ArrayList<Carte> getDefausse() {
        return defausse;
    }

    public Carte getCarteCourante() {
        return carteCourante;
    }
}

