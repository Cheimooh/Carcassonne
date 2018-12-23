package Jeu.Model;

import Jeu.Exception.PiocheVideException;

import java.util.*;

public class Pioche {
    private Queue<Carte> lCarte = new ArrayDeque<>();
    private int taille; // Taille de la pioche

    /*
     * Ajout de toutes les cartes dans la pioche
     */
    public Pioche(){
        for (int i = 0; i < 9 ; i++) { lCarte.add(new Carte(TypeCarte.cartePPCC)); }
        for (int i = 0; i < 4; i++) { lCarte.add(new Carte(TypeCarte.cartePCCC)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVCCP)); }
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.carteVCCVBlason)); }
        lCarte.add(new Carte(TypeCarte.carteVVCV));
        lCarte.add(new Carte(TypeCarte.carteVVPVBlason));
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVCCV)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVCCC)); }
        for (int i = 0; i < 8; i++) { lCarte.add(new Carte(TypeCarte.carteCPCP)); }
        for (int i = 0; i < 5; i++) { lCarte.add(new Carte(TypeCarte.carteVPPP)); }
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.carteVVPP)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVVPV)); }
        for (int i = 0; i < 4; i++) { lCarte.add(new Carte(TypeCarte.cartePPPP)); }
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.cartePPCP)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVPPV)); }
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.cartePVPVRelieesBlason)); }
        for (int i = 0; i < 4; i++) { lCarte.add(new Carte(TypeCarte.carteVCPC)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.carteVPCC)); }
        lCarte.add(new Carte(TypeCarte.cartePVPVReliees));
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.carteVVCVBlason)); }
        lCarte.add(new Carte(TypeCarte.carteVVVVBlason));
        lCarte.add(new Carte(TypeCarte.carteCCCC));
        for (int i = 0; i < 2; i++) { lCarte.add(new Carte(TypeCarte.carteVPPVBlason)); }
        for (int i = 0; i < 3; i++) { lCarte.add(new Carte(TypeCarte.cartePVPVNonReliees)); }

        taille=lCarte.size();

        melanger(lCarte);
    }

    /*
     * Mélange la pioche
     */
    private void melanger(Queue<Carte> lCarte) {
        List<Carte> liste = new ArrayList<>(lCarte);
        Collections.shuffle(liste);
        this.lCarte= new ArrayDeque<>(liste);
    }

    /*
     * Retire une carte de la pioche et la renvoie
     */
    public Carte piocher() throws PiocheVideException {
        if (taille>0) {
            taille--;
            return lCarte.poll();
        } else throw new PiocheVideException();
    }

    /*
     * Retourne la prochaine carte sans la supprimer de la pioche
     */
    public Carte getProchaineCarte() throws PiocheVideException{
        if (taille>0) {
            return lCarte.peek();
        } else throw new PiocheVideException();
    }

    public int getTaille() { return taille; }
}
