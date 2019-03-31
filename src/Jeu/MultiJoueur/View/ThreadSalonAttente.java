package Jeu.MultiJoueur.View;

import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.Model.Joueur;
import Jeu.MultiJoueur.Model.SocketJoueur;

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
                        }
                        if (test.equals("poserCarte")){
                            menu.actualiserPoserCarte();
                            menu.actualiserPlateau();
                            menu.setMode(1);
                            menu.actualiserBarreInfo();
                        }
                        if (test.equals("defausse")){
                            menu.actualiserDefausse();
                        }
                        if (test.equals("poserPartisant")){
                            //menu.actualiserPoserPartisant();
                        }
                    }
                    else if (test.equals("erreur")) {
                        String titre = (String) oi.readObject();
                        String contenue = (String) oi.readObject();
                        menu.afficheErreur(contenue, titre);
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