package src.up.mi.yfh.exception;

/**
 * Cette classe représente une exception personnalisée pour les routes
 * dupliquées.
 */
public class RouteDupliqueeException extends RuntimeException {

    /**
     * Constructeur de l'exception RouteDupliqueeException.
     *
     * @param message Le message d'erreur associé à cette exception.
     */
    public RouteDupliqueeException(String message) {
        super(message);
    }
}
