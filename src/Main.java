import exceptions.KilometrageInvalideException;
import exceptions.VehiculeIndisponibleException;
import modele.Vehicule;
import services.CalculateurStatistiques;
import services.CsvVehiculeLoader;
import services.GenerateurRapport;
import services.GestionnaireFlotte;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Point d'entrée — démonstration du système de gestion de flotte.
 */
public class Main {

    private static final Path FICHIER_CSV = Path.of("data", "vehicules.csv");
    private static final Path FICHIER_RAPPORT = Path.of("reports", "rapport_flotte.txt");

    public static void main(String[] args) {
        System.out.println("=== Gestion de flotte de véhicules — UA3 ===\n");

        try {
            GestionnaireFlotte flotte = chargerFlotte();
            executerDemo(flotte);

            if (args.length > 0 && args[0].equalsIgnoreCase("--menu")) {
                menuInteractif(flotte);
            }

            afficherStatistiques(flotte);
            genererRapport(flotte);

            System.out.println("\nProgramme terminé avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur de fichier: " + e.getMessage());
        }
    }

    private static GestionnaireFlotte chargerFlotte() throws IOException {
        System.out.println("Chargement du fichier CSV: " + FICHIER_CSV);
        CsvVehiculeLoader loader = new CsvVehiculeLoader();
        CsvVehiculeLoader.ResultatChargement resultat = loader.charger(FICHIER_CSV);

        if (!resultat.erreurs().isEmpty()) {
            System.out.println("\n--- Erreurs de chargement (lignes ignorées) ---");
            for (String erreur : resultat.erreurs()) {
                System.out.println("  ! " + erreur);
            }
        }

        System.out.printf("\n%d véhicule(s) chargé(s) avec succès.\n", resultat.vehicules().size());
        return new GestionnaireFlotte(resultat.vehicules());
    }

    private static void executerDemo(GestionnaireFlotte flotte) {
        System.out.println("\n--- Parc automobile ---");
        afficherParc(flotte);

        System.out.println("\n--- Démonstration: locations et retours ---");
        try {
            flotte.louer("V001");
            System.out.println("Location V001 effectuée.");

            flotte.louer("M003");
            System.out.println("Location M003 effectuée.");

            double tarif = flotte.retourner("V001", 45200, 5);
            System.out.printf("Retour V001 — tarif: %.2f $\n", tarif);

            flotte.louer("C002");
            System.out.println("Location C002 effectuée.");

            double tarifMoto = flotte.retourner("M003", 8200, 3);
            System.out.printf("Retour M003 — tarif: %.2f $\n", tarifMoto);

        } catch (VehiculeIndisponibleException | KilometrageInvalideException e) {
            System.out.println("Erreur démo: " + e.getMessage());
        }

        System.out.println("\n--- Démonstration: entretien ---");
        try {
            flotte.signalerEntretien("V005");
            System.out.println("Entretien signalé pour V005.");
            flotte.terminerEntretien("V005");
            System.out.println("Entretien terminé pour V005.");
        } catch (VehiculeIndisponibleException e) {
            System.out.println("Erreur entretien: " + e.getMessage());
        }

        System.out.println("\n--- Test gestion d'erreur: kilométrage invalide ---");
        try {
            flotte.louer("V002");
            flotte.retourner("V002", 10000, 2);
        } catch (VehiculeIndisponibleException | KilometrageInvalideException e) {
            System.out.println("Erreur capturée (attendu): " + e.getMessage());
        }
    }

    private static void afficherParc(GestionnaireFlotte flotte) {
        for (Vehicule v : flotte.getVehicules()) {
            System.out.println("  " + v);
        }
    }

    private static void afficherStatistiques(GestionnaireFlotte flotte) {
        CalculateurStatistiques stats = new CalculateurStatistiques(flotte.getVehicules());

        System.out.println("\n--- Statistiques ---");
        System.out.printf("Revenu total        : %.2f $\n", stats.revenuTotal());
        System.out.printf("Kilométrage moyen   : %.0f km\n", stats.kilometrageMoyen());
        System.out.printf("Locations actives   : %d\n", flotte.getLocationsActives());

        System.out.println("\nVéhicules par type:");
        for (Map.Entry<String, Long> entry : stats.nombreParType().entrySet()) {
            System.out.printf("  %s : %d\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nTaux d'utilisation (jours moyens / type):");
        for (Map.Entry<String, Double> entry : stats.tauxUtilisationParType().entrySet()) {
            System.out.printf("  %s : %.1f jours\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nTop 3 véhicules les plus utilisés:");
        for (Vehicule v : stats.vehiculesLesPlusUtilises(3)) {
            System.out.printf("  %s — %d jours\n", v.getId(), v.getJoursLoues());
        }

        System.out.println("\nVéhicules nécessitant un entretien:");
        List<Vehicule> entretien = flotte.vehiculesNecessitantEntretien();
        if (entretien.isEmpty()) {
            System.out.println("  Aucun");
        } else {
            for (Vehicule v : entretien) {
                System.out.printf("  %s — %d km\n", v.getId(), v.getKilometrage());
            }
        }
    }

    private static void genererRapport(GestionnaireFlotte flotte) throws IOException {
        CalculateurStatistiques stats = new CalculateurStatistiques(flotte.getVehicules());
        GenerateurRapport generateur = new GenerateurRapport();
        generateur.generer(FICHIER_RAPPORT, stats, flotte.getVehicules(),
                flotte.vehiculesNecessitantEntretien());
        System.out.println("\nRapport généré: " + FICHIER_RAPPORT);
    }

    private static void menuInteractif(GestionnaireFlotte flotte) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Afficher le parc");
            System.out.println("2. Louer un véhicule");
            System.out.println("3. Retourner un véhicule");
            System.out.println("4. Signaler un entretien");
            System.out.println("5. Terminer un entretien");
            System.out.println("6. Afficher les statistiques");
            System.out.println("0. Quitter le menu");
            System.out.print("Choix: ");

            String choix = scanner.nextLine().trim();

            try {
                switch (choix) {
                    case "1" -> afficherParc(flotte);
                    case "2" -> {
                        System.out.print("ID du véhicule: ");
                        flotte.louer(scanner.nextLine().trim());
                        System.out.println("Location effectuée.");
                    }
                    case "3" -> {
                        System.out.print("ID: ");
                        String id = scanner.nextLine().trim();
                        System.out.print("Nouveau km: ");
                        int km = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Jours: ");
                        int jours = Integer.parseInt(scanner.nextLine().trim());
                        double tarif = flotte.retourner(id, km, jours);
                        System.out.printf("Retour effectué — tarif: %.2f $\n", tarif);
                    }
                    case "4" -> {
                        System.out.print("ID: ");
                        flotte.signalerEntretien(scanner.nextLine().trim());
                        System.out.println("Entretien signalé.");
                    }
                    case "5" -> {
                        System.out.print("ID: ");
                        flotte.terminerEntretien(scanner.nextLine().trim());
                        System.out.println("Entretien terminé.");
                    }
                    case "6" -> afficherStatistiques(flotte);
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Choix invalide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nombre invalide.");
            } catch (VehiculeIndisponibleException | KilometrageInvalideException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }
    }
}
