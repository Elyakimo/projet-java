# Architecture Spring Boot & feuille de route — Gestionnaire de stages

## Les 3 couches d'une application Spring Boot

```
Requête HTTP (Vue, Postman, navigateur...)
        ↓
[CONTRÔLEUR / API REST]   ← reçoit les requêtes HTTP (GET, POST, PUT, DELETE...)
        ↓
[SERVICE]                  ← logique métier (optionnel, souvent utile)
        ↓
[REPOSITORY]                ← parle à la base de données (SQL généré automatiquement)
        ↓
Base de données PostgreSQL
```

### Le Repository
- Couche la plus basse, communique directement avec la base.
- Pas d'URL, pas de HTTP : uniquement des méthodes Java qui exécutent des `SELECT`/`INSERT`/`UPDATE`/`DELETE` en interne.
- Avec Spring Data JPA, une interface **vide** suffit à obtenir automatiquement `save()`, `findById()`, `findAll()`, `delete()`...

```java
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
}
```

### Le Service (optionnel mais recommandé)
- Contient la logique métier : règles, validations, orchestration entre plusieurs repositories.
- Fait le lien entre le contrôleur et le(s) repository(ies).

### Le Contrôleur (API REST)
- Expose les fonctionnalités au monde extérieur via des URLs HTTP.
- C'est cette couche que le futur frontend Vue appellera.

```
GET    /api/organisations        → liste toutes les organisations
POST   /api/organisations        → crée une nouvelle organisation
GET    /api/organisations/{id}   → récupère une organisation précise
PUT    /api/organisations/{id}   → modifie une organisation
DELETE /api/organisations/{id}   → supprime une organisation
```

### Pourquoi séparer ces couches ?
Le repository ne connaît rien à HTTP ou JSON — il ne fait que parler à la base.
Le contrôleur sait recevoir des requêtes web et les traduire en appels au repository (ou au service).
Cette séparation rend le code plus clair et plus facile à maintenir.

---

## Les Repositories en détail

### Rôle du repository
Le repository est la couche la plus basse de l'application : il parle directement à la base de données.
Il ne connaît rien à HTTP, aux URLs ou au JSON — uniquement à l'entité qu'il gère et à sa clé primaire.

### Pourquoi une `interface` et pas une `class` ?

Une interface Java définit un **contrat** (une liste de méthodes), sans fournir leur implémentation.
Spring Data JPA s'appuie sur ce mécanisme :

```java
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
}
```

Ce code ne contient **aucune méthode écrite à la main**. Pourtant `save()`, `findAll()`, `findById()`... fonctionnent.

**Ce qui se passe réellement :** au démarrage de l'application, Spring scanne toutes les interfaces qui
étendent `JpaRepository`, et **génère lui-même, en mémoire, une classe** qui les implémente (un *proxy
dynamique*). Le développeur ne voit jamais cette classe générée dans son code source.

### Décomposer `JpaRepository<Organisation, UUID>`

- `Organisation` → le type d'entité géré par ce repository
- `UUID` → le type de la clé primaire de cette entité (doit correspondre au type du champ `@Id`)

### Ce que `JpaRepository` fournit automatiquement

| Méthode | Rôle |
|---|---|
| `save(entity)` | Insère ou met à jour une ligne |
| `findById(id)` | Retourne un `Optional<Entity>` |
| `findAll()` | Retourne `List<Entity>` — toutes les lignes |
| `deleteById(id)` | Supprime une ligne |
| `count()` | Nombre total de lignes |
| `existsById(id)` | Vérifie l'existence d'une ligne |

Une vingtaine d'autres méthodes héritées sont disponibles, dont certaines paginées/triées (`findAll(Pageable)`, `findAll(Sort)`).

### Pourquoi ce choix de conception (interface + proxy) ?

1. **Séparation contrat / implémentation** — le développeur déclare *ce qu'il veut* (gérer des `Organisation`
   par `UUID`), Spring fournit *le comment* (le SQL/Hibernate généré).
2. **Extensible sans rien casser** — on peut ajouter des méthodes personnalisées, et Spring génère aussi
   leur implémentation à partir du **nom de la méthode** :
   ```java
   public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
       List<Organisation> findByNomEntreprise(String nomEntreprise);
   }
   ```
   Spring analyse `findBy` + `NomEntreprise` et génère automatiquement la requête correspondante — aucune
   implémentation à écrire. C'est ce mécanisme qui pourra remplacer les méthodes de recherche personnalisées
   du projet console (`rechercherParNomEtudiant`, etc.).
3. **Découplage** — le reste du code (services, contrôleurs) dépend de l'**interface**, jamais de
   l'implémentation concrète générée par Spring. Si Spring change sa façon de générer le code en interne,
   rien à modifier côté application.

### Repositories du projet

```java
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> { }
public interface StagiaireRepository extends JpaRepository<Stagiaire, UUID> { }
public interface TuteurRepository extends JpaRepository<Tuteur, UUID> { }
public interface StageRepository extends JpaRepository<Stage, UUID> { }
```

Tous situés dans `com.onocia.gestionstage.repository`.

---

## Les Contrôleurs REST en détail

### Rôle du contrôleur
Le contrôleur est la couche qui **expose** l'application au monde extérieur via des URLs HTTP.
Il reçoit une requête (GET, POST, PUT, DELETE...), délègue le travail au repository (ou au service),
et renvoie une réponse — généralement au format JSON.

### Les annotations clés

| Annotation | Rôle |
|---|---|
| `@RestController` | Indique que la classe expose des endpoints HTTP qui retournent directement des données (JSON), pas des pages HTML |
| `@RequestMapping("/api/xxx")` | Préfixe d'URL commun à toutes les routes de la classe |
| `@GetMapping` | Route qui répond à une requête HTTP **GET** (lire) |
| `@PostMapping` | Route qui répond à une requête **POST** (créer) |
| `@PutMapping` | Route qui répond à une requête **PUT** (modifier) |
| `@DeleteMapping` | Route qui répond à une requête **DELETE** (supprimer) |
| `@PathVariable` | Récupère une valeur directement depuis l'URL (ex : l'`{id}` dans `/api/organisations/{id}`) |
| `@RequestBody` | Convertit automatiquement le JSON envoyé dans le corps de la requête en objet Java |

### L'injection de dépendances

Le contrôleur a besoin du repository pour fonctionner. On le lui donne via le **constructeur** :

```java
@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {

    private final OrganisationRepository organisationRepository;

    public OrganisationController(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }
}
```

Spring détecte automatiquement, au démarrage, que ce contrôleur a besoin d'un `OrganisationRepository`,
et lui **injecte** l'implémentation qu'il a générée (le proxy dynamique vu dans la section précédente).
Jamais besoin d'écrire `new OrganisationRepository()` soi-même.

### Les 5 routes CRUD de base — pattern complet

```java
// GET liste complète
@GetMapping
public List<Organisation> getAllOrganisations() {
    return organisationRepository.findAll();
}

// GET par id — avec gestion propre du cas "non trouvé" (404)
@GetMapping("/{id}")
public ResponseEntity<Organisation> getOrganisationById(@PathVariable UUID id) {
    return organisationRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}

// POST — création
@PostMapping
public Organisation createOrganisation(@RequestBody Organisation organisation) {
    return organisationRepository.save(organisation);
}

// PUT — modification
@PutMapping("/{id}")
public ResponseEntity<Organisation> updateOrganisation(@PathVariable UUID id, @RequestBody Organisation organisation) {
    return organisationRepository.findById(id)
        .map(existingOrganisation -> {
            existingOrganisation.setNomEntreprise(organisation.getNomEntreprise());
            existingOrganisation.setMailEntreprise(organisation.getMailEntreprise());
            existingOrganisation.setTelephoneEntreprise(organisation.getTelephoneEntreprise());
            existingOrganisation.setAdresseEntreprise(organisation.getAdresseEntreprise());
            Organisation updated = organisationRepository.save(existingOrganisation);
            return ResponseEntity.ok(updated);
        })
        .orElse(ResponseEntity.notFound().build());
}

// DELETE — suppression, avec vérification d'existence
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteOrganisation(@PathVariable UUID id) {
    if (!organisationRepository.existsById(id)) {
        return ResponseEntity.status(404).body("L'organisation n'existe pas.");
    }
    organisationRepository.deleteById(id);
    return ResponseEntity.ok("Organisation supprimée avec succès.");
}
```

### `ResponseEntity<T>` — pourquoi l'utiliser

`ResponseEntity` permet de contrôler explicitement :
- Le **code de statut HTTP** renvoyé (200 OK, 201 Created, 404 Not Found, 400 Bad Request...)
- Le **corps** de la réponse

Sans lui, Spring renvoie toujours un 200 par défaut, même en cas d'échec logique (ex : id introuvable) —
ce qui empêche le frontend de distinguer un succès d'un échec autrement qu'en lisant le texte du message.
Utiliser les bons codes de statut est une convention REST importante : le frontend (Vue, plus tard) s'appuiera
dessus pour savoir comment réagir.

### Le pattern `Optional.map(...).orElse(...)`

`findById(id)` renvoie un `Optional<Entity>` (une "boîte" qui peut contenir une valeur ou être vide) :
- `.map(...)` s'exécute **seulement si** une valeur est présente, et la transforme en autre chose
  (ici, en `ResponseEntity`)
- Si l'`Optional` est vide, `.map(...)` ne fait rien et laisse passer un `Optional` vide, qui déclenche
  alors `.orElse(...)`

C'est une façon concise d'éviter les `if (present) {...} else {...}` explicites.

### Le cas des entités avec relations (`Tuteur`, `Stage`)

`Tuteur` et `Stage` contiennent des champs `@ManyToOne` (vers `Organisation`, et vers
`Organisation`/`Tuteur`/`Stagiaire` pour `Stage`). Pour créer/modifier ces entités via l'API, le JSON envoyé
doit inclure l'entité liée (ou son id), et le contrôleur doit s'assurer que l'objet lié référence une ligne
existante en base — point à traiter avec attention lors de l'écriture de `TuteurController` et
`StageController`.

---

## État d'avancement du projet

### ✅ Fait
- Modélisation et console Java (CRUD en mémoire + persistance CSV)
- Schéma PostgreSQL créé et testé en CLI (4 tables, contraintes, ENUM)
- Projet Spring Boot généré (Maven, dépendances Web/JPA/PostgreSQL)
- Connexion à PostgreSQL configurée et validée (`ddl-auto=validate`)
- 4 entités JPA créées et validées : `Organisation`, `Stagiaire`, `Tuteur`, `Stage` (+ enum `StatutStage`)
- 4 repositories Spring Data JPA (`OrganisationRepository`, `StagiaireRepository`, `TuteurRepository`, `StageRepository`)
- 4 contrôleurs REST complets et testés (CRUD sur les 4 entités, gestion des relations `@ManyToOne`, cast ENUM PostgreSQL résolu)
- **Backend Spring Boot entièrement fonctionnel et validé de bout en bout**

### ⬜ À venir — déroulement détaillé

**Étape 1 (optionnelle) — Couche Service**
Ajouter une couche service si la logique métier devient plus complexe que du simple CRUD (ex : recherche par statut, par nom d'étudiant/entreprise — équivalent de ce qui existait déjà dans `GestionnaireStage.java` du projet console). Permettrait aussi de corriger la gestion d'erreurs actuelle (certaines exceptions remontent en 500 au lieu de 400/404 propres).

**Étape 2 — Frontend Vue**
Construire l'interface Vue qui consomme les 20 endpoints REST déjà testés et validés.

**Étape 3 (bonus) — Sécurité**
Ajouter Spring Security si le projet doit un jour être accessible au-delà d'un usage strictement personnel/local.
