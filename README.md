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

- Chargement d'un fichier CSV (≥15 véhicules, dont au moins une donnée invalide)
- Hiérarchie de véhicules (`Vehicule` abstrait → `Voiture`, `Camion`, `Moto`)
- Interfaces : location, entretien, rapport
- Exceptions personnalisées et validation des données
- Calcul de tarifs par type (redéfinition de méthodes)
- Statistiques (revenu, kilométrage, utilisation, entretien)
- Génération d'un rapport TXT/CSV
- Conception ouverte à l'ajout d'un nouveau type de véhicule (OCP)

## Structure

```
├── README.md
├── contributions.txt
├── data/vehicules.csv
├── reports/
├── src/
├── PetroAsinyo/
└── KassoumDene/
```

## Exécution

*(Instructions à compléter après l'implémentation — IntelliJ / `javac` + `java`)*

## Principes SOLID

- **SRP** : services séparés (CSV, flotte, statistiques, rapport)
- **OCP** : nouveau type de véhicule sans modifier le cœur de la flotte
