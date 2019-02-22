package Jeu;

import Jeu.ModelServeur.Joueur;
import Jeu.ModelServeur.SocketJoueur;

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
                            menu.getListJoueurs().add(joueur); // Bug -> Reception d'un joueur pas pret obligatoirement sauf le 1er
                        }
                        menu.actualiser();
                    }
                    else if (test.equals("quitter")){
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