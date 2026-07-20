Travail de Kassoum Dene
=======================

## Fichiers développés
- data/vehicules.csv — 17 véhicules + 2 lignes invalides (type BUS, km négatif)
- src/services/VehiculeFactory.java — création des véhicules par type
- src/services/CsvVehiculeLoader.java — lecture et validation CSV
- src/services/GestionnaireFlotte.java — location, retour, entretien
- src/services/CalculateurStatistiques.java — statistiques agrégées
- src/services/GenerateurRapport.java — export rapport TXT
- src/Main.java — statistiques, rapport, menu interactif

## Principes appliqués
- SRP : une classe par responsabilité (CSV, flotte, stats, rapport)
- Gestion des exceptions lors du chargement CSV
- ArrayList pour stocker la flotte
