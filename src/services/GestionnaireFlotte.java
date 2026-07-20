package services;

import exceptions.KilometrageInvalideException;
import exceptions.VehiculeIndisponibleException;
import modele.Vehicule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Gère les opérations métier sur la flotte (location, retour, entretien).
 */
public class GestionnaireFlotte {

    public static final int MAX_LOCATIONS_SIMULTANEES = 5;

    private final List<Vehicule> vehicules;
    private int locationsActives;

    public GestionnaireFlotte(List<Vehicule> vehicules) {
        this.vehicules = new ArrayList<>(vehicules);
        this.locationsActives = compterLocationsActives();
    }

    public List<Vehicule> getVehicules() {
        return List.copyOf(vehicules);
    }

    public void louer(String id) throws VehiculeIndisponibleException {
        if (locationsActives >= MAX_LOCATIONS_SIMULTANEES) {
            throw new VehiculeIndisponibleException(
                    "Limite de " + MAX_LOCATIONS_SIMULTANEES + " locations simultanées atteinte.");
        }

        Vehicule vehicule = trouverVehicule(id)
                .orElseThrow(() -> new VehiculeIndisponibleException(
                        "Véhicule introuvable: " + id));

        vehicule.louer();
        locationsActives++;
    }

    public double retourner(String id, int nouveauKilometrage, int joursLoues)
            throws KilometrageInvalideException, VehiculeIndisponibleException {
        Vehicule vehicule = trouverVehicule(id)
                .orElseThrow(() -> new VehiculeIndisponibleException(
                        "Véhicule introuvable: " + id));

        if (vehicule.isDisponible()) {
            throw new VehiculeIndisponibleException(
                    "Le véhicule " + id + " n'est pas actuellement loué.");
        }

        double tarif = vehicule.retourner(nouveauKilometrage, joursLoues);
        locationsActives = Math.max(0, locationsActives - 1);
        return tarif;
    }

    public void signalerEntretien(String id) throws VehiculeIndisponibleException {
        Vehicule vehicule = trouverVehicule(id)
                .orElseThrow(() -> new VehiculeIndisponibleException(
                        "Véhicule introuvable: " + id));
        vehicule.signalerEntretien();
    }

    public void terminerEntretien(String id) throws VehiculeIndisponibleException {
        Vehicule vehicule = trouverVehicule(id)
                .orElseThrow(() -> new VehiculeIndisponibleException(
                        "Véhicule introuvable: " + id));
        vehicule.terminerEntretien();
    }

    public List<Vehicule> vehiculesNecessitantEntretien() {
        return vehicules.stream()
                .filter(Vehicule::besoinEntretien)
                .sorted(Comparator.comparing(Vehicule::getId))
                .toList();
    }

    public int getLocationsActives() {
        return locationsActives;
    }

    private Optional<Vehicule> trouverVehicule(String id) {
        return vehicules.stream()
                .filter(v -> v.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    private int compterLocationsActives() {
        return (int) vehicules.stream()
                .filter(v -> !v.isDisponible() && !v.isEnEntretien())
                .count();
    }
}
