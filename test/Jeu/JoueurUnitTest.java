package Jeu;

import Jeu.Exception.PiocheVideException;
import Jeu.Model.*;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

public class JoueurUnitTest {

    @Test
    public void testPiocherCarte() throws PiocheVideException {
        Pioche p = new Pioche();
        Joueur j = new Joueur(0, p, Color.BLUE);

        Carte carte = p.getProchaineCarte();
        j.joue();
        Assert.assertEquals(carte, j.getCarteEnMain());
    }

    @Test
    public void testPiocherCarteAvecPiocheVide() throws PiocheVideException {
        Pioche p = new Pioche();
        for (int i = 0; i < 72; i++) {
            p.piocher();
        }

        Joueur j = new Joueur(0, p, Color.BLUE);
        j.joue();
        Assert.assertNull(j.getCarteEnMain());
    }
}
