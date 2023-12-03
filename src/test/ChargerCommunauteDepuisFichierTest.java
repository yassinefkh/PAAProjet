import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ChargerCommunauteDepuisFichierTest {

    @Test
    public void testChargerCommunauteDepuisFichier() {
        try {
            String cheminFichier = "communaute.txt"; 
            ChargerCommunauteDepuisFichier chargeur = new ChargerCommunauteDepuisFichier(cheminFichier);
            CommunauteAgglomeration communaute = chargeur.getCommunaute();

            assertNotNull(communaute);
            assertEquals(3, communaute.getVilles().size());
            assertEquals(2, communaute.getRoutes().size());

            // verifie les villes et les routes chargées depuis le fichier
            Ville ville1 = communaute.getVilleParNom("Ville1");
            Ville ville2 = communaute.getVilleParNom("Ville2");
            Ville ville3 = communaute.getVilleParNom("Ville3");

            assertNotNull(ville1);
            assertNotNull(ville2);
            assertNotNull(ville3);

            assertTrue(communaute.getRoutes().contains(new Route(ville1, ville2)));
            assertTrue(communaute.getRoutes().contains(new Route(ville2, ville3)));

            // verifie que les bornes de recharge ont été correctement ajoutées
            assertTrue(ville1.possedeBorneRecharge());
            assertFalse(ville2.possedeBorneRecharge());
            assertTrue(ville3.possedeBorneRecharge());

        } catch (IOException e) {
            fail("Exception inattendue lors du chargement du fichier : " + e.getMessage());
        }
    }

    @Test(expected = IOException.class)
    public void testChargerCommunauteDepuisFichierInexistant() throws IOException {
        // test le chargement à partir d'un fichier inexistant
        String cheminFichierInexistant = "commu.txt";
        new ChargerCommunauteDepuisFichier(cheminFichierInexistant);
    }
}
