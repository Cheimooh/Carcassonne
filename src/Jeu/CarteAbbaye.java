package Jeu;

public class CarteAbbaye extends Carte {

    public CarteAbbaye(int valId) {
        super(valId, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie);
        super.setAbbaye(true);
    }
}
