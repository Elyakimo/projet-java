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
        {{ stagiaire.nom }} — {{ stagiaire.prenom }} — {{ stagiaire.classe }} — {{ stagiaire.mailEtudiant }}
        <button @click="modifierStagiaire(stagiaire)">Modifier</button>
        <button @click="supprimerStagiaire(stagiaire.id)">Supprimer</button>
      </li>
    </ul>
  </div>
</template>