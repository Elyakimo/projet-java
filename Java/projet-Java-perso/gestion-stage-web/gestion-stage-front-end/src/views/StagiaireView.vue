<script setup>
import StagiaireForm from '@/components/StagiaireForm.vue'
import { ref, onMounted } from 'vue'

const stagiaireEnEdition = ref(null)
const stagiaires = ref([])

async function chargerStagiaires(){
    const response = await fetch('http://localhost:8080/api/stagiaires')
    const data = await response.json()
    stagiaires.value = data
    
}
async function supprimerStagiaire(id) {
    await fetch(`http://localhost:8080/api/stagiaires/${id}`,{
        method: 'DELETE'
    })
    await chargerStagiaires()
}

function modifierStagiaire(stagiaire) {
    stagiaireEnEdition.value = stagiaire
}

async function onStagiaireCreee() {
  await chargerStagiaires()
}

async function onStagiaireModifiee() {
    stagiaireEnEdition.value = null
    await chargerStagiaires()
}

onMounted(() => {
    chargerStagiaires()
})
</script>

<template>
  <div>
    <h1>Liste des stagiaires</h1>

    <StagiaireForm :stagiaire-a-modifier="stagiaireEnEdition"
      @created="onStagiaireCreee"
      @updated="onStagiaireModifiee"
    />

    <ul>
      <li v-for="stagiaire in stagiaires" :key="stagiaire.id">
        <span class="infos">
          <strong>{{ stagiaire.nom }} {{ stagiaire.prenom }}</strong>
          <span class="detail">{{ stagiaire.mailEtudiant }} — {{ stagiaire.classe }}</span>
        </span>
        <button @click="modifierStagiaire(stagiaire)">Modifier</button>
        <button @click="supprimerStagiaire(stagiaire.id)">Supprimer</button>
      </li>
    </ul>
  </div>
</template>
<style scoped>
h1 {
  font-size: 1.75rem;
  margin-bottom: 1.5rem;
}

ul {
  list-style: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: var(--surface);
  border: 1px solid var(--border);
  border-left: 3px solid var(--accent);
  border-radius: 6px;
  padding: 0.75rem 1rem;
  transition: box-shadow 0.15s ease, transform 0.15s ease;
}

li:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transform: translateY(-1px);
}
li button {
  margin-left: 0.5rem;
  padding: 0.4rem 0.8rem;
  border: 1px solid var(--border);
  border-radius: 4px;
  background: var(--surface);
}

li button:hover {
  background: var(--bg);
}

li button:last-child {
  color: var(--danger);
  border-color: var(--danger);
}
form,
.filtres {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

form input,
form select,
.filtres input,
.filtres select {
  flex: 1;
  min-width: 140px;
  padding: 0.5rem 0.75rem;
  border: 1px solid var(--border);
  border-radius: 4px;
  background: var(--surface);
  font-size: 0.9rem;
}

form button {
  padding: 0.5rem 1.25rem;
  border: none;
  border-radius: 4px;
  background: var(--accent);
  color: white;
  font-weight: 500;
}

form button:hover {
  background: var(--accent-hover);
}
li button {
  min-width: 100px;
  text-align: center;
}
.infos {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.infos strong {
  font-weight: 600;
}

.infos .detail {
  font-size: 0.85rem;
  color: var(--text-muted);
}
</style>