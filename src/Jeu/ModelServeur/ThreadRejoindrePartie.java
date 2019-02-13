package Jeu.ModelServeur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadRejoindrePartie {
    private Thread rejoindrePartie;
    private Carcassonne carcassonne;
    private ServerSocket serverSocket;
    private boolean isArreter;

    public ThreadRejoindrePartie(Carcassonne carcassonne, ServerSocket serverSocket){
        isArreter = false;
        this.carcassonne = carcassonne;
        this.serverSocket = serverSocket;
        rejoindrePartie = new Thread(new ThreadRejoindrePartie.TraitementRejoindre());
        rejoindrePartie.start();
    }

    private class TraitementRejoindre implements Runnable{
        public void run(){
            Socket sock = null;
            do try {
                sock = serverSocket.accept();
                if (!isArreter) {
                    ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());
                    ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());

                    int nbJoueur = carcassonne.getTabJoueur().size();
                    oo.writeInt(nbJoueur);

                    for (int i = 0; i < nbJoueur; i++) {
                        Joueur joueurTmp = carcassonne.getTabJoueur().get(i);
                        oo.writeObject(joueurTmp);
                    }

                    Joueur newJoueur = (Joueur) oi.readObject();

                    carcassonne.ajouterJoueurDansPartie(newJoueur);
                    carcassonne.getTabSocket().add(new SocketJoueur(sock, oi, oo));

                    carcassonne.miseAJourJoueur();
                } else {
                    sock.close();
                    continue;
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