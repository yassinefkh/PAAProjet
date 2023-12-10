import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChargerCommunauteDepuisFichier {

    private CommunauteAgglomeration communaute;

     /**
     * Constructeur de la classe ChargerCommunauteDepuisFichier.
     * Charge la communauté d'agglomération depuis un fichier donné.
     *
     * @param cheminFichier Le chemin du fichier contenant les informations sur la communauté d'agglomération.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    public ChargerCommunauteDepuisFichier(String cheminFichier) throws IOException {
        this.communaute = new CommunauteAgglomeration();
        chargerDepuisFichier(cheminFichier);
    }

    /**
     * Charge les données de la communauté d'agglomération depuis un fichier texte.
     *
     * @param cheminFichier Le chemin du fichier contenant les informations sur la communauté d'agglomération.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    private void chargerDepuisFichier(String cheminFichier) throws IOException {
        Pattern patternVille = Pattern.compile("ville\\((.*?)\\)\\.");
        Pattern patternRoute = Pattern.compile("route\\((.*?),(.*?)\\)\\.");
        Pattern patternRecharge = Pattern.compile("recharge\\((.*?)\\)\\.");

        Set<String> routesAjoutees = new HashSet<>();

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
                    String cleRoute = villeA + "-" + villeB;

                    if (routesAjoutees.contains(cleRoute) || routesAjoutees.contains(villeB + "-" + villeA)) {
                        throw new IllegalArgumentException("Route dupliquée détectée: " + cleRoute);
                    }

                    Ville objetVilleA = communaute.getVilleParNom(villeA);
                    Ville objetVilleB = communaute.getVilleParNom(villeB);
                    if (objetVilleA == null || objetVilleB == null) {
                        throw new IllegalArgumentException("Route impossible à ajouter: Une ou plusieurs villes spécifiées n'existent pas.");
                    }

                    communaute.ajouterRoute(new Route(objetVilleA, objetVilleB));
                    routesAjoutees.add(cleRoute);
                } else if (matcherRecharge.find()) {
                    String villeRecharge = matcherRecharge.group(1);
                    Ville ville = communaute.getVilleParNom(villeRecharge);
                    if (ville == null) {
                        throw new IllegalArgumentException("Zone de recharge impossible à ajouter: La ville spécifiée n'existe pas.");
                    }
                    ville.ajouterBorneRecharge();
                } else {
                    throw new IOException("Ligne non reconnue ou malformée: " + ligne);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur dans le fichier : " + e.getMessage());
        }
    }

   

    /**
     * Récupère la communauté d'agglomération chargée depuis le fichier.
     *
     * @return La communauté d'agglomération chargée.
     */
    public CommunauteAgglomeration getCommunaute() {
        return communaute;
    }
}
