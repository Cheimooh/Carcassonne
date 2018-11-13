package Jeu.Model;

import javafx.scene.image.Image;

public enum TypeCarte {
    /*  P = Prairie
     *  C = Chemin
     *  V = Ville
     *  Les "lieux" sont dans l'ordre nord, est, sud, ouest
     */

    cartePPCC(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/PPCC.jpg"),new Image("Jeu/imgCartes/90degres/PPCC.jpg"),
            new Image("Jeu/imgCartes/180degres/PPCC.jpg"),new Image("Jeu/imgCartes/270degres/PPCC.jpg"),
            false, new int[]{0,0,0,0,0,2,0,2}),
    carteVCCP(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VCCP.jpg"),new Image("Jeu/imgCartes/90degres/VCCP.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCP.jpg"),new Image("Jeu/imgCartes/270degres/VCCP.jpg"),
            false, new int[]{3,1,3,2,0,2,0,0}),
    carteVCCV(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VCCV.jpg"),new Image("Jeu/imgCartes/90degres/VCCV.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCV.jpg"),new Image("Jeu/imgCartes/270degres/VCCV.jpg"),
            false, new int[]{1,1,3,2,0,2,3,1}),
    carteVCCVBlason(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VCCVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VCCVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VCCVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VCCVBlason.jpg"),
            true, new int[]{1,1,3,2,0,2,3,1}),
    carteVVCV(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVCV.jpg"),new Image("Jeu/imgCartes/90degres/VVCV.jpg"),
            new Image("Jeu/imgCartes/180degres/VVCV.jpg"),new Image("Jeu/imgCartes/270degres/VVCV.jpg"),
            false, new int[]{1,1,1,1,3,2,3,1}),
    carteVVCVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.chemin, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVCVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVCVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVCVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVCVBlason.jpg"),
            true, new int[]{1,1,1,1,3,2,3,1}),
    carteVVPV(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVPV.jpg"),new Image("Jeu/imgCartes/90degres/VVPV.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPV.jpg"),new Image("Jeu/imgCartes/270degres/VVPV.jpg"),
            false, new int[]{1,1,1,1,3,0,3,1}),
    carteVVPVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVPVBlason.jpg"),
            true, new int[]{1,1,1,1,3,0,3,1}),
    carteVCCC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VCCC.jpg"),new Image("Jeu/imgCartes/90degres/VCCC.jpg")
            ,new Image("Jeu/imgCartes/180degres/VCCC.jpg"),new Image("Jeu/imgCartes/270degres/VCCC.jpg"),
            false, new int[]{3,1,3,2,0,2,0,2}),
    carteCPCP(CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/CPCP.jpg"),new Image("Jeu/imgCartes/90degres/CPCP.jpg"),
            new Image("Jeu/imgCartes/180degres/CPCP.jpg"),new Image("Jeu/imgCartes/270degres/CPCP.jpg"),
            false, new int[]{0,2,0,0,0,2,0,0}),
    cartePCCC(CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/PCCC.jpg"),new Image("Jeu/imgCartes/90degres/PCCC.jpg"),
            new Image("Jeu/imgCartes/180degres/PCCC.jpg"),new Image("Jeu/imgCartes/270degres/PCCC.jpg"),
            false, new int[]{0,0,0,2,0,2,0,2}),
    carteVPPP(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VPPP.jpg"),new Image("Jeu/imgCartes/90degres/VPPP.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPP.jpg"),new Image("Jeu/imgCartes/270degres/VPPP.jpg"),
            false, new int[]{3,1,3,0,0,0,0,0}),
    carteVVPP(CoteCarte.ville, CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/VVPP.jpg"),new Image("Jeu/imgCartes/90degres/VVPP.jpg"),
            new Image("Jeu/imgCartes/180degres/VVPP.jpg"),new Image("Jeu/imgCartes/270degres/VVPP.jpg"),
            false, new int[]{3,1,3,1,3,0,0,0}),
    cartePPPP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.prairie,
            new Image("Jeu/imgCartes/PPPP.jpg"),new Image("Jeu/imgCartes/90degres/PPPP.jpg"),
            new Image("Jeu/imgCartes/180degres/PPPP.jpg"),new Image("Jeu/imgCartes/270degres/PPPP.jpg"),
            false, new int[]{0,0,0,0,0,0,0,0}),
    cartePPCP(CoteCarte.prairie, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.prairie,
            new Image("Jeu/imgCartes/PPCP.jpg"),new Image("Jeu/imgCartes/90degres/PPCP.jpg"),
            new Image("Jeu/imgCartes/180degres/PPCP.jpg"),new Image("Jeu/imgCartes/270degres/PPCP.jpg"),
            false, new int[]{0,0,0,0,0,2,0,0}),
    carteVPPV(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VPPV.jpg"),new Image("Jeu/imgCartes/90degres/VPPV.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPV.jpg"),new Image("Jeu/imgCartes/270degres/VPPV.jpg"),
            false, new int[]{1,1,3,0,0,0,3,1}),
    carteVPPVBlason(CoteCarte.ville, CoteCarte.prairie, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/VPPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VPPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VPPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VPPVBlason.jpg"),
            true, new int[]{1,1,3,0,0,0,3,1}),
    cartePVPVRelieesBlason(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVBlason.jpg"),new Image("Jeu/imgCartes/90degres/PVPVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVBlason.jpg"),new Image("Jeu/imgCartes/270degres/PVPVBlason.jpg"),
            true, new int[]{3,0,3,1,3,0,3,1}),
    cartePVPVReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVReliees.jpg"),new Image("Jeu/imgCartes/90degres/PVPVReliees.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVReliees.jpg"),new Image("Jeu/imgCartes/270degres/PVPVReliees.jpg"),
            false, new int[]{3,0,3,1,3,0,3,1}),
    cartePVPVNonReliees(CoteCarte.prairie, CoteCarte.ville, CoteCarte.prairie, CoteCarte.ville,
            new Image("Jeu/imgCartes/PVPVNonReliees.jpg"),new Image("Jeu/imgCartes/90degres/PVPVNonReliees.jpg"),
            new Image("Jeu/imgCartes/180degres/PVPVNonReliees.jpg"),new Image("Jeu/imgCartes/270degres/PVPVNonReliees.jpg"),
            false, new int[]{3,0,3,1,3,0,3,1}),
    carteVCPC(CoteCarte.ville, CoteCarte.chemin, CoteCarte.prairie, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VCPC.jpg"),new Image("Jeu/imgCartes/90degres/VCPC.jpg"),
            new Image("Jeu/imgCartes/180degres/VCPC.jpg"),new Image("Jeu/imgCartes/270degres/VCPC.jpg"),
            false, new int[]{3,1,3,2,0,0,0,2}),
    carteVPCC(CoteCarte.ville, CoteCarte.prairie, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/VPCC.jpg"),new Image("Jeu/imgCartes/90degres/VPCC.jpg"),
            new Image("Jeu/imgCartes/180degres/VPCC.jpg"),new Image("Jeu/imgCartes/270degres/VPCC.jpg"),
            false, new int[]{3,1,3,0,0,2,0,2}),
    carteVVVVBlason(CoteCarte.ville, CoteCarte.ville, CoteCarte.ville, CoteCarte.ville,
            new Image("Jeu/imgCartes/VVVVBlason.jpg"),new Image("Jeu/imgCartes/90degres/VVVVBlason.jpg"),
            new Image("Jeu/imgCartes/180degres/VVVVBlason.jpg"),new Image("Jeu/imgCartes/270degres/VVVVBlason.jpg"),
            true, new int[]{1,1,1,1,1,1,1,1}),
    carteCCCC(CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin, CoteCarte.chemin,
            new Image("Jeu/imgCartes/CCCC.jpg"),new Image("Jeu/imgCartes/90degres/CCCC.jpg"),
            new Image("Jeu/imgCartes/180degres/CCCC.jpg"),new Image("Jeu/imgCartes/270degres/CCCC.jpg"),
            false, new int[]{0,2,0,2,0,2,0,2});

    private CoteCarte nord;
    private CoteCarte est;
    private CoteCarte sud;
    private CoteCarte ouest;
    private Image img;
    private Image img90;
    private Image img180;
    private Image img270;
    private boolean isBlason;
    private String[] zones;

    TypeCarte(CoteCarte nord, CoteCarte est, CoteCarte sud, CoteCarte ouest,
              Image Image, Image img90, Image img180, Image img270, boolean isBlason,
              int[] zonesInt) {
        this.nord=nord;
        this.est=est;
        this.sud=sud;
        this.ouest=ouest;
        this.img=Image;
        this.img90=img90;
        this.img180=img180;
        this.img270=img270;
        this.isBlason=isBlason;
        zones = new String[8];
        for (int i = 0; i < zonesInt.length; i++) {
            if (zonesInt[i]==0) zones[i]="prairie";
            if (zonesInt[i]==1) zones[i]="ville";
            if (zonesInt[i]==2) zones[i]="chemin";
            if (zonesInt[i]==3) zones[i]="prairie/ville";
        }
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

    public String[] getZones() { return zones; }
}
