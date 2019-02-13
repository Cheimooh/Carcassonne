package Jeu;

import Jeu.ModelServeur.Joueur;
import Jeu.ModelServeur.SocketJoueur;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

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
                List<Joueur> tabJoueurs = menu.getListJoueurs();
                try {
                    if ((socket.getOi().readObject()).equals("j'envoie")){
                        int nombreJoueur = oi.readInt();
                        menu.setNombreJoueur(nombreJoueur);

                        for (int i = 0; i < nombreJoueur; i++) {
                            Joueur joueur = (Joueur) oi.readObject();
                            tabJoueurs.add(joueur);
                        }
                        menu.actualiser();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }while(!isArreter);
        }
    }
}