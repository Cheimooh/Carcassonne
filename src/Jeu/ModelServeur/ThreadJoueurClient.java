package Jeu.ModelServeur;

import java.io.*;

public class ThreadJoueurClient{
    private Thread rejoindrePartie;
    Test test;
    private boolean isArreter;
    SocketJoueur socketJoueur;

    public ThreadJoueurClient(SocketJoueur socketJoueur, Test test){
        isArreter = false;
        this.test = test;
        this.socketJoueur = socketJoueur;
        rejoindrePartie = new Thread(new ThreadJoueurClient.MajJoueur());
        rejoindrePartie.start();
    }

    private class MajJoueur implements Runnable{
        public void run(){
            do try {
                String te = (String) socketJoueur.getOi().readObject();
                if(te.equals("j'envoie")){
                    int nbJoueur = socketJoueur.getOi().readInt();
                    for (int i = 0; i < nbJoueur; i++) {
                        Joueur joueurTmp = (Joueur) socketJoueur.getOi().readObject();
                        test.listJoueur.add(joueurTmp);
                    }
                    test.afficher();
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