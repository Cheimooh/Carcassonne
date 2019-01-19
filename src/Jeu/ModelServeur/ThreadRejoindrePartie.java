package Jeu.ModelServeur;

import Jeu.ModelServeur.Serizable.ListJoueur;

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
            System.out.println("peuvent rejoindre");
            Socket sock = null;
            do try {
                sock = serverSocket.accept();
                if (!isArreter) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintStream ps = new PrintStream(sock.getOutputStream());

                    DataInputStream di = new DataInputStream(sock.getInputStream());
                    DataOutputStream dO = new DataOutputStream(sock.getOutputStream());

                    ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());
                    ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());

                    System.out.println("initialisation des flux");

                    System.out.println("Reception du nom ...");
                    String nomJoueur = br.readLine();
                    System.out.println("Fait");

                    carcassonne.ajouterJoueurDansPartie(nomJoueur);
                    carcassonne.getTabSocket().add(sock);

                    System.out.println("Envoie du nombre de joueur ...");
                    int nbJoueur = carcassonne.getTabJoueur().size();
                    dO.write(nbJoueur);
                    System.out.println("Fait");

                    for (int i = 0; i < nbJoueur; i++) {
                        Joueur joueurTmp = carcassonne.getTabJoueur().get(i);
                        oo.writeObject(joueurTmp);
                    }

                    oo.close();
                    oi.close();
                    dO.close();
                    di.close();
                    ps.close();
                    br.close();

                    System.out.println("Fin");
                } else {
                    sock.close();
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } while(!isArreter);
        }
    }

    public void arreter(){
        isArreter = true;
    }
}