package Jeu.MultiJoueur;

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

                    SocketJoueur socketJoueur = new SocketJoueur(sock, oi, oo);
                    carcassonne.getTabSocket().add(socketJoueur);

                    int nbJoueur = carcassonne.getListJoueur().size();
                    oo.writeObject(nbJoueur);

                    for (int i = 0; i < nbJoueur; i++) {
                        Joueur joueurTmp = carcassonne.getListJoueur().get(i);
                        oo.writeObject(joueurTmp);
                    }

                    Joueur newJoueur = (Joueur) oi.readObject();

                    carcassonne.ajouterJoueurDansPartie(newJoueur);

                    carcassonne.miseAJourJoueur();
                    carcassonne.getListReceptionClient().add(new ThreadReceptionClient(carcassonne, socketJoueur, carcassonne.getListJoueur().size()-1));
                } else {
                    sock.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } while(!isArreter);
        }
    }

    public void arreter(){
        isArreter = true;
    }
}