package Jeu.ModelServeur;

import Jeu.ModelServeur.Serizable.ListJoueur;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Test {


    public static void main(String[] args) {

        String nom = "Michele";
        ListJoueur listJoueur = new ListJoueur();

        Socket sock = null;
        try {
            sock = new Socket("62.39.234.71", 3333);

            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintStream ps = new PrintStream(sock.getOutputStream());

            DataInputStream di = new DataInputStream(sock.getInputStream());
            DataOutputStream dO = new DataOutputStream(sock.getOutputStream());

            ObjectOutputStream oo = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(sock.getInputStream());

            System.out.println("initialisation des flux");

            System.out.println("envoie du nom ... ");
            ps.print(nom);
            System.out.println("Fait");

            System.out.println("Reception du nombre de joueur ...");
            int nbJoueur = di.readInt();
            System.out.println("Fait");

            for (int i = 0; i < nbJoueur; i++) {
                Joueur joueurTmp = (Joueur) oi.readObject();
                listJoueur.add(joueurTmp);
            }

            oo.close();
            oi.close();
            dO.close();
            di.close();
            ps.close();
            br.close();

            System.out.println("Fin");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
