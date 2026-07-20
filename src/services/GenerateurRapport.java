package services;

import modele.Vehicule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Génère le rapport texte de synthèse de la flotte.
 */
public class GenerateurRapport {

    private static final DateTimeFormatter FORMAT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void generer(Path cheminSortie, CalculateurStatistiques stats,
                        List<Vehicule> tousLesVehicules, List<Vehicule> vehiculesEntretien)
            throws IOException {
        Files.createDirectories(cheminSortie.getParent());

        StringBuilder rapport = new StringBuilder();
        rapport.append("RAPPORT DE FLOTTE — Gestion de véhicules\n");
        rapport.append("Généré le: ").append(LocalDateTime.now().format(FORMAT_DATE)).append("\n");
        rapport.append("=".repeat(60)).append("\n\n");

        rapport.append("--- Statistiques globales ---\n");
        rapport.append(String.format("Revenu total généré     : %.2f $\n", stats.revenuTotal()));
        rapport.append(String.format("Kilométrage moyen       : %.0f km\n", stats.kilometrageMoyen()));
        rapport.append(String.format("Véhicules en entretien  : %d\n", stats.nombreEnEntretien()));
        rapport.append("\n");

        rapport.append("--- Nombre de véhicules par type ---\n");
        for (Map.Entry<String, Long> entry : stats.nombreParType().entrySet()) {
            rapport.append(String.format("  %s : %d\n", entry.getKey(), entry.getValue()));
        }
        rapport.append("\n");

        rapport.append("--- Taux d'utilisation (jours loués moyens par type) ---\n");
        for (Map.Entry<String, Double> entry : stats.tauxUtilisationParType().entrySet()) {
            rapport.append(String.format("  %s : %.1f jours\n", entry.getKey(), entry.getValue()));
        }
        rapport.append("\n");

        rapport.append("--- Top 5 véhicules les plus utilisés ---\n");
        for (Vehicule v : stats.vehiculesLesPlusUtilises(5)) {
            rapport.append(String.format("  %s (%s %s) — %d jours\n",
                    v.getId(), v.getMarque(), v.getModele(), v.getJoursLoues()));
        }
        rapport.append("\n");

        rapport.append("--- Véhicules jamais loués ---\n");
        List<Vehicule> jamaisLoues = stats.vehiculesJamaisLoues();
        if (jamaisLoues.isEmpty()) {
            rapport.append("  Aucun\n");
        } else {
            for (Vehicule v : jamaisLoues) {
                rapport.append(String.format("  %s (%s %s)\n",
                        v.getId(), v.getMarque(), v.getModele()));
            }
        }
        rapport.append("\n");

        rapport.append("--- Véhicules nécessitant un entretien ---\n");
        if (vehiculesEntretien.isEmpty()) {
            rapport.append("  Aucun\n");
        } else {
            for (Vehicule v : vehiculesEntretien) {
                rapport.append(String.format("  %s — %d km (seuil: %d km)\n",
                        v.getId(), v.getKilometrage(), v.getSeuilEntretienKm()));
            }
        }
        rapport.append("\n");

        rapport.append("--- Détail du parc ---\n");
        rapport.append("id;type;vehicule;km;statut;jours;entretien;revenu\n");
        for (Vehicule v : tousLesVehicules) {
            rapport.append(v.genererLigneRapport()).append("\n");
        }

        Files.writeString(cheminSortie, rapport.toString(), StandardCharsets.UTF_8);
    }
}
