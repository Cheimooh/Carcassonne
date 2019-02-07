package Jeu;

import Jeu.ModelServeur.SocketJoueur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadSalonAttente {
    private Thread ThreadSalonAttente;
    private Menu menu;
    private boolean isArreter;

    public ThreadSalonAttente(Menu menu){
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
                try {
                    if (((String) socket.getOi().readObject()).equals("je t'envoie")){
                        menu.actualiser(menu.getGPElements());
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