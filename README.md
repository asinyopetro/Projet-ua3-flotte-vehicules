# Gestion d'une flotte de véhicules

Cours : Programmation Avancée (UA3) — Printemps 2026  
Projet 3 : conception orientée objet appliquée à l'analyse de données

## Membres

| Nom | Dossier | GitHub |
|-----|---------|--------|
| Komla Petro Asinyo | `PetroAsinyo/` | [asinyopetro](https://github.com/asinyopetro) |
| Kassoum Dene | `KassoumDene/` | [Youri2K](https://github.com/Youri2K) |

## Objectif

Petit programme console en Java pour gérer une flotte de location : catalogue de véhicules, locations / retours, entretien, quelques stats et un rapport texte.

## Ce que fait le programme

- Charge `data/vehicules.csv` (17 véhicules valides + 2 lignes invalides pour tester les erreurs)
- Hiérarchie `Vehicule` (abstraite) → `Voiture`, `Camion`, `Moto`
- Interfaces `Louable`, `Entretenable`, `Rapportable`
- Exceptions perso + validation à la lecture du CSV
- Tarif différent selon le type (`calculerTarif` redéfini)
- Stats (revenu, km, utilisation, entretien) + fichier dans `reports/`
- Menu optionnel avec `--menu`
- On peut ajouter un nouveau type de véhicule surtout via la factory (OCP)

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

## Lancer le projet

### IntelliJ

1. Ouvrir le dossier du projet
2. Marquer `src/` comme Sources Root
3. Lancer `Main.java`
4. Optionnel : ajouter `--menu` dans les arguments du Run Configuration

### Ligne de commande

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Main
java -cp out Main --menu
```

## SOLID (ce qu'on a appliqué)

- SRP : le loader CSV, le gestionnaire de flotte, les stats et le rapport sont séparés
- OCP : nouveau type = nouvelle sous-classe + case dans `VehiculeFactory`, sans tout casser ailleurs

## GitHub

https://github.com/asinyopetro/Projet-ua3-flotte-vehicules
