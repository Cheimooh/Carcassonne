package Jeu.View;

import Jeu.Model.Carte;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class FenetreDefausse {
    private final int TAILLE_CARTE=75;
    private Stage popup;
    private Canvas canvas;
    private Pane pane;
    private GraphicsContext graphicsContext;

    public FenetreDefausse(){
        popup = new Stage();
        canvas = new Canvas(TAILLE_CARTE*5+60,TAILLE_CARTE*5+60);
        graphicsContext = canvas.getGraphicsContext2D();
        pane = new Pane();
        Scene scene = new Scene(pane);
        popup.setScene(scene);
    }

    public void afficherDefausse(ArrayList<Carte> defausse) {
        pane.getChildren().clear();
        ArrayList<Carte> carteDejaPresenteDansLaDefausse = new ArrayList<>();
        boolean carteDejaPresente;
        for (int i = 0; i < defausse.size(); i++) {
            for (int j = 0; j < 5; j++) {
                carteDejaPresente=false;
                if (defausse.size()>i*5+j) {
                    Carte c = defausse.get(i*5+j);
                    for (int k = 0; k < carteDejaPresenteDansLaDefausse.size() ; k++) {
                        if (carteDejaPresenteDansLaDefausse.get(k).getTypeCarte()==c.getTypeCarte()){
                            carteDejaPresente=true;
                        }
                    }
                    if (!carteDejaPresente) graphicsContext.drawImage(c.getDraw().getImg(), TAILLE_CARTE*j + 10*(j+1), TAILLE_CARTE*i + 10*(i+1), TAILLE_CARTE, TAILLE_CARTE);
                    else carteDejaPresenteDansLaDefausse.add(c);
                }
            }
        }
        pane.getChildren().add(canvas);
        popup.setTitle("Défausse");
        popup.showAndWait();
    }
}
