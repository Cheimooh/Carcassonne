package Jeu;

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
    public void testPiocherDecrementeLaTailleDeLaPioche(){
        Pioche p = new Pioche();
        p.piocher();
        Assert.assertEquals(p.getTaille(),71);
    }

    @Test
    public void testGetProchaineCarte(){
        Pioche p = new Pioche();
        Carte c = p.getProchaineCarte();
        Assert.assertEquals(p.piocher(), c);
    }
}
