import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChargerCommunauteDepuisFichier {

    private CommunauteAgglomeration communaute;

    public ChargerCommunauteDepuisFichier(String cheminFichier) throws IOException {
        this.communaute = new CommunauteAgglomeration();
        chargerDepuisFichier(cheminFichier);
    }

    private void chargerDepuisFichier(String cheminFichier) throws IOException {
        Pattern patternVille = Pattern.compile("ville\\((.*?)\\)\\.");
        Pattern patternRoute = Pattern.compile("route\\((.*?),(.*?)\\)\\.");
        Pattern patternRecharge = Pattern.compile("recharge\\((.*?)\\)\\.");

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                Matcher matcherVille = patternVille.matcher(ligne);
                Matcher matcherRoute = patternRoute.matcher(ligne);
                Matcher matcherRecharge = patternRecharge.matcher(ligne);

                if (matcherVille.find()) {
                    String nomVille = matcherVille.group(1);
                    communaute.ajouterVille(new Ville(nomVille));
                } else if (matcherRoute.find()) {
                    String villeA = matcherRoute.group(1);
                    String villeB = matcherRoute.group(2);
                    if (!communaute.ajouterRoute(new Route(communaute.getVilleParNom(villeA), communaute.getVilleParNom(villeB)))) {
                        System.out.println("Erreur");
                    }
                } else if (matcherRecharge.find()) {
                    String villeRecharge = matcherRecharge.group(1);
                    Ville ville = communaute.getVilleParNom(villeRecharge);
                    if (ville != null) {
                        ville.ajouterBorneRecharge();
                    } else {
                        System.out.println("Erreur: La ville " + villeRecharge + " pour la zone de recharge n'existe pas.");
                    }
                } else {
                    System.out.println("Erreur: Ligne non reconnue ou malformée -> " + ligne);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            throw e;
        }
    }

    public CommunauteAgglomeration getCommunaute() {
        return communaute;
    }
}