
class Ville {
    private final String nom;
    private boolean possedeBorneRecharge;

    public Ville(String nom) {
        this.nom = nom;
        this.possedeBorneRecharge = true; // est ce que une ville possede une borne par defaut?
    }

    public String getNom() {
        return nom;
    }

    public boolean possedeBorneRecharge() {
        return possedeBorneRecharge;
    }
 
    public void ajouterBorneRecharge() {
        possedeBorneRecharge = true;
    }

    public void retirerBorneRecharge() {
        possedeBorneRecharge = false;
    }
}