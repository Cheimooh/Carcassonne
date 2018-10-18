package Jeu;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//

public class Appli extends Application {

    public static Fenetre fenetre;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        Group root = new Group();
        Carcassonne carcasonne = new Carcassonne();
        fenetre = new Fenetre(carcasonne);
        root.getChildren().add(fenetre);
        //primaryStage.setScene(new Scene(root, carcasonne.getNB_CASES()*50, carcasonne.getNB_CASES()*50, Color.LIGHTGREY));
        primaryStage.setScene(new Scene(root, 1000, 1000, Color.LIGHTGREY));
        primaryStage.show();
        //carcasonne.jouer();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
