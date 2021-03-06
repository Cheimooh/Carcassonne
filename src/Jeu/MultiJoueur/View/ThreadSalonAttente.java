package Jeu.MultiJoueur.View;

import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.Model.CartePosee;
import Jeu.MultiJoueur.Model.Joueur;
import Jeu.MultiJoueur.Model.SocketJoueur;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ThreadSalonAttente {
    private Thread ThreadSalonAttente;
    private MenuReseau menu;
    private boolean isArreter;

    public ThreadSalonAttente(MenuReseau menu){
        isArreter = false;
        this.menu = menu;
        ThreadSalonAttente = new Thread(new ThreadSalonAttente.TraitementAttente());
        ThreadSalonAttente.start();
    }

    public class TraitementAttente implements Runnable {

        @Override
        public void run() {
            do {
                SocketJoueur socket = menu.getSocketJoueur();
                ObjectInputStream oi = socket.getOi();
                try {
                    String test = (String) socket.getOi().readObject();
                    if (test.equals("j'envoie")){
                        menu.getListJoueurs().clear();

                        int nombreJoueur = oi.readInt();
                        menu.setNombreJoueur(nombreJoueur);

                        for (int i = 0; i < nombreJoueur; i++) {
                            Joueur joueur = (Joueur) oi.readObject();
                            menu.getListJoueurs().add(joueur);
                        }
                        menu.actualiser();
                    }
                    else if (test.equals("startPartie")){
                        menu.startPartie();
                    }
                    else if (test.equals("actualise")) {
                        test = (String) oi.readObject();
                        if(test.equals("tourSuivant")){
                            Joueur joueurCourant = (Joueur) oi.readObject();
                            Carte carteCourante = (Carte) oi.readObject();
                            menu.setCarteCourante(carteCourante);
                            menu.setJoueurCourant(joueurCourant);
                            menu.setMode(0);
                            menu.actualiserBarreInfo();
                            menu.getPopUpPartisan().quitter();
                        }
                        else if (test.equals("partisan")){
                            menu.actualiserPartisan();
                            menu.placerPartisan();
                        }
                        else if (test.equals("reDraw")){
                            CartePosee cartePosee = (CartePosee) oi.readObject();
                            Point position = (Point) oi.readObject();
                            menu.redrawCarte(cartePosee, position);
                            String color = (String) oi.readObject();
                            int score = oi.readInt();
                            menu.retirerPartisan(color, score);
                        }
                        else if (test.equals("poserCarte")){
                            menu.actualiserPoserCarte();
                            menu.setMode(1);
                            menu.actualiserBarreInfo();
                        }
                        else if (test.equals("defausse")){
                            menu.actualiserDefausse();
                        }
                    }
                    else if (test.equals("erreur")) {
                        String titre = (String) oi.readObject();
                        String contenue = (String) oi.readObject();
                        menu.afficheErreur(contenue, titre);
                    }
                    else if (test.equals("finPartie")) {
                        menu.afficherFinDuJeu();
                        menu.fenetreFinDuJeu();
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