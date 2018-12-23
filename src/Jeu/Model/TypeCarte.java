package Jeu.Model;

import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;

public enum TypeCarte {
    /*  P = Prairie
     *  C = Chemin
     *  V = Ville
     *  Les "lieux" sont dans l'ordre nord, est, sud, ouest
     */

    cartePPCC(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            "Jeu/imgCartes/PPCC.jpg","Jeu/imgCartes/90degres/PPCC.jpg",
            "Jeu/imgCartes/180degres/PPCC.jpg","Jeu/imgCartes/270degres/PPCC.jpg",
            false, new Point[]{new Point(38,14), new Point(11,39), new Point(24,27)},
            new int[][]{{1,2,3,4,5,6,7,12},{9,10},{8,11}}, "PPCC"),
    carteVCCP(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.prairie,
            "Jeu/imgCartes/VCCP.jpg","Jeu/imgCartes/90degres/VCCP.jpg",
            "Jeu/imgCartes/180degres/VCCP.jpg","Jeu/imgCartes/270degres/VCCP.jpg",
            false, new Point[]{new Point(26,7), new Point(12,26), new Point(31,31), new Point(40,40)},
            new int[][]{{1,2,3},{4,9,10,11,12},{5,8},{6,7}}, "VCCP"),
    carteVCCV(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            "Jeu/imgCartes/VCCV.jpg","Jeu/imgCartes/90degres/VCCV.jpg",
            "Jeu/imgCartes/180degres/VCCV.jpg","Jeu/imgCartes/270degres/VCCV.jpg",
            false, new Point[]{new Point(9,9), new Point(26,27), new Point(35,35), new Point(43,42)},
            new int[][]{{1,2,3,10,11,12},{4,9},{5,8},{6,7}}, "VCCV"),
    carteVCCVBlason(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            "Jeu/imgCartes/VCCVBlason.jpg","Jeu/imgCartes/90degres/VCCVBlason.jpg",
            "Jeu/imgCartes/180degres/VCCVBlason.jpg","Jeu/imgCartes/270degres/VCCVBlason.jpg",
            true, new Point[]{new Point(9,9), new Point(26,27), new Point(35,35), new Point(43,42)},
            new int[][]{{1,2,3,10,11,12},{4,9},{5,8},{6,7}}, "VCCVBla"),
    carteVVCV(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            "Jeu/imgCartes/VVCV.jpg","Jeu/imgCartes/90degres/VVCV.jpg",
            "Jeu/imgCartes/180degres/VVCV.jpg","Jeu/imgCartes/270degres/VVCV.jpg",
            false, new Point[]{new Point(25,20),new Point(26,43),new Point(15,46),new Point(38,46)},
            new int[][]{{1,2,3,4,5,6,10,11,12},{8},{9},{7}}, "VVCV"),
    carteVVCVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            "Jeu/imgCartes/VVCVBlason.jpg","Jeu/imgCartes/90degres/VVCVBlason.jpg",
            "Jeu/imgCartes/180degres/VVCVBlason.jpg","Jeu/imgCartes/270degres/VVCVBlason.jpg",
            true, new Point[]{new Point(25,20),new Point(26,43),new Point(15,46),new Point(38,46)},
            new int[][]{{1,2,3,4,5,6,10,11,12},{8},{9},{7}}, "VVCVBla"),
    carteVVPV(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/VVPV.jpg","Jeu/imgCartes/90degres/VVPV.jpg",
            "Jeu/imgCartes/180degres/VVPV.jpg","Jeu/imgCartes/270degres/VVPV.jpg",
            false, new Point[]{new Point(25,20),new Point(27,43)},
            new int[][]{{1,2,3,4,5,6,10,11,12},{7,8,9}}, "VVPV"),
    carteVVPVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/VVPVBlason.jpg","Jeu/imgCartes/90degres/VVPVBlason.jpg",
            "Jeu/imgCartes/180degres/VVPVBlason.jpg","Jeu/imgCartes/270degres/VVPVBlason.jpg",
            true, new Point[]{new Point(25,20),new Point(27,43)},
            new int[][]{{1,2,3,4,5,6,10,11,12},{7,8,9}}, "VVPVBla"),
    carteVCCC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            "Jeu/imgCartes/VCCC.jpg","Jeu/imgCartes/90degres/VCCC.jpg"
            ,"Jeu/imgCartes/180degres/VCCC.jpg","Jeu/imgCartes/270degres/VCCC.jpg",
            false, new Point[]{new Point(25,4),new Point(26,21),new Point(11,26),new Point(42,28),
            new Point(24,40),new Point(9,40),new Point(39,41)},
            new int[][]{{1,2,3},{4,12},{11},{5},{8},{9,10},{6,7}}, "VCCC"),
    carteCPCP(CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            "Jeu/imgCartes/CPCP.jpg","Jeu/imgCartes/90degres/CPCP.jpg",
            "Jeu/imgCartes/180degres/CPCP.jpg","Jeu/imgCartes/270degres/CPCP.jpg",
            false, new Point[]{new Point(10,25),new Point(26,26),new Point(40,25)},
            new int[][]{{1,9,10,11,12},{2,8},{3,4,5,6,7}}, "CPCP"),
    cartePCCC(CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            "Jeu/imgCartes/PCCC.jpg","Jeu/imgCartes/90degres/PCCC.jpg",
            "Jeu/imgCartes/180degres/PCCC.jpg","Jeu/imgCartes/270degres/PCCC.jpg",
            false, new Point[]{new Point(25,8),new Point(7,25),new Point(46,24),
            new Point(12,39),new Point(26,39),new Point(41,38)},
            new int[][]{{1,2,3,4,12},{11},{5},{9,10},{8},{6,7}}, "PCCC"),
    carteVPPP(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            "Jeu/imgCartes/VPPP.jpg","Jeu/imgCartes/90degres/VPPP.jpg",
            "Jeu/imgCartes/180degres/VPPP.jpg","Jeu/imgCartes/270degres/VPPP.jpg",
            false,new Point[]{new Point(25,5),new Point(25,30)},
            new int[][]{{1,2,3},{4,5,6,7,8,9,10,11,12}}, "VPPP"),
    carteVVPP(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie,
            "Jeu/imgCartes/VVPP.jpg","Jeu/imgCartes/90degres/VVPP.jpg",
            "Jeu/imgCartes/180degres/VVPP.jpg","Jeu/imgCartes/270degres/VVPP.jpg",
            false,new Point[]{new Point(24,3),new Point(45,25),new Point(17,29)},
            new int[][]{{1,2,3},{4,5,6},{7,8,9,10,11,12}}, "VVPP"),
    cartePPPP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            "Jeu/imgCartes/PPPP.jpg","Jeu/imgCartes/90degres/PPPP.jpg",
            "Jeu/imgCartes/180degres/PPPP.jpg","Jeu/imgCartes/270degres/PPPP.jpg",
            false,new Point[]{new Point(25,25)},
            new int[][]{{1,2,3,4,5,6,7,8,9,10,11,12}}, "PPPP"),
    cartePPCP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            "Jeu/imgCartes/PPCP.jpg","Jeu/imgCartes/90degres/PPCP.jpg",
            "Jeu/imgCartes/180degres/PPCP.jpg","Jeu/imgCartes/270degres/PPCP.jpg",
            false,new Point[]{new Point(25,25),new Point(23,40)},
            new int[][]{{1,2,3,4,5,6,7,9,10,11,12},{8}}, "PPCP"),
    carteVPPV(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/VPPV.jpg","Jeu/imgCartes/90degres/VPPV.jpg",
            "Jeu/imgCartes/180degres/VPPV.jpg","Jeu/imgCartes/270degres/VPPV.jpg",
            false,new Point[]{new Point(9,8),new Point(33,31)},
            new int[][]{{1,2,3,10,11,12},{4,5,6,7,8,9}}, "VPPV"),
    carteVPPVBlason(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/VPPVBlason.jpg","Jeu/imgCartes/90degres/VPPVBlason.jpg",
            "Jeu/imgCartes/180degres/VPPVBlason.jpg","Jeu/imgCartes/270degres/VPPVBlason.jpg",
            true,new Point[]{new Point(9,8),new Point(33,31)},
            new int[][]{{1,2,3,10,11,12},{4,5,6,7,8,9}}, "VPPVBla"),
    cartePVPVRelieesBlason(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/PVPVBlason.jpg","Jeu/imgCartes/90degres/PVPVBlason.jpg",
            "Jeu/imgCartes/180degres/PVPVBlason.jpg","Jeu/imgCartes/270degres/PVPVBlason.jpg",
            true,new Point[]{new Point(26,22),new Point(29,3),new Point(26,44)},
            new int[][]{{4,5,6,10,11,12},{1,2,3},{7,8,9}}, "PVPVRelBla"),
    cartePVPVReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/PVPVReliees.jpg","Jeu/imgCartes/90degres/PVPVReliees.jpg",
            "Jeu/imgCartes/180degres/PVPVReliees.jpg","Jeu/imgCartes/270degres/PVPVReliees.jpg",
            false,new Point[]{new Point(26,22),new Point(29,3),new Point(26,44)},
            new int[][]{{4,5,6,10,11,12},{1,2,3},{7,8,9}}, "PVPVRel"),
    cartePVPVNonReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            "Jeu/imgCartes/PVPVNonReliees.jpg","Jeu/imgCartes/90degres/PVPVNonReliees.jpg",
            "Jeu/imgCartes/180degres/PVPVNonReliees.jpg","Jeu/imgCartes/270degres/PVPVNonReliees.jpg",
            false,new Point[]{new Point(26,25),new Point(5,25),new Point(45,25)},
            new int[][]{{1,2,3,7,8,9},{10,11,12},{4,5,6}}, "PVPV"),
    carteVCPC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin,
            "Jeu/imgCartes/VCPC.jpg","Jeu/imgCartes/90degres/VCPC.jpg",
            "Jeu/imgCartes/180degres/VCPC.jpg","Jeu/imgCartes/270degres/VCPC.jpg",
            false,new Point[]{new Point(26,4),new Point(7,18),new Point(27,22),new Point(25,40)},
            new int[][]{{1,2,3},{4,12},{5,11},{6,7,8,9,10}}, "VCPC"),
    carteVPCC(CoteCarte.ville, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            "Jeu/imgCartes/VPCC.jpg","Jeu/imgCartes/90degres/VPCC.jpg",
            "Jeu/imgCartes/180degres/VPCC.jpg","Jeu/imgCartes/270degres/VPCC.jpg",
            false,new Point[]{new Point(25,5),new Point(34,23),new Point(19,32),new Point(10,40)},
            new int[][]{{1,2,3},{4,5,6,7,12},{8,11},{9,10}}, "VPCC"),
    carteVVVVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, CoteCarte.ville,
            "Jeu/imgCartes/VVVVBlason.jpg","Jeu/imgCartes/90degres/VVVVBlason.jpg",
            "Jeu/imgCartes/180degres/VVVVBlason.jpg","Jeu/imgCartes/270degres/VVVVBlason.jpg",
            true,new Point[]{new Point(25,25)},new int[][]{{1,2,3,4,5,6,7,8,9,10,11,12}}, "VVVVBla"),
    carteCCCC(CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            "Jeu/imgCartes/CCCC.jpg","Jeu/imgCartes/90degres/CCCC.jpg",
            "Jeu/imgCartes/180degres/CCCC.jpg","Jeu/imgCartes/270degres/CCCC.jpg",
            false,new Point[]{new Point(11,11),new Point(29,8),new Point(42,11),new Point(8,25),
            new Point(42,27),new Point(9,40),new Point(25,42),new Point(40,42)},
            new int[][]{{1,12},{2},{3,4},{11},{5},{9,10},{8},{6,7}}, "CCCC");

    private CoteCarte nord;
    private CoteCarte est;
    private CoteCarte sud;
    private CoteCarte ouest;
    private String path;
    private String path90;
    private String path180;
    private String path270;
    private boolean isBlason;
    private ArrayList<Point> coordonneesPartisans;
    private int[][] zonesControlleesParLesPoints;
    private String typeCarte;

    TypeCarte(CoteCarte nord, CoteCarte est, CoteCarte sud, CoteCarte ouest,
              String path, String path90, String path180, String path270, boolean isBlason,
              Point[] coordonnees, int[][] zonesControlleesParLesPoints, String typeCarte) {
        this.nord=nord;
        this.est=est;
        this.sud=sud;
        this.ouest=ouest;
        this.path = path;
        this.path90 = path90;
        this.path180 = path180;
        this.path270 = path270;
        this.isBlason=isBlason;
        coordonneesPartisans=new ArrayList<>();
        for (int i = 0; i < coordonnees.length; i++) {
            coordonneesPartisans.add(coordonnees[i]);
        }
        this.zonesControlleesParLesPoints=zonesControlleesParLesPoints;
        this.typeCarte=typeCarte;
    }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getOuest() { return ouest; }

    public String getPath() { return path; }

    public String getPath90() { return path90; }

    public String getPath180() { return path180; }

    public String getPath270() { return path270; }

    public boolean isBlason() { return isBlason; }

    public ArrayList<Point> getCoordonneesPartisans() { return coordonneesPartisans; }

    public int[][] getZonesControlleesParLesPoints() { return zonesControlleesParLesPoints; }

    public String getTypeCarte() { return typeCarte; }
}
