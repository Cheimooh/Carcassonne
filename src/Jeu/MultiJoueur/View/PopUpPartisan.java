package Jeu.MultiJoueur.View;

import Jeu.MultiJoueur.Controller.ControlMousePartisan;
import Jeu.MultiJoueur.Model.Carte;
import Jeu.MultiJoueur.Model.CartePosee;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class PopUpPartisan {
    private Pane pane;
    private Stage popup;
    private MenuReseau menuReseau;
    private ControlMousePartisan controlMousePartisan;

    public PopUpPartisan(MenuReseau menuReseau){
        controlMousePartisan = new ControlMousePartisan(menuReseau);
        popup = new Stage();
        this.menuReseau = menuReseau;
        pane = new Pane();
        Scene scene = new Scene(pane);
        popup.setScene(scene);
        pane.setOnMouseClicked(controlMousePartisan);
    }

    public void afficherCarte(Carte carte){
        CartePosee cartePosee = new CartePosee(carte);
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

    public void quitter() {
        Platform.runLater(()->popup.close());
    }
}
