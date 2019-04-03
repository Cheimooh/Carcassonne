package Jeu;

import Jeu.MultiJoueur.View.MenuReseau;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Appli extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Carcassonne");
        this.primaryStage = primaryStage;
        primaryStage.show();
        menuDepart();
    }

    public static void menuDepart() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button jouer = new Button("Jouer");
        Button quitter = new Button("Quitter");
        quitter.setStyle("    -fx-background-radius: 6, 5;\n");
        boutonsMenu.getChildren().addAll(jouer, quitter);
        jouer.setOnAction(event -> choixTypeJeu());
        quitter.setOnAction(event -> System.exit(0));
        Scene scene = new Scene(boutonsMenu, 350, 300);
        primaryStage.setScene(scene);
    }

    public static void choixTypeJeu() {
        VBox boutonsMenu = new VBox(10);
        boutonsMenu.setAlignment(Pos.CENTER);
        Button internet = new Button("Jeu en ligne");
        Button local = new Button("Jeu hors ligne");
        boutonsMenu.getChildren().addAll(internet, local);
        internet.setOnAction(event -> jeuInternet());
        local.setOnAction(event -> jeuLocal());
        Scene scene = new Scene(boutonsMenu, 350, 300);
        primaryStage.setScene(scene);
    }

    private static void jeuLocal() {
        new Menu(primaryStage).askNbJoueurs();
    }

    private static void jeuInternet() {
        new MenuReseau(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
