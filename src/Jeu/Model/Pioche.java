package Jeu.Model;

import java.util.*;

public class Pioche {
    private Queue<Carte> lCarte = new ArrayDeque<>();
    private int taille;

    public Pioche(int taille){
        this.taille = taille;
        for (int i = 0; i < 9 ; i++) {
            lCarte.add(new Carte(TypeCarte.cartePPCC));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVCCP));
        }
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.carteVCCVBlason));
        }
        lCarte.add(new Carte(TypeCarte.carteVVCV));
        lCarte.add(new Carte(TypeCarte.carteVVPVBlason));
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVCCV));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVCCC));
        }
        for (int i = 0; i < 8; i++) {
            lCarte.add(new Carte(TypeCarte.carteCPCP));
        }
        for (int i = 0; i < 4; i++) {
            lCarte.add(new Carte(TypeCarte.cartePCCC));
        }
        for (int i = 0; i < 5; i++) {
            lCarte.add(new Carte(TypeCarte.carteVPPP));
        }
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.carteVVPP));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVVPV));
        }
        for (int i = 0; i < 4; i++) {
            lCarte.add(new Carte(TypeCarte.cartePPPP));
        }
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.cartePPCP));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVPPV));
        }
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.cartePVPVRelieesBlason));
        }
        for (int i = 0; i < 4; i++) {
            lCarte.add(new Carte(TypeCarte.carteVCPC));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.carteVPCC));
        }
        lCarte.add(new Carte(TypeCarte.cartePVPVReliees));
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.carteVVCVBlason));
        }
        lCarte.add(new Carte(TypeCarte.carteVVVVBlason));
        lCarte.add(new Carte(TypeCarte.carteCCCC));
        for (int i = 0; i < 2; i++) {
            lCarte.add(new Carte(TypeCarte.carteVPPVBlason));
        }
        for (int i = 0; i < 3; i++) {
            lCarte.add(new Carte(TypeCarte.cartePVPVNonReliees));
        }
        melanger(lCarte);
    }

    private void melanger(Queue<Carte> lCarte) {
        List<Carte> liste = new ArrayList<Carte>();
        for(Carte c: lCarte){
            liste.add(c);
        }
        Collections.shuffle(liste);
        Queue<Carte> queue = new ArrayDeque<>();
        for (int i=0; i<liste.size(); i++){
            queue.add(liste.get(i));
        }
        this.lCarte=queue;
    }

    public int getTaille() {
        return taille;
    }

    public Carte piocher(){
        taille--;
        return lCarte.poll();
    }

    public Carte getProchaineCarte(){
        return lCarte.peek();
    }
}
