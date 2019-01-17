package Jeu;

import Jeu.Exception.PiocheVideException;
import Jeu.Model.Carte;
import Jeu.Model.Pioche;
import org.junit.Assert;
import org.junit.Test;

public class PiocheUnitTest {

    @Test
    public void testBonNombreDeCartesDansLaPioche(){
        Pioche p = new Pioche();
        Assert.assertEquals(p.getTaille(), 72);
    }

    @Test
    public void testPiocherDecrementeLaTailleDeLaPioche() throws PiocheVideException {
        Pioche p = new Pioche();
        p.piocher();
        Assert.assertEquals(p.getTaille(),71);
    }

    @Test
    public void testGetProchaineCarte() throws PiocheVideException {
        Pioche p = new Pioche();
        Carte c = p.getProchaineCarte();
        Assert.assertEquals(p.piocher(), c);
    }

    @Test (expected = PiocheVideException.class)
    public void testPiocherPlusDeCartesQuePossible() throws PiocheVideException {
        Pioche p = new Pioche();
        for (int i = 0; i < 73; i++) {
            p.piocher();
        }
    }

    @Test (expected = PiocheVideException.class)
    public void testGetProchaineCarteAvecPiocheVide() throws PiocheVideException {
        Pioche p = new Pioche();
        for (int i = 0; i < 72; i++) {
            p.piocher();
        }
        p.getProchaineCarte();
    }
}
