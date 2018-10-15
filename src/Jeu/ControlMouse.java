package Jeu;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControlMouse implements EventHandler<MouseEvent> {

    private Fenetre fenetre;
    private GridPane gridPane;
    private Carte carteEnMain;

    public ControlMouse(Fenetre fenetre){
        this.fenetre = fenetre;
        this.gridPane=fenetre.getGridPane();
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("x: " + event.getX()); // De gauche a droite
        System.out.println("y: " + event.getY()); // De haut en bas
        if((event.getX()>=102 && event.getX()<=146) && (event.getY()>=51 && event.getY()<=90)){
            ImageView imageView = carteEnMain.getDraw().img;
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            GridPane gridPane2 = new GridPane();
            gridPane2.add(imageView, (int)carteEnMain.getPosition().getX()+1,(int)carteEnMain.getPosition().getY());
            gridPane.getChildren().add(gridPane2);
            fenetre.setGridPane(gridPane);
        }
    }

    public void setCarteEnMain(Carte carteEnMain) {
        this.carteEnMain = carteEnMain;
    }
}
