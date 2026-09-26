## État d'avancement du projet — Gestionnaire de stages

### ✅ Fait
- Modélisation et console Java (CRUD en mémoire + persistance CSV)
- Schéma PostgreSQL créé et testé en CLI (4 tables, contraintes, ENUM)
- Projet Spring Boot généré (Maven, dépendances Web/JPA/PostgreSQL)
- Connexion à PostgreSQL configurée et validée (`ddl-auto=validate`)
- 4 entités JPA créées et validées : `Organisation`, `Stagiaire`, `Tuteur`, `Stage` (+ enum `StatutStage`)
- 4 repositories Spring Data JPA (`OrganisationRepository`, `StagiaireRepository`, `TuteurRepository`, `StageRepository`)
- 4 contrôleurs REST complets et testés (CRUD sur les 4 entités, gestion des relations `@ManyToOne`, cast ENUM PostgreSQL résolu)
- **Backend Spring Boot entièrement fonctionnel et validé de bout en bout**
- Couche Service ajoutée sur les 4 entités (`OrganisationService`, `StagiaireService`, `TuteurService`, `StageService`)
- Gestion d'erreurs centralisée (`RessourceNonTrouveeException` + `GlobalExceptionHandler`) : 400/404 propres au lieu de 500 bruts
- Architecture 3 couches complète : Controller → Service → Repository, testée et validée
- **Frontend Vue.js créé (Vue Router, sans TypeScript/Pinia pour l'instant)**
- CORS configuré entre Vue (5173) et Spring Boot (8080)
- CRUD complet côté frontend sur les 4 entités : `Organisation`, `Stagiaire`, `Tuteur`, `Stage`
  - Listes avec chargement API (`fetch` + `onMounted`)
  - Formulaires réutilisables création/modification (`props`/`emit`/`watch`)
  - Gestion des relations via `<select>` (Tuteur → Organisation ; Stage → Organisation/Tuteur/Stagiaire)
  - Gestion du statut (enum) et des dates (conversion `LocalDateTime` ↔ `input type="date"`)
- **MVP fonctionnel atteint : afficher, ajouter, supprimer, modifier sur les 4 entités**

### ⬜ À venir

**Étape 1 — Polish de l'interface**
Mise en forme visuelle (CSS), gestion des états de chargement/erreur, validation de formulaires plus poussée.

**Étape 2 (bonus) — Sécurité**
Ajouter Spring Security si le projet doit un jour être accessible au-delà d'un usage strictement personnel/local.

**Étape 3 (bonus) — Dashboard/statistiques**
Vue d'ensemble visuelle des stages (par statut, par période...), évoquée dans les bonus de l'expression de besoin initiale.

**Étape 4 (bonus) — Recherche**
Ajouter une fonctionnalité de recherche côté frontend (par nom d'étudiant, d'entreprise, par statut...), en s'appuyant sur les endpoints déjà existants ou en ajoutant des méthodes de recherche dédiées côté backend.
