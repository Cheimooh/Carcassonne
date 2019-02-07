package Jeu;

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
            ObjectOutputStream oo = menu.getOo();

            menu.actualiser(menu.getGPElements());




        }
    }


}