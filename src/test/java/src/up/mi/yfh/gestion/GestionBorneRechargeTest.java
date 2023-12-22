package src.up.mi.yfh.gestion;


import org.junit.jupiter.api.Test;
import src.up.mi.yfh.agglomeration.CommunauteAgglomeration;
import src.up.mi.yfh.agglomeration.Ville;
import static org.junit.jupiter.api.Assertions.*;
import src.up.mi.yfh.agglomeration.Route;
import src.up.mi.yfh.gestion.GestionBorneRecharge;

public class GestionBorneRechargeTest {

    @Test
    public void testListeVillesAvecBornesRecharge() {
        // Arrange
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        Ville villeA = new Ville("A");
        Ville villeB = new Ville("B");
        communaute.ajouterVille(villeA);
        communaute.ajouterVille(villeB);
        villeA.ajouterBorneRecharge();

        String resultat = GestionBorneRecharge.listeVillesAvecBornesRecharge(communaute);

        // Assert
        assertEquals("A ", resultat, "Seule A devrait avoir une borne de recharge.");
    }

    
    @Test
    public void testContrainteAccessibiliteBornesRechargeComplexe() {
        // Création des villes avec leurs bornes de recharge
        Ville A = new Ville("A"); 
        Ville B = new Ville("B");
        Ville C = new Ville("C"); 
        Ville D = new Ville("D");
        Ville E = new Ville("E"); 
        Ville F = new Ville("F"); 
        Ville G = new Ville("G"); 
        Ville H = new Ville("H");
        Ville I = new Ville("I"); 
        Ville J = new Ville("J");
        Ville K = new Ville("K"); 
        
        //on ajoute les bornes de recharges
        A.ajouterBorneRecharge();
        B.ajouterBorneRecharge();
        C.ajouterBorneRecharge();
        D.ajouterBorneRecharge();
        E.ajouterBorneRecharge();
        F.ajouterBorneRecharge();
        G.ajouterBorneRecharge();
        H.ajouterBorneRecharge();
        I.ajouterBorneRecharge();
        J.ajouterBorneRecharge();
        K.ajouterBorneRecharge();
        
        // Création de l'agglomération
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();
        // Ajout des villes à la communauté
        communaute.ajouterVille(A);
        communaute.ajouterVille(B);
        communaute.ajouterVille(C);
        communaute.ajouterVille(D);
        communaute.ajouterVille(E);
        communaute.ajouterVille(F);
        communaute.ajouterVille(G);
        communaute.ajouterVille(H);
        communaute.ajouterVille(I);
        communaute.ajouterVille(J);
        communaute.ajouterVille(K);

        // Ajout des routes
        communaute.ajouterRoute(new Route(A,B));
        communaute.ajouterRoute(new Route(A,D));
        communaute.ajouterRoute(new Route(B,C));
        communaute.ajouterRoute(new Route(B,H));
        communaute.ajouterRoute(new Route(C,D));
        communaute.ajouterRoute(new Route(C,I));
        communaute.ajouterRoute(new Route(D,E));
        communaute.ajouterRoute(new Route(E,F));
        communaute.ajouterRoute(new Route(E,G));
        communaute.ajouterRoute(new Route(H,I));
        communaute.ajouterRoute(new Route(H,J));
        communaute.ajouterRoute(new Route(H,K));
        
        // On teste la contrainte d'accessibilité
        //1e test
        assertTrue(GestionBorneRecharge.peutRetirerBorneRecharge(A, communaute), "On devrait pouvoir retirer la borne de A");
        A.retirerBorneRecharge();
        assertTrue(GestionBorneRecharge.peutRetirerBorneRecharge(B, communaute), "On devrait pouvoir retirer la borne de B");
        B.retirerBorneRecharge();
        //la ville n'aurait pas de voisins ayant une bornes de recharge : probleme d'incohérence
        assertFalse(GestionBorneRecharge.peutRetirerBorneRecharge(D, communaute), "On ne devrait pas pouvoir retirer la borne de D");
        
        //2e test
        assertTrue(GestionBorneRecharge.peutRetirerBorneRecharge(H, communaute), "On devrait pouvoir retirer la borne de H");
        H.retirerBorneRecharge();
        assertFalse(GestionBorneRecharge.peutRetirerBorneRecharge(J, communaute), "On ne devrait pas pouvoir retirer la borne de J");
        assertFalse(GestionBorneRecharge.peutRetirerBorneRecharge(K, communaute), "On ne devrait pas pouvoir retirer la borne de K");
        assertTrue(GestionBorneRecharge.peutRetirerBorneRecharge(I, communaute), "On devrait pouvoir retirer la borne de I");
        I.retirerBorneRecharge();
        assertFalse(GestionBorneRecharge.peutRetirerBorneRecharge(C, communaute), "On ne devrait pas pouvoir retirer la borne de C");
   }
}