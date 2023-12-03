import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CommunauteAgglomerationTest {

    @Test
    public void testAjouterVille() {
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville ville1 = new Ville("Ville1");
        Ville ville2 = new Ville("Ville2");

        communaute.ajouterVille(ville1);
        communaute.ajouterVille(ville2);

        assertEquals(2, communaute.getVilles().size());
        assertTrue(communaute.getVilles().contains(ville1));
        assertTrue(communaute.getVilles().contains(ville2));
    }

    @Test
    public void testAjouterRoute() {
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville ville1 = new Ville("Ville1");
        Ville ville2 = new Ville("Ville2");
        Route route = new Route(ville1, ville2);

        boolean result = communaute.ajouterRoute(route);

        assertTrue(result);
        assertEquals(1, communaute.getRoutes().size());
        assertTrue(communaute.getRoutes().contains(route));

        // doit retourner false
        boolean duplicateResult = communaute.ajouterRoute(route);
        assertFalse(duplicateResult);
        assertEquals(1, communaute.getRoutes().size());
    }

    @Test
    public void testGetVilleParNom() {
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville ville1 = new Ville("Ville1");
        Ville ville2 = new Ville("Ville2");

        communaute.ajouterVille(ville1);
        communaute.ajouterVille(ville2);

        Ville result = communaute.getVilleParNom("Ville1");
        assertNotNull(result);
        assertEquals("Ville1", result.getNom());

        Ville notFound = communaute.getVilleParNom("Ville3");
        assertNull(notFound);
    }

    @Test
    public void testEstSolutionValide() {
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville ville1 = new Ville("Ville1");
        Ville ville2 = new Ville("Ville2");
        Ville ville3 = new Ville("Ville3");
        Route route1 = new Route(ville1, ville2);
        Route route2 = new Route(ville2, ville3);

        communaute.ajouterVille(ville1);
        communaute.ajouterVille(ville2);
        communaute.ajouterVille(ville3);
        communaute.ajouterRoute(route1);
        communaute.ajouterRoute(route2);

        assertTrue(communaute.estSolutionValide());

        // retirer la borne de recharge de Ville2 rend la solution invalide
        ville2.retirerBorneRecharge();
        assertFalse(communaute.estSolutionValide());
    }

}