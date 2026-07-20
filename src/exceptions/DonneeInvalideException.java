package exceptions;

/**
 * Levée lorsque des données CSV ou métier sont invalides.
 */
public class DonneeInvalideException extends Exception {

    public DonneeInvalideException(String message) {
        super(message);
    }

    public DonneeInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}
