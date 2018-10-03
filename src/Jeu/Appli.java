package Jeu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Appli extends Application {

    private Carcassonne carcassonne;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        Group root = new Group();
        carcassonne = new Carcassonne();
        root.getChildren().add(carcassonne);
        primaryStage.setScene(new Scene(root, 800, 650, Color.LIGHTGREY));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
