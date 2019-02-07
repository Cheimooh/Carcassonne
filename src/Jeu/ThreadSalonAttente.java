package Jeu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
                ObjectOutputStream oo = menu.getOo();
                ObjectInputStream oi = menu.getOi();
                try {
                    if (((String) oi.readObject()).equals("je t'envoie")){
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