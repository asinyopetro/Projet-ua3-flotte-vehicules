package exceptions;

/** Kilometrage de retour incoherent (plus bas que l'actuel). */
public class KilometrageInvalideException extends Exception {

    public KilometrageInvalideException(String message) {
        super(message);
    }
}
