package Jeu.ModelServeur.Serizable;

import Jeu.ModelServeur.Joueur;

import java.io.Serializable;
import java.util.ArrayList;

public class ListJoueur implements Serializable {
    public ArrayList<Joueur> listJoueur;

    public ListJoueur() {
        this.listJoueur = new ArrayList<>();
    }

    public void add(Joueur joueur) {
        listJoueur.add(joueur);
    }

    public int size() {
        return listJoueur.size();
    }

    public Joueur get(int i) {
        return listJoueur.get(i);
    }

    public void clear() {
        listJoueur.clear();
    }
}
