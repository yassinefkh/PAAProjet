package src.up.mi.yfh.agglomeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe Ville.
 */
public class VilleTest {

    /**
     * Teste la création d'une ville.
     */
    @Test
    public void testCreationVille() {
        Ville ville = new Ville("A");
        assertEquals("A", ville.getNom(), "Le nom de la ville doit être 'A'.");
    }

    /**
     * Teste l'obtention du nom d'une ville.
     */
    @Test
    public void testObtentionNom() {
        Ville ville = new Ville("B");
        assertEquals("B", ville.getNom(), "Le nom de la ville doit être 'B'.");
    }

    /**
     * Teste la présence d'une borne de recharge dans une ville.
     */
    @Test
    public void testPresenceBorneRecharge() {
        // Arrange
        Ville ville = new Ville("A");

        // Assert
        assertFalse(ville.possedeBorneRecharge(), "La ville ne doit pas posséder une borne de recharge.");

        ville.ajouterBorneRecharge();
        assertTrue(ville.possedeBorneRecharge(), "La ville doit posséder une borne de recharge.");
    }

}
