package Jeu.ModelServeur.Serizable;

import java.io.Serializable;

public class Color implements Serializable {
    public javafx.scene.paint.Color color;

    public Color(javafx.scene.paint.Color color) {
        this.color = color;
    }
}
