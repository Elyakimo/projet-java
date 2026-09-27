<template>
  <div class="about">
    <h1>À propos</h1>
    <p>
      Ce gestionnaire de stages a été conçu pour organiser et centraliser le suivi
      des stages : entreprises d'accueil, tuteurs, stagiaires et informations liées
      à chaque période de stage.
    </p>
    <p>
      Le projet s'appuie sur une base de données PostgreSQL, un backend en
      Spring Boot exposant une API REST, et ce frontend développé avec Vue.js.
    </p>
  </div>
</template>

<style scoped>
.about {
  max-width: 600px;
}

.about h1 {
  font-size: 1.75rem;
  margin-bottom: 1rem;
}

.about p {
  color: var(--text-muted);
  line-height: 1.6;
  margin-bottom: 1rem;
}
</style>