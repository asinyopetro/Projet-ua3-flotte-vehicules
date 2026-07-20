package interfaces;

import exceptions.KilometrageInvalideException;
import exceptions.VehiculeIndisponibleException;

/**
 * Contrat pour les opérations de location et de retour.
 */
public interface Louable {

    void louer() throws VehiculeIndisponibleException;

    double retourner(int nouveauKilometrage, int joursLoues) throws KilometrageInvalideException;
}
