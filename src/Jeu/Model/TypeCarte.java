package Jeu.Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public enum TypeCarte {
    /*  P = Prairie
     *  C = Chemin
     *  V = Ville
     *  Les "lieux" sont dans l'ordre nord, est, sud, ouest
     */

    cartePPCC(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/PPCC.jpg"),new Image("Jeu/imgCartes/90degres/PPCC.jpg"),
            new Image("Jeu/imgCartes/180degres/PPCC.jpg"),new Image("Jeu/imgCartes/270degres/PPCC.jpg"),
            false, new int[]{0,0,0,0,0,2,0,2}, "chemin"),
    carteVCCP(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VCCP.jpg"),new Image("Jeu/imgCartes/90degres/VCCP.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCP.jpg"),new Image("Jeu/imgCartes/270degres/VCCP.jpg"),
            false, new int[]{3,1,3,2,0,2,0,0}, "chemin"),
    carteVCCV(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VCCV.jpg"),new Image("Jeu/imgCartes/90degres/VCCV.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCV.jpg"),new Image("Jeu/imgCartes/270degres/VCCV.jpg"),
            false, new int[]{1,1,3,2,0,2,3,1}, "chemin"),
    carteVCCVBlason(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VCCVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VCCVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VCCVBlason.jpg"),
            true, new int[]{1,1,3,2,0,2,3,1}, "chemin"),
    carteVVCV(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVCV.jpg"),new Image("Jeu/imgCartes/90degres/VVCV.jpg"),
            new Image("Jeu/imgCartes/180degres/VVCV.jpg"),new Image("Jeu/imgCartes/270degres/VVCV.jpg"),
            false, new int[]{1,1,1,1,3,2,3,1}, "ville"),
    carteVVCVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVCVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVCVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVCVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVCVBlason.jpg"),
            true, new int[]{1,1,1,1,3,2,3,1}, "ville"),
    carteVVPV(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVPV.jpg"),new Image("Jeu/imgCartes/90degres/VVPV.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPV.jpg"),new Image("Jeu/imgCartes/270degres/VVPV.jpg"),
            false, new int[]{1,1,1,1,3,0,3,1}, "ville"),
    carteVVPVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVPVBlason.jpg"),
            true, new int[]{1,1,1,1,3,0,3,1}, "ville"),
    carteVCCC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VCCC.jpg"),new Image("Jeu/imgCartes/90degres/VCCC.jpg")
            ,new Image("Jeu/imgCartes/180degres/VCCC.jpg"),new Image("Jeu/imgCartes/270degres/VCCC.jpg"),
            false, new int[]{3,1,3,2,0,2,0,2}, "carrefour"),
    carteCPCP(CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/CPCP.jpg"),new Image("Jeu/imgCartes/90degres/CPCP.jpg"),
            new Image("Jeu/imgCartes/180degres/CPCP.jpg"),new Image("Jeu/imgCartes/270degres/CPCP.jpg"),
            false, new int[]{0,2,0,0,0,2,0,0}, "chemin"),
    cartePCCC(CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/PCCC.jpg"),new Image("Jeu/imgCartes/90degres/PCCC.jpg"),
            new Image("Jeu/imgCartes/180degres/PCCC.jpg"),new Image("Jeu/imgCartes/270degres/PCCC.jpg"),
            false, new int[]{0,0,0,2,0,2,0,2}, "carrefour"),
    carteVPPP(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VPPP.jpg"),new Image("Jeu/imgCartes/90degres/VPPP.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPP.jpg"),new Image("Jeu/imgCartes/270degres/VPPP.jpg"),
            false, new int[]{3,1,3,0,0,0,0,0}, "prairie"),
    carteVVPP(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VVPP.jpg"),new Image("Jeu/imgCartes/90degres/VVPP.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPP.jpg"),new Image("Jeu/imgCartes/270degres/VVPP.jpg"),
            false, new int[]{3,1,3,1,3,0,0,0}, "prairie"),
    cartePPPP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/PPPP.jpg"),new Image("Jeu/imgCartes/90degres/PPPP.jpg"),
            new Image("Jeu/imgCartes/180degres/PPPP.jpg"),new Image("Jeu/imgCartes/270degres/PPPP.jpg"),
            false, new int[]{0,0,0,0,0,0,0,0}, "abbaye"),
    cartePPCP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/PPCP.jpg"),new Image("Jeu/imgCartes/90degres/PPCP.jpg"),
            new Image("Jeu/imgCartes/180degres/PPCP.jpg"),new Image("Jeu/imgCartes/270degres/PPCP.jpg"),
            false, new int[]{0,0,0,0,0,2,0,0}, "abbaye"),
    carteVPPV(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VPPV.jpg"),new Image("Jeu/imgCartes/90degres/VPPV.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPV.jpg"),new Image("Jeu/imgCartes/270degres/VPPV.jpg"),
            false, new int[]{1,1,3,0,0,0,3,1}, "prairie"),
    carteVPPVBlason(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VPPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VPPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VPPVBlason.jpg"),
            true, new int[]{1,1,3,0,0,0,3,1}, "prairie"),
    cartePVPVRelieesBlason(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/PVPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/PVPVBlason.jpg"),
            true, new int[]{3,0,3,1,3,0,3,1}, "ville"),
    cartePVPVReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVReliees.jpg"),new Image("Jeu/imgCartes/90degres/PVPVReliees.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVReliees.jpg"),new Image("Jeu/imgCartes/270degres/PVPVReliees.jpg"),
            false, new int[]{3,0,3,1,3,0,3,1}, "ville"),
    cartePVPVNonReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVNonReliees.jpg"),new Image("Jeu/imgCartes/90degres/PVPVNonReliees.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVNonReliees.jpg"),new Image("Jeu/imgCartes/270degres/PVPVNonReliees.jpg"),
            false, new int[]{3,0,3,1,3,0,3,1}, "prairie"),
    carteVCPC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VCPC.jpg"),new Image("Jeu/imgCartes/90degres/VCPC.jpg"),
            new Image("Jeu/imgCartes/180degres/VCPC.jpg"),new Image("Jeu/imgCartes/270degres/VCPC.jpg"),
            false, new int[]{3,1,3,2,0,0,0,2}, "chemin"),
    carteVPCC(CoteCarte.ville, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VPCC.jpg"),new Image("Jeu/imgCartes/90degres/VPCC.jpg"),
            new Image("Jeu/imgCartes/180degres/VPCC.jpg"),new Image("Jeu/imgCartes/270degres/VPCC.jpg"),
            false, new int[]{3,1,3,0,0,2,0,2}, "chemin"),
    carteVVVVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVVVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVVVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVVVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVVVBlason.jpg"),
            true, new int[]{1,1,1,1,1,1,1,1}, "ville"),
    carteCCCC(CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/CCCC.jpg"),new Image("Jeu/imgCartes/90degres/CCCC.jpg"),
            new Image("Jeu/imgCartes/180degres/CCCC.jpg"),new Image("Jeu/imgCartes/270degres/CCCC.jpg"),
            false, new int[]{0,2,0,2,0,2,0,2}, "carrefour");

    private CoteCarte nord;
    private CoteCarte est;
    private CoteCarte sud;
    private CoteCarte ouest;
    private Image img;
    private Image img90;
    private Image img180;
    private Image img270;
    private boolean isBlason;
    private ArrayList<String> zones;
    private String zoneCentrale;

    TypeCarte(CoteCarte nord, CoteCarte est, CoteCarte sud, CoteCarte ouest,
              Image Image, Image img90, Image img180, Image img270, boolean isBlason,
              int[] zonesInt, String zoneCentrale) {
        this.nord=nord;
        this.est=est;
        this.sud=sud;
        this.ouest=ouest;
        this.img=Image;
        this.img90=img90;
        this.img180=img180;
        this.img270=img270;
        this.isBlason=isBlason;
        zones = new ArrayList<>();
        for (int aZonesInt : zonesInt) {
            if (aZonesInt == 0) zones.add("prairie");
            if (aZonesInt == 1) zones.add("ville");
            if (aZonesInt == 2) zones.add("chemin");
            if (aZonesInt == 3) zones.add("prairie/ville");
        }
        this.zoneCentrale = zoneCentrale;
    }

    public CoteCarte getNord() { return nord; }

    public CoteCarte getEst() { return est; }

    public CoteCarte getSud() { return sud; }

    public CoteCarte getOuest() { return ouest; }

    public Image getImg() { return img; }

    public Image getImg90() { return img90; }

    public Image getImg180() { return img180; }

    public Image getImg270() { return img270; }

    public boolean isBlason() { return isBlason; }

    public ArrayList<String> getZones() { return zones; }

    public String getZoneCentrale() { return zoneCentrale; }
}
