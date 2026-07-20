# Gestion d'une flotte de véhicules

**Cours :** Programmation Avancée · UA3 · Printemps 2026  
**Projet :** Projet 3 — Conception orientée objet appliquée à l'analyse de données

## Membres

| Nom | Dossier | GitHub |
|-----|---------|--------|
| Komla Petro Asinyo | `PetroAsinyo/` | [asinyopetro](https://github.com/asinyopetro) |
| Kassoum Dene | `KassoumDene/` | [Youri2K](https://github.com/Youri2K) |

## Objectif

Application console Java permettant de gérer une flotte de véhicules de location : catalogue (héritage), location/retour, entretien, statistiques et génération de rapport.

## Fonctionnalités

- Chargement d'un fichier CSV (17 véhicules valides + 2 lignes invalides)
- Hiérarchie de véhicules (`Vehicule` abstrait → `Voiture`, `Camion`, `Moto`)
- Interfaces : `Louable`, `Entretenable`, `Rapportable`
- Exceptions personnalisées et validation des données
- Calcul de tarifs par type (redéfinition de méthodes)
- Gestion des locations, retours et entretiens
- Statistiques (revenu, kilométrage, utilisation, entretien)
- Génération d'un rapport TXT dans `reports/`
- Menu interactif optionnel (`--menu`)
- Conception ouverte à l'ajout d'un nouveau type de véhicule (OCP)

## Structure

```
├── README.md
├── contributions.txt
├── data/vehicules.csv
├── reports/
├── src/
│   ├── Main.java
│   ├── modele/
│   ├── interfaces/
│   ├── exceptions/
│   └── services/
├── PetroAsinyo/
└── KassoumDene/
```

## Exécution

### IntelliJ IDEA

1. Ouvrir le dossier du projet
2. Marquer `src/` comme **Sources Root**
3. Exécuter `Main.java`
4. (Optionnel) Ajouter `--menu` dans *Run → Edit Configurations → Program arguments*

### Ligne de commande

```powershell
# Compiler
Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName } | ForEach-Object { } 
javac -encoding UTF-8 -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Exécuter (depuis la racine du projet)
java -cp out Main

# Avec menu interactif
java -cp out Main --menu
```

## Principes SOLID

- **SRP** : `CsvVehiculeLoader`, `GestionnaireFlotte`, `CalculateurStatistiques` et `GenerateurRapport` ont chacun une responsabilité unique
- **OCP** : ajout d'un nouveau type via `VehiculeFactory` sans modifier la logique de la flotte

## Lien GitHub

https://github.com/asinyopetro/Projet-ua3-flotte-vehicules
