<script setup>
    import { ref, onMounted, watch } from 'vue'

    const props = defineProps({
        stageAModifier: {
            type: Object,
            default: null
        }
    })

    const emit = defineEmits(['created', 'updated'])

    const statut = ref('')
    const dateDebut = ref('')
    const dateFin = ref('')
    const organisationId = ref('')
    const tuteurId = ref('')
    const stagiaireId = ref('')
    const organisations = ref([])
    const tuteurs = ref([])
    const stagiaires = ref([])

    async function chargerOrganisation() {
        const response = await fetch('http://localhost:8080/api/organisations')
        organisations.value = await response.json()
    }

    async function chargerStagiaire(){
        const response = await fetch('http://localhost:8080/api/stagiaires')
        stagiaires.value = await response.json()
    }

    async function chargerTuteur(){
        const response = await fetch('http://localhost:8080/api/tuteurs')
        tuteurs.value = await response.json()
    }

    onMounted(() => {
        chargerOrganisation(),
        chargerStagiaire(),
        chargerTuteur()
    })
    
    watch(() => props.stageAModifier, (stage) => {
        if (stage){
            statut.value = stage.statut
            dateDebut.value = stage.dateDebut.split('T')[0]
            dateFin.value = stage.dateFin.split('T')[0]
            organisationId.value = stage.organisation.id
            tuteurId.value = stage.tuteur.id 
            stagiaireId.value = stage.stagiaire.id
        } else {
            statut.value = ''
            dateDebut.value = ''
            dateFin.value = ''
            organisationId.value = ''
            tuteurId.value = ''
            stagiaireId.value = ''
        }
    })

    async function soumettreFormulaire() {
        const donnees = {
            statut: statut.value,
            dateDebut: dateDebut.value + 'T00:00:00',
            dateFin: dateFin.value + 'T00:00:00',
            organisation: { id: organisationId.value },
            tuteur: { id: tuteurId.value },
            stagiaire: { id: stagiaireId.value }
        }
    

    if (props.stageAModifier){
        await fetch(`http://localhost:8080/api/stages/${props.stageAModifier.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(donnees)
        })
        emit('updated')
    } else {
        await fetch('http://localhost:8080/api/stages', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(donnees)
    })
        emit('created')    
    }
    statut.value = ''
    dateDebut.value = ''
    dateFin.value = ''
    organisationId.value = ''
    tuteurId.value = ''
    stagiaireId.value = ''
}
</script>

<template>
    <form @submit.prevent="soumettreFormulaire">
        <select v-model="statut" required>
            <option value="" disabled>Choisir un statut</option>
            <option value="CANDIDATURE">Candidature</option>
            <option value="EN_COURS">En cours</option>
            <option value="TERMINE">Terminé</option>
            <option value="REFUSE">Refusé</option>
        </select>
        <input v-model="dateDebut" type="date" required/>
        <input v-model="dateFin" type="date" required />
        <select v-model="organisationId" required>
            <option value="" disabled>Choisir une organisation</option>
            <option v-for="organisation in organisations" :key="organisation.id" :value="organisation.id">
                {{ organisation.nomEntreprise }}
            </option>
        </select>
    <select v-model="tuteurId" required>
      <option value="" disabled>Choisir un tuteur</option>
      <option v-for="tuteur in tuteurs" :key="tuteur.id" :value="tuteur.id">
        {{tuteur.nomTuteur }} — {{ tuteur.prenomTuteur }}
      </option>
    </select>
    <select v-model="stagiaireId" required>
      <option value="" disabled>Choisir un stagiaire</option>
      <option v-for="stagiaire in stagiaires" :key="stagiaire.id" :value="stagiaire.id">
        {{stagiaire.nom }} — {{ stagiaire.prenom }}
      </option>
    </select>
    <button type="submit">{{ stageAModifier ? 'Modifier' : 'Créer' }}</button>
    </form>
</template>