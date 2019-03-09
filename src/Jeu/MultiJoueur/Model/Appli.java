package Jeu.MultiJoueur.Model;

import java.io.IOException;
import java.net.ServerSocket;

public class Appli {

    public static void main(String[] args) {
        try {
            ServerSocket conn = new ServerSocket(3333);
            System.out.println("Le serveur tourne !!!!");
            Carcassonne carcassonne = new Carcassonne(conn);
        }
        catch(IOException e) {
            System.out.println("problème création socket serveur : "+e.getMessage());
            System.exit(1);
        }
    }
}