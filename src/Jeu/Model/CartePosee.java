package Jeu.Model;



import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/*
 * Carte qui a déjà été placée
 */
public class CartePosee {

    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private Image imageCarte;
    private ArrayList<CoteCarte> listeZones;
    private Map<Integer, Color> zonesOccupees;
    private ArrayList<Point> positionsCoordonnees;
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
        zonesOccupees = new HashMap<>();
        positionsCoordonnees=carte.getPositionsCoordonnees();
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

    public void addZonesOccupees(int numZone, Color couleurJoueur) {

        for (int i = 0; i < zonesControlleesParLesPoints.length ; i++) {
            if (i==numZone) {
                for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                    zonesOccupees.put(zonesControlleesParLesPoints[i][j], couleurJoueur);
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

    public Map<Integer, Color> getZonesOccupees() { return zonesOccupees; }

    public void setZonesOccupees(Map<Integer, Color> zonesOccupees) { this.zonesOccupees = zonesOccupees; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }

    public ArrayList<Point> getPositionsCoordonnees() { return positionsCoordonnees; }
}