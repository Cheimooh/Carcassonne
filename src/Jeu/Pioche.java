package Jeu;

import javafx.scene.control.Tab;

import java.util.ArrayList;

public class Pioche {
    private ArrayList <Carte> lCarte = new ArrayList<>();
    private final int TAILLE = 10;

    public Pioche(){
       for (int i = 0; i < TAILLE ; i++) {
            lCarte.add(new Carte(i, 0,0,0,0));
        }
    }

    public ArrayList<Carte> getlCarte() {
        return lCarte;
    }

    public void setlCarte(ArrayList<Carte> lCarte) {
        this.lCarte = lCarte;
    }

    public int getTAILLE() {
        return TAILLE;
    }
}
