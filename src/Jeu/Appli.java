package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.View.FenetreJeu;
import Jeu.View.PopUpPartisant;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Appli extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        Group root = new Group();
        Carcassonne carcasonne = new Carcassonne();
        int WIDTH = 1000;
        int HEIGHT = 700;
        PopUpPartisant popUpPartisant = new PopUpPartisant(primaryStage);
        FenetreJeu fenetreJeu = new FenetreJeu(carcasonne, WIDTH, HEIGHT, popUpPartisant);
        root.getChildren().add(fenetreJeu);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
        primaryStage.show();
        carcasonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
