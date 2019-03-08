package Jeu;

import Jeu.MultiJoueur.*;

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
                String test = (String) oi.readObject();
                if (test.equals("actualise")) {
                    test = (String) oi.readObject();
                    if(test.equals("tourSuivant")){
                        /*Recup nom joueur courant*/
                        /*Recup carte courante*/
                        vueMultiJoueurs.actualiserTourSuivant();
                    }
                    if (test.equals("poserCarte")){
                        vueMultiJoueurs.actualiserPoserCarte();
                    }
                    if (test.equals("defausse")){
                        vueMultiJoueurs.actualiserDefausse();
                    }
                    if (test.equals("poserPartisant")){
                        vueMultiJoueurs.actualiserPoserPartisant();
                    }
                }
                else if (test.equals("erreur")) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            }while(!isArreter);
        }


    }
    public void arreter(){
        isArreter = true;
    }
}
