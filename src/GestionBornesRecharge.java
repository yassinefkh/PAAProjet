    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    public class GestionBornesRecharge {
    
        public static String listeVillesAvecBornesRecharge(CommunauteAgglomeration communaute) {
            List<Ville> villesAvecBornes = new ArrayList<>();
            for (Ville ville : communaute.getVilles()) {
                if (ville.possedeBorneRecharge()) {
                    villesAvecBornes.add(ville);
                }
            }
            StringBuilder builder = new StringBuilder();
            for (Ville ville : villesAvecBornes) {
                builder.append(ville.getNom());
            }
            return builder.toString();
        }

        public static boolean peutRetirerBorneRecharge(Ville ville, CommunauteAgglomeration communaute) {
            for (Route route : communaute.getRoutes()) {
                Ville autreVille = (route.getVilleA() == ville) ? route.getVilleB() : route.getVilleA();
                if (autreVille.possedeBorneRecharge()) {
                    return true;
                }
            }
            return false;
        }
    }