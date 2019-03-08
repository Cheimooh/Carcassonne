package Jeu.MultiJoueur;

public class DrawCard {
    private String path; // Lien de l'image non tournée
    private String path90; // Lien de l'image tournée à 90 degrés
    private String path180; // Lien de l'image tournée à 180 degrés
    private String path270; // Lien de l'image tournée à 270 degrés

    public DrawCard(String path, String path90, String path180, String path270){
        this.path = path;
        this.path90 = path90;
        this.path180 = path180;
        this.path270 = path270;
    }

    public String getPath() { return path; }

    public String getPath90() { return path90; }

    public String getPath180() { return path180; }

    public String getPath270() { return path270; }
}