package Jeu.ModelServeur;

import java.io.IOException;

public class ThreadReceptionClient {
    private Thread rejoindrePartie;
    private Carcassonne carcassonne;
    private SocketJoueur socketJoueur;
    private boolean isArreter;
    private int idList;

    public ThreadReceptionClient(Carcassonne carcassonne, SocketJoueur socketJoueur, int idList){
        isArreter = false;
        this.carcassonne = carcassonne;
        this.socketJoueur = socketJoueur;
        this.idList = idList;
        rejoindrePartie = new Thread(new ThreadReceptionClient.TraitementReception());
        rejoindrePartie.start();
    }

    private class TraitementReception implements Runnable{
        public void run(){
            do try {
                if ((socketJoueur.getOi().readObject()).equals("j'envoie")){
                    Object objetRecu = socketJoueur.getOi().readObject();
                    if(objetRecu instanceof String){
                        String string = (String) objetRecu;
                        if(string.equals("pret")){
                            carcassonne.getTabJoueur().get(idList).setPret(true);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } while(!isArreter);
        }
    }

    public void arreter(){
        isArreter = true;
    }
}
