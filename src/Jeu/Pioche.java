package Jeu;

import javafx.scene.control.Tab;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Pioche {
    private Queue<Carte> lCarte = new ArrayDeque<>();
    private int taille = 71; // Quand trop grand, java.lang.NullPointerException.

    public Pioche(){
       for (int i = 0; i < taille ; i++) {
           lCarte.add(new Carte(i, 0,0,0,0));
       }
    }

    public Queue<Carte> getPioche() {
        return lCarte;
    }

    public void setPioche(Queue<Carte> lCarte) {
        this.lCarte = lCarte;
    }

    public int getTaille() {
        return taille;
    }

    public Carte piocher(){
        taille--;
        return lCarte.poll();
    }
}
