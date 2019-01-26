package Jeu.ModelServeur;

import Jeu.ModelServeur.Serizable.ListJoueur;

import java.io.*;
import java.net.Socket;

public class Test {

    String nom;
    ListJoueur listJoueur;

    public static void main(String[] args) {

        Test test = new Test();
        test.nom = "theo";
        test.listJoueur = new ListJoueur();

        Socket sock = null;
        try {
            sock = new Socket("localhost", 3333);

            ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());

            oo.writeObject(test.nom);

            int nbJoueur = oi.readInt();

            for (int i = 0; i < nbJoueur; i++) {
                Joueur joueurTmp = (Joueur) oi.readObject();
                test.listJoueur.add(joueurTmp);
            }

            test.afficher();

            new ThreadJoueurClient(oi, oo, test);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void afficher(){
        for (int i = 0; i < listJoueur.size(); i++) {
            System.out.println(listJoueur.get(i).getNom());
        }
    }
}
