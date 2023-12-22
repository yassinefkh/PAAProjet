package src.up.mi.yfh.agglomeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe CommunauteAgglomeration.
 */
public class CommunauteAgglomerationTest {

	/**
	 * Teste la méthode ajouterVille de la classe CommunauteAgglomeration.
	 */
	@Test
	public void testAjouterVille() {
		CommunauteAgglomeration communaute = new CommunauteAgglomeration();
		Ville ville = new Ville("A");

		communaute.ajouterVille(ville);

		assertTrue(communaute.getVilles().contains(ville), "La ville doit être ajoutée à la communauté.");
	}

	/**
	 * Teste la méthode ajouterRoute de la classe CommunauteAgglomeration.
	 */
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

	/**
	 * Teste la méthode estSolutionValide de la classe CommunauteAgglomeration.
	 */
	@Test
	public void testEstSolutionValide() {
		CommunauteAgglomeration communaute = new CommunauteAgglomeration();
		Ville villeA = new Ville("A");
		Ville villeB = new Ville("B");
		communaute.ajouterVille(villeA);
		communaute.ajouterVille(villeB);

		villeB.retirerBorneRecharge();

		assertFalse(communaute.estSolutionValide(),
				"La solution doit être invalide si une ville n'a pas de borne de recharge et n'est pas reliée à une ville qui en a.");
	}

	/**
	 * Teste la méthode getVilleParNom de la classe CommunauteAgglomeration.
	 */
	@Test
	public void testGetVilleParNom() {
		CommunauteAgglomeration communaute = new CommunauteAgglomeration();
		Ville villeA = new Ville("A");
		communaute.ajouterVille(villeA);

		Ville villeRecuperee = communaute.getVilleParNom("A");

		assertNotNull(villeRecuperee, "La ville doit être récupérée avec succès.");
		assertEquals(villeA, villeRecuperee, "Les instances de villes doivent être les mêmes.");
	}

	/**
	 * Teste la méthode estRelieeAVilleAvecBorne de la classe
	 * CommunauteAgglomeration.
	 */
	@Test
	public void testEstRelieeAVilleAvecBorne() {
		CommunauteAgglomeration communaute = new CommunauteAgglomeration();
		Ville villeA = new Ville("A");
		Ville villeB = new Ville("B");
		Route route = new Route(villeA, villeB);
		communaute.ajouterVille(villeA);
		communaute.ajouterVille(villeB);
		communaute.ajouterRoute(route);
		villeB.ajouterBorneRecharge();

		boolean estReliee = communaute.estRelieeAVilleAvecBorne(villeA);

		assertTrue(estReliee, "La ville A doit être reliée à la ville B avec une borne de recharge.");
	}

}