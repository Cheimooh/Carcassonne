package Jeu;

import javafx.scene.image.Image;
import javafx.scene.image.Image;

public enum TypeCarte {

    /*  P = Prairie
     *  C = Chemin
     *  V = Ville
     *  Les "lieux" sont dans l'ordre nord, est, sud, ouest
     */

    cartePPCC(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, new Image("Jeu/imgCartes/PPCC.jpg"), false),
    carteVCCP(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.prairie, new Image("Jeu/imgCartes/VCCP.jpg"), false),
    carteVCCV(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville, new Image("Jeu/imgCartes/VCCV.jpg"), false),
    carteVCCVBlason(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville, new Image("Jeu/imgCartes/VCCVBlason.jpg"), true),
    carteVVCV(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville, new Image("Jeu/imgCartes/VVCV.jpg"), false),
    carteVVCVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville, new Image("Jeu/imgCartes/VVCVBlason.jpg"), true),
    carteVVPV(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/VVPV.jpg"), false),
    carteVVPVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/VVPVBlason.jpg"), true),
    carteVCCC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new Image("Jeu/imgCartes/VCCC.jpg"), false),
    carteCPCP(CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie, new Image("Jeu/imgCartes/CPCP.jpg"), false),
    cartePCCC(CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new Image("Jeu/imgCartes/PCCC.jpg"), false),
    carteVPPP(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, new Image("Jeu/imgCartes/VPPP.jpg"), false),
    carteVVPP(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, new Image("Jeu/imgCartes/VVPP.jpg"), false),
    cartePPPP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, new Image("Jeu/imgCartes/PPPP.jpg"), false),
    cartePPCP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie, new Image("Jeu/imgCartes/PPCP.jpg"), false),
    carteVPPV(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/VPPV.jpg"), false),
    carteVPPVBlason(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/VPPVBlason.jpg"), true),
    cartePVPVRelieesBlason(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/PVPVBlason.jpg"), true),
    cartePVPVReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/PVPVReliees.jpg"), false),
    cartePVPVNonReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new Image("Jeu/imgCartes/PVPVNonReliees.jpg"), false),
    carteVCPC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, new Image("Jeu/imgCartes/VCPC.jpg"), false),
    carteVPCC(CoteCarte.ville, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, new Image("Jeu/imgCartes/VPCC.jpg"), false),
    carteVVVVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, new Image("Jeu/imgCartes/VVVVBlason.jpg"), true),
    carteCCCC(CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new Image("Jeu/imgCartes/CCCC.jpg"), false);

    private CoteCarte nord;
    private CoteCarte est;
    private CoteCarte sud;
    private CoteCarte ouest;
    private Image img;
    private boolean isBlason;

    TypeCarte(CoteCarte nord, CoteCarte est, CoteCarte sud, CoteCarte ouest, Image Image, boolean isBlason) {
        this.nord=nord;
        this.est=est;
        this.sud=sud;
        this.ouest=ouest;
        this.img=Image;
        this.isBlason=isBlason;
    }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getOuest() { return ouest; }

    public Image getImg() { return img; }

    public boolean isBlason() { return isBlason; }
}
