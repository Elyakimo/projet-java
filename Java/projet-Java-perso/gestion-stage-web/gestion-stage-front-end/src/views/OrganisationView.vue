<script setup>
import OrganisationForm from '@/components/OrganisationForm.vue'
import { ref, onMounted } from 'vue'

const organisationEnEdition = ref(null)
const organisations = ref([])

async function chargerOrganisations(){
    const response = await fetch('http://localhost:8080/api/organisations')
    const data = await response.json()
    organisations.value = data
    
}
async function supprimerOrganisation(id) {
    await fetch(`http://localhost:8080/api/organisations/${id}`,{
        method: 'DELETE'
    })
    await chargerOrganisations()
}

function modifierOrganisation(organisation) {
  organisationEnEdition.value = organisation
}

async function onOrganisationCreee() {
  await chargerOrganisations()
}

async function onOrganisationModifiee() {
  organisationEnEdition.value = null
  await chargerOrganisations()
}

onMounted(() => {
    chargerOrganisations()
})
</script>

<template>
  <div>
    <h1>Liste des organisations</h1>

    <OrganisationForm :organisation-a-modifier="organisationEnEdition"
      @created="onOrganisationCreee"
      @updated="onOrganisationModifiee"
    />

    <ul>
      <li v-for="organisation in organisations" :key="organisation.id">
        {{ organisation.nomEntreprise }} — {{ organisation.mailEntreprise }}
        <button @click="modifierOrganisation(organisation)">Modifier</button>
        <button @click="supprimerOrganisation(organisation.id)">Supprimer</button>
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
  border-radius: 6px;
  padding: 0.75rem 1rem;
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
</style>