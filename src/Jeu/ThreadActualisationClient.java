package Jeu;

import Jeu.ModelServeur.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;

public class ThreadActualisationClient {
    private Thread ThreadActualisationClient;
    private VueMultiJoueurs vueMultiJoueurs;
    private boolean isArreter;
    private SocketJoueur socketJoueur;
    private Map<Point, CartePosee> pointCarteMap; // Map qui contient pour chaque point sa Carte
    private ArrayList<Point> listPointDispo; // Liste de point où l'on peut ajouter une carte
    private ArrayList<Point> listPointOccupe; // Liste de point où il y a déjà une carte posée
    private ArrayList<Carte> defausse; // Liste de carte où il y a la défausse

    public ThreadActualisationClient(VueMultiJoueurs vueMultiJoueurs){
        isArreter = false;
        this.vueMultiJoueurs = vueMultiJoueurs;
        this.socketJoueur = vueMultiJoueurs.getSocketJoueur();
        ThreadActualisationClient = new Thread(new ThreadActualisationClient.TraitementActualisationClient());
        ThreadActualisationClient.start();
    }

    public class TraitementActualisationClient implements Runnable {

        @Override
        public void run() {
            do {
            ObjectInputStream oi = socketJoueur.getOi();
            try {
                String test = (String) socketJoueur.getOi().readObject();
            if (test.equals("actualise")) {
                try {
                    /*On récupère la map point carte*/
                    try {
                        pointCarteMap = (Map) oi.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    /*On récupère la liste des points disponible*/
                    int tailleListePointDispo = oi.readInt() - 1;
                    for (int i = 0; i < tailleListePointDispo; i++) {
                        try {
                            listPointDispo.add((Point) oi.readObject());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    /*On récupère la liste des points occuper*/
                    int tailleListePointOccupe = oi.readInt() - 1;
                    for (int i = 0; i < tailleListePointOccupe; i++) {
                        try {
                            listPointOccupe.add((Point) oi.readObject());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    /*On récupère la defausse*/
                    int tailleDefausse = oi.readInt() - 1;
                    for (int i = 0; i < tailleDefausse; i++) {
                        try {
                            defausse.add((Carte) oi.readObject());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            vueMultiJoueurs.setPointCarteMap(pointCarteMap);
            vueMultiJoueurs.setListPointDispo(listPointDispo);
            vueMultiJoueurs.setListPointOccupe(listPointOccupe);
            vueMultiJoueurs.setDefausse(defausse);
            }while(!isArreter);
        }


    }
    public void arreter(){
        isArreter = true;
    }
}
