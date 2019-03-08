package Jeu.MultiJoueur.View;

import Jeu.Model.Carte;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FenetreDefausse {
    private final int TAILLE_CARTE=75;
    private Stage popup;
    private Canvas canvas;
    private Pane pane;
    private GraphicsContext graphicsContext;

    public FenetreDefausse(){
        popup = new Stage();
        canvas = new Canvas(TAILLE_CARTE*5+60,TAILLE_CARTE*5+60);
        graphicsContext = canvas.getGraphicsContext2D();
        pane = new Pane();
        Scene scene = new Scene(pane);
        popup.setScene(scene);
    }

    public void afficherDefausse(ArrayList<Carte> defausse) {
        pane.getChildren().clear();
        for (int i = 0; i < defausse.size(); i++) {
            for (int j = 0; j < 5; j++) {
                if (defausse.size()>i*5+j) {
                    Carte c = defausse.get(i*5+j);
                    Image image = new Image(c.getDraw().getPath());
                    graphicsContext.drawImage(image, TAILLE_CARTE*j + 10*(j+1), TAILLE_CARTE*i + 10*(i+1), TAILLE_CARTE, TAILLE_CARTE);
                }
            }
        }
        pane.getChildren().add(canvas);
        popup.setTitle("Défausse");
        popup.showAndWait();
    }
}
