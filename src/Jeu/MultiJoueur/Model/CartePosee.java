package Jeu.MultiJoueur.Model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Jeu.MultiJoueur.Model.Carte qui a déjà été placée
 */
public class CartePosee implements Serializable, Cloneable {

    private Point position;
    private CoteCarte nord, sud, est, ouest;
    private boolean isAbbaye;
    private String imageCarte;
    private ArrayList<CoteCarte> listeZones;
    private Map<Integer, String> zonesCouleurPartisan;
    private ArrayList<Point> positionsCoordonnees;
    private int[][] zonesControlleesParLesPoints;
    private String type;
    private Partisan[] zonesControlleesParLesPartisans;

    public CartePosee(Carte carte) {
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
        positionsCoordonnees = carte.getPositionsCoordonnees();
        zonesControlleesParLesPoints = carte.getZonesControlleesParLesPoints();
        zonesControlleesParLesPartisans = carte.getZonesControlleesParLesPartisans();
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
        if (cote.equals(CoteCarte.ville)) {
            for (int i = 0; i < 3; i++) {
                listeZones.add(CoteCarte.ville);
            }
        } else {
            listeZones.add(CoteCarte.prairie);
            listeZones.add(cote); // Ajoute soit un chemin soit une prairie
            listeZones.add(CoteCarte.prairie);
        }
    }

    private String setImageCarte(Carte carte) {
        String image;
        switch (carte.getNbRotation()) {
            case 0:
                image = carte.getDraw().getPath();
                break;
            case 1:
                image = carte.getDraw().getPath90();
                break;
            case 2:
                image = carte.getDraw().getPath180();
                break;
            case 3:
                image = carte.getDraw().getPath270();
                break;
            default:
                image = null;
        }
        return image;
    }

    public void addZonesOccupees(int numZone, String couleurJoueur) {
        for (int i = 0; i < zonesControlleesParLesPoints.length; i++) {
            if (i == numZone) {
                for (int j = 0; j < zonesControlleesParLesPoints[i].length; j++) {
                    zonesCouleurPartisan.put(zonesControlleesParLesPoints[i][j], couleurJoueur);
                }
            }
        }
    }

    public void attributionPartisan(Partisan p, int numZone) {
        if (p != null) {
            zonesControlleesParLesPartisans[numZone] = p;
        }
    }

    public void retirerPartisan(int numZone) {
        zonesControlleesParLesPartisans[numZone]=null;

    }

    @Override
    public CartePosee clone() throws CloneNotSupportedException {
        return (CartePosee)super.clone();
    }

    public Point getPosition() {
        return position;
    }

    public CoteCarte getNord() {
        return nord;
    }

    public CoteCarte getSud() {
        return sud;
    }

    public CoteCarte getEst() {
        return est;
    }

    public CoteCarte getOuest() {
        return ouest;
    }

    public String getImageCarte() {
        return imageCarte;
    }

    public Map<Integer, String> getZonesCouleurPartisan() {
        return zonesCouleurPartisan;
    }

    public void setZonesCouleurPartisan(Map<Integer, String> zonesCouleurPartisan) {
        this.zonesCouleurPartisan = zonesCouleurPartisan;
    }

    public int[][] getZonesControlleesParLesPoints() {
        return zonesControlleesParLesPoints;
    }

    public ArrayList<Point> getPositionsCoordonnees() {
        return positionsCoordonnees;
    }

    public ArrayList<CoteCarte> getListeZones() {
        return listeZones;
    }

    public String getType() {
        return type;
    }

    public Partisan[] getZonesControlleesParLesPartisans() { return zonesControlleesParLesPartisans; }
}