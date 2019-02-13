package Jeu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Appli extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        this.primaryStage = primaryStage;
        primaryStage.show();
        menuDepart();
    }

    private void menuDepart() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button jouer = new Button("Jouer");
        Button quitter = new Button("Quitter");
        boutonsMenu.getChildren().addAll(jouer, quitter);
        jouer.setOnAction(event -> choixTypeJeu());
        quitter.setOnAction(event -> System.exit(0));
        Scene scene = new Scene(boutonsMenu, 350, 300);
        primaryStage.setScene(scene);
    }

    private void choixTypeJeu() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button internet = new Button("Internet");
        Button local = new Button("Local");
        boutonsMenu.getChildren().addAll(internet, local);
        internet.setOnAction(event -> jeuInternet());
        local.setOnAction(event -> jeuLocal());
        Scene scene = new Scene(boutonsMenu, 350, 300);
        primaryStage.setScene(scene);
    }

    private void jeuLocal() {
        new Menu(primaryStage).askNbJoueurs();
    }

    private void jeuInternet() {
        new MenuReseau(primaryStage).jeuInternet();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
