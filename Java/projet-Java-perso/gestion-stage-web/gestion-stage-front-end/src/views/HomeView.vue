<script setup>
</script>

<template>
  <div class="home">
    <h1>Gestionnaire de stages</h1>
    <p>Suivez vos stages, entreprises, tuteurs et stagiaires en un seul endroit.</p>
  </div>
</template>
<style scoped>
.home h1 {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.home p {
  color: var(--text-muted);
  margin-bottom: 2rem;
}

.home-links {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.home-link {
  padding: 0.75rem 1.25rem;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: var(--surface);
  font-weight: 500;
}

.home-link:hover {
  border-color: var(--accent);
  color: var(--accent);
}
</style>