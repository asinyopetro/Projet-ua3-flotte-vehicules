package exceptions;

/** Vehicule deja loue, en entretien, ou introuvable. */
public class VehiculeIndisponibleException extends Exception {

    public VehiculeIndisponibleException(String message) {
        super(message);
    }
}
