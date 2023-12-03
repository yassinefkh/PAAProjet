import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class GestionBornesRechargeTest {

    @Test
    public void testListeVillesAvecBornesRecharge() {
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville ville1 = new Ville("Ville1");
        Ville ville2 = new Ville("Ville2");
        Ville ville3 = new Ville("Ville3");
        ville1.ajouterBorneRecharge();
        ville3.ajouterBorneRecharge();
        communaute.ajouterVille(ville1);
        communaute.ajouterVille(ville2);
        communaute.ajouterVille(ville3);

        String result = GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute);
        
        assertTrue(result.contains("Ville1"));
        assertFalse(result.contains("Ville2"));
        assertTrue(result.contains("Ville3"));
    }

    @Test
    public void testPeutRetirerBorneRecharge() {
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

        assertFalse(GestionBornesRecharge.peutRetirerBorneRecharge(ville1, communaute));
        assertTrue(GestionBornesRecharge.peutRetirerBorneRecharge(ville2, communaute));
        assertFalse(GestionBornesRecharge.peutRetirerBorneRecharge(ville3, communaute));
    }
}
