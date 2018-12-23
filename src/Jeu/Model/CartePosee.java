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
    private Map<Integer, Color> zonesCouleurPartisan;
    private ArrayList<Point> positionsCoordonnees;
    private int[][] zonesControlleesParLesPoints;
    private String type;

    public CartePosee(Carte carte){
        type = carte.getType();
        nord = carte.getNord();
        sud = carte.getSud();
        est = carte.getEst();
        ouest = carte.getOuest();
        isAbbaye = carte.isAbbaye();
        imageCarte = setImageCarte(carte);
        position = carte.getPosition();
        listeZones = new ArrayList<>();
        zonesCouleurPartisan = new HashMap<>();
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
                image= new Image(carte.getDraw().getPath());
                break;
            case 1:
                image = new Image(carte.getDraw().getPath90());
                break;
            case 2:
                image = new Image(carte.getDraw().getPath180());
                break;
            case 3:
                image = new Image(carte.getDraw().getPath270());
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
                    zonesCouleurPartisan.put(zonesControlleesParLesPoints[i][j], couleurJoueur);
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

    public Map<Integer, Color> getZonesCouleurPartisan() { return zonesCouleurPartisan; }

    public void setZonesCouleurPartisan(Map<Integer, Color> zonesCouleurPartisan) { this.zonesCouleurPartisan = zonesCouleurPartisan; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }

    public ArrayList<Point> getPositionsCoordonnees() { return positionsCoordonnees; }

    public ArrayList<CoteCarte> getListeZones() { return listeZones; }

    public String getType() { return type; }
}