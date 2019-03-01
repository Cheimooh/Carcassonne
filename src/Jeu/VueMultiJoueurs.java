package Jeu;

import Jeu.ModelServeur.*;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VueMultiJoueurs extends Parent {
    private Map<Point, CartePosee> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo; // Liste de point où l'on peut ajouter une carte
    private ArrayList<Point> listPointOccupe; // Liste de point où il y a déjà une carte posée
    private ArrayList<Carte> defausse; // Liste de carte où il y a la défausse
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
    }

    public void initialiser(){
        ObjectInputStream oi = socketJoueur.getOi();
        try {
            /*On récupère la map point carte*/
            int tailleMap = oi.readInt();
            for (int i = 0; i < tailleMap; i++) {
                try {
                    Point point = (Point) oi.readObject();
                    CartePosee cartePosee = (CartePosee) oi.readObject();
                    pointCarteMap.put(point,cartePosee);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            /*On récupère la liste des points disponible*/
            int tailleListePointDispo = oi.readInt();
            for (int i = 0; i < tailleListePointDispo; i++) {
                try {
                    listPointDispo.add((Point)oi.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            /*On récupère la liste des points occuper*/
            int tailleListePointOccupe = oi.readInt();
            for (int i = 0; i < tailleListePointOccupe; i++) {
                try {
                    listPointOccupe.add((Point)oi.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            /*On récupère la defausse*/
            int tailleDefausse = oi.readInt();
            for (int i = 0; i < tailleDefausse; i++) {
                try {
                    defausse.add((Carte)oi.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

