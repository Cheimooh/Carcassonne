package Jeu;

import Jeu.Model.Carcassonne;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Appli extends Application {
    private Carcassonne carcassonne;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        carcassonne = new Carcassonne();
        primaryStage.setTitle("Carcassonne");
        Menu menu = new Menu(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
