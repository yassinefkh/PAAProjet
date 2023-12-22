package src.up.mi.yfh.exception;

/**
 * Cette classe représente une exception personnalisée pour les villes
 * dupliquées.
 */
public class VilleDupliqueeException extends RuntimeException {

    /**
     * Constructeur de l'exception VilleDupliqueeException.
     *
     * @param message Le message d'erreur associé à cette exception.
     */
    public VilleDupliqueeException(String message) {
        super(message);
    }
}
