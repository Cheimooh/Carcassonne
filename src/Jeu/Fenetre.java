package Jeu;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Fenetre extends Parent {

    private final Bouton bouton = new Bouton();

    public Fenetre(){ }

    public GridPane placerCarte(Carte carte, Point p, GridPane gridPane){
        ImageView premiereCarte = new ImageView(new Image("Jeu/prairie.jpg")); // A modifier, toutes les cartes ne sont pas des prairies :)
        premiereCarte.setFitHeight(55);
        premiereCarte.setFitWidth(65);

        gridPane.add(premiereCarte, 1,1);

        int x = (int) p.getX();
        int y = (int) p.getY();

        gridPane.add(bouton.createBouton(),1,2);
        gridPane.add(bouton.createBouton(),0,1);
        gridPane.add(bouton.createBouton(),2,1);
        gridPane.add(bouton.createBouton(),1,0);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
        // Voir taille fenetre, je met l'image dans le coin en haut à gauche pour le moment

        return gridPane;
    }

}
