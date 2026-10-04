<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">

      <nav>
        <RouterLink to="/">Acceuil</RouterLink>
        <RouterLink to="/organisations">Organisations</RouterLink>
        <RouterLink to="/stagiaires">Stagiaires</RouterLink>
        <RouterLink to="/tuteurs">Tuteurs</RouterLink>
        <RouterLink to="/stages">Stages</RouterLink>
        <RouterLink to="/about">À propos</RouterLink>
        <RouterLink to="/login">Connexion</RouterLink>

      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  border-bottom: 1px solid var(--border);
  margin-bottom: 2rem;
  padding-bottom: 1rem;
}

nav {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
}

nav a {
  font-weight: 500;
  padding: 0.25rem 0;
  border-bottom: 2px solid transparent;
}

nav a.router-link-exact-active {
  color: var(--accent);
  border-bottom-color: var(--accent);
}
</style>
