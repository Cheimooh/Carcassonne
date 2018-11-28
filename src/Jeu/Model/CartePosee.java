package Jeu.Model;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.image.Image;

/*
 * Carte qui a déjà été placée
 */
public class CartePosee {

    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private Image imageCarte;
    private ArrayList<CoteCarte> listeZones;
    private ArrayList<Integer> zonesOccupees;
    private int[][] zonesControlleesParLesPoints;

    public CartePosee(Carte carte){
        nord = carte.getNord();
        sud = carte.getSud();
        est = carte.getEst();
        ouest = carte.getOuest();
        isAbbaye = carte.isAbbaye();
        imageCarte = setImageCarte(carte);
        position = carte.getPosition();
        listeZones = new ArrayList<>();
        zonesOccupees = new ArrayList<>();
        zonesControlleesParLesPoints = carte.getZonesControlleesParLesPoints();
        addZones();
    }

    public void addZones() {
        addZone(nord);

        addZone(est);

        addZone(sud);

        addZone(ouest);
    }

    private void addZone(CoteCarte cote) {
        // Si le centre du côté est une ville
        if (cote.equals(CoteCarte.ville)){
            for (int i = 0; i < 3 ; i++) {
                listeZones.add(CoteCarte.ville);
            }
        } else {
            listeZones.add(CoteCarte.prairie);
            listeZones.add(cote); // Ajoute soit un chemin soit une prairie
            listeZones.add(CoteCarte.prairie);
        }
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

    public void addZonesOccupees(int numZone) {
        for (int i = 0; i < zonesControlleesParLesPoints.length ; i++) {
            if (i==numZone) {
                for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                    zonesOccupees.add(zonesControlleesParLesPoints[i][j]);
                }
            }
        }
    }

    public Point getPosition() { return position; }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getOuest() { return ouest; }

    public Image getImageCarte() { return imageCarte; }

    public ArrayList<CoteCarte> getListeZones() { return listeZones; }

    public void setZonesOccupees(ArrayList<Integer> zonesOccupees) { this.zonesOccupees = zonesOccupees; }

    public ArrayList<Integer> getZonesOccupees() { return zonesOccupees; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }
}