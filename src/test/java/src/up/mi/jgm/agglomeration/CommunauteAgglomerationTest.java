package src.up.mi.jgm.agglomeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommunauteAgglomerationTest {
	
	@Test
	public void testAjouterVille() {
	    CommunauteAgglomeration communaute = new CommunauteAgglomeration();
	    Ville ville = new Ville("A");

	    communaute.ajouterVille(ville);

	    assertTrue(communaute.getVilles().contains(ville), "La ville doit être ajoutée à la communauté.");
	}
	
	@Test
	public void testAjouterRoute() {
	    CommunauteAgglomeration communaute = new CommunauteAgglomeration();
	    Ville villeA = new Ville("A");
	    Ville villeB = new Ville("B");
	    Route route = new Route(villeA, villeB);

	    boolean ajoutReussi = communaute.ajouterRoute(route);

	    assertTrue(ajoutReussi, "La route doit être ajoutée avec succès.");
	    assertTrue(communaute.getRoutes().contains(route), "La route doit être présente dans la liste des routes.");
	}
	
	@Test
	public void testEstSolutionValide() {
	    CommunauteAgglomeration communaute = new CommunauteAgglomeration();
	    Ville villeA = new Ville("A");
	    Ville villeB = new Ville("B");
	    communaute.ajouterVille(villeA);
	    communaute.ajouterVille(villeB);

	    villeB.retirerBorneRecharge(); 

	    assertFalse(communaute.estSolutionValide(), "La solution doit être invalide si une ville n'a pas de borne de recharge et n'est pas reliée à une ville qui en a.");
	}

}
