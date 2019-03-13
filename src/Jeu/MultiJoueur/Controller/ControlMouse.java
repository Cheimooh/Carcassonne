package Jeu.MultiJoueur.Controller;

import Jeu.MultiJoueur.Model.Carte;
import Jeu.View.FenetreJeu;
import Jeu.MultiJoueur.View.VueMultiJoueurs;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ControlMouse implements EventHandler<MouseEvent> {

    private FenetreJeu fenetreJeu;
    private VueMultiJoueurs vueMultiJoueurs;
    private Carte carteEnMain;
    private int mode; // 0 placement de carte

    public ControlMouse(VueMultiJoueurs vueMultiJoueurs1){
        vueMultiJoueurs = vueMultiJoueurs1;
        this.mode=0;
    }

    /*
     * S'active si l'on clique sur un endroit de la fenêtre de jeu
     */
    @Override
    public void handle(MouseEvent event) {
        placerCarte(event);
    }

    /*
     * Si l'on doit placer une carte sur la fenêtre, on appelle cette fonction
     */
    private void placerCarte(MouseEvent event){
        ObjectOutputStream oo = vueMultiJoueurs.getSocketJoueur().getOo();
        ObjectInputStream oi = vueMultiJoueurs.getSocketJoueur().getOi();
        // Position x de la dernière carte placée
        int xCartePlacee = (int) event.getX() / 50;
        // Position y de la dernière carte placée
        int yCartePlacee = (int) event.getY() / 50;
        Point point = new Point(xCartePlacee, yCartePlacee);
        try {
            oo.writeObject("j'envoie");
            oo.writeObject("poserCarte");
            oo.writeObject(point);
            String test = (String) oi.readObject();
            if (test.equals("erreur")){

            }else{
                vueMultiJoueurs.actualiserPoserCarte();
                oo.writeObject("j'envoie");
                oo.writeObject("tourSuivant");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void setCarteEnMain(Carte carteEnMain) { this.carteEnMain = carteEnMain; }

    public Carte getCarteEnMain() { return carteEnMain; }

    public void setMode(int mode) { this.mode = mode; }

    public FenetreJeu getFenetreJeu() { return fenetreJeu; }
}