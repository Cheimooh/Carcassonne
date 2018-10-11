package Jeu;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private static GridPane gridPane;

    public ControlMouse(Fenetre fenetre, GridPane gridPane){
        this.fenetre = fenetre;
        this.gridPane = gridPane;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("x: " + event.getX());
        System.out.println("y: " + event.getY());
        if((event.getX()>=102 && event.getX()<=146) && (event.getY()>=51 && event.getY()<=90)){
            ImageView imageView = new ImageView("Jeu/prairie.jpg");
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            gridPane.add(imageView, 1,1);
        }
    }
}
