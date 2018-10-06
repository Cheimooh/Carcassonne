package Jeu;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Fenetre extends Parent {

    private static Bouton bouton;
    private GridPane gridPane;

    public Fenetre(){ }

    public void placerCarte(Carte carte, Point p){
        ImageView premiereCarte = new ImageView("Jeu/prairie.jpg"); // A modifier, toutes les cartes ne sont pas des prairies :)
        premiereCarte.setFitHeight(50);
        premiereCarte.setFitWidth(50);

        int x = (int) p.getX();
        int y = (int) p.getY();

        gridPane = new GridPane();
        bouton = new Bouton(gridPane);

        gridPane.add(new Button("", premiereCarte), x,y);

        gridPane.add(bouton.createBouton(),x,y+1);
        gridPane.add(bouton.createBouton(),x-1,y);
        gridPane.add(bouton.createBouton(),x+1,y);
        gridPane.add(bouton.createBouton(),x,y-1);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
        // Voir taille fenetre, je met l'image dans le coin en haut à gauche pour le moment

    }

}
