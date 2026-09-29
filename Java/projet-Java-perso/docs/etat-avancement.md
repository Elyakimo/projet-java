# Cours de référence — Java & Spring Boot (projet Gestionnaire de stages)

Ce document récapitule, sous forme de cours, tous les concepts vus au fil du projet — de la console Java au backend Spring Boot.

---

# PARTIE 1 — Java : les fondamentaux (projet console)

## 1. La Programmation Orientée Objet (POO)

### Classe, attributs, encapsulation

Une **classe** est un modèle qui décrit un type d'objet (ex : `Stage`). Ses **attributs** doivent être `private` (encapsulation) : on ne peut pas les modifier directement depuis l'extérieur, il faut passer par des méthodes dédiées.

```java
public class Etudiant {
    private String nom;
    private String prenom;
    // ...
}
```

### Constructeur

Méthode spéciale, portant le même nom que la classe, appelée lors de la création d'un objet (`new`). Sert à initialiser les attributs.

```java
public Etudiant(String nom, String prenom) {
    this.nom = nom;
    this.prenom = prenom;
}
```

### Getters / Setters

- **Getter** : lit la valeur d'un attribut privé (`getNom()`)
- **Setter** : modifie la valeur d'un attribut privé (`setNom(String nom)`)

Permettent de contrôler l'accès aux données (encapsulation), contrairement à des attributs publics.

### `toString()`

Méthode héritée de la classe `Object`, à **redéfinir** (`@Override`) pour afficher un objet de façon lisible plutôt que son adresse mémoire.

```java
@Override
public String toString() {
    return nom + " " + prenom;
}
```

### Composition

Une classe peut **contenir** d'autres objets plutôt que d'avoir tous les attributs à plat. `Stage` contient un `Etudiant` et une `Entreprise` plutôt que 13 attributs mélangés.

```java
public class Stage {
    private Etudiant etudiant;
    private Entreprise entreprise;
    // ...
}
```

Avantages : chaque classe a une seule responsabilité (principe SRP), réutilisation facilitée, code plus lisible.

### Enum

Type qui définit un ensemble **fixe et limité** de valeurs possibles. Empêche les erreurs de saisie (fautes de frappe, casse) qu'on aurait avec un `String` libre.

```java
public enum StatutStage {
    CANDIDATURE,
    EN_COURS,
    TERMINE,
    REFUSE
}
```

Avantages : sécurité à la compilation, autocomplétion IDE, comparaison directe avec `==`, `switch` propre.

---

## 2. Les Collections — `List` / `ArrayList`

### `List` (interface) vs `ArrayList` (implémentation)

`List` définit le comportement d'une liste ordonnée. `ArrayList` est l'implémentation concrète la plus utilisée.

```java
List<Stage> stages = new ArrayList<>();
```

On déclare avec l'interface (`List`), on instancie avec l'implémentation (`ArrayList`) — bonne pratique dite "programmer contre une interface".

### Méthodes principales

| Méthode | Rôle |
|---|---|
| `.add(element)` | Ajoute un élément à la fin |
| `.remove(element)` | Retire un élément précis |
| `.get(index)` | Récupère l'élément à une position donnée |
| `.size()` | Nombre d'éléments |
| `for (Type x : liste) { }` | Parcourt tous les éléments (boucle for-each) |

Le `<Stage>` est un **générique** : il garantit que la liste ne contient que des objets `Stage`.

---

## 3. Structures de contrôle

### `while`

Répète un bloc tant qu'une condition est vraie.

```java
while (choix != 0) {
    // ...
}
```

### `switch` (syntaxe moderne avec `->`)

Exécute un bloc différent selon la valeur d'une variable, plus lisible qu'une chaîne de `if/else if`.

```java
switch (jour) {
    case 1 -> System.out.println("Lundi");
    case 2 -> System.out.println("Mardi");
    default -> System.out.println("Invalide");
}
```

Avec la syntaxe moderne (`->`), pas de risque d'oublier un `break` (contrairement à l'ancienne syntaxe `case:`/`break;`).

---

## 4. `Scanner` et la saisie utilisateur

### Le piège `nextInt()` suivi de `nextLine()`

`nextInt()` ne consomme pas le retour à la ligne (`\n`) laissé après la saisie. Le `nextLine()` suivant le lit immédiatement (chaîne vide), sans laisser taper l'utilisateur.

**Solution** : ajouter un `scanner.nextLine();` "à vide" juste après tout `nextInt()`, avant le prochain `nextLine()` utile.

```java
int age = scanner.nextInt();
scanner.nextLine(); // consomme le \n restant
String nom = scanner.nextLine(); // fonctionne correctement
```

### Bonnes pratiques

- `scanner.close()` doit être appelé **une seule fois**, à la toute fin du programme, **en dehors** de toute boucle.
- Toujours comparer des `String` avec `.equals()`, jamais `==` (qui compare les références mémoire, pas le contenu).

---

## 5. Lecture / écriture de fichiers (CSV)

### Écriture

```java
try (FileWriter writer = new FileWriter("fichier.csv")) {
    writer.write("valeur1;valeur2\n");
} catch (IOException e) {
    System.out.println("Erreur : " + e.getMessage());
}
```

### Lecture

```java
try (BufferedReader reader = new BufferedReader(new FileReader("fichier.csv"))) {
    String ligne;
    while ((ligne = reader.readLine()) != null) {
        String[] champs = ligne.split(";");
        // reconstruire les objets à partir de champs[0], champs[1]...
    }
} catch (IOException e) {
    System.out.println("Erreur : " + e.getMessage());
}
```

### Concepts clés

- **`try-with-resources`** (`try (...) { }`) : ferme automatiquement le fichier à la fin du bloc, même en cas d'erreur.
- **`catch (IOException e)`** : Java oblige à gérer les erreurs possibles sur les fichiers (fichier introuvable, disque plein...).
- **`.split(";")`** : découpe une ligne de texte en tableau de `String`, selon un séparateur.

---

# PARTIE 2 — Spring Boot & JPA (backend REST)

## 6. Architecture en couches

```
Requête HTTP (Vue, Postman, curl...)
        ↓
[CONTRÔLEUR / API REST]   ← reçoit les requêtes HTTP
        ↓
[SERVICE]                  ← logique métier (optionnel)
        ↓
[REPOSITORY]                ← parle à la base de données
        ↓
Base de données PostgreSQL
```

Chaque couche a une responsabilité unique : le repository ne connaît rien à HTTP, le contrôleur ne connaît rien au SQL.

---

## 7. Les entités JPA

Une **entité** est une classe Java annotée `@Entity`, mappée automatiquement à une table par Hibernate.

```java
@Entity
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nom_entreprise")
    private String nomEntreprise;

    // constructeur vide (requis par JPA), constructeur avec paramètres,
    // getters/setters (aucun setter sur id), toString()
}
```

### Annotations clés

| Annotation | Rôle |
|---|---|
| `@Entity` | Classe mappée à une table |
| `@Table(name = "...")` | Nom exact de la table |
| `@Id` | Clé primaire |
| `@GeneratedValue(strategy = GenerationType.UUID)` | Génération automatique de l'id (stratégie moderne Hibernate 6+) |
| `@Column(name = "...")` | Fait correspondre un champ Java (camelCase) à une colonne SQL (snake_case) |
| `@Enumerated(EnumType.STRING)` | Stocke un enum Java sous forme de texte |
| `@ManyToOne` | Relation "plusieurs vers un" (ex : plusieurs tuteurs → une organisation) |
| `@JoinColumn(name = "...")` | Nom de la colonne de clé étrangère associée à une relation |

### Pourquoi pas de setter sur `id` ?

Une clé primaire doit rester **immuable** après création : la modifier casserait l'intégrité des relations et l'identité de la ligne.

### Cas particulier : ENUM PostgreSQL personnalisé

Un type `ENUM` créé côté PostgreSQL (`CREATE TYPE ... AS ENUM (...)`) nécessite une annotation supplémentaire pour qu'Hibernate génère le bon cast SQL :

```java
@Enumerated(EnumType.STRING)
@JdbcTypeCode(SqlTypes.NAMED_ENUM)
@Column(name = "statut_stage")
private StatutStage statut;
```

Sans `@JdbcTypeCode(SqlTypes.NAMED_ENUM)`, PostgreSQL refuse l'insertion avec l'erreur *"column is of type xxx_enum but expression is of type character varying"*.

---

## 8. Les Repositories

Une interface qui hérite de `JpaRepository<Entité, TypeDeLId>` :

```java
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
}
```

### Pourquoi une interface vide suffit

Spring Data JPA génère, au démarrage, une implémentation concrète en mémoire (un **proxy dynamique**), à partir du contrat défini par l'interface. Aucune implémentation à écrire à la main.

### Méthodes fournies automatiquement

`save()`, `findById()`, `findAll()`, `deleteById()`, `existsById()`, `count()`...

### Méthodes personnalisées "magiques"

Spring peut générer une requête à partir du **nom** d'une méthode :
```java
List<Organisation> findByNomEntreprise(String nomEntreprise);
```

---

## 9. Les Contrôleurs REST

### Annotations clés

| Annotation | Rôle |
|---|---|
| `@RestController` | La classe expose des endpoints HTTP retournant du JSON |
| `@RequestMapping("/api/xxx")` | Préfixe d'URL commun à toute la classe |
| `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` | Route associée à une méthode HTTP |
| `@PathVariable` | Récupère une valeur depuis l'URL (`/api/xxx/{id}`) |
| `@RequestBody` | Convertit le JSON du corps de la requête en objet Java |

### Injection de dépendances

Le contrôleur reçoit son (ses) repository(ies) via le **constructeur** ; Spring les injecte automatiquement.

```java
private final OrganisationRepository organisationRepository;

public OrganisationController(OrganisationRepository organisationRepository) {
    this.organisationRepository = organisationRepository;
}
```

Le mot-clé `final` garantit qu'une fois assigné (dans le constructeur), ce champ ne peut plus jamais être réassigné — cohérent avec le fait qu'une dépendance injectée ne doit pas changer pendant la vie de l'objet.

### Le pattern CRUD complet

```java
@GetMapping
public List<Organisation> getAll() {
    return organisationRepository.findAll();
}

@GetMapping("/{id}")
public ResponseEntity<Organisation> getById(@PathVariable UUID id) {
    return organisationRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}

@PostMapping
public Organisation create(@RequestBody Organisation organisation) {
    return organisationRepository.save(organisation);
}

@PutMapping("/{id}")
public ResponseEntity<Organisation> update(@PathVariable UUID id, @RequestBody Organisation organisation) {
    return organisationRepository.findById(id)
        .map(existing -> {
            existing.setNomEntreprise(organisation.getNomEntreprise());
            // ... autres champs
            return ResponseEntity.ok(organisationRepository.save(existing));
        })
        .orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
public ResponseEntity<String> delete(@PathVariable UUID id) {
    if (!organisationRepository.existsById(id)) {
        return ResponseEntity.status(404).body("Introuvable.");
    }
    organisationRepository.deleteById(id);
    return ResponseEntity.ok("Supprimé avec succès.");
}
```

### `Optional` et le pattern `.map().orElse()`

`findById()` renvoie un `Optional<T>` (une "boîte" qui peut contenir une valeur ou être vide).
- `.map(...)` s'exécute **seulement si** une valeur est présente, et la transforme.
- `.orElse(...)` fournit une valeur de repli si l'`Optional` est vide.

Évite les `if (present) {...} else {...}` explicites.

### `ResponseEntity<T>`

Permet de contrôler explicitement le **code de statut HTTP** (200, 201, 400, 404...) et le corps de la réponse. Sans lui, Spring renvoie toujours 200 par défaut, même en cas d'échec logique — ce qui empêche un frontend de distinguer un succès d'un échec.

---

## 10. Gérer les relations entre entités dans l'API

### En lecture (GET)

Jackson (la librairie JSON de Spring) sérialise l'objet lié **complet** par défaut (ex : le `Tuteur` renvoyé inclut son `organisation` entière).

### En écriture (POST/PUT)

Le client n'envoie généralement que l'**id** de l'entité liée :
```json
{"nomTuteur": "Dupont", "organisation": {"id": "..."}}
```

Le contrôleur doit alors **récupérer la vraie entité complète** depuis son repository, avant de l'associer et de sauvegarder — ne jamais faire confiance à l'objet partiel reçu tel quel.

```java
@PostMapping
public ResponseEntity<Tuteur> createTuteur(@RequestBody Tuteur tuteur) {
    UUID organisationId = tuteur.getOrganisation().getId();
    return organisationRepository.findById(organisationId)
        .map(organisation -> {
            tuteur.setOrganisation(organisation); // remplace l'objet partiel par le vrai
            return ResponseEntity.ok(tuteurRepository.save(tuteur));
        })
        .orElse(ResponseEntity.badRequest().build());
}
```

Avec **plusieurs** relations (ex : `Stage` → organisation, tuteur, stagiaire), on répète ce principe pour chacune, plutôt que d'imbriquer plusieurs `.map()` (peu lisible) :

```java
Organisation organisation = organisationRepository.findById(stage.getOrganisation().getId()).orElse(null);
Tuteur tuteur = tuteurRepository.findById(stage.getTuteur().getId()).orElse(null);
Stagiaire stagiaire = stagiaireRepository.findById(stage.getStagiaire().getId()).orElse(null);

if (organisation == null || tuteur == null || stagiaire == null) {
    return ResponseEntity.badRequest().build();
}

stage.setOrganisation(organisation);
stage.setTuteur(tuteur);
stage.setStagiaire(stagiaire);
```

### Attention au type primitif `int` avec des champs optionnels

Si un objet lié n'est envoyé que partiellement (juste son `id`), les champs `String` non fournis deviennent `null` (sans problème), mais un champ `int` **primitif** ne peut jamais être `null` → erreur de désérialisation JSON (`Cannot map null into type int`). Préférer `Integer` (objet, accepte `null`) pour les champs numériques d'une entité qui peut être reçue partiellement.

---

## 11. Configuration Spring Boot ↔ PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestionstage
spring.datasource.username=postgres
spring.datasource.password=...

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

`ddl-auto=validate` : Hibernate **vérifie** que les entités correspondent au schéma existant, sans jamais le modifier — approprié quand le schéma SQL a été conçu et testé à la main au préalable (CLI), pour ne pas laisser Hibernate l'altérer automatiquement.

---

# PARTIE 3 — Vue.js (frontend)

## 12. Principe général d'une SPA (Single Page Application)

Une seule page HTML (`index.html`) sert de coquille ; Vue Router change dynamiquement le contenu affiché selon l'URL, **sans recharger la page**. Le point de montage est `<div id="app">`, rempli par `app.mount('#app')` dans `main.js`.

## 13. Anatomie d'un fichier `.vue`

```vue
<script setup>
  // logique : imports, données, fonctions
</script>

<template>
  <!-- HTML affiché -->
</template>

<style scoped>
  /* CSS propre à CE composant */
</style>
```

`<script setup>` est la syntaxe **Composition API** moderne. `scoped` isole le CSS à ce composant uniquement (Vue ajoute des attributs uniques en coulisses).

## 14. Réactivité — `ref()`

Rend une variable réactive : Vue met automatiquement à jour l'affichage partout où elle est utilisée, dès qu'elle change.

```js
const organisations = ref([])
```

**Règle importante** : dans le `<script>`, on lit/modifie toujours via `.value` (`organisations.value = data`). Dans le `<template>`, Vue enlève automatiquement ce besoin (`{{ organisation.nom }}`, pas `.value`).

## 15. `onMounted()` — hook de cycle de vie

Exécute du code juste après que le composant soit affiché à l'écran. Bon endroit pour aller chercher des données via l'API.

```js
onMounted(() => {
  chargerDonnees()
})
```

## 16. Afficher une liste — `v-for` + `:key`

```html
<li v-for="organisation in organisations" :key="organisation.id">
  {{ organisation.nomEntreprise }}
</li>
```

`:key` est **obligatoire** avec `v-for` : identifiant unique par élément, permet à Vue de détecter efficacement les ajouts/suppressions.

## 17. Formulaires — `v-model`

Liaison **bidirectionnelle** entre un champ (`<input>`, `<select>`) et une variable réactive.

```html
<input v-model="nomEntreprise" placeholder="Nom de l'entreprise" />
<select v-model="statut">
  <option value="CANDIDATURE">Candidature</option>
</select>
```

## 18. Réagir aux événements — `@click`, `@submit.prevent`

```html
<button @click="supprimerOrganisation(organisation.id)">Supprimer</button>
<form @submit.prevent="soumettreFormulaire">...</form>
```

`.prevent` empêche le comportement HTML par défaut (rechargement de page au submit).

## 19. Appeler l'API — `fetch()`

```js
// Lecture
const response = await fetch('http://localhost:8080/api/organisations')
const data = await response.json()

// Création
await fetch('http://localhost:8080/api/organisations', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(donnees)
})

// Suppression
await fetch(`http://localhost:8080/api/organisations/${id}`, { method: 'DELETE' })
```

`JSON.stringify()` convertit un objet JS en texte JSON (envoi) ; `.json()` fait l'inverse (réception).

## 20. Composants réutilisables — `props` et `emit`

**`props`** : données transmises du parent vers l'enfant (sens unique, haut → bas).
**`emit`** : événement personnalisé remonté de l'enfant vers le parent (bas → haut).

```js
// Dans le composant enfant (ex: OrganisationForm.vue)
const props = defineProps({
  organisationAModifier: { type: Object, default: null }
})
const emit = defineEmits(['created', 'updated'])
// ...
emit('created')
```

```html
<!-- Dans le parent -->
<OrganisationForm
  :organisation-a-modifier="organisationEnEdition"
  @created="onOrganisationCreee"
/>
```

Note de syntaxe : camelCase dans le `<script>` (`organisationAModifier`), kebab-case (tirets) dans le `<template>` (`organisation-a-modifier`) — convention Vue, pas une erreur.

### Pattern formulaire réutilisable (création ET édition)

Un même composant sert aux deux cas grâce à une prop : si elle contient un objet, mode édition (PUT) ; sinon, mode création (POST).

```js
async function soumettreFormulaire() {
  const donnees = { /* ... */ }
  if (props.entiteAModifier) {
    await fetch(`.../${props.entiteAModifier.id}`, { method: 'PUT', ... })
    emit('updated')
  } else {
    await fetch('...', { method: 'POST', ... })
    emit('created')
  }
}
```

## 21. Observer une prop — `watch()`

Exécute du code à chaque fois qu'une valeur réactive (ou une prop) change. Utilisé pour pré-remplir un formulaire quand la donnée à éditer change.

```js
watch(() => props.organisationAModifier, (organisation) => {
  if (organisation) {
    nomEntreprise.value = organisation.nomEntreprise
    // ...
  } else {
    nomEntreprise.value = ''
    // reset
  }
})
```

## 22. `<select>` dynamique (rempli depuis l'API) vs statique (valeurs fixes)

**Dynamique** (ex : choisir une organisation) :
```html
<select v-model="organisationId">
  <option v-for="organisation in organisations" :key="organisation.id" :value="organisation.id">
    {{ organisation.nomEntreprise }}
  </option>
</select>
```
Nécessite de charger la liste des options via un `fetch` séparé (`onMounted`).

**Statique** (ex : un enum comme le statut) : les `<option>` sont codées en dur, pas besoin de fetch.

### Gérer une relation dans le formulaire

Le `<select>` stocke un **id simple** (`organisationId`, une String), mais l'API attend un **objet** avec cet id :
```js
organisation: { id: organisationId.value }
```
Et pour pré-remplir en édition, on extrait l'id depuis l'objet complet renvoyé par l'API :
```js
organisationId.value = tuteur.organisation.id
```

## 23. Propriété calculée — `computed()`

Une valeur dérivée d'autres données réactives, **recalculée automatiquement** à chaque changement d'une dépendance. Idéal pour du filtrage/recherche en mémoire.

```js
const stagesFiltres = computed(() => {
  return stages.value.filter(stage => {
    const matchStatut = !filtreStatut.value || stage.statut === filtreStatut.value
    const matchNom = !filtreNom.value ||
      stage.stagiaire.nom.toLowerCase().includes(filtreNom.value.toLowerCase())
    return matchStatut && matchNom
  })
})
```

Aucun bouton "Rechercher" nécessaire : dès qu'un `v-model` de filtre change, Vue recalcule et réaffiche automatiquement.

## 24. Gestion des dates (LocalDateTime backend ↔ input date frontend)

Le backend stocke un `LocalDateTime` (`"2025-06-01T00:00:00"`), mais `<input type="date">` n'accepte que `"2025-06-01"`.

**À l'envoi** (ajouter l'heure) :
```js
dateDebut: dateDebut.value + 'T00:00:00'
```

**À la réception / pré-remplissage** (retirer l'heure) :
```js
dateDebut.value = stage.dateDebut.split('T')[0]
```

## 25. CORS — communication entre deux origines

Le frontend (`localhost:5173`) et le backend (`localhost:8080`) sont deux **origines différentes** pour le navigateur, qui bloque par défaut les requêtes entre elles (politique CORS). Solution côté Spring Boot :

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
```
Attention : `localhost` et `127.0.0.1` sont deux origines distinctes pour le navigateur, il faut autoriser les deux si besoin.

## 26. Architecture CSS du projet

**Variables globales** (`src/assets/main.css`, non scoped → s'applique partout) :
```css
:root {
  --bg: #FAFAF8;
  --surface: #FFFFFF;
  --border: #E2E0DA;
  --text: #2A2A28;
  --text-muted: #6B6963;
  --accent: #3D5A4C;
  --danger: #B3452C;
}
```

**Styles globaux** pour les éléments répétés partout (inputs, boutons, selects) → dans `main.css`, pas dans chaque composant, pour éviter la duplication.

**Styles spécifiques** à une vue (mise en page d'une liste précise) → dans le `<style scoped>` du composant concerné.

**Limite du `<select>` natif** : le panneau déroulant ouvert est en grande partie stylé par le navigateur/OS, pas totalement personnalisable en CSS pur. Pour un contrôle visuel complet, il faudrait un composant dropdown "maison" (div cliquable + liste stylée), plus complexe à construire.

---

## Glossaire rapide

| Terme | Définition |
|---|---|
| **Encapsulation** | Rendre les attributs privés, y accéder via getters/setters |
| **Composition** | Une classe contient des objets d'autres classes plutôt que tous leurs attributs à plat |
| **Interface** | Contrat de méthodes sans implémentation ; une classe (ou un proxy généré) doit le respecter |
| **Proxy dynamique** | Classe générée automatiquement par Spring en mémoire, à partir d'une interface |
| **Injection de dépendances** | Spring fournit automatiquement les objets dont une classe a besoin (via le constructeur) |
| **ORM** | Object-Relational Mapping — correspondance automatique entre objets Java et lignes SQL (ex : Hibernate) |
| **Endpoint** | Une URL précise exposée par une API (ex : `GET /api/organisations`) |
| **SPA** | Single Page Application — une seule page HTML, contenu changé dynamiquement en JS |
| **Composant** | Bloc réutilisable d'interface (HTML + logique + style), fichier `.vue` |
| **Réactivité** | Mécanisme Vue qui met à jour l'affichage automatiquement quand une donnée change |
| **Props** | Données transmises d'un composant parent vers un composant enfant |
| **Emit / événement personnalisé** | Signal envoyé d'un composant enfant vers son parent |
| **Computed** | Valeur dérivée d'autres données réactives, recalculée automatiquement |
| **CORS** | Politique de sécurité navigateur qui restreint les requêtes entre origines différentes |
| **Origine (origin)** | Protocole + domaine + port (`localhost:5173` ≠ `localhost:8080` ≠ `127.0.0.1:5173`) |


---

# ÉTAT D'AVANCEMENT DU PROJET — Gestionnaire de stages

## ✅ Fait

### Phase 1 — Application console Java
- Modélisation (`Etudiant`, `Entreprise`, `Stage`, `StatutStage`) avec composition et encapsulation
- Gestionnaire en mémoire (CRUD + recherches) et menu interactif (`Scanner`, `while`/`switch`)
- Persistance en fichier CSV (sauvegarde/chargement)

### Phase 2 — Base de données PostgreSQL
- Schéma créé et testé en CLI : 4 tables (`organisation`, `stagiaire`, `tuteur`, `stage`), contraintes de clés étrangères (`ON DELETE RESTRICT`), type `ENUM` personnalisé
- Script SQL complet généré (`schema_gestionstage.sql`)

### Phase 3 — Backend Spring Boot
- Projet Maven généré (Spring Web, Spring Data JPA, PostgreSQL Driver), connexion validée (`ddl-auto=validate`)
- 4 entités JPA (`@Entity`, `@ManyToOne`, `@Enumerated` + `@JdbcTypeCode(SqlTypes.NAMED_ENUM)` pour le cast ENUM PostgreSQL)
- 4 repositories Spring Data JPA
- 4 contrôleurs REST (CRUD complet, 20 routes), testés avec `curl`
- Couche Service sur les 4 entités (logique métier isolée, résolution des relations)
- Gestion d'erreurs centralisée (`RessourceNonTrouveeException` + `GlobalExceptionHandler`) : 404 propres au lieu de 500 bruts
- CORS configuré pour autoriser le frontend (`localhost` et `127.0.0.1` sur le port 5173)
- **Architecture en 3 couches validée : Controller → Service → Repository**

### Phase 4 — Frontend Vue.js
- Projet Vue créé (Vue Router, ESLint/Prettier, sans TypeScript/Pinia)
- CRUD complet sur les 4 entités : `Organisation`, `Stagiaire`, `Tuteur`, `Stage`
  - Listes chargées via `fetch` + `onMounted`
  - Formulaires réutilisables création/modification (`props` / `emit` / `watch`)
  - Relations gérées via `<select>` dynamiques (Tuteur → Organisation ; Stage → Organisation, Tuteur, Stagiaire)
  - Gestion du statut (enum) et des dates (`LocalDateTime` ↔ `input type="date"`)
- Recherche/filtrage sur la page Stages (statut, nom d'étudiant, nom d'entreprise, date de début) via `computed`, combinables et réactifs en temps réel
- **MVP complet** : afficher, ajouter, supprimer, modifier, rechercher

### Phase 5 — Polish visuel
- Nettoyage du code de démo Vue (composants d'exemple, icônes, logo)
- Palette de couleurs centralisée via variables CSS (`:root`)
- Typographie d'affichage pour les titres (Fraunces) + police système pour le corps
- Navbar unique dans `App.vue`, avec indicateur de page active
- Listes en cartes avec bordure d'accent, effet de survol, hiérarchie nom (gras) / détails (grisé)
- Badges de statut colorés sur les stages (Candidature, En cours, Terminé, Refusé)
- Styles globaux des `input` / `select` / `button` dans `main.css` (focus visible, bouton principal en vert plein, bouton Supprimer en rouge)
- Pages Accueil et À propos avec contenu réel

## 🐛 Pièges rencontrés et résolus (à retenir)

| Problème | Cause | Solution |
|---|---|---|
| Erreur de package / `main.java` | Racine des sources mal reconnue par VS Code | Structure Maven standard, cache `redhat.java` supprimé |
| `Cannot map null into type int` | Champ `int` primitif reçu partiellement en JSON | Passer à `Integer` |
| `column is of type ..._enum but expression is of type character varying` | Hibernate envoie un `VARCHAR` à un ENUM PostgreSQL | `@JdbcTypeCode(SqlTypes.NAMED_ENUM)` |
| Erreur CORS | `localhost:5173` / `127.0.0.1:5173` ≠ `localhost:8080` | `CorsConfig` avec les deux origines |
| `Bus error` au lancement de Vite | `node_modules` corrompu | Suppression + `npm install` propre |
| Rien ne se passe au clic | Faute de frappe entre le nom de fonction du `<script>` et du `<template>` | Vérifier les noms caractère par caractère |
| Statut `TERMINE` refusé (400) | Enum Java `TERMINER` ≠ enum PostgreSQL `TERMINE` | Aligner strictement les deux enums |
| `Cannot infer type ... map` | Variable `updateX` (nom de méthode) utilisée au lieu de la variable locale | Utiliser la variable retournée par `save()` |

## ⬜ À venir

**Étape 1 (bonus) — Sécurité**
Ajouter Spring Security si le projet doit un jour être accessible au-delà d'un usage strictement personnel/local.

**Étape 2 (bonus) — Dashboard/statistiques**
Vue d'ensemble visuelle des stages (par statut, par période...), évoquée dans les bonus de l'expression de besoin initiale.

**Étape 3 (bonus) — Missions et compétences**
Ajouter et afficher les missions effectuées et les compétences acquises, également listées dans les bonus de l'expression de besoin.

**Étape 4 (optionnelle) — Dropdown personnalisé**
Remplacer le `<select>` natif par un composant maison si un contrôle visuel complet du panneau déroulant devient nécessaire.
