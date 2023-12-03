import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RouteTest {

    @Test
    public void testConstructeurRoute() {
        Ville villeA = new Ville("VilleA");
        Ville villeB = new Ville("VilleB");
        Route route = new Route(villeA, villeB);

        assertEquals(villeA, route.getVilleA());
        assertEquals(villeB, route.getVilleB());
    }

    @Test
    public void testEquals() {
        Ville villeA = new Ville("VilleA");
        Ville villeB = new Ville("VilleB");
        Ville villeC = new Ville("VilleC");

        Route route1 = new Route(villeA, villeB);
        Route route2 = new Route(villeB, villeA);
        Route route3 = new Route(villeA, villeC);

        assertTrue(route1.equals(route2));
        assertFalse(route1.equals(route3));
    }
}
