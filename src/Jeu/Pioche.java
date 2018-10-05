package Jeu;

import java.util.ArrayDeque;
import java.util.Queue;

public class Pioche {
    private Queue<Carte> lCarte = new ArrayDeque<>();
    private int taille;

    public Pioche(int taille){
        this.taille = taille;
        for (int i = 0; i < taille ; i++) {
            lCarte.add(new Carte(i, TypeCarte.cartePPPP));
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
