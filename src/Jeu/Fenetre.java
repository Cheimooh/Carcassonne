package Jeu;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Fenetre extends Parent {

    public Fenetre(){ }

    public void placerCarte(Carte carte, Point p){
        ImageView premiereCarte = new ImageView(new Image("Jeu/prairie.jpg")); // A modifier, toutes les cartes ne sont pas des prairies :)
        premiereCarte.setFitHeight(50);
        premiereCarte.setFitWidth(50);

        GridPane gridPane = new GridPane();
        gridPane.add(premiereCarte, 1,0);

        int x = (int) p.getX();
        int y = (int) p.getY();

        gridPane.add(createBouton(x+1,y),1,1);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
        // Voir taille fenetre, je met l'image dans le coin en haut à gauche pour le moment
    }

    private Button createBouton(int x, int y){
        ImageView imageView = new ImageView(new Image("Jeu/plus.png"));
        imageView.setFitHeight(40);
        imageView.setFitWidth(35);

        Button button = new Button("",imageView);

        return button;
    }

    public static void main(String[] args){}

}
