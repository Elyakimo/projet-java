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