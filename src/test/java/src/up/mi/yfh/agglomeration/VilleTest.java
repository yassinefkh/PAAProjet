package test.java.src.up.mi.yfh.agglomeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VilleTest {

	
    @Test
    public void testPresenceBorneRecharge() {
        // Arrange
        Ville ville = new Ville("A");
        
        // Assert
        assertFalse(ville.possedeBorneRecharge(), "La ville ne doit pas posséder une borne de recharge.");
        
        ville.ajouterBorneRecharge() ;
        assertTrue(ville.possedeBorneRecharge(), "La ville doit posséder une borne de recharge.");
    }

}
