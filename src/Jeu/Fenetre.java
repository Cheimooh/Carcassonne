package Jeu;

//detection de la posisition de la souris.
//souris recupère une ccordonnée dans la zone visible, mais il faut recuperer les coordonnée de la zone sur la map
//penser a linvisible : translation

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Fenetre extends Parent {

    public static Bouton bouton;
    public GridPane gridPane;
    private ArrayDeque <ImageView> pBouton;
    private ImageView image;
    private ControlButton controlButton;
    private ArrayList <Point> lDispo;
    private ArrayList <Point> lOccupee;
    private Point p; //point temporaire qui permet de tester si lDispo contient ce point
    private ControlMouse controlMouse;


    public Fenetre(){
        gridPane = new GridPane();
        gridPane.setMaxSize(71*71, 71*71);
        gridPane.setMinSize(71*71, 71*71);
        controlMouse = new ControlMouse(this, gridPane);
        gridPane.setOnMouseClicked(controlMouse);
        bouton = new Bouton(gridPane);
        lDispo = new ArrayList<>();
        lOccupee = new ArrayList<>();
        pBouton = new ArrayDeque<>();
    }

    public void placerCarte(Carte carte){
        controlMouse.setCarteEnMain(carte);
        lOccupee.add(carte.getPosition());
        image = carte.getDraw().img;
        int x = (int) carte.getPosition().getX();
        int y = (int) carte.getPosition().getY();

        //bloc de test pour tester les listes
        p = new Point(x+1,y);
        if ( !lDispo.contains(p)) {
            lDispo.add(new Point(x+1, y));
            pBouton.addLast(bouton.createBouton());
            gridPane.add(pBouton.getLast(),x+1,y);
        }
        p = new Point(x-1,y);
        if ( !lDispo.contains(p)) {
            lDispo.add(new Point(x-1, y));
            pBouton.addLast(bouton.createBouton());
            gridPane.add(pBouton.getLast(),x-1,y);
        }
        p = new Point(x,y+1);
        if ( !lDispo.contains(p)) {
            lDispo.add(new Point(x, y+1));
            pBouton.addLast(bouton.createBouton());
            gridPane.add(pBouton.getLast(),x,y+1);
        }
        p = new Point(x,y-1);
        if ( !lDispo.contains(p)) {
            lDispo.add(new Point(x, y-1));
            pBouton.addLast(bouton.createBouton());
            gridPane.add(pBouton.getLast(),x,y-1);
        }
        if (lDispo.contains(carte.getPosition())){
            lDispo.remove(carte.getPosition());
        }


        controlButton = new ControlButton(this);

        gridPane.add(image, x,y);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
    }

    public ImageView getImage() { return image; }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
