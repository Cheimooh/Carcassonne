package Jeu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.awt.*;

public class Fenetre extends Parent {

    private static Bouton bouton;
    private static GridPane gridPane;
    Button[] tabButton;

    public Fenetre(){
        gridPane = new GridPane();
        bouton = new Bouton(gridPane);
    }

    public void placerCarte(Carte carte){
        tabButton = new Button[]{bouton.createBouton(), bouton.createBouton(), bouton.createBouton(), bouton.createBouton()};
        new ControlButton(this);
        ImageView premiereCarte = new ImageView("Jeu/prairie.jpg"); // A modifier, toutes les cartes ne sont pas des prairies :)
        premiereCarte.setFitHeight(50);
        premiereCarte.setFitWidth(50);

        int x = (int) carte.getNbPosition().getX();
        int y = (int) carte.getNbPosition().getY();
        Button button = new Button("", premiereCarte);
        button.setDisable(true);

        gridPane.add(button, x,y);

        gridPane.add(tabButton[0],x,y+1);
        gridPane.add(tabButton[1],x-1,y);
        gridPane.add(tabButton[2],x+1,y);
        gridPane.add(tabButton[3],x,y-1);

        this.getChildren().add(gridPane);
        this.setTranslateX(50*x);
        this.setTranslateY(50*y);
        // Voir taille fenetre, je met l'image dans le coin en haut à gauche pour le moment

    }

    public void setControlButton(EventHandler<ActionEvent> eventEventHandler){
        for (int i = 0; i < tabButton.length; i++) {
            tabButton[i].setOnAction(eventEventHandler);
        }
    }

}
