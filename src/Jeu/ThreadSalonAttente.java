package Jeu;

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
                            System.out.println(joueur.isPret());
                            menu.getListJoueurs().add(joueur);
                        }
                        menu.actualiser();
                    }else if (test.equals("startPartie")){
                        arreter();
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