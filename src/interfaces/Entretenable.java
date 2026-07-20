package interfaces;

import exceptions.VehiculeIndisponibleException;

/**
 * Contrat pour le suivi des opérations d'entretien.
 */
public interface Entretenable {

    void signalerEntretien() throws VehiculeIndisponibleException;

    boolean besoinEntretien();

    void terminerEntretien();
}
