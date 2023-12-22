package src.up.mi.yfh.agglomeration;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {
	
	//vérifie que les villes sont correctement assignées lors de la création de l'objet Route
    @Test
    public void testCreationRoute() {
        Ville villeA = new Ville("A");
        Ville villeB = new Ville("B");
        Route route = new Route(villeA, villeB);

        assertSame(villeA, route.getVilleA(), "La première ville doit être Paris.");
        assertSame(villeB, route.getVilleB(), "La deuxième ville doit être Lyon.");
    }
    
    //vérifie que l'égalité entre deux objets Route est correctement évaluée, même si l'ordre des villes est inversé
    @Test
    public void testEgaliteRoutes() {
        // Arrange
        Ville villeA = new Ville("A");
        Ville villeB = new Ville("B");
        Route route1 = new Route(villeA, villeB);
        Route route2 = new Route(villeB, villeA); // Inverse les villes

        //Assert
        assertEquals(route1, route2, "Deux routes avec les mêmes villes doivent être égales.");
    }
    

}