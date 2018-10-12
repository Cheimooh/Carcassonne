package Jeu;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private GridPane gridPane;
    private Carte carteEnMain;

    public ControlMouse(Fenetre fenetre, GridPane gridPane){
        this.fenetre = fenetre;
        this.gridPane=fenetre.getGridPane();
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("x: " + event.getX());
        System.out.println("y: " + event.getY());
        if((event.getX()>=102 && event.getX()<=146) && (event.getY()>=51 && event.getY()<=90)){
            ImageView imageView = carteEnMain.getDraw().img;
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            GridPane gridPane2 = new GridPane();
            gridPane2.add(imageView, 1,1);
            gridPane.getChildren().add(gridPane2);
            fenetre.setGridPane(gridPane2);
        }
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }
}
