package Jeu.ModelServeur;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Test {

    String nom;
    List<Joueur> listJoueur;

    public static void main(String[] args) {

        Test test = new Test();
        test.nom = "theo";
        test.listJoueur = new ArrayList<>();

        Socket sock = null;
        try {
            sock = new Socket("localhost", 3333);

            ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());
            SocketJoueur socketJoueur = new SocketJoueur(sock, oi, oo);

            int nbJoueur = oi.readInt();

            for (int i = 0; i < nbJoueur; i++) {
                Joueur joueurTmp = (Joueur) oi.readObject();
                test.listJoueur.add(joueurTmp);
            }

            oo.writeObject(test.nom);
            oo.writeObject("rouge");

            test.listJoueur.add(new Joueur(test.nom, "rouge"));

            new ThreadJoueurClient(socketJoueur, test);

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
