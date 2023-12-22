package src.up.mi.jgm.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Set;
import src.up.mi.jgm.agglomeration.*;
import src.up.mi.jgm.exceptions.*;

public class ChargerCommunauteDepuisFichier {

    private CommunauteAgglomeration communaute;
    	
    /**
     * Constructeur de la classe ChargerCommunaute.
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
        // Patterns pour extraire les informations de chaque ligne
        Pattern patternVille = Pattern.compile("ville\\((.*?)\\)\\.");
        Pattern patternRoute = Pattern.compile("route\\((.*?),(.*?)\\)\\.");
        Pattern patternRecharge = Pattern.compile("recharge\\((.*?)\\)\\.");

        // Pour vérifier l'unicité des villes et des routes
        Set<String> villesExistantes = new HashSet<>();
        Set<String> routesExistantes = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                Matcher matcherVille = patternVille.matcher(ligne);
                Matcher matcherRoute = patternRoute.matcher(ligne);
                Matcher matcherRecharge = patternRecharge.matcher(ligne);

                if (matcherVille.find()) {
                    // Si la ligne contient une définition de ville
                    String nomVille = matcherVille.group(1);
                    if (!villesExistantes.contains(nomVille)) {
                    	Ville ville = new Ville(nomVille);
                        communaute.ajouterVille(ville);
                        villesExistantes.add(nomVille);
                    } else {
                        throw new VilleDupliqueeExceptions("Erreur : La ville " + nomVille + " est définie plus d'une fois.");
                    }
                } else if (matcherRoute.find()) {
                    // Si la ligne contient une définition de route
                    String villeA = matcherRoute.group(1);
                    String villeB = matcherRoute.group(2);
                    if (villesExistantes.contains(villeA) && villesExistantes.contains(villeB)) {
                        String routeKey = villeA + "-" + villeB;
                        if (!routesExistantes.contains(routeKey)) {
                            communaute.ajouterRoute(new Route(communaute.getVilleParNom(villeA), communaute.getVilleParNom(villeB)));
                            routesExistantes.add(routeKey);
                        } else {
                            throw new RouteDupliqueeExceptions("Erreur : La route entre " + villeA + " et " + villeB + " est définie plus d'une fois.");
                        }
                    } else {
                        throw new VilleInexisistanteExceptions("Erreur : Les villes " + villeA + " ou " + villeB + " n'existent pas.");
                    }

                } else if (matcherRecharge.find()) {
                    // Si la ligne contient une définition de recharge
                    String villeRecharge = matcherRecharge.group(1);
                    Ville ville = communaute.getVilleParNom(villeRecharge);
                    if (ville != null) {
                        ville.ajouterBorneRecharge();
                    } else {
                        throw new VIlleRechargeInexistante("Erreur : La ville " + villeRecharge + " pour la zone de recharge n'existe pas.");
                    }
                } else {
              
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
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
