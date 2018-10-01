package Jeu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Fenetre extends Application {

    Carcassonne carcassonne;
    Stage fenetre;
    Scene scene;
    Scene scene1;
    Scene scene2;
    Button btn;
    Button btn1;
    Button btn2;


    public Fenetre(Carcassonne carcassonne){
        this.carcassonne = carcassonne;
    }

    /*
        Stage = Fenetre
        Group contient tous les objet graphique
        Scene contient le Group puis affiche
        1 Scene = 1 Group
     */

    public void start(Stage fenetre) {
        this.fenetre = fenetre;
        this.fenetre.setTitle("Carcassonne");

        Group group = new Group();
        btn = new Button();
        btn.setText("changer Scene 1");
        group.getChildren().add(btn);

        Group group1 = new Group();
        btn1 = new Button();
        btn1.setText("changer Scene 2");
        group1.getChildren().add(btn1);

        Group group2 = new Group();
        btn2 = new Button();
        btn2.setText("changer Scene Default");
        group2.getChildren().add(btn2);

        scene = new Scene(group);
        scene1 = new Scene(group1, 500, 500, Color.DEEPPINK);
        scene2 = new Scene(group2, 100, 100, Color.GREEN);
        this.fenetre.setScene(scene);
        this.fenetre.show();
    }

    public void setControlButton(EventHandler eventHandler){
        btn.setOnAction(eventHandler);
        btn1.setOnAction(eventHandler);
        btn2.setOnAction(eventHandler);
    }

    public void changerScene(int numScene){
        switch(numScene){
            case 1:
                fenetre.setScene(scene1);
                break;
            case 2:
                fenetre.setScene(scene2);
                break;
            default:
                fenetre.setScene(scene);
        }
    }
}
