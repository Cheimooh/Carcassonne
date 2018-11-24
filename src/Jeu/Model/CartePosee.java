package Jeu.Model;

import java.awt.*;
import java.util.List;
import javafx.scene.image.Image;

/*
 * Carte qui a déjà été placée
 */
public class CartePosee {

    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private Image imageCarte;

    public CartePosee(Carte carte){
        nord = carte.getNord();
        sud = carte.getSud();
        est = carte.getEst();
        ouest = carte.getOuest();
        isAbbaye = carte.isAbbaye();
        imageCarte = setImageCarte(carte);
        position = carte.getPosition();
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

    public Image getImageCarte() { return imageCarte; }
}