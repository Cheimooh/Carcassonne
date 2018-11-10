package Jeu;

import Jeu.Model.Carcassonne;
import Jeu.View.Fenetre;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//

public class Appli extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        Group root = new Group();
        Carcassonne carcasonne = new Carcassonne();
        int WIDTH = 1000;
        int HEIGHT = 1000;
        Fenetre fenetre = new Fenetre(carcasonne, WIDTH, HEIGHT);
        root.getChildren().add(fenetre);
        //primaryStage.setScene(new Scene(root, carcasonne.getNB_CASES()*50, carcasonne.getNB_CASES()*50, Color.LIGHTGREY));
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, Color.LIGHTGREY));
        primaryStage.show();
        carcasonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
