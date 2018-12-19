package Jeu.View;

import Jeu.Controller.ControlMousePartisant;
import Jeu.Model.Carte;
import Jeu.Model.CartePosee;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PopUpPartisant {
    private Pane pane;
    private Stage primaryStage;
    private Stage popup;
    private ControlMousePartisant controlMousePartisant;

    public PopUpPartisant( Stage primaryStage){
        popup = new Stage();
        this.primaryStage = primaryStage;
        initPopup();
    }

    private void initPopup(){
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        pane = new Pane();
        Scene scene = new Scene(pane);
        popup.setScene(scene);
    }

    public void afficherCarte(CartePosee cartePosee){
        ImageView image = new ImageView(cartePosee.getImageCarte());
        image.setFitWidth(500);
        image.setFitHeight(500);
        pane.getChildren().add(image);
        for (int i = 0; i < cartePosee.getPositionsCoordonnees().size(); i++) {
            double xPartisan = cartePosee.getPositionsCoordonnees().get(i).getX();
            double yPartisan = cartePosee.getPositionsCoordonnees().get(i).getY();
            Circle circle = new Circle(xPartisan*10, yPartisan*10, 10);
            pane.getChildren().add(circle);
        }
        popup.show();
    }

    public void lierControl(FenetreJeu fenetreJeu){
        controlMousePartisant = new ControlMousePartisant(fenetreJeu);
        pane.setOnMouseClicked(controlMousePartisant);
    }

    public void quitter() {
        popup.close();
    }

    public void lierCarteEnMain(CartePosee carte){
        controlMousePartisant.setCarteEnMain(carte);
    }
}
