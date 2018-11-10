package Jeu.Model;

import java.awt.*;
import javafx.scene.image.Image;

public class CartePosse {

    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private Image imageCarte;

    public CartePosse(Carte carte){
        this.nord = carte.getNord();
        this.sud = carte.getSud();
        this.est = carte.getEst();
        this.ouest = carte.getOuest();
        this.isAbbaye = carte.isAbbaye();
        this.imageCarte = setImageCarte(carte);
        this.position = carte.getPosition();
    }

    private Image setImageCarte(Carte carte){
        Image image;
        switch (carte.getNbRotation()){
            case 0:
                image= carte.getDraw().getImg();
                break;
            case 1:
                image = carte.getDraw().getImg90();
                break;
            case 2:
                image = carte.getDraw().getImg180();
                break;
            case 3:
                image = carte.getDraw().getImg270();
                break;
            default:
                image=null;
        }
        return image;
    }

    public Point getPosition() { return position; }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getOuest() { return ouest; }

    public boolean isAbbaye() { return isAbbaye; }

    public Image getImageCarte() { return imageCarte; }
}