<script setup>
import { ref, onMounted } from 'vue'
import { apiFetch } from '@/stores/auth'

const tousLesStagiaires = ref([])
const idsSuivis = ref(new Set())

async function chargerDonnees() {
  const [reponseTous, reponseSuivis] = await Promise.all([
    apiFetch('http://localhost:8080/api/stagiaires'),
    apiFetch('http://localhost:8080/api/professeur/etudiants-suivis')
  ])

  tousLesStagiaires.value = await reponseTous.json()
  const suivis = await reponseSuivis.json()
  idsSuivis.value = new Set(suivis.map(s => s.id))
}

async function basculerSuivi(stagiaire) {
  if (idsSuivis.value.has(stagiaire.id)) {
    await apiFetch(`http://localhost:8080/api/professeur/etudiants-suivis/${stagiaire.id}`, {
      method: 'DELETE'
    })
    idsSuivis.value.delete(stagiaire.id)
  } else {
    await apiFetch(`http://localhost:8080/api/professeur/etudiants-suivis/${stagiaire.id}`, {
      method: 'POST'
    })
    idsSuivis.value.add(stagiaire.id)
  }
  idsSuivis.value = new Set(idsSuivis.value)
}

onMounted(() => {
  chargerDonnees()
})
</script>

<template>
  <div>
    <h1>Gérer mes étudiants suivis</h1>
    <ul>
      <li v-for="stagiaire in tousLesStagiaires" :key="stagiaire.id">
        <label>
          <input
            type="checkbox"
            :checked="idsSuivis.has(stagiaire.id)"
            @change="basculerSuivi(stagiaire)"
          />
          {{ stagiaire.nom }} {{ stagiaire.prenom }} — {{ stagiaire.classe }}
        </label>
      </li>
    </ul>
  </div>
</template>
<style scoped>
h1 {
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

label {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  font-size: 0.95rem;
}

input[type="checkbox"] {
  width: 1.15rem;
  height: 1.15rem;
  accent-color: var(--accent);
  cursor: pointer;
  flex-shrink: 0;
}
</style>