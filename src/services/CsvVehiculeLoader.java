package services;

import exceptions.DonneeInvalideException;
import modele.Vehicule;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable unique du chargement et de la validation du CSV (SRP).
 */
public class CsvVehiculeLoader {

    private static final int COLONNES_ATTENDUES = 11;
    private final VehiculeFactory factory = new VehiculeFactory();

    public ResultatChargement charger(Path cheminFichier) throws IOException {
        List<Vehicule> vehicules = new ArrayList<>();
        List<String> erreurs = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(cheminFichier, StandardCharsets.UTF_8)) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = reader.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();

                if (ligne.isEmpty() || ligne.startsWith("#")) {
                    continue;
                }
                if (ligne.startsWith("id;")) {
                    continue;
                }

                try {
                    vehicules.add(parserLigne(ligne, numeroLigne));
                } catch (DonneeInvalideException e) {
                    erreurs.add("Ligne " + numeroLigne + ": " + e.getMessage());
                }
            }
        }

        return new ResultatChargement(vehicules, erreurs);
    }

    private Vehicule parserLigne(String ligne, int numeroLigne) throws DonneeInvalideException {
        String[] colonnes = ligne.split(";", -1);

        if (colonnes.length != COLONNES_ATTENDUES) {
            throw new DonneeInvalideException(
                    "Nombre de colonnes incorrect (" + colonnes.length + " au lieu de "
                            + COLONNES_ATTENDUES + ").");
        }

        try {
            String id = colonnes[0].trim();
            String type = colonnes[1].trim();
            String marque = colonnes[2].trim();
            String modele = colonnes[3].trim();
            int annee = Integer.parseInt(colonnes[4].trim());
            int kilometrage = Integer.parseInt(colonnes[5].trim());
            boolean disponible = Boolean.parseBoolean(colonnes[6].trim());
            int joursLoues = Integer.parseInt(colonnes[7].trim());
            double revenuCumule = Double.parseDouble(colonnes[8].trim());
            int seuilEntretienKm = Integer.parseInt(colonnes[9].trim());
            double attribut = Double.parseDouble(colonnes[10].trim());

            validerChamps(id, marque, modele, annee, kilometrage, joursLoues, revenuCumule, seuilEntretienKm);

            return factory.creer(type, id, marque, modele, annee, kilometrage,
                    disponible, joursLoues, revenuCumule, seuilEntretienKm, attribut);
        } catch (NumberFormatException e) {
            throw new DonneeInvalideException(
                    "Format numérique invalide à la ligne " + numeroLigne + ".", e);
        }
    }

    private void validerChamps(String id, String marque, String modele, int annee,
                               int kilometrage, int joursLoues, double revenuCumule,
                               int seuilEntretienKm) throws DonneeInvalideException {
        if (id.isEmpty() || marque.isEmpty() || modele.isEmpty()) {
            throw new DonneeInvalideException("Champs obligatoires manquants (id, marque ou modèle).");
        }
        if (annee < 1990 || annee > 2026) {
            throw new DonneeInvalideException("Année invalide: " + annee);
        }
        if (kilometrage < 0) {
            throw new DonneeInvalideException("Kilométrage négatif: " + kilometrage);
        }
        if (joursLoues < 0) {
            throw new DonneeInvalideException("Jours loués négatifs: " + joursLoues);
        }
        if (revenuCumule < 0) {
            throw new DonneeInvalideException("Revenu cumulé négatif: " + revenuCumule);
        }
        if (seuilEntretienKm <= 0) {
            throw new DonneeInvalideException("Seuil d'entretien invalide: " + seuilEntretienKm);
        }
    }

    public record ResultatChargement(List<Vehicule> vehicules, List<String> erreurs) {
    }
}
