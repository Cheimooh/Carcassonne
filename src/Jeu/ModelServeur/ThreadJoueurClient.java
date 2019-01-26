package Jeu.ModelServeur;

import Jeu.ModelServeur.Serizable.ListJoueur;

import java.io.*;

public class ThreadJoueurClient{
    private Thread rejoindrePartie;
    Test test;
    private boolean isArreter;
    ObjectInputStream oi;
    ObjectOutputStream oo;

    public ThreadJoueurClient(ObjectInputStream oi, ObjectOutputStream oo, Test test){
        isArreter = false;
        this.test = test;
        this.oi = oi;
        this.oo = oo;
        rejoindrePartie = new Thread(new ThreadJoueurClient.MajJoueur());
        rejoindrePartie.start();
    }

    private class MajJoueur implements Runnable{
        public void run(){
            do try {
                if(((String) oi.readObject()).equals("j'envoie")){
                    test.listJoueur.clear();
                    int nbJoueur = oi.readInt();

                    for (int i = 0; i < nbJoueur; i++) {
                        Joueur joueurTmp = (Joueur) oi.readObject();
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