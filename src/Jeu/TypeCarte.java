package Jeu;

import javafx.scene.image.ImageView;

public enum TypeCarte {

    /*  P = Prairie
     *  C = Chemin
     *  V = Ville
     *  Les "lieux" sont dans l'ordre nord, est, sud, ouest
     */

    cartePPCC(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg")),
    carteVCCP(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    carteVCCV(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    carteVVCV(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    carteVVPV(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    carteVCCC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg")),
    carteCPCP(CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    cartePCCC(CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg")),
    carteVPPP(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    carteVVPP(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    cartePPPP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    cartePPCP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie, new ImageView("Jeu/prairie.jpg")),
    carteVPPV(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    cartePVPV(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    carteVCPC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg")),
    carteVPCC(CoteCarte.ville, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg")),
    carteVVVV(CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, new ImageView("Jeu/prairie.jpg")),
    carteCCCC(CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, new ImageView("Jeu/prairie.jpg"));

    private CoteCarte nord;
    private CoteCarte est;
    private CoteCarte sud;
    private CoteCarte ouest;
    private ImageView img;

    TypeCarte(CoteCarte nord, CoteCarte est, CoteCarte sud, CoteCarte ouest, ImageView imageView) {
        this.nord=nord;
        this.est=est;
        this.sud=sud;
        this.ouest=ouest;
        this.img=imageView;
        img.setFitHeight(50);
        img.setFitWidth(50);
    }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getOuest() { return ouest; }

    public ImageView getImg() { return img; }
}
