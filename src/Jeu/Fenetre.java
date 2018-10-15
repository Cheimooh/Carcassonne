package Jeu;

//detection de la posisition de la souris.
//souris recupère une ccordonnée dans la zone visible, mais il faut recuperer les coordonnée de la zone sur la map
//penser a linvisible : translation

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Fenetre extends Parent {

    public static PlaceDispo placeDispo;
    public GridPane gridPane;
    private ArrayDeque <ImageView> queueImage;
    private ImageView image;
    private ControlButton controlButton;
    private ArrayList <Point> lDispo;
    private ArrayList <Point> lOccupee;
    private ControlMouse controlMouse;
    private Carcassonne carcassonne;

    public Fenetre(Carcassonne newCarcassonne){
        carcassonne = newCarcassonne;
        gridPane = new GridPane();
        int nbCases = carcassonne.getNB_CASES();
        gridPane.setMaxSize(nbCases, nbCases);
        gridPane.setMinSize(nbCases,nbCases);
        controlMouse = new ControlMouse(this);
        gridPane.setOnMouseClicked(controlMouse);
        placeDispo = new PlaceDispo();
        lDispo = new ArrayList<>();
        lOccupee = new ArrayList<>();
        queueImage = new ArrayDeque<>();
        placerCarte(carcassonne.getCarteDeBase());
    }

    public void placerCarte(Carte carte){
        controlMouse.setCarteEnMain(carte);
        lOccupee.add(carte.getPosition());
        image = carte.getDraw().img;
        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();

        //bloc de test pour tester les listes
        Point p = new Point(x+1,y);
        testLDispo(p);

        p.setLocation(x-1,y);
        testLDispo(p);

        p.setLocation(x,y+1);
        testLDispo(p);

        p.setLocation(x,y-1);
        testLDispo(p);

        if (lDispo.contains(carte.getPosition())){ lDispo.remove(carte.getPosition()); }

        gridPane.add(image, x,y);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
    }

    public ImageView getImage() { return image; }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Carcassonne getCarcassonne() {
        return carcassonne;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void testLDispo(Point p){
        if ( !lDispo.contains(p)) {
            lDispo.add(new Point((int)p.getX(), (int)p.getY()));
            queueImage.addLast(placeDispo.createPlaceDispo());
            gridPane.add(queueImage.getLast(),(int)p.getX(), (int)p.getY());
        }
    }

}
