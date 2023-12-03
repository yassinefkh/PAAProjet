import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class VilleTest {

    @Test
    public void testConstructeurVille() {
        String nomVille = "MaVille";
        Ville ville = new Ville(nomVille);

        assertEquals(nomVille, ville.getNom());
        assertTrue(ville.possedeBorneRecharge());
    }

    @Test
    public void testAjouterRetirerBorneRecharge() {
        Ville ville = new Ville("MaVille");

        assertTrue(ville.possedeBorneRecharge());
        ville.retirerBorneRecharge();
        assertFalse(ville.possedeBorneRecharge());
        ville.ajouterBorneRecharge();
        assertTrue(ville.possedeBorneRecharge());
    }
}
